using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;

namespace CampaignData
{
    public class MonstersDataBase
    {
        public MonstersDataBase()
        {
            LoadMonsters();
        }

        public delegate void UpdateHandler(object sender);
        public event UpdateHandler onMonstersUpdated;

        public SortableBindingList<Monster> Monsters { get; set; }

        private SortableBindingList<Monster> customMonsters;
        private Dictionary<string, string> FilterDic;

        public void UpdateCustomMonsters(SortableBindingList<Monster> CustomMonsters)
        {
            customMonsters = CustomMonsters;
            LoadMonsters();
            onMonstersUpdated?.Invoke(this);
        }
        public void LoadMonsters()
        {

            Type resType = Type.GetType("CampaignData.BookData");
            PropertyInfo[] resProps = resType.GetProperties(BindingFlags.NonPublic | BindingFlags.Static | BindingFlags.GetProperty);
            Monsters = new SortableBindingList<Monster>();
            foreach(PropertyInfo info in resProps)
            {
                var value = info.GetValue(null,null);
                if (value is byte[])
                {
                    byte[] bytes = (byte[]) value;
                    var stream = new MemoryStream(bytes, false);
                    var reader = new JsonTextReader(new StreamReader(stream));
                    var loadedmon = JsonSerializer.Create().Deserialize<List<Monster>>(reader);
                    Monsters.AddRange(loadedmon);
                }
            }

            //Append the custom monsters
            if (customMonsters != null)
            {
                foreach (var monster in customMonsters)
                {
                    Monsters.Add(monster);
                }
            }

            //Build our quick filtering dictionary
            FilterDic = new Dictionary<string, string>();
            foreach (var monster in Monsters)
            {
                if (!FilterDic.ContainsKey(monster.Name.StripPunctuationAndSpaces().ToUpper()))
                {
                    FilterDic.Add(monster.Name.StripPunctuationAndSpaces().ToUpper(), monster.Name);
                }
            }
        }


        public SortableBindingList<Monster> SearchForMonsters(string name)
        {
            SortableBindingList<Monster> result = new SortableBindingList<Monster>();
            name = name.Trim().ToUpper().StripPunctuation();

            //This isn't very efficient, but it should work for now
            foreach (var monster in FilterDic.Keys)
            {
                if (monster.Contains(name))
                {
                    result.Add(GetMonster(FilterDic[monster]));
                }
            }
            return result;
        }
        public Monster GetMonster(string name)
        {
            //name = name.StripPunctuationAndSpaces().ToUpper();
            //This isn't very efficient, but it should work for now
            foreach (var monster in Monsters)
            {
                if (monster.Name == name)
                {
                    return monster;
                }
            }
            return new Monster { Name = name };
        }

    }

}
