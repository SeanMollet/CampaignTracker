using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;

namespace CampaignData
{

    public class Player : IComparable<Player>, INotifyPropertyChanged
    {


        public event PropertyChangedEventHandler PropertyChanged;

        private void NotifyPropertyChanged([CallerMemberName] String propertyName = "")
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }

        private string name;
        private int initiative;
        private int ac;
        private int currentHP;
        private int maxHP;
        private int roll;
        private bool advantage;
        private bool dead;
        private bool stable;
        private string race;
        private string char_class;

        public string Name { get => name; set { if (name != value) { name = value; NotifyPropertyChanged(); } } }
        public string Race { get => race; set { if (race != value) { race = value; NotifyPropertyChanged(); } } }
        public string Class { get => char_class; set { if (char_class != value) { char_class = value; NotifyPropertyChanged(); } } }
        public int Initiative { get => initiative; set { if (initiative != value) { initiative = value; NotifyPropertyChanged(); } } }
        public int AC { get => ac; set { if (ac != value) { ac = value; NotifyPropertyChanged(); } } }
        public int CurrentHP
        {
            get => currentHP; set
            {
                if (currentHP != value)
                {

                    currentHP = value;
                    //Clear the stable and dead flags
                    if (currentHP > 0)
                    {
                        Stable = false;
                        Dead = false;
                    }
                    if (currentHP <= maxHP * -2)
                    {
                        Stable = false;
                        dead = true;
                    }

                    NotifyPropertyChanged();
                    NotifyPropertyChanged("Appearance");
                }
            }
        }
        public int MaxHP { get => maxHP; set { if (maxHP != value) { maxHP = value; NotifyPropertyChanged(); } } }
        [JsonIgnore]
        public int HPtoChange { get; set; }
        public int Roll { get => roll; set { if (roll != value) { roll = value; NotifyPropertyChanged(); } } }
        public bool Adv { get => advantage; set { if (advantage != value) { advantage = value; NotifyPropertyChanged(); } } }
        public bool Dead { get => dead; set { if (dead != value) { dead = value; NotifyPropertyChanged(); NotifyPropertyChanged("Appearance"); } } }
        public bool Stable { get => stable; set { if (stable != value) { stable = value; NotifyPropertyChanged(); NotifyPropertyChanged("Appearance"); } } }
        public string Appearance
        {
            get
            {
                if (Dead)
                {
                    return "Dead";
                }
                if (CurrentHP <= 0 && Stable)
                {
                    return "Stable";
                }
                if (CurrentHP <= 0)
                {
                    return "Unconscious";
                }

                return HPAppearance.Appearance(CurrentHP, MaxHP);
            }
        }
        public int CompareTo(Player comparison)
        {
            if (comparison == null)
            {
                return -1;
            }
            if (comparison.Roll == this.Roll)
            {
                return this.Initiative.CompareTo(comparison.Initiative);
            }
            return this.Roll.CompareTo(comparison.Roll);
        }
    }
}
