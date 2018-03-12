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
    public partial class PCView : Controls.BaseForm, DataBindReload
    {

        public PCView()
        {
            InitializeComponent();
            //data.onPlayersUpdated += Data_onPlayersUpdated;
            DataBind();
            SetAlwaysOnTop();
        }


        public void DataBind()
        {
            this.PlayerGrid.Columns.Clear();

            this.PlayerGrid.AutoGenerateColumns = false;
            this.PlayerGrid.DataSource = Program.db.database.Players;

            this.PlayerGrid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Name", DataPropertyName = "Name", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.PlayerGrid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Appearance", DataPropertyName = "Appearance", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.PlayerGrid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Mod", DataPropertyName = "Initiative", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.PlayerGrid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Roll", DataPropertyName = "Roll", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.PlayerGrid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Adv", DataPropertyName = "Adv", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });

        }

    }


}
