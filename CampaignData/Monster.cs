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
            LoadXP();
        }
        public SortableBindingList<Monster> Monsters { get; set; }

        private SortableBindingList<Monster> customMonsters;
        private Dictionary<string, string> FilterDic;
        private Dictionary<Fraction, int> XPDic;

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

        public int GetXP(Fraction CR)
        {
            if (XPDic.ContainsKey(CR))
            {
                return XPDic[CR];
            }
            if(CR > 30)
            {
                int levels = (int)CR - 30;
                int basexp = XPDic[new Fraction("30")];
                return basexp + (levels * 15000);
            }
            return 0;
        }
        private void LoadXP()
        {
            XPDic = new Dictionary<Fraction, int>();
            XPDic.Add(new Fraction("0"), 10);
            XPDic.Add(new Fraction("1/8"), 25);
            XPDic.Add(new Fraction("1/4"), 50);
            XPDic.Add(new Fraction("1/2"), 100);
            XPDic.Add(new Fraction("1"), 200);
            XPDic.Add(new Fraction("2"), 450);
            XPDic.Add(new Fraction("3"), 700);
            XPDic.Add(new Fraction("4"), 1100);
            XPDic.Add(new Fraction("5"), 1800);
            XPDic.Add(new Fraction("6"), 2300);
            XPDic.Add(new Fraction("7"), 2900);
            XPDic.Add(new Fraction("8"), 3900);
            XPDic.Add(new Fraction("9"), 5000);
            XPDic.Add(new Fraction("10"), 5900);
            XPDic.Add(new Fraction("11"), 7200);
            XPDic.Add(new Fraction("12"), 8400);
            XPDic.Add(new Fraction("13"), 10000);
            XPDic.Add(new Fraction("14"), 11500);
            XPDic.Add(new Fraction("15"), 13000);
            XPDic.Add(new Fraction("16"), 15000);
            XPDic.Add(new Fraction("17"), 18000);
            XPDic.Add(new Fraction("18"), 20000);
            XPDic.Add(new Fraction("19"), 22000);
            XPDic.Add(new Fraction("20"), 25000);
            XPDic.Add(new Fraction("21"), 33000);
            XPDic.Add(new Fraction("22"), 41000);
            XPDic.Add(new Fraction("23"), 50000);
            XPDic.Add(new Fraction("24"), 62000);
            XPDic.Add(new Fraction("25"), 75000);
            XPDic.Add(new Fraction("26"), 90000);
            XPDic.Add(new Fraction("27"), 105000);
            XPDic.Add(new Fraction("28"), 120000);
            XPDic.Add(new Fraction("29"), 135000);
            XPDic.Add(new Fraction("30 "), 155000);
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
        public SortableBindingList<BindableString> Speed { get; set; }
        public Abilities Abilities { get; set; }
        public SortableBindingList<BindableString> DamageVulnerabilities { get; set; }
        public SortableBindingList<BindableString> DamageResistances { get; set; }
        public SortableBindingList<BindableString> DamageImmunities { get; set; }
        public SortableBindingList<BindableString> ConditionImmunities { get; set; }
        public SortableBindingList<Save> Saves { get; set; }
        public SortableBindingList<Skill> Skills { get; set; }
        public SortableBindingList<BindableString> Senses { get; set; }
        public SortableBindingList<BindableString> Languages { get; set; }
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
        public int HitDiceCount { get; set; }
        public int HitDice { get; set; }
        public int HitModifier { get; set; }
        public string Notes { get; set; }
        //Temporary change to convert notes containing hit dice info to a more useful form
        //private string notes;
        //public string Notes { get => notes;
        //    set
        //    {
        //        //var hits = DiceUtilities.GetHitDice(value);
        //        //HitDiceCount = hits.HitDiceCount;
        //        //HitDice = hits.HitDiceSize;
        //        //HitModifier = hits.HitModifier;
        //        if (value != null)
        //            notes = value;
        //        else
        //            notes = "";
        //    }
        //}
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
