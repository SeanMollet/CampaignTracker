using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using System.ComponentModel;
using System.Runtime.CompilerServices;
using Newtonsoft.Json;

namespace CampaignData
{

    public class Database
    {
        public Database()
        {
            Battles = new SortableBindingList<Battle>();
            Encounters = new SortableBindingList<Encounter>();
            Players = new SortableBindingList<Player>();
            CustomMonsters = new SortableBindingList<Monster>();
            XP = new SortableBindingList<XPEvent>();
            Session = 1;

            LoadStats();
        }

        public SortableBindingList<Battle> Battles { get; set; }
        public SortableBindingList<Encounter> Encounters { get; set; }
        public SortableBindingList<Player> Players { get; set; }
        public SortableBindingList<Monster> CustomMonsters { get; set; }
        public SortableBindingList<XPEvent> XP { get; set; }
        public int Session { get; set; }
        [JsonIgnore]
        public List<Stats> CreatureStats;
        private void LoadStats()
        {
            CreatureStats = new List<Stats>();
            CreatureStats.Add(new Stats("Str", "Strength"));
            CreatureStats.Add(new Stats("Dex", "Dexterity"));
            CreatureStats.Add(new Stats("Con", "Constitution"));
            CreatureStats.Add(new Stats("Int", "Intelligence"));
            CreatureStats.Add(new Stats("Wis", "Wisdom"));
            CreatureStats.Add(new Stats("Cha", "Charisma"));
        }

    }

    public class Stats
    {
        public Stats(string stat,string name)
        {
            Stat = stat;
            StatName = name;
        }
        public Stats()
        {

        }
        public string Stat { get; set; }
        public string StatName { get; set; }
    }

    public class DatabaseManager
    {
        public delegate void UpdateHandler(object sender);
        public event UpdateHandler onBattlesUpdated;
        public event UpdateHandler onEncountersUpdated;
        public event UpdateHandler onMonstersUpdated;
        public event UpdateHandler onPlayersUpdated;

        public Database database;

        private object dbLock;

        public DatabaseManager()
        {
            database = new Database();
            dbLock = new object();
            BindNotifications();

        }


        private void BindNotifications()
        {
            if (database != null)
            {
                database.Battles.ListChanged += ((object sender, ListChangedEventArgs e) => { onBattlesUpdated?.Invoke(sender); });
                database.Encounters.ListChanged += ((object sender, ListChangedEventArgs e) => { onEncountersUpdated?.Invoke(sender); });
                database.Players.ListChanged += ((object sender, ListChangedEventArgs e) => { onPlayersUpdated?.Invoke(sender); });
                database.CustomMonsters.ListChanged += ((object sender, ListChangedEventArgs e) => { onMonstersUpdated?.Invoke(sender); });
            }
        }


        public void NewFile()
        {
            this.database = new Database();
            BindNotifications();

            onBattlesUpdated?.Invoke(this);
            onEncountersUpdated?.Invoke(this);
            onPlayersUpdated?.Invoke(this);
            onMonstersUpdated?.Invoke(this);
        }

        public bool LoadFile(string path)
        {
            //This purposely doesn't have a try/catch so the error will be passed up to the UI
            using (FileStream file = new FileStream(path, System.IO.FileMode.Open, FileAccess.Read))
            {
                lock (dbLock)
                {
                    string json = File.ReadAllText(path);
                    var result = Newtonsoft.Json.JsonConvert.DeserializeObject<Database>(json);
                    if (result != null)
                    {
                        this.database = result;
                        database.Session++;
                        BindNotifications();

                        onBattlesUpdated?.Invoke(this);
                        onEncountersUpdated?.Invoke(this);
                        onPlayersUpdated?.Invoke(this);
                        onMonstersUpdated?.Invoke(this);
                    }
                }
            }
            return true;

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

}



