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
        }


        public void DataBind()
        {
            if (Program.active_battle != null)
            {
                this.Monsters.Columns.Clear();

                this.Monsters.AutoGenerateColumns = false;
                
                this.Monsters.DataSource = Program.active_battle.monsters;
                this.Monsters.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "ID", DataPropertyName = "Index", ReadOnly = true,AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells});
                this.Monsters.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Name", DataPropertyName = "Name", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
                this.Monsters.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Type", DataPropertyName = "Type", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
                this.Monsters.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Appearance", DataPropertyName = "Appearance", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });

                this.Monsters.Refresh();
                //this.Monsters.AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.AllCells;

                this.Monsters.AutoResizeColumns(DataGridViewAutoSizeColumnsMode.AllCells);

                this.XP.DataSource = Program.active_battle.XP;
                pollingTimer.Stop();
            }
            else
            {
                pollingTimer.Start();
            }
        }
    }
}
