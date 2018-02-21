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
    public partial class BattleManager : Controls.BaseForm, DataBindReload
    {
        public BattleManager()
        {
            InitializeComponent();
            DataBind();
            Program.db.onBattlesUpdated += (object sender) => { DataBind(); };
        }


        public void DataBind()
        {
            DataBind(Program.db.database.Battles);
        }
        public void DataBind(CampaignData.SortableBindingList<Battle> list)
        {
            List<string> VisibleColumns = new List<string>(new string[] { "Began", "LiveMonsters", "EarnedXP","Ended" });


            this.BattleList.DataSource = list;
            //Hide them all to start
            if (this.BattleList.Columns.Count > 0)
            {
                for (int a = 0; a < this.BattleList.ColumnCount; a++)
                {
                    //Enable the ones we want
                    if (!VisibleColumns.Contains(this.BattleList.Columns[a].Name))
                    {
                        this.BattleList.Columns[a].Visible = false;
                    }
                }
            }


            DataGridViewButtonColumn BattleCol = new DataGridViewButtonColumn();
            BattleCol.HeaderText = "End";
            BattleCol.Text = "Battle";
            BattleCol.UseColumnTextForButtonValue = true;

            this.BattleList.Columns.Add(BattleCol);
        }

        private void NewButton_Click(object sender, EventArgs e)
        {
            Battle battle = new Battle();
            BattleViewer viewer = new BattleViewer(battle);
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
            if (e.RowIndex > -1 && e.ColumnIndex > -1 && BattleList.Rows.Count > e.RowIndex &&
                BattleList.Rows[e.RowIndex].Cells.Count > e.ColumnIndex )
            {
                var Cell = BattleList.Rows[e.RowIndex].Cells[e.ColumnIndex];
                if (!(Cell is DataGridViewButtonCell))
                {
                    var row = BattleList.Rows[e.RowIndex];
                    if (row.DataBoundItem is Battle)
                    {
                        var battleview = (Battle)row.DataBoundItem;
                        BattleViewer viewer = new BattleViewer(battleview.Clone());
                        viewer.Show();
                    }

                }
            }

        }
    }
}
