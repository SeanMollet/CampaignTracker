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


        public void ConvertJsonMonsterTypes(List<Monster> Monsters)
        {
            foreach (var monster in Monsters)
            {
                //Split up the type field
                var matches = PCRE.PcreRegex.Matches(monster.Type, @"(\w*) ((?:[\w ]+|(?:\(.*\)))+),\s([\w \-\(\)%]*)");
                //There should only be one of these
                foreach (var match in matches)
                {
                    if (match.CaptureCount == 3)
                    {
                        monster.Size = new CreatureSize(match[1]);
                        monster.Type = match[2];
                        monster.Alignment = match[3];
                    }
                }
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

        public static void ParseBestiary(string inputpath, string outputpath)
        {
            Dictionary<string, List<Monster>> books = new Dictionary<string, List<Monster>>();

            Console.WriteLine("Parsing Markup from " + inputpath);
            inputpath = Path.Combine(inputpath, "bestiary.json");

            if (File.Exists(inputpath))
            {
                var data = File.ReadAllText(inputpath);


                var file = Newtonsoft.Json.JsonConvert.DeserializeObject<dynamic>(data);

                if(file["monster"] != null)
                {
                    var monsters = file["monster"];
                    foreach (var monster in monsters)
                    {
                        Monster newmonster = new Monster();
                        newmonster.Name = monster["name"].ToString().Trim();
                        newmonster.Size = new CreatureSize(monster["size"].ToString().Trim());
                        if (monster["type"] is JObject)
                        {
                            newmonster.Type = monster["type"]["type"].ToString().Trim();
                            if (monster["type"]["tags"] != null)
                            {
                                newmonster.Tags = new SortableBindingList<string>();
                                foreach (var tag in monster["type"]["tags"]) { newmonster.Tags.Add(tag.ToString().Trim()); }
                            }
                        }
                        else
                        {
                            newmonster.Type = monster["type"].ToString().Trim();
                        }
                        newmonster.Source = monster["source"].ToString().Trim();
                        newmonster.Alignment = monster["alignment"].ToString().Trim();

                        newmonster.HP.SetFromString(monster["hp"].ToString().Trim());

                        if (monster["ac"] != null && monster["ac"].ToString().Contains(" "))
                        {
                            string acStr = monster["ac"].ToString().Trim();
                            int acValue = 0;
                            int.TryParse(acStr.Substring(0, acStr.IndexOf(' ')), out acValue);
                            newmonster.AC.Value = acValue;
                            newmonster.AC.Notes = acStr.Substring(acStr.IndexOf(' ')).Trim();
                        }
                        else
                        {
                            int acValue = 0;
                            int.TryParse(monster["ac"].ToString(), out acValue);
                            newmonster.AC.Value = acValue;
                        }

                        var tempspeeds = monster["speed"].ToString().Split(',');
                        foreach (var speed in tempspeeds)
                        {
                            newmonster.Speed.Add(speed.Trim());
                        }

                        int str = 0, dex = 0, con = 0, intel = 0, wis = 0, cha = 0;
                        int.TryParse(monster["str"].ToString(), out str);
                        int.TryParse(monster["dex"].ToString(), out dex);
                        int.TryParse(monster["con"].ToString(), out con);
                        int.TryParse(monster["int"].ToString(), out intel);
                        int.TryParse(monster["wis"].ToString(), out wis);
                        int.TryParse(monster["cha"].ToString(), out cha);

                        newmonster.Abilities = new Abilities { Str = str, Dex = dex, Con = con, Int = intel, Wis = wis, Cha = cha };

                        if (monster["save"] != null)
                        {
                            var tempsaves = monster["save"].ToString().Split(',');
                            foreach (var save in tempsaves)
                            {
                                string savestr = save.ToString().Trim();
                                var newsave = new Save();
                                newsave.Name = savestr.Substring(0, savestr.IndexOf(' '));

                                int value = 0;
                                int.TryParse(savestr.Substring(savestr.IndexOf(' ')), out value);
                                newsave.Modifier = value;
                                newmonster.Saves.Add(newsave);
                            }
                        }

                        if (monster["skill"] != null)
                        {
                            foreach (var tag in monster["skill"])
                            {
                                string skill = tag.Name.ToString().Trim();
                                var newskill = new Skill();
                                newskill.Name = skill;
                                int mod = 0;
                                int.TryParse(tag.Value.ToString(), out mod);
                                newskill.Modifier = mod;
                            }
                        }

                        if (monster["immune"] != null)
                        {
                            foreach (var immun in monster["immune"].ToString().Split(',')) { newmonster.DamageImmunities.Add(immun.Trim()); }
                        }

                        if (monster["vulnerable"] != null)
                        {
                            foreach (var dmgvul in monster["vulnerable"].ToString().Split(',')) { newmonster.DamageVulnerabilities.Add(dmgvul.Trim()); }
                        }
                        if (monster["conditionImmune"] != null)
                        {
                            foreach (var conimm in monster["conditionImmune"].ToString().Split(',')) { newmonster.ConditionImmunities.Add(conimm.Trim()); }
                        }



                        if(monster["senses"] != null)
                        {
                            foreach (var sense in monster["senses"].ToString().Split(',')) { newmonster.Senses.Add(sense.Trim()); }
                        }
                        if (monster["passive"] != null)
                        {
                            int passive = 0;
                            int.TryParse(monster["passive"].ToString(), out passive);
                            newmonster.Senses.Add("passive Perception " + monster["passive"].ToString().Trim());
                        }

                        if (monster["languages"] != null)
                        {
                            foreach (var language in monster["languages"].ToString().Split(',')) { newmonster.Languages.Add(language.Trim()); }
                        }

                        if (monster["cr"] != null)
                        {
                            if (monster["cr"] == "Unknown")
                            {
                                newmonster.Challenge = new Fraction(0);
                            }
                            else
                            {
                                newmonster.Challenge = new Fraction(monster["cr"].ToString().Trim());
                            }
                        }

                        if (monster["trait"] != null)
                        {
                            foreach (var trait in monster["trait"])
                            {
                                if (trait["text"] is JArray)
                                {
                                    string text = "";
                                    foreach (var line in trait["text"])
                                    {
                                        text += line.ToString().Trim();
                                    }
                                    newmonster.Traits.Add(new Trait { Content = text, Name = trait["name"].ToString().Trim() });
                                }
                                else
                                {
                                    newmonster.Traits.Add(new Trait { Content = trait["text"].ToString().Trim(), Name = trait["name"].ToString().Trim() });
                                }
                            }
                        }

                        if (monster["action"] != null)
                        {
                            foreach (var trait in monster["action"])
                            {
                                string text = "";
                                string attack = "";

                                if (trait["text"] is JArray)
                                {
                                    foreach (var line in trait["text"])
                                    {
                                        text += line.ToString().Trim();
                                    }
                                    
                                }
                                else
                                {
                                    text = trait["text"].ToString();
                                }

                                if (trait["attack"] is JArray)
                                {
                                    foreach (var line in trait["attack"])
                                    {
                                        attack += line.ToString().Trim();
                                    }

                                }
                                else
                                {
                                    attack = trait["attack"] == null ? "" : trait["attack"].ToString().Trim();
                                }

                                newmonster.Actions.Add(new CampaignData.Action { Content = text, Name = trait["name"].ToString().Trim(), Attack = attack });
                            }
                        }

                        if (monster["spells"] != null)
                        {
                            foreach (var spell in monster["spells"].ToString().Split(',')) { newmonster.Spells.Add(spell.Trim()); }
                        }

                        newmonster.ReadOnly = true;

                        if (!books.ContainsKey(newmonster.Source))
                        {
                            books.Add(newmonster.Source, new List<Monster>());
                        }
                        books[newmonster.Source].Add(newmonster);
                    }
                }


                if (books.Count > 0)
                {
                    foreach (var book in books)
                    {
                        try
                        {
                            var savejson = Newtonsoft.Json.JsonConvert.SerializeObject(book.Value,Formatting.Indented);
                            File.WriteAllText(Path.Combine(outputpath, book.Key + ".json"), savejson);
                        }
                        catch (Exception E)
                        {
                            Console.WriteLine(E.ToString());
                        }
                    }
                }
            }

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
            if(opts.Bestiary)
            {
                ParseBestiary(opts.InputPath, opts.OutputPath);
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


            [Option('b', Default = false, HelpText = "Parses bestiary file")]
            public bool Bestiary { get; set; }

            // Omitting long name, defaults to name of property, ie "--verbose"
            [Option(Default = false, HelpText = "Prints all messages to standard output.")]
            public bool Verbose { get; set; }
        }
    }
}
