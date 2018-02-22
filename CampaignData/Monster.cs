using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CampaignData
{

    public class Monster : IComparable<Monster>, IEquatable<Monster>
    {
        private Fraction _challenge;

        public int CompareTo(Monster comparison)
        {
            return this.Name.CompareTo(comparison.Name);
        }
        public Monster()
        {
            Name = "";
            Source = "User Custom";
            Type = "";
            HP = new HP();
            AC = new AC();
            InitiativeModifier = 0;
            Speed = new SortableBindingList<BindableString>();
            Abilities = new Abilities();
            DamageVulnerabilities = new SortableBindingList<BindableString>();
            DamageResistances = new SortableBindingList<BindableString>();
            DamageImmunities = new SortableBindingList<BindableString>();
            ConditionImmunities = new SortableBindingList<BindableString>();
            Saves = new SortableBindingList<Save>();
            Senses = new SortableBindingList<BindableString>();
            Languages = new SortableBindingList<BindableString>();
            Challenge = new Fraction("0");
            Traits = new SortableBindingList<Trait>();
            Actions = new SortableBindingList<Action>();
            Reactions = new SortableBindingList<Reaction>();
            LegendaryActions = new SortableBindingList<LegendaryAction>();
            ReadOnly = false;
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
        public Fraction Challenge
        {
            get { return _challenge; }
            set { _challenge = value; }
        }
        public SortableBindingList<Trait> Traits { get; set; }
        public SortableBindingList<Action> Actions { get; set; }
        public SortableBindingList<Reaction> Reactions { get; set; }
        public SortableBindingList<LegendaryAction> LegendaryActions { get; set; }
        public bool ReadOnly { get; set; }
        public Monster Clone()
        {
            //Lazy way to make a clone, just let json do the work
            return Newtonsoft.Json.JsonConvert.DeserializeObject<Monster>(Newtonsoft.Json.JsonConvert.SerializeObject(this));
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
