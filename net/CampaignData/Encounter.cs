using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CampaignData
{
    public class Encounter : IComparable<Encounter>, IEquatable<Encounter>
    {
        public string Name { get; set; }
        public Fraction Challenge
        {
            get
            {
                Fraction total_ch = 0;
                if (monsters != null)
                {
                    foreach (var monster in monsters)
                    {
                        total_ch += monster.Challenge;
                    }
                }
                return total_ch;
            }
        }
        public string Description { get; set; }
        public SortableBindingList<Monster> monsters { get; set; }

        public Encounter()
        {
            monsters = new SortableBindingList<Monster>();
        }
        public int CompareTo(Encounter comparison)
        {
            return Name.CompareTo(comparison.Name);
        }
        public bool Equals(Encounter other)
        {
            return Name.Equals(other.Name);
        }

        public Encounter Clone()
        {
            //Quick and dirty cloning
            return Newtonsoft.Json.JsonConvert.DeserializeObject<Encounter>(Newtonsoft.Json.JsonConvert.SerializeObject(this));
        }
    }
}
