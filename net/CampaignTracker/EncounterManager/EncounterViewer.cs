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


            this.Monsters.Columns.Clear();

            this.Monsters.AutoGenerateColumns = false;
            this.Monsters.DataSource = encounter.monsters;
            this.Monsters.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Name", DataPropertyName = "Name", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.Monsters.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Source", DataPropertyName = "Source", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.Monsters.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Type", DataPropertyName = "Type", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.ColumnHeader, Resizable = DataGridViewTriState.True });
            this.Monsters.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Challenge", DataPropertyName = "Challenge", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.Monsters.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Description", DataPropertyName = "Description", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.ColumnHeader });
            this.Monsters.Columns.Add(new DataGridViewCheckBoxColumn { HeaderText = "Hidden", DataPropertyName = "Hidden", ReadOnly = false, AutoSizeMode = DataGridViewAutoSizeColumnMode.ColumnHeader });
            this.Monsters.Columns.Add(new DataGridViewCheckBoxColumn { HeaderText = "Unknown", DataPropertyName = "Unknown", ReadOnly = false, AutoSizeMode = DataGridViewAutoSizeColumnMode.ColumnHeader });

            this.Monsters.AutoResizeColumns();
        }

        //Wire up access through the static program so add operations can succeed
        private void EncounterViewer_Activated(object sender, EventArgs e)
        {
            Program.Active_encounter = encounter;
        }

        private void EncounterViewer_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (Program.Active_encounter == encounter)
            {
                Program.Active_encounter = null;
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
