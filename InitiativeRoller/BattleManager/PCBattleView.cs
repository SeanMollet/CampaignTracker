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
        Timer pollingTimer;
        public PCBattleView()
        {
            InitializeComponent();
            pollingTimer = new Timer();
            pollingTimer.Interval = 1000;
            pollingTimer.Tick += (object sender, EventArgs e) => { DataBind(); };

            DataBind();
            SetAlwaysOnTop();
            Program.onActiveBattleChanged += (object sender) => { DataBind(); };

        }

        public void DataBind()
        {
            if (Program.Active_battle != null)
            {
                this.Monsters.Columns.Clear();

                this.Monsters.AutoGenerateColumns = false;
                
                this.Monsters.DataSource = Program.Active_battle.monsters;
                this.Monsters.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "ID", DataPropertyName = "Index", ReadOnly = true,AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells});
                this.Monsters.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Name", DataPropertyName = "Name", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
                this.Monsters.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Type", DataPropertyName = "Type", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
                this.Monsters.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Appearance", DataPropertyName = "Appearance", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });

                this.Monsters.Refresh();
                //this.Monsters.AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.AllCells;

                this.Monsters.AutoResizeColumns(DataGridViewAutoSizeColumnsMode.AllCells);

                this.XP.AutoGenerateColumns = false;
                this.XP.DataSource = Program.Active_battle.XP;

                this.XP.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Timestamp", DataPropertyName = "Timestamp", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
                this.XP.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Session", DataPropertyName = "Session", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
                this.XP.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Battle", DataPropertyName = "Battle", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
                this.XP.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Event", DataPropertyName = "Event", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
                this.XP.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "XP", DataPropertyName = "XP", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });

                this.XP.Refresh();
                this.XP.Sort(this.XP.Columns[0], ListSortDirection.Descending);

                this.XP.AutoResizeColumns(DataGridViewAutoSizeColumnsMode.AllCells);

                pollingTimer.Stop();
            }
            else
            {
                pollingTimer.Start();
            }

        }

    }
}
