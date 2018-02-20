using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace CampaignTracker
{
    static class Program
    {
        public static CampaignData.DatabaseManager db;
        public static CampaignData.MonstersDataBase mon_db;
        public static string dbFile;
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
