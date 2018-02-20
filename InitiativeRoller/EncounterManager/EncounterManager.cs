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
    public partial class EncounterManager : Controls.BaseForm, DataBindReload
    {
        public EncounterManager()
        {
            InitializeComponent();
            DataBind();
            Program.db.onEncountersUpdated += (object sender) => { DataBind(); };
        }


        public void DataBind()
        {
            DataBind(Program.db.database.Encounters);
        }
        public void DataBind(CampaignData.SortableBindingList<Encounter> list)
        {
            List<string> VisibleColumns = new List<string>(new string[] { "Name", "Challenge", "Description" });

            //this.EncounterList.Columns.Clear();

            this.EncounterList.DataSource = list;
            //Hide them all to start
            if (this.EncounterList.Columns.Count > 0)
            {
                for (int a = 0; a < this.EncounterList.ColumnCount; a++)
                {
                    //Enable the ones we want
                    if (!VisibleColumns.Contains(this.EncounterList.Columns[a].Name))
                    {
                        this.EncounterList.Columns[a].Visible = false;
                    }
                }
            }


            DataGridViewButtonColumn BattleCol = new DataGridViewButtonColumn();
            BattleCol.HeaderText = "Start";
            BattleCol.Text = "Battle";
            BattleCol.UseColumnTextForButtonValue = true;

            this.EncounterList.Columns.Add(BattleCol);





        }

        private void NewButton_Click(object sender, EventArgs e)
        {
            Encounter encounter = new Encounter { Name = "New Encounter" };
            EncounterViewer viewer = new EncounterViewer(encounter);
            viewer.Show();

        }

        private void ImportButton_Click(object sender, EventArgs e)
        {

        }

        private void ExportButton_Click(object sender, EventArgs e)
        {

        }

        private void EncounterManager_Load(object sender, EventArgs e)
        {

        }

        private void EncounterList_CellDoubleClick(object sender, DataGridViewCellEventArgs e)
        {
            //Don't do this on the button column
            if (EncounterList.Rows.Count > e.RowIndex &&
                EncounterList.Rows[e.RowIndex].Cells.Count > e.ColumnIndex)
            {
                var Cell = EncounterList.Rows[e.RowIndex].Cells[e.ColumnIndex];
                if (!(Cell is DataGridViewButtonCell))
                {
                    var row = EncounterList.Rows[e.RowIndex];
                    if (row.DataBoundItem is Encounter)
                    {
                        var encounterview = (Encounter)row.DataBoundItem;
                        EncounterViewer viewer = new EncounterViewer(encounterview.Clone());
                        viewer.Show();
                    }

                }
            }

        }
    }
}
