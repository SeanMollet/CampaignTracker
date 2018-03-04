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
using CampaignData;


namespace CampaignTracker
{
    public partial class BattleListing : Controls.BaseForm, DataBindReload
    {

        public BattleListing()
        {
            InitializeComponent();
            //data.onPlayersUpdated += Data_onPlayersUpdated;
            DataBind();
        }


        public void DataBind()
        {
            this.BattleListingGrid.Columns.Clear();

            this.BattleListingGrid.AutoGenerateColumns = false;
            this.BattleListingGrid.DataSource = Program.db.database.Battles;

            this.BattleListingGrid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Began", DataPropertyName = "Began", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.BattleListingGrid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Session", DataPropertyName = "Session", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.BattleListingGrid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Battle", DataPropertyName = "BattleNumber", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.BattleListingGrid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Live", DataPropertyName = "LiveMonsters", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.BattleListingGrid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Monsters", DataPropertyName = "TotalMonsters", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });

            this.BattleListingGrid.Sort(this.BattleListingGrid.Columns[0], ListSortDirection.Descending);

        }

        private void BattleListingGrid_CellDoubleClick(object sender, DataGridViewCellEventArgs e)
        {
            if (this.BattleListingGrid.Rows.Count > e.RowIndex)
            {
                var obj = this.BattleListingGrid.Rows[e.RowIndex].DataBoundItem;
                var battle = obj as Battle;
                if (battle != null)
                {
                    BattleViewer viewer = new BattleViewer(battle);
                    viewer.Show();
                }
            }
        }
    }


}
