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
    public partial class BattleViewer : Controls.BaseForm, DataBindReload
    {
        private Battle battle;
        public BattleViewer(Battle startBattle)
        {
            InitializeComponent();
            battle = startBattle;
            DataBind();
        }

        public void DataBind()
        {
            this.Started.Text = battle.Began.ToString();

            this.Monsters.Columns.Clear();

            this.Monsters.AutoGenerateColumns = false;
            this.Monsters.DataSource = battle.monsters;
            this.Monsters.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "ID", DataPropertyName = "Index",ReadOnly=true,AutoSizeMode= DataGridViewAutoSizeColumnMode.AllCells });
            this.Monsters.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Name", DataPropertyName = "Name", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.Monsters.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Type", DataPropertyName = "Type", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.ColumnHeader,Resizable= DataGridViewTriState.True });
            this.Monsters.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Appearance", DataPropertyName = "Appearance", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });

            this.Monsters.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Current HP", DataPropertyName = "CurrentHP", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.Monsters.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Max HP", DataPropertyName = "MaxHP", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });

            this.Monsters.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "HP", DataPropertyName= "HPtoChange" ,Width = 25 });

            this.Monsters.Columns.Add(new DataGridViewButtonColumn { HeaderText = "HP", Text = "Damage", UseColumnTextForButtonValue = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.Monsters.Columns.Add(new DataGridViewButtonColumn { HeaderText = "HP", Text = "Heal", UseColumnTextForButtonValue = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });

            this.Monsters.Columns.Add(new DataGridViewButtonColumn { HeaderText = "Grant", Text = "XP", UseColumnTextForButtonValue = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });

        }

        //Wire up access through the static program so add operations can succeed
        private void EncounterViewer_Activated(object sender, EventArgs e)
        {
            Program.Active_battle = battle;
        }

        private void EncounterViewer_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (Program.Active_battle == battle)
            {
                Program.Active_encounter = null;
            }
        }

        private void Monsters_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            //do this on the button column
            if (e.RowIndex > -1 && e.ColumnIndex > -1 && Monsters.Rows.Count > e.RowIndex &&
                Monsters.Rows[e.RowIndex].Cells.Count > e.ColumnIndex)
            {
                var Cell = Monsters.Rows[e.RowIndex].Cells[e.ColumnIndex];
                if (Cell is DataGridViewButtonCell)
                {
                    var row = Monsters.Rows[e.RowIndex];
                    if (row.DataBoundItem is BattleMonster)
                    {
                        var battlemonster = (BattleMonster)row.DataBoundItem;

                        var type = Cell.Value.ToString();
                        switch (type)
                        {
                            case "Damage":
                                battlemonster.CurrentHP -= battlemonster.HPtoChange;
                                if (battlemonster.CurrentHP <= 0)
                                {
                                    battle.GrantXP(Program.db.database.Session,"Killed", battlemonster);
                                }
                                break;
                            case "Heal":
                                battlemonster.CurrentHP += battlemonster.HPtoChange;
                                break;
                            case "XP":
                                battle.GrantXP(Program.db.database.Session, "Granted", battlemonster);
                                battlemonster.Persuaded = true;
                                break;
                        }
                    }

                }
            }
        }
    }
}
