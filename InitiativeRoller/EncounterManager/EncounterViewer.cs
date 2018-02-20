using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using CampaignData;

namespace CampaignTracker
{
    public partial class EncounterViewer : Controls.BaseForm, DataBindReload
    {
        private Encounter encounter;
        public EncounterViewer(Encounter Encounter)
        {
            InitializeComponent();
            encounter = Encounter;
            DataBind();
        }

        public void DataBind()
        {
            this.NameBox.DataBindings.Add("Text", encounter, "Name");
            this.Description.DataBindings.Add("Text", encounter, "Description");

            List<string> VisibleColumns = new List<string>(new string[] { "Name", "Source", "Type", "Challenge", "Description" });

            this.Monsters.Columns.Clear();

            this.Monsters.DataSource = encounter.monsters;
            //Hide them all to start
            if (this.Monsters.Columns.Count > 0)
            {
                for (int a = 0; a < this.Monsters.ColumnCount; a++)
                {
                    //Enable the ones we want
                    if (!VisibleColumns.Contains(this.Monsters.Columns[a].Name))
                    {
                        this.Monsters.Columns[a].Visible = false;
                    }
                }
            }
        }

        //Wire up access through the static program so add operations can succeed
        private void EncounterViewer_Activated(object sender, EventArgs e)
        {
            Program.active_encounter = encounter;
        }

        private void EncounterViewer_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (Program.active_encounter == encounter)
            {
                Program.active_encounter = null;
            }
        }

        private void SaveButton_Click(object sender, EventArgs e)
        {
            if (Program.db.database.Encounters.IndexOf(encounter) > -1)
            {
                if (MessageBox.Show("Replace existing " + encounter.Name + "?", "Overwrite Encounter?", MessageBoxButtons.YesNo) == DialogResult.Yes)
                {
                    var oldencounter = Program.db.database.Encounters.IndexOf(encounter);
                    Program.db.database.Encounters.RemoveAt(oldencounter);
                    Program.db.database.Encounters.Add(encounter.Clone());
                }
            }
            else
            {
                Program.db.database.Encounters.Add(encounter.Clone());
            }
        }
    }
}
