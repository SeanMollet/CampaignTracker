using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using System.ComponentModel;
using System.Runtime.CompilerServices;

namespace CampaignData
{
    public class Database
    {
        public Database()
        {
            Battles = new SortableBindingList<Battle>();
            Encounters = new SortableBindingList<Encounter>();
            Monsters = new SortableBindingList<Monster>();
            Players = new SortableBindingList<Player>();
        }

        public SortableBindingList<Battle> Battles { get; set; }
        public SortableBindingList<Encounter> Encounters { get; set; }
        public SortableBindingList<Monster> Monsters { get; set; }
        public SortableBindingList<Player> Players { get; set; }
        
    }

    public class DatabaseManager
    {
        public delegate void UpdateHandler(object sender);
        public event UpdateHandler onBattlesUpdated;
        public event UpdateHandler onEncountersUpdated;
        public event UpdateHandler onMontersUpdated;
        public event UpdateHandler onPlayersUpdated;
        
        
        

        public Database database;

        private object dbLock;

        public DatabaseManager()
        {
            database = new Database();
            dbLock = new object();
        }


        private void BindNotifications()
        {
            if(database != null)
            {
                database.Battles.ListChanged += ((object sender, ListChangedEventArgs e) => { onBattlesUpdated?.Invoke(sender); });
                database.Encounters.ListChanged += ((object sender, ListChangedEventArgs e) => { onEncountersUpdated?.Invoke(sender); });
                database.Monsters.ListChanged += ((object sender, ListChangedEventArgs e) => { onMontersUpdated?.Invoke(sender); });
                database.Players.ListChanged += ((object sender, ListChangedEventArgs e) => { onPlayersUpdated?.Invoke(sender); });
            }
        }
        

        public bool LoadFile(string path)
        {
            try
            {
                using (FileStream file = new FileStream(path, System.IO.FileMode.Open, FileAccess.Read))
                {
                    lock (dbLock)
                    {
                        string json = File.ReadAllText(path);
                        var result = Newtonsoft.Json.
                        JsonConvert.DeserializeObject<Database>(json);
                        BindNotifications();

                        this.database = result;
                        onBattlesUpdated?.Invoke(this);
                        onEncountersUpdated?.Invoke(this);
                        onMontersUpdated?.Invoke(this);
                        onPlayersUpdated?.Invoke(this);
                    }
                }
                return true;
            }
            catch(Exception E)
            {
                return false;
            }
        }

        public bool SaveFile(string path)
        {
            try
            {
                lock (dbLock)
                {
                    string json = Newtonsoft.Json.JsonConvert.SerializeObject(database, Newtonsoft.Json.Formatting.Indented);
                    File.WriteAllText(path, json);
                }
                return true;
            }
            catch (Exception E)
            {
                return false;
            }

        }


    }

    public class Battle : IComparable<Battle>
    {
        public int CompareTo(Battle comparison)
        {
            return 0;
        }
    }
    public class Encounter : IComparable<Encounter>
    {
        public int CompareTo(Encounter comparison)
        {
            return 0;
        }

    }
    public class Monster : IComparable<Monster>
    {
        public int CompareTo(Monster comparison)
        {
            return 0;
        }
    }

    public class Player : IComparable<Player>, INotifyPropertyChanged
    {


        public event PropertyChangedEventHandler PropertyChanged;

        private void NotifyPropertyChanged([CallerMemberName] String propertyName = "")
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }

        private string _name;
        private int _stat;
        private int _roll;
        private bool _adv;

        public string Name { get => _name; set { if (_name != value) { _name = value; NotifyPropertyChanged(); } } }
        public int Stat { get => _stat; set { if (_stat != value) { _stat = value; NotifyPropertyChanged(); } } }
        public int Roll { get => _roll; set { if (_roll != value) { _roll = value; NotifyPropertyChanged(); } } }
        public bool Adv { get => _adv; set { if (_adv != value) { _adv = value; NotifyPropertyChanged(); } } }
        public int CompareTo(Player comparison)
        {
            if (comparison == null)
            {
                return -1;
            }
            if (comparison.Roll == this.Roll)
            {
                return this.Stat.CompareTo(comparison.Stat);
            }
            return this.Roll.CompareTo(comparison.Roll);
        }
    }
}
