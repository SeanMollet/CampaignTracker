﻿using Newtonsoft.Json;
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
        public int BattleNumber { get; private set; }
        public int Session { get; private set; }
        public Battle(int session,int battlenumber, IList<Monster> preloadMonsters=null)
        {
            Began = DateTime.Now;
            Session = session;

            monsters = new SortableBindingList<BattleMonster>();
            XP = new SortableBindingList<XPEvent>();

            if (preloadMonsters != null)
            {
                foreach (var monster in preloadMonsters)
                {
                    AddMonster(monster);
                }
            }
            BattleNumber = battlenumber;
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

        public void GrantXP(int Session,string Reason, BattleMonster monster)
        {
            if (monster.XPGiven <=0)
            {
                XPEvent xp = new XPEvent();
                xp.Event = monster.Name + " " + monster.Index.ToString() + " " + Reason;
                xp.Session = Session;
                xp.Battle = BattleNumber;
                xp.Timestamp = DateTime.Now;
                xp.XP = BattleXP.GetXP(monster.Challenge);
                monster.XPGiven = xp.XP;
                XP.Add(xp);
            }
        }


    }

    public class BattleMonster : Monster, INotifyPropertyChanged
    {
        public DateTime spawned { get; }
        private int index;
        public int Index { get => index; set { index = value; NotifyPropertyChanged(); } }
        private bool currentHPSet = false;
        private int currentHP;
        public int CurrentHP
        {
            get
            {
                //Handle newly battle readied (type casted) monsters
                if (!currentHPSet)
                {
                    currentHP = MaxHP;
                    currentHPSet = true;
                }
                return currentHP;
            }
            set
            {
                //Cap the bottom at -1, makes it easier to revive if needed
                if (value < -1)
                {
                    value = -1;
                }
                currentHP = value;
                currentHPSet = true;
                NotifyPropertyChanged();
                NotifyPropertyChanged("Appearance");
            }
        }
        [JsonIgnore]
        public int MaxHP { get => HP.Value; }
        [JsonIgnore]
        public int HPtoChange { get; set; }
        private bool persuaded;
        private int savingRoll;

        public bool Persuaded
        {
            get => persuaded; set
            {
                if (persuaded != value)
                {
                    persuaded = value;
                    NotifyPropertyChanged("Appearance");
                }
            }
        }
        public int XPGiven { get; set; }
        [JsonIgnore]
        public string Appearance
        {
            get
            {
                if (Persuaded)
                {
                    return "Persuaded";
                }
                if (CurrentHP <= 0)
                {
                    return "Dead";
                }

                return HPAppearance.Appearance(CurrentHP, HP.Value);
            }
        }
        [JsonIgnore]
        public int SavingRoll { get => savingRoll; set { savingRoll = value; NotifyPropertyChanged(); } }
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

    public static class BattleXP
    {
        static BattleXP()
        {
            LoadXP();
        }

        private static Dictionary<Fraction, int> XPDic;

        public static int GetXP(Fraction CR)
        {
            if (XPDic.ContainsKey(CR))
            {
                return XPDic[CR];
            }
            if (CR > 30)
            {
                int levels = (int)CR - 30;
                int basexp = XPDic[new Fraction("30")];
                return basexp + (levels * 15000);
            }
            return 0;
        }
        private static void LoadXP()
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
}

