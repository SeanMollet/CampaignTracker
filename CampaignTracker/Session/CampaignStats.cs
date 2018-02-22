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
            AppendText("XP Earned: " + battles.Sum(x => x.monsters.Sum(y => y.XPGiven)).ToString("N0"), Color.CornflowerBlue, true);

            AppendText("", Color.CornflowerBlue, true);

            AppendText("Stats by session:", Color.CornflowerBlue, true);
            var sessions = battles.GroupBy(x => x.Session);
            foreach (var session in sessions)
            {
                AppendText("Session "+session.Key+":", Color.CornflowerBlue, true);
                AppendText("Monsters killed: " + session.Sum(x => x.monsters.Count(y => y.CurrentHP < 0)).ToString("N0"), Color.CornflowerBlue, true);
                AppendText("Monsters not killed (XP Given): " + session.Sum(x => x.monsters.Count(y => y.Persuaded)).ToString("N0"), Color.CornflowerBlue, true);
                AppendText("Monsters not killed (no XP Given): " + session.Sum(x => x.monsters.Count(y => y.CurrentHP>0 && !y.Persuaded)).ToString("N0"), Color.CornflowerBlue, true);
                AppendText("XP Earned: " + session.Sum(x => x.monsters.Sum(y => y.XPGiven)).ToString("N0"), Color.CornflowerBlue, true);
                AppendText("", Color.CornflowerBlue, true);
            }

            AppendText("", Color.CornflowerBlue, true);
            AppendText("Monsters killed:", Color.CornflowerBlue, true);

            List<BattleMonster> kills = new List<BattleMonster>();
            foreach(var battle in battles)
            {
                kills.AddRange(battle.monsters);
            }

            Dictionary<Monster, killstats> monsters = new Dictionary<Monster, killstats>();
            foreach (var monster in kills)
            {
                if (monster.CurrentHP <= 0 || monster.Persuaded)
                {
                    if (monsters.ContainsKey(monster))
                    {
                        monsters[monster].Count++;
                        monsters[monster].XP += monster.XPGiven;
                    }
                    else
                    {
                        monsters.Add(monster, new killstats { Count = 1, XP = monster.XPGiven });
                    }
                }
            }
            

            foreach (var monster in monsters)
            {
                AppendText("Earned XP for " +monster.Value.Count+ " "+ monster.Key.Name+" for "+monster.Value.XP+" XP", Color.CornflowerBlue, true);
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
