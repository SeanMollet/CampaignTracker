using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

namespace DataParser
{
    class Program
    {
        static void Main(string[] args)
        {
            if (args.Length == 0)
            {
                Console.WriteLine("Path required!\n");
                return;
            }

            string path = args[0];
            if (Directory.Exists(path)) {
                //Load up the download list
                JArray statblocks = (JArray) JsonConvert.DeserializeObject(File.ReadAllText(Path.Combine(path, "statblocks.json")));

                Dictionary<string, string> searchhints = new Dictionary<string, string>();
                Dictionary<string, object> monsters = new Dictionary<string, object>();

                List<string> sources = new List<string>();

                foreach (var block in statblocks)
                {
                    var name = block["Name"].ToString();
                    var id = block["Id"].ToString();
                    var searchHint = block["SearchHint"].ToString();
                    var link = block["Link"].ToString().Replace("/statblocks/","");

                    searchhints.Add(name, searchHint);
                }

                var monstersjson = JsonConvert.DeserializeObject<List<CampaignData.Monster>>(File.ReadAllText(Path.Combine(path, "together.json")));

                var monstersjson2 = JsonConvert.DeserializeObject<List<CampaignData.Monster>>(File.ReadAllText(Path.Combine(path, "new.json")));


            }
            
        }
    }
}
