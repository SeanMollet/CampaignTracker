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
            this.comboBox1.DataSource = Program.db.database.CreatureStats;
            this.comboBox1.DisplayMember = "StatName";
            this.comboBox1.ValueMember = "Stat";
        }

        public void DataBind()
        {
            this.Started.Text = battle.Began.ToString();

            this.Monsters.Columns.Clear();

            this.Monsters.AutoGenerateColumns = false;
            this.Monsters.DataSource = battle.monsters;
            this.Monsters.Columns.Add(new DataGridViewCheckBoxColumn { HeaderText = "Hidden", DataPropertyName = "Hidden", ReadOnly = false, AutoSizeMode = DataGridViewAutoSizeColumnMode.ColumnHeader });
            this.Monsters.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "ID", DataPropertyName = "Index",ReadOnly=true,AutoSizeMode= DataGridViewAutoSizeColumnMode.AllCells });
            this.Monsters.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Name", DataPropertyName = "Name", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.Monsters.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Type", DataPropertyName = "Type", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.ColumnHeader,Resizable= DataGridViewTriState.True });
            this.Monsters.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Appearance", DataPropertyName = "Appearance", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.Monsters.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Save", DataPropertyName = "SavingRoll", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.ColumnHeader });

            this.Monsters.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Current HP", DataPropertyName = "CurrentHP", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.Monsters.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Max HP", DataPropertyName = "MaxHP", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });

            this.Monsters.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "HP", DataPropertyName= "HPtoChange" ,Width = 25 });

            this.Monsters.Columns.Add(new DataGridViewButtonColumn { HeaderText = "HP", Text = "Damage", UseColumnTextForButtonValue = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.Monsters.Columns.Add(new DataGridViewButtonColumn { HeaderText = "HP", Text = "Heal", UseColumnTextForButtonValue = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });

            this.Monsters.Columns.Add(new DataGridViewButtonColumn { HeaderText = "Grant", Text = "XP", UseColumnTextForButtonValue = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });


            this.XP.AutoGenerateColumns = false;

            this.XP.DataSource = Program.db.database.getCurrentXP();

            this.XP.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Timestamp", DataPropertyName = "Timestamp", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.XP.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Session", DataPropertyName = "Session",AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.XP.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Battle", DataPropertyName = "Battle",  AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.XP.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Event", DataPropertyName = "Event",  AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.XP.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Monster", DataPropertyName = "Monster", AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.XP.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "XP", DataPropertyName = "XP",  AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });

            this.XP.Refresh();
            try
            {
                this.XP.Sort(this.XP.Columns[0], ListSortDirection.Descending);
            }
            catch(Exception E){}

            this.XP.AutoResizeColumns(DataGridViewAutoSizeColumnsMode.AllCells);

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
                                    battle.GrantXP(Program.db.database.Session,"Killed", battlemonster,Program.db.database.getCurrentXP());
                                }
                                break;
                            case "Heal":
                                battlemonster.CurrentHP += battlemonster.HPtoChange;
                                break;
                            case "XP":
                                battle.GrantXP(Program.db.database.Session, "Granted", battlemonster,Program.db.database.getCurrentXP());
                                battlemonster.Persuaded = true;
                                break;
                        }
                    }

                }
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            //Find the stat we're using
            if (this.comboBox1.SelectedValue.ToString().Length > 0)
            {

                foreach (var monster in battle.monsters)
                {
                    int statvalue = 0;
                    switch (this.comboBox1.SelectedValue.ToString())
                    {
                        case "Str":
                            statvalue = monster.Abilities.Str;
                            break;
                        case "Dex":
                            statvalue = monster.Abilities.Dex;
                            break;
                        case "Con":
                            statvalue = monster.Abilities.Con;
                            break;
                        case "Int":
                            statvalue = monster.Abilities.Int;
                            break;
                        case "Wis":
                            statvalue = monster.Abilities.Wis;
                            break;
                        case "Cha":
                            statvalue = monster.Abilities.Cha;
                            break;
                    }
                    //Convert to a modifider
                    var modifier = (statvalue - 10) / 2;
                    //Check if this monster has a save bonus for this
                    foreach (var save in monster.Saves)
                    {
                        if (save.Name.ToUpper().Trim() == this.comboBox1.SelectedValue.ToString().ToUpper().Trim())
                        {
                            modifier += save.Modifier;
                        }
                    }
                    //Roll it!
                    monster.SavingRoll = Dice.RollXwithMod(1, 20, modifier);
                }
            }
        }

        private void Monsters_CellDoubleClick(object sender, DataGridViewCellEventArgs e)
        {
            //don't do this on the button column
            if (e.RowIndex > -1 && e.ColumnIndex > -1 && Monsters.Rows.Count > e.RowIndex &&
                Monsters.Rows[e.RowIndex].Cells.Count > e.ColumnIndex)
            {
                var Cell = Monsters.Rows[e.RowIndex].Cells[e.ColumnIndex];
                if (!(Cell is DataGridViewButtonCell))
                {
                    //Get the monster in question
                    var monster = battle.monsters[e.RowIndex];
                    MonsterViewer viewer = new MonsterViewer(monster);
                    viewer.Show();
                }
            }
        }

        private void button2_Click(object sender, EventArgs e)
        {
            LootDispenser lootDispenser = new LootDispenser();
            lootDispenser.LoadBattle(this.battle);
            lootDispenser.Show();

        }
    }
}
