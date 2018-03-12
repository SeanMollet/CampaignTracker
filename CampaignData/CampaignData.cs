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

    public class Database : INotifyPropertyChanged
    {
        public Database()
        {
            Battles = new SortableBindingList<Battle>();
            Encounters = new SortableBindingList<Encounter>();
            Players = new SortableBindingList<Player>();
            CustomMonsters = new SortableBindingList<Monster>();
            XP = new Dictionary<int, SortableBindingList<XPEvent>>();
            Loot = new SortableBindingList<LootItem>();
            Session = 1;
            XP.Add(1,new SortableBindingList<XPEvent>());

            LoadStats();
        }

        public SortableBindingList<XPEvent> getCurrentXP()
        {
            //Make sure there are enough in there
            if(!XP.ContainsKey(Session))
            {
                XP.Add(Session,new SortableBindingList<XPEvent>());
            }

            if (XP[Session] == null)
            {
                XP[Session] = new SortableBindingList<XPEvent>();
            }
            return XP[Session];
        }

        public Dictionary<int,SortableBindingList<XPEvent>> getAllXP()
        {
            return XP;
        }

        public SortableBindingList<Battle> Battles { get; set; }
        public SortableBindingList<Encounter> Encounters { get; set; }
        public SortableBindingList<Player> Players { get; set; }
        public SortableBindingList<Monster> CustomMonsters { get; set; }
        public Dictionary<int,SortableBindingList<XPEvent>> XP { get; set; }
        public SortableBindingList<LootItem> Loot { get; set; }

        public int Session { get => session; set { session = value; NotifyPropertyChanged(); } }
        [JsonIgnore]
        public List<Stats> CreatureStats;
        private int session;

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
        public event PropertyChangedEventHandler PropertyChanged;
        private void NotifyPropertyChanged([CallerMemberName] String propertyName = "")
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
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
        public event UpdateHandler onSessionUpdated;

        public Database database;

        private object dbLock;

        public DatabaseManager()
        {
            database = new Database();
            dbLock = new object();
            BindNotifications();

        }


        public string GetDatabaseJson()
        {
            return JsonConvert.SerializeObject(database, Formatting.Indented, new JsonSerializerSettings
            {
                DateTimeZoneHandling = DateTimeZoneHandling.Unspecified,
                DateFormatString = "yyyy-MM-dd'T'HH:mm:ss.FFF"
            });
        }
        private void BindNotifications()
        {
            if (database != null)
            {
                database.Battles.ListChanged += ((object sender, ListChangedEventArgs e) => { onBattlesUpdated?.Invoke(sender); });
                database.Encounters.ListChanged += ((object sender, ListChangedEventArgs e) => { onEncountersUpdated?.Invoke(sender); });
                database.Players.ListChanged += ((object sender, ListChangedEventArgs e) => { onPlayersUpdated?.Invoke(sender); });
                database.CustomMonsters.ListChanged += ((object sender, ListChangedEventArgs e) => { onMonstersUpdated?.Invoke(sender); });
                database.PropertyChanged += (object sender, PropertyChangedEventArgs e) =>
                {
                    switch (e.PropertyName)
                    {
                        case "Session":
                            onSessionUpdated?.Invoke(this);
                            break;
                    }
                };
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
                    File.WriteAllText(path, GetDatabaseJson());
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



