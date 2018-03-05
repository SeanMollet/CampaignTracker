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
    public partial class PCBattleView : Controls.BaseForm, DataBindReload
    {
        private Battle currentbattle;
        private SortableBindingList<XPEvent> currentxp;
        private SortableBindingList<BattleMonster> filteredMonsters;
        Timer pollingTimer;
        public PCBattleView()
        {
            InitializeComponent();
            filteredMonsters = new SortableBindingList<BattleMonster>();

            pollingTimer = new Timer();
            pollingTimer.Interval = 1000;
            pollingTimer.Tick += (object sender, EventArgs e) => { DataBind(); };

            DataBind();
            SetAlwaysOnTop();
            Program.onActiveBattleChanged += (object sender) => { DataBind(); };

            Program.db.onSessionUpdated += Db_onSessionUpdated;
        }

        private void Db_onSessionUpdated(object sender)
        {
            DataBind();
        }

        ~PCBattleView()
        { 
            Program.db.onSessionUpdated -= Db_onSessionUpdated;
        }

        public void DataBind()
        {           

            if (Program.Active_battle != null)
            {
                //Build our list of visible monsters
                filteredMonsters.Clear();
                var filtered = Program.Active_battle.monsters.Where(x => x.Hidden == false).ToArray();
                filteredMonsters.AddRange(filtered);

                //If the battle has changed, reconfigure everything
                if (Program.Active_battle != currentbattle)
                {
                    this.Monsters.Columns.Clear();

                    this.Monsters.AutoGenerateColumns = false;

                    this.Monsters.DataSource = filteredMonsters;
                    this.Monsters.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "ID", DataPropertyName = "Index", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
                    this.Monsters.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Name", DataPropertyName = "PC_Name", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
                    this.Monsters.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Type", DataPropertyName = "PC_Type", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
                    this.Monsters.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Appearance", DataPropertyName = "Appearance", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });

                    this.Monsters.Refresh();

                    //This forces refiltering for hidden monsters
                    Program.Active_battle.monsters.ListChanged += (object sender, ListChangedEventArgs e) => { DataBind(); };

                    //this.Monsters.AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.AllCells;

                    this.Monsters.AutoResizeColumns(DataGridViewAutoSizeColumnsMode.AllCells);

                    currentbattle = Program.Active_battle;
                }
                else
                {
                    this.Monsters.AutoResizeColumns(DataGridViewAutoSizeColumnsMode.AllCells);
                    this.XP.AutoResizeColumns(DataGridViewAutoSizeColumnsMode.AllCells);
                }

                pollingTimer.Stop();
            }
            else
            {
                pollingTimer.Start();
            }

            if (currentxp != Program.db.database.getCurrentXP())
            {
                this.XP.Columns.Clear();
                this.XP.AutoGenerateColumns = false;
                this.XP.DataSource = Program.db.database.getCurrentXP();

                this.XP.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Timestamp", DataPropertyName = "Timestamp", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
                this.XP.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Session", DataPropertyName = "Session", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
                this.XP.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Battle", DataPropertyName = "Battle", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
                this.XP.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Event", DataPropertyName = "Event", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
                //this.XP.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "XP", DataPropertyName = "XP", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });

                this.XP.Refresh();
                try
                {
                    this.XP.Sort(this.XP.Columns[0], ListSortDirection.Descending);
                }
                catch (Exception e)
                { }

                this.XP.AutoResizeColumns(DataGridViewAutoSizeColumnsMode.AllCells);
                currentxp = Program.db.database.getCurrentXP();
            }

        }

    }
}
