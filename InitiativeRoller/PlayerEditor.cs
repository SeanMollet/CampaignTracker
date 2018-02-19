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
    public partial class PlayerEditor : Form, DataBindReload
    {

        private static readonly IntPtr HWND_TOPMOST = new IntPtr(-1);
        private const UInt32 SWP_NOSIZE = 0x0001;
        private const UInt32 SWP_NOMOVE = 0x0002;
        private const UInt32 TOPMOST_FLAGS = SWP_NOMOVE | SWP_NOSIZE;
        [DllImport("user32.dll")]
        [return: MarshalAs(UnmanagedType.Bool)]
        public static extern bool SetWindowPos(IntPtr hWnd, IntPtr hWndInsertAfter, int X, int Y, int cx, int cy, uint uFlags);

        public PlayerEditor()
        {
            InitializeComponent();
            //data.onPlayersUpdated += Data_onPlayersUpdated;
            DataBind();

            SetWindowPos(this.Handle, HWND_TOPMOST, 0, 0, 0, 0, TOPMOST_FLAGS);
        }

        public void DataBind()
        {
            this.dataGridView1.DataSource = Program.db.database.Players;
            this.dataGridView1.Refresh();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            foreach (var player in Program.db.database.Players)
            {
                
                if (player.Adv)
                {
                    player.Roll = Dice.Roll(20, RollType.Advantage) + player.Stat;
                }
                else
                {
                    player.Roll = Dice.Roll(20, RollType.Normal) + player.Stat;
                }
            }

            this.dataGridView1.Sort(this.dataGridView1.Columns[2], ListSortDirection.Descending);
        }

    }


}
