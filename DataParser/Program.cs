using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using CommandLine;
using CampaignData;
using PCRE;
using System.Globalization;

namespace DataParser
{
    class Program
    {
        static void Main(string[] args)
        {
            CommandLine.Parser.Default.ParseArguments<Options>(args)
                .WithParsed<Options>(opts => RunOptionsAndReturnExitCode(opts))
                .WithNotParsed<Options>((errs) => HandleParseError(errs));

            if (args.Length == 0)
            {
                Console.WriteLine("Path required!\n");
                return;
            }


        }



        public static Monster getMonsterfromMarkup(string markup)
        {
            var monster = new Monster();
            var textInfo = new CultureInfo("en-US", false).TextInfo;

            //Regex headersRX = new Regex("^(\\w*):\\s\"?(.*)\"?/gm");


            var headersRGX = PcreRegex.Matches(markup, @"^(\w*):\s\""?([\w\d -\[\]\,\ \/]*)\""?", PcreOptions.MultiLine);

            Dictionary<string, string> headers = new Dictionary<string, string>();
            foreach(var rgx in headersRGX)
            {
                //A valid header
                if (rgx.CaptureCount == 2)
                {
                    headers.Add(rgx[1].ToString(), rgx[2].ToString());
                }
            }

            monster.Name = headers["title"];

            var cr_sourceRGX = PcreRegex.Matches(markup, @"cr(\d\/?\d?),\s+([\w-]+)");

            foreach(var rgx in cr_sourceRGX)
            {
                if (rgx.CaptureCount == 2)
                {
                    monster.Challenge = new Fraction(rgx[1].ToString());
                    monster.Source = textInfo.ToTitleCase(rgx[2].ToString().Replace('-', ' '));
                }
            }

            var infoRGX = PcreRegex.Matches(markup, @"^\*+(.*?)\*+(.*)", PcreOptions.MultiLine);

            int a = 0;

            List<String> properties = new List<string>();
            foreach(var rgx in infoRGX)
            {
                //First one is the type string
                if (a > 0)
                {

                }
                //switch (a)
                //{

                //}
                a++;
            }



            return monster;
        }

        public enum Position
        {
            Type,
            Armor,
            HP,
            Speed,
            DamageVulnerabilities,
            DamageResistances,
            DamageImmunities,
            ConditionImmunities,
            Saves,
            Skills,
            Challenge,
            Traits,
            Actions
        }
        public static void ParseMarkup(string inputpath,string outputpath)
        {
            Dictionary<string, List<Monster>> books = new Dictionary<string, List<Monster>>();

            Console.WriteLine("Parsing Markup from " + inputpath);

            if (Directory.Exists(inputpath))
            {
                foreach(var file in Directory.EnumerateFiles(inputpath,"*.markdown"))
                {
                    var data = File.ReadAllText(file);

                    var monster = getMonsterfromMarkup(data);
                    if(!books.ContainsKey(monster.Source))
                    {
                        books.Add(monster.Source, new List<Monster>());
                    }
                    books[monster.Source].Add(monster);
                }
                if (books.Count > 0)
                {
                    foreach(var book in books)
                    {
                        try
                        {
                            var json = Newtonsoft.Json.JsonConvert.SerializeObject(book.Value);
                            File.WriteAllText(Path.Combine(outputpath, book.Key + ".json"), json);
                        }
                        catch(Exception E)
                        {
                            Console.WriteLine(E.ToString());
                        }
                    }
                }
            }

        }

        public static void ParseJsonMonsters(string path)
        {
            if (Directory.Exists(path))
            {
                //Load up the download list
                JArray statblocks = (JArray)JsonConvert.DeserializeObject(File.ReadAllText(Path.Combine(path, "statblocks.json")));

                Dictionary<string, string> searchhints = new Dictionary<string, string>();
                Dictionary<string, object> monsters = new Dictionary<string, object>();

                List<string> sources = new List<string>();

                foreach (var block in statblocks)
                {
                    var name = block["Name"].ToString();
                    var id = block["Id"].ToString();
                    var searchHint = block["SearchHint"].ToString();
                    var link = block["Link"].ToString().Replace("/statblocks/", "");

                    searchhints.Add(name, searchHint);
                }

                var monstersjson = JsonConvert.DeserializeObject<List<CampaignData.Monster>>(File.ReadAllText(Path.Combine(path, "together.json")));

                var monstersjson2 = JsonConvert.DeserializeObject<List<CampaignData.Monster>>(File.ReadAllText(Path.Combine(path, "new.json")));


            }
        }



        private static void HandleParseError(IEnumerable<Error> errs)
        {
            foreach (var err in errs)
            {
                Console.WriteLine(err.ToString());
            }
        }

        private static void RunOptionsAndReturnExitCode(Options opts)
        {
            if (opts.JsonFiles)
            {
                ParseJsonMonsters(opts.InputPath);
                return;
            }
            if (opts.Markup)
            {
                ParseMarkup(opts.InputPath,opts.OutputPath);
                return;
            }
        }
        class Options
        {
            [Option('o', "outputpath", Required = true, HelpText = "Path to store resulting json book files")]
            public string OutputPath { get; set; }

            [Option('p',"path",Required=true,HelpText = "Path containing files to parse")]
            public string InputPath { get; set; }

            [Option('j',Default = false, HelpText = "Parses json monster files")]
            public bool JsonFiles { get; set; }

            [Option('m', Default = false, HelpText = "Parses markup monster files")]
            public bool Markup { get; set; }

            // Omitting long name, defaults to name of property, ie "--verbose"
            [Option(Default = false, HelpText = "Prints all messages to standard output.")]
            public bool Verbose { get; set; }
        }
    }
}
