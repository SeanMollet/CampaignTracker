using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;
using CampaignData;
using System.IO;


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
            try
            {
                Application.Run(new Menu());
            }
            catch(Exception E)
            {
                //Try to save our stuff first
                MessageBox.Show(E.ToString(), "Critical Error, please send log!");
                string appdata = Environment.GetFolderPath(Environment.SpecialFolder.ApplicationData);
                string AppFolder = Path.Combine(appdata, "CampaignTracker");
                string LogFile = Path.Combine(AppFolder, DateTime.Now.ToString("yyyy-dd-M--HH-mm-ss") + " Error.log");
                if (!Directory.Exists(AppFolder))
                {
                    Directory.CreateDirectory(AppFolder);
                }
                string LogData = E.ToString() + Environment.NewLine + Environment.NewLine;
                if(db != null)
                {
                    LogData += db.GetDatabaseJson();
                }
                File.WriteAllText(LogFile, LogData);

                //Now, try to save their data as well
                if (Program.dbFile.Length > 0)
                {
                    string NewFile = Path.Combine(Path.GetDirectoryName(dbFile), Path.GetFileNameWithoutExtension(dbFile) + " Recovered.ctct");
                    File.WriteAllText(NewFile, db.GetDatabaseJson());
                }
            }
        }

        private static void Db_onMonstersUpdated(object sender)
        {
            throw new NotImplementedException();
        }
    }
}
