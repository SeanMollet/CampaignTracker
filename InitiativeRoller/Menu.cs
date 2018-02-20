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

        private void Initiative_Click(object sender, EventArgs e)
        {
            Initiative init = new Initiative();
            init.Show();
        }

        private void MonsterManager_Click(object sender, EventArgs e)
        {
            MonsterManager monsters = new CampaignTracker.MonsterManager();
            monsters.Show();
        }


        private void save_Click(object sender, EventArgs e)
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
                        dlg.Filter = "Json Files (*.json)|*.json|All Files(*.*)|*.*";
                        dlg.FilterIndex = 0;
                        dlg.AddExtension = true;
                        dlg.DefaultExt = "json";

                        if (dlg.ShowDialog() == DialogResult.OK)
                        {
                            Program.db.SaveFile(dlg.FileName);
                            Program.dbFile = dlg.FileName;
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
            dlg.Filter = "Json Files (*.json)|*.json|All Files(*.*)|*.*";
            dlg.FilterIndex = 0;
            dlg.AddExtension = true;
            dlg.DefaultExt = "json";
            if (dlg.ShowDialog() == DialogResult.OK)
            {
                try
                {

                    if (Program.db.LoadFile(dlg.FileName))
                    {
                        Program.dbFile = dlg.FileName;

                        this.label1.Text = "Main Menu - " + Path.GetFileNameWithoutExtension(dlg.FileName);

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
    }
}
