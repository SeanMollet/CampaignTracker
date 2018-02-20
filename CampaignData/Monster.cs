using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
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
        public SortableBindingList<Monster> Monsters { get; set; }

        private SortableBindingList<Monster> customMonsters;

        private Dictionary<string, string> FilterDic;

        public void UpdateCustomMonsters(SortableBindingList<Monster> CustomMonsters)
        {
            customMonsters = CustomMonsters;
            LoadMonsters();
        }
        public void LoadMonsters()
        {
            var stream = new MemoryStream(BookData.MonsterManual);
            var reader = new JsonTextReader(new StreamReader(stream));

            Monsters = JsonSerializer.Create().Deserialize<List<Monster>>(reader);

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
            foreach(var monster in Monsters)
            {
                FilterDic.Add(monster.Name.StripPunctuationAndSpaces().ToUpper(), monster.Name);
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
                if(monster.Name == name)
                {
                    return monster;
                }
            }
            return new Monster { Name = name };
        }
    }

    public class Monster : IComparable<Monster>
    {
        public int CompareTo(Monster comparison)
        {
            return 0;
        }
        public string Name { get; set; }
        public string Source { get; set; }
        public string Type { get; set; }
        public HP HP { get; set; }
        public AC AC { get; set; }
        public int InitiativeModifier { get; set; }
        public SortableBindingList<string> Speed { get; set; }
        public Abilities Abilities { get; set; }
        public SortableBindingList<string> DamageVulnerabilities { get; set; }
        public SortableBindingList<string> DamageResistances { get; set; }
        public SortableBindingList<string> DamageImmunities { get; set; }
        public SortableBindingList<string> ConditionImmunities { get; set; }
        public SortableBindingList<Save> Saves { get; set; }
        public SortableBindingList<Skill> Skills { get; set; }
        public SortableBindingList<string> Senses { get; set; }
        public SortableBindingList<string> Languages { get; set; }
        public Fraction Challenge { get; set; }
        public SortableBindingList<Trait> Traits { get; set; }
        public SortableBindingList<Action> Actions { get; set; }
        public SortableBindingList<Reaction> Reactions { get; set; }
        public SortableBindingList<LegendaryAction> LegendaryActions { get; set; }
        public bool ReadOnly { get; set; }
    }

    public class HP
    {
        public int Value { get; set; }
        public string Notes { get; set; }
    }

    public class AC
    {
        public int Value { get; set; }
        public string Notes { get; set; }
    }

    public class Abilities
    {
        public int Str { get; set; }
        public int Dex { get; set; }
        public int Con { get; set; }
        public int Int { get; set; }
        public int Wis { get; set; }
        public int Cha { get; set; }
    }

    public class Save
    {
        public string Name { get; set; }
        public int Modifier { get; set; }
    }

    public class Skill
    {
        public string Name { get; set; }
        public int Modifier { get; set; }
    }

    public class Trait
    {
        public string Name { get; set; }
        public string Content { get; set; }
        public string Usage { get; set; }
    }

    public class Action
    {
        public string Name { get; set; }
        public string Content { get; set; }
        public string Usage { get; set; }
    }

    public class Reaction
    {
        public string Name { get; set; }
        public string Content { get; set; }
        public string Usage { get; set; }
    }

    public class LegendaryAction
    {
        public string Name { get; set; }
        public string Content { get; set; }
        public string Usage { get; set; }
    }


}
