using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CampaignData
{

    public class Monster : IComparable<Monster>, IEquatable<Monster>
    {

        public int CompareTo(Monster comparison)
        {
            return this.Name.CompareTo(comparison.Name);
        }
        public Monster()
        {
            Name = "";
            Size = new CreatureSize();
            Source = "User Custom";
            Type = "";
            HP = new HP();
            AC = new AC();
            InitiativeModifier = 0;
            Tags = new SortableBindingList<string>();
            Speed = new SortableBindingList<BindableString>();
            Abilities = new Abilities();
            DamageVulnerabilities = new SortableBindingList<BindableString>();
            DamageResistances = new SortableBindingList<BindableString>();
            DamageImmunities = new SortableBindingList<BindableString>();
            ConditionImmunities = new SortableBindingList<BindableString>();
            Saves = new SortableBindingList<Save>();
            Skills = new SortableBindingList<Skill>();
            Senses = new SortableBindingList<BindableString>();
            Languages = new SortableBindingList<BindableString>();
            Challenge = new Fraction("0");
            Traits = new SortableBindingList<Trait>();
            Actions = new SortableBindingList<Action>();
            Reactions = new SortableBindingList<Reaction>();
            LegendaryActions = new SortableBindingList<LegendaryAction>();
            Spells = new SortableBindingList<BindableString>();
            ReadOnly = false;
        }

        public string Name { get; set; }
        public CreatureSize Size { get; set; }
        [JsonIgnore]
        public string DisplayType { get { return Size.Value + " " + Type + " " + Alignment; } }
        public string Type { get; set; }
        public SortableBindingList<string> Tags { get; set; }
        public string Source { get; set; }
        public string Alignment { get; set; }
        public AC AC { get; set; }
        public HP HP { get; set; }
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
        //private Fraction _challenge;
        //{
        //    get { return _challenge; }
        //    set { _challenge = value; }
        //}
        public SortableBindingList<Trait> Traits { get; set; }
        public SortableBindingList<Action> Actions { get; set; }
        public SortableBindingList<Reaction> Reactions { get; set; }
        public SortableBindingList<LegendaryAction> LegendaryActions { get; set; }
        public SortableBindingList<BindableString> Spells { get; set; }
        public bool ReadOnly { get; set; }
        public Monster Clone()
        {
            //Lazy way to make a clone, just let json do the work
            var json = Newtonsoft.Json.JsonConvert.SerializeObject(this);
            return Newtonsoft.Json.JsonConvert.DeserializeObject<Monster>(json);
        }

        public int GetHashCode(Monster obj)
        {
            throw new NotImplementedException();
        }

        public bool Equals(Monster other)
        {
            return Name.Equals(other.Name);
        }

        public override bool Equals(object obj)
        {
            if (obj.GetType() != this.GetType()) return false;
            return Name.Equals((obj as Monster).Name);
        }

        public override int GetHashCode()
        {
            return Name.GetHashCode();
        }
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
        public void RollHP()
        {
            Value = Dice.RollXwithMod(HitDiceCount, HitDice, HitModifier);
        }
        public void SetFromString(string value)
        {
            if(!value.Contains(" "))
            {
                value = value.Substring(0, value.IndexOf("(")) +" "+ value.Substring(value.IndexOf("("));
            }
            string hp = value.Substring(0, value.IndexOf(' '));
            string dice = value.Substring(value.IndexOf(' '));

            int basehp = 0;
            int.TryParse(hp, out basehp);
            Value = basehp;

            var hits = DiceUtilities.GetHitDice(dice);
            HitDiceCount = hits.HitDiceCount;
            HitDice = hits.HitDiceSize;
            HitModifier = hits.HitModifier;
        }
    }


    public class CreatureSize : IEquatable<CreatureSize>
    {
        public CreatureSize()
        {

        }
        public CreatureSize(string size)
        {
            this.size = sizeFromString(size);
        }
        public static CreatureSizes sizeFromString(string size)
        {
            CreatureSizes newsize = CreatureSizes.Medium;
            size = size.Trim().ToUpper();
            switch (size)
            {
                case "TINY":
                case "T":
                    newsize = CreatureSizes.Tiny;
                    break;
                case "SMALL":
                case "S":
                    newsize = CreatureSizes.Small;
                    break;
                case "MEDIUM":
                case "M":
                    newsize = CreatureSizes.Medium;
                    break;
                case "LARGE":
                case "L":
                    newsize = CreatureSizes.Large;
                    break;
                case "HUGE":
                case "H":
                    newsize = CreatureSizes.Huge;
                    break;
                case "GARGANTUAN":
                case "G":
                    newsize = CreatureSizes.Gargantuan;
                    break;
            }
            return newsize;
        }
        private CreatureSizes size;
        public string Value { get
            {
                return ToString();
            }
             set
            {
                size = sizeFromString(value);
            }
        }

        public enum CreatureSizes
        {
            Tiny,
            Small,
            Medium,
            Large,
            Huge,
            Gargantuan
        }
        public override string ToString()
        {
            switch (size)
            {
                case CreatureSizes.Tiny:
                    return "Tiny";
                case CreatureSizes.Small:
                    return "Small";
                case CreatureSizes.Medium:
                    return "Medium";
                case CreatureSizes.Large:
                    return "Large";
                case CreatureSizes.Huge:
                    return "Huge";
                case CreatureSizes.Gargantuan:
                    return "Gargantuan";
            }
            return "Medium";
        }

        public bool Equals(CreatureSize other)
        {
            return this.Value.Equals(other.Value);
        }
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
    }

    public class Action
    {
        public string Name { get; set; }
        public string Content { get; set; }
        public string Attack { get; set; }
    }

    public class Reaction
    {
        public string Name { get; set; }
        public string Content { get; set; }
    }

    public class LegendaryAction
    {
        public string Name { get; set; }
        public string Content { get; set; }
    }


}
