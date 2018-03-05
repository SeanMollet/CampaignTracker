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
using System.Runtime.InteropServices;
using CampaignData;


namespace CampaignTracker
{
    public partial class PlayerEditor : Controls.BaseForm, DataBindReload
    {

        public PlayerEditor()
        {
            InitializeComponent();
            //data.onPlayersUpdated += Data_onPlayersUpdated;
            DataBind();
        }

        public void DataBind()
        {

            this.PlayerGrid.Columns.Clear();

            this.PlayerGrid.AutoGenerateColumns = false;
            this.PlayerGrid.DataSource = Program.db.database.Players;

            this.PlayerGrid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Name", DataPropertyName = "Name", AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.PlayerGrid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Race", DataPropertyName = "Race", AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.PlayerGrid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Class", DataPropertyName = "Class", AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.PlayerGrid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "AC", DataPropertyName = "AC", AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.PlayerGrid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "HP", DataPropertyName = "CurrentHP", AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.PlayerGrid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Max HP", DataPropertyName = "MaxHP", AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.PlayerGrid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Dex", DataPropertyName = "Initiative", AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.PlayerGrid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Roll", DataPropertyName = "Roll", AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.PlayerGrid.Columns.Add(new DataGridViewComboBoxColumn
            {
                HeaderText = "Adv",
                DataPropertyName = "Adv",
                AutoSizeMode = DataGridViewAutoSizeColumnMode.ColumnHeader,
                DataSource = Dice.AdvantageTypes,
                ValueMember = "RollType",
                DisplayMember = "RollTypeName",
                DropDownWidth = 80
            });
            this.PlayerGrid.Columns.Add(new DataGridViewCheckBoxColumn { HeaderText = "Dead", DataPropertyName = "Dead", AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.PlayerGrid.Columns.Add(new DataGridViewCheckBoxColumn { HeaderText = "Stable", DataPropertyName = "Stable", AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.PlayerGrid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Appearance", DataPropertyName = "Appearance", AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.PlayerGrid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "HP", DataPropertyName = "HPtoChange", Width = 25 });
            this.PlayerGrid.Columns.Add(new DataGridViewButtonColumn { HeaderText = "HP", Text = "Damage", UseColumnTextForButtonValue = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.PlayerGrid.Columns.Add(new DataGridViewButtonColumn { HeaderText = "HP", Text = "Heal", UseColumnTextForButtonValue = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });

            this.PlayerGrid.Refresh();
        }


        private void button1_Click(object sender, EventArgs e)
        {
            //Make sure there is a monsters line
            bool hasMonsters = false;
            foreach (var player in Program.db.database.Players)
            {
                if(player.Name == "Monsters")
                {
                    hasMonsters = true;
                }
            }

            if (!hasMonsters)
            {
                Program.db.database.Players.Add(new Player { Name = "Monsters" });
            }

            foreach (var player in Program.db.database.Players)
            {
                player.Roll = Dice.Roll(20, player.Adv) + player.Initiative;
            }

            //Find the roll column, since I always seem to be changing this grid
            foreach (DataGridViewColumn col in this.PlayerGrid.Columns)
            {
                if(col.HeaderText == "Roll")
                {
                    this.PlayerGrid.Sort(col, ListSortDirection.Descending);
                    break;
                }
            }
            
        }

        private void AdjustCurrentPlayer(int adjustment)
        {
            int rownumber = -1;
            if (this.PlayerGrid.SelectedRows.Count > 0)
            {
                rownumber = this.PlayerGrid.SelectedRows[0].Index;
            }
            else
            {
                if (this.PlayerGrid.SelectedCells.Count > 0)
                {
                    rownumber = this.PlayerGrid.SelectedCells[0].RowIndex;
                }
            }
            if (rownumber > -1)
            {
                var row = this.PlayerGrid.Rows[rownumber];
                var player = row.DataBoundItem as Player;
                if (player != null)
                {
                    AdjustPlayer(player, adjustment);
                }
            }
            
        }

        private void AdjustPlayer(Player player, int adjustment)
        {

            
            //edge case
            //If the player has temporary HP and someone heals them
            //Do not cap the hp automatically, leave it up to the GM to decide
            if (player.CurrentHP > player.MaxHP && adjustment > 0)
            {
                return;
            }
            player.CurrentHP += adjustment;
            //Make sure they don't heal past max
            if (adjustment >0 && player.CurrentHP > player.MaxHP)
            {
                player.CurrentHP = player.MaxHP;
            }
        }

        private void DmgAll_Click(object sender, EventArgs e)
        {
            foreach (var player in Program.db.database.Players)
            {
                AdjustPlayer(player, -1 * (int)this.numericUpDown1.Value);
            }
        }

        private void HealAll_Click(object sender, EventArgs e)
        {
            foreach (var player in Program.db.database.Players)
            {
                AdjustPlayer(player, (int)this.numericUpDown1.Value);
            }
        }

        private void PlayerGrid_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            //do this on the button column
            if (e.RowIndex > -1 && e.ColumnIndex > -1 && PlayerGrid.Rows.Count > e.RowIndex &&
                PlayerGrid.Rows[e.RowIndex].Cells.Count > e.ColumnIndex)
            {
                var Cell = PlayerGrid.Rows[e.RowIndex].Cells[e.ColumnIndex];
                if (Cell is DataGridViewButtonCell)
                {
                    var row = PlayerGrid.Rows[e.RowIndex];
                    if (row.DataBoundItem is Player)
                    {
                        var player = (Player)row.DataBoundItem;

                        var type = Cell.Value.ToString();
                        switch (type)
                        {
                            case "Damage":
                                player.CurrentHP -= player.HPtoChange;
                                break;
                            case "Heal":
                                player.CurrentHP += player.HPtoChange;
                                break;

                        }
                    }

                }
            }
        }
    }


}
