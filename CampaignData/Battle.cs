using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;

namespace CampaignData
{

    public class Battle
    {
        public DateTime Began { get; set; }
        public int LiveMonsters { get
            {
                int livemonsters = 0;
                if (monsters != null)
                {
                    foreach(var monster in monsters)
                    {
                        if (monster.CurrentHP > 0)
                        {
                            livemonsters++;
                        }
                    }
                }
                return livemonsters;
            } }
        public int EarnedXP
        {
            get
            {
                int earned = 0;
                if (XP != null)
                {
                    foreach(var xp in XP)
                    {
                        earned += xp.XP;
                    }
                }
                return earned;
            }
        }
        DateTime Ended { get; set; }
        public SortableBindingList<BattleMonster> monsters { get; set; }
        public SortableBindingList<XPEvent> XP { get; set; }
        public Battle(IList<Monster> preloadMonsters=null)
        {
            Began = DateTime.Now;
            monsters = new SortableBindingList<BattleMonster>();
            XP = new SortableBindingList<XPEvent>();

            if (preloadMonsters != null)
            {
                foreach (var monster in preloadMonsters)
                {
                    AddMonster(monster);
                }
            }

        }


        public void AddMonster(Monster monster)
        {
            var converted = BattleMonster.ReadyforBattle(monster);
            converted.Index = monsters.Count + 1;
            monsters.Add(converted);

        }
        public Battle Clone()
        {
            return Newtonsoft.Json.JsonConvert.DeserializeObject<Battle>(Newtonsoft.Json.JsonConvert.SerializeObject(this));
        }



    }

    public class BattleMonster : Monster, INotifyPropertyChanged
    {
        public DateTime spawned { get; }
        private int index;
        public int Index { get => index; set { index = value; NotifyPropertyChanged(); } }
        private int HPOffset;
        public int CurrentHP
        {
            get => HP.Value + HPOffset;
            set
            {
                HPOffset = value - HP.Value;
                NotifyPropertyChanged();
                NotifyPropertyChanged("Appearance");
            }
        }
        public int MaxHP { get => HP.Value; }
        public int HPtoChange { get; set; }
        private bool persuaded;
        public bool Persuaded { get => persuaded; set { persuaded = value; NotifyPropertyChanged("Appearance"); } }
        public string Appearance
        {
            get
            {
                if (Persuaded)
                {
                    return "Persuaded";
                }

                return HPAppearance.Appearance(CurrentHP, HP.Value);
            }
        }
        public static BattleMonster ReadyforBattle(Monster monster)
        {
            return Newtonsoft.Json.JsonConvert.DeserializeObject<BattleMonster>(Newtonsoft.Json.JsonConvert.SerializeObject(monster));
        }
        public event PropertyChangedEventHandler PropertyChanged;
        private void NotifyPropertyChanged([CallerMemberName] String propertyName = "")
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }
    }
}

