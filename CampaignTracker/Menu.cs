using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO;

namespace CampaignTracker
{
    public partial class Menu : Controls.BaseForm
    {
        public Menu()
        {
            InitializeComponent();
        }

        private void PlayerEditor_Click(object sender, EventArgs e)
        {
            PlayerEditor editor = new PlayerEditor();
            editor.Show();
        }

        private void PlayerView_Click(object sender, EventArgs e)
        {
            PCView init = new PCView();
            init.Show();
        }

        private void MonsterManager_Click(object sender, EventArgs e)
        {
            MonsterManager monsters = new CampaignTracker.MonsterManager();
            monsters.Show();
        }


        private void save_Click(object sender, EventArgs e)
        {
            Save();
        }

        private void Save()
        {
            try
            {
                DialogResult dialogResult = DialogResult.No;

                if (Program.dbFile.Length > 0)
                {
                    dialogResult = MessageBox.Show("Overwrite previous save?", "Overwrite?", MessageBoxButtons.YesNoCancel);
                }
                switch (dialogResult)
                {
                    case DialogResult.Yes:
                        Program.db.SaveFile(Program.dbFile);
                        break;
                    case DialogResult.No:
                        SaveFileDialog dlg = new SaveFileDialog();
                        dlg.Filter = "Campaign Files (*.ctct)|*.ctct|All Files(*.*)|*.*";
                        dlg.FilterIndex = 0;
                        dlg.AddExtension = true;
                        dlg.DefaultExt = "ctct";

                        if (dlg.ShowDialog() == DialogResult.OK)
                        {
                            Program.db.SaveFile(dlg.FileName);
                            Program.dbFile = dlg.FileName;
                            this.label1.Text = "Main Menu - " + Path.GetFileNameWithoutExtension(dlg.FileName) + " #" + Program.db.database.Session.ToString();
                        }
                        break;
                }
            }
            catch (Exception E)
            {
                MessageBox.Show(E.ToString(), "Error saving game");
            }
        }

        private void open_Click(object sender, EventArgs e)
        {
            OpenFileDialog dlg = new OpenFileDialog();
            dlg.Filter = "Campaign Files (*.ctct)|*.ctct|All Files(*.*)|*.*";
            dlg.FilterIndex = 0;
            dlg.AddExtension = true;
            dlg.DefaultExt = "ctct";
            if (dlg.ShowDialog() == DialogResult.OK)
            {
                try
                {

                    if (Program.db.LoadFile(dlg.FileName))
                    {
                        Program.dbFile = dlg.FileName;

                        this.label1.Text = "Main Menu - " + Path.GetFileNameWithoutExtension(dlg.FileName) + " #" + Program.db.database.Session.ToString();

                        //Rebind any open forms we have to the new database
                        var forms = Application.OpenForms;
                        foreach (var form in forms)
                        {
                            if (form is DataBindReload)
                            {
                                var casted = form as DataBindReload;
                                casted.DataBind();
                            }
                        }
                    }
                }
                catch (Exception E)
                {
                    MessageBox.Show(E.ToString(), "Error loading game");
                }
            }


        }

        private void button4_Click(object sender, EventArgs e)
        {
            EncounterManager manager = new CampaignTracker.EncounterManager();
            manager.Show();
        }

        //We don't want this one to close on ESC
        private new void BaseForm_KeyDown(object sender, KeyEventArgs e)
        {

        }

        private void BattlePCView_Click(object sender, EventArgs e)
        {
            PCBattleView view = new PCBattleView();
            view.Show();
        }

        private void NewCampaign_click(object sender, EventArgs e)
        {
            //If they have any progress, prompt before creating a new game
            if (Program.db.database != null)
            {
                if (Program.db.database.Players.Count > 0 || Program.db.database.Encounters.Count > 0 || Program.db.database.getCurrentXP().Count > 0 || Program.db.database.Battles.Count > 0)
                {
                    if (MessageBox.Show("Are you sure you want to create a new campaign?", "Confirm", MessageBoxButtons.YesNo) != DialogResult.Yes)
                    {
                        return;
                    }
                }
            }
            Program.db.NewFile();
        }

        private void Menu_FormClosing(object sender, FormClosingEventArgs e)
        {
            //If they have any progress, prompt before closing
            if (Program.db.database.Players.Count > 0 || Program.db.database.Encounters.Count > 0 || Program.db.database.getCurrentXP().Count > 0 || Program.db.database.Battles.Count > 0)
            {
                if (MessageBox.Show("Save campaign before closing?", "Confirm", MessageBoxButtons.YesNo) == DialogResult.Yes)
                {
                    Save();
                }
            }

        }

        private void Battle_click(object sender, EventArgs e)
        {
            
            CampaignData.Battle battle = new CampaignData.Battle(Program.db.database.Session, Program.db.database.Battles.Count + 1);
            Program.db.database.Battles.Add(battle);
            BattleViewer viewer = new BattleViewer(battle);
            viewer.Show();
        }

        private void button10_Click(object sender, EventArgs e)
        {
            CampaignStats stats = new CampaignStats();
            stats.Show();
        }

        private void button11_Click(object sender, EventArgs e)
        {
            AdjustSession session = new AdjustSession();
            session.Show();
        }

        private void button12_Click(object sender, EventArgs e)
        {
            RollOMatic roll = new CampaignTracker.RollOMatic();
            roll.Show();
        }

        private void button13_Click(object sender, EventArgs e)
        {
            BattleListing listing = new BattleListing();
            listing.Show();
        }
    }
}
