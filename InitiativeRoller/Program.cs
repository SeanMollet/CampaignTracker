using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;
using CampaignData;

namespace CampaignTracker
{
    static class Program
    {
        public static CampaignData.DatabaseManager db;
        public static CampaignData.MonstersDataBase mon_db;
        public static string dbFile;

        public delegate void ActiveHandler(object sender);
        public static event ActiveHandler onActiveEncounterChanged;
        public static event ActiveHandler onActiveBattleChanged;

        private static CampaignData.Encounter active_encounter;
        private static CampaignData.Battle active_battle;

        public static Encounter Active_encounter
        {
            get => active_encounter; set
            {
                active_encounter = value;
                onActiveEncounterChanged?.Invoke(null);
            }
        }
        public static Battle Active_battle
        {
            get => active_battle; set
            {
                active_battle = value;
                onActiveBattleChanged?.Invoke(null);
            }
        }


        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            db = new CampaignData.DatabaseManager();
            dbFile = "";

            mon_db = new CampaignData.MonstersDataBase();
            //Update the monsters DB if the custom ones are changed (loading, etc...)
            db.onMonstersUpdated += (object sender) => { mon_db.UpdateCustomMonsters(db.database.CustomMonsters); };

            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new Menu());
        }

        private static void Db_onMonstersUpdated(object sender)
        {
            throw new NotImplementedException();
        }
    }
}
