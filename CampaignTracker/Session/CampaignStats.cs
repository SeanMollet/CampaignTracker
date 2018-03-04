using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Drawing.Printing;
using CampaignData;

namespace CampaignTracker
{
    public partial class CampaignStats : Controls.BaseForm
    {
        public CampaignStats()
        {
            InitializeComponent();
            this.printDocument1.BeginPrint += new System.Drawing.Printing.PrintEventHandler(this.printDocument1_BeginPrint);
            this.printDocument1.PrintPage += new System.Drawing.Printing.PrintPageEventHandler(this.printDocument1_PrintPage);

            GenerateText();

        }

        private void GenerateText()
        {

            var battles = Program.db.database.Battles;
            var xpEntries = Program.db.database.getAllXP();

            //Configure the base font
            richTextBox1.Font = new Font("Courier New", 14);

            AppendText("Campaign Manager Campaign " + Path.GetFileNameWithoutExtension(Program.dbFile), Color.MediumVioletRed, true);

            if (Program.db.database.Battles.Count > 0)
            {
                
                AppendText("Began " + Program.db.database.Battles.Min(x => x.Began).ToString("F"), Color.MediumVioletRed, true);
                AppendText("Stats as of " + DateTime.Now.ToString("F"), Color.MediumVioletRed, true);
            }

            AppendText("", Color.CornflowerBlue, true);

            AppendText("Heroes:", Color.GreenYellow, true);

            foreach(var player in Program.db.database.Players)
            {
                AppendText(player.Name +" the "+player.Race+" "+player.Class, Color.GreenYellow, true);
            }

            AppendText("", Color.CornflowerBlue, true);

            AppendText("Monsters killed: " + battles.Sum(x => x.monsters.Count(y => y.CurrentHP < 0)).ToString("N0"), Color.CornflowerBlue, true);
            AppendText("Monsters not killed (XP Given): " + battles.Sum(x => x.monsters.Count(y => y.Persuaded)).ToString("N0"), Color.CornflowerBlue, true);
            AppendText("Monsters not killed (no XP Given): " + battles.Sum(x => x.monsters.Count(y => y.CurrentHP>0 && !y.Persuaded)).ToString("N0"), Color.CornflowerBlue, true);            
            AppendText("XP Earned: " + xpEntries.Sum(x => x.Value.Sum(y => y.XP)).ToString("N0"), Color.CornflowerBlue, true);

            AppendText("", Color.CornflowerBlue, true);

            AppendText("Stats by session:", Color.CornflowerBlue, true);
            var sessions = battles.GroupBy(x => x.Session);
            foreach (var session in sessions)
            {
                int xpearned = 0;
                AppendText("Session "+session.Key+":", Color.CornflowerBlue, true);
                if (xpEntries.ContainsKey(session.Key))
                {
                    xpearned = xpEntries[session.Key].Sum(x => x.XP);
                    foreach (var xp in xpEntries[session.Key])
                    {
                        AppendText("Earned " + xp.XP.ToString("N0") + " for " + xp.Event, Color.CornflowerBlue, true);
                    }
                }
                //AppendText("Monsters killed: " + session.Sum(x => x.monsters.Count(y => y.CurrentHP < 0)).ToString("N0"), Color.CornflowerBlue, true);
                //AppendText("Monsters not killed (XP Given): " + session.Sum(x => x.monsters.Count(y => y.Persuaded)).ToString("N0"), Color.CornflowerBlue, true);
                //AppendText("Monsters not killed (no XP Given): " + session.Sum(x => x.monsters.Count(y => y.CurrentHP>0 && !y.Persuaded)).ToString("N0"), Color.CornflowerBlue, true);
                
                AppendText("XP Earned: " + xpearned.ToString("N0"), Color.CornflowerBlue, true);
                AppendText("", Color.CornflowerBlue, true);
            }

            AppendText("", Color.CornflowerBlue, true);
            AppendText("Monsters killed:", Color.CornflowerBlue, true);

            List<BattleMonster> kills = new List<BattleMonster>();
            foreach(var battle in battles)
            {
                kills.AddRange(battle.monsters);
            }

            Dictionary<string, killstats> monsters = new Dictionary<string, killstats>();
            foreach (var xp in xpEntries.SelectMany(x => x.Value))
            {
                if (xp.Monster.Length > 0)
                {
                    if (monsters.ContainsKey(xp.Monster))
                    {
                        monsters[xp.Monster].Count++;
                        monsters[xp.Monster].XP += xp.XP;
                    }
                    else
                    {
                        monsters.Add(xp.Monster, new killstats { Count = 1, XP = xp.XP });
                    }
                }
            }



            foreach (var monster in monsters)
            {
                AppendText("Earned XP for " + monster.Value.Count + " " + monster.Key + " for " + monster.Value.XP + " XP", Color.CornflowerBlue, true);
            }

        }

        private class killstats
        {
            public int Count { get; set; }
            public int XP { get; set; }
        }
        public void AppendText(string text, Color color, bool addNewLine = false)
        {
            var box = richTextBox1;
            box.SuspendLayout();
            box.SelectionColor = color;

            box.AppendText(addNewLine
                ? $"{text}{Environment.NewLine}"
                : text);
            box.ScrollToCaret();
            box.ResumeLayout();
        }


        private int checkPrint;

        private void printDocument1_BeginPrint(object sender, System.Drawing.Printing.PrintEventArgs e)
        {
            checkPrint = 0;
        }

        private void printDocument1_PrintPage(object sender, System.Drawing.Printing.PrintPageEventArgs e)
        {
            // Print the content of RichTextBox. Store the last character printed.
            checkPrint = richTextBox1.Print(checkPrint, richTextBox1.TextLength, e);

            // Check for more pages
            if (checkPrint < richTextBox1.TextLength)
                e.HasMorePages = true;
            else
                e.HasMorePages = false;
        }

        private void button2_Click(object sender, EventArgs e)
        {
            pageSetupDialog1.ShowDialog();
        }

        private void button3_Click(object sender, EventArgs e)
        {
            printPreviewDialog1.ShowDialog();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (printDialog1.ShowDialog() == DialogResult.OK)
                printDocument1.Print();
        }


    }
}
