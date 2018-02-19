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


namespace InitiativeRoller
{
    public partial class Initiative : Form
    {
        public DatabaseManager data = new DatabaseManager();

        private static readonly IntPtr HWND_TOPMOST = new IntPtr(-1);
        private const UInt32 SWP_NOSIZE = 0x0001;
        private const UInt32 SWP_NOMOVE = 0x0002;
        private const UInt32 TOPMOST_FLAGS = SWP_NOMOVE | SWP_NOSIZE;
        [DllImport("user32.dll")]
        [return: MarshalAs(UnmanagedType.Bool)]
        public static extern bool SetWindowPos(IntPtr hWnd, IntPtr hWndInsertAfter, int X, int Y, int cx, int cy, uint uFlags);

        public Initiative()
        {
            InitializeComponent();
            data.onPlayersUpdated += Data_onPlayersUpdated;
            BindGrid();

            SetWindowPos(this.Handle, HWND_TOPMOST, 0, 0, 0, 0, TOPMOST_FLAGS);
        }

        private void Data_onPlayersUpdated(object sender)
        {
            BindGrid();
        }

        private void button3_Click(object sender, EventArgs e)
        {
            SaveFileDialog dlg = new SaveFileDialog();
            dlg.DefaultExt = "dddb";
            dlg.Filter = "*.dddb|*.*";
            dlg.FilterIndex = 0;

            if (dlg.ShowDialog() == DialogResult.OK)
            {
                try
                {
                    data.SaveFile(dlg.FileName);
                }
                catch (Exception E)
                {
                    MessageBox.Show(E.ToString(), "Error saving game");
                }
            }
        }

        private void button2_Click(object sender, EventArgs e)
        {
            OpenFileDialog dlg = new OpenFileDialog();
            dlg.DefaultExt = "dddb";
            if (dlg.ShowDialog() == DialogResult.OK)
            {
                try
                {
                    data.LoadFile(dlg.FileName);
                    this.textBox1.Text = dlg.FileName;
                }
                catch(Exception E)
                {
                    MessageBox.Show(E.ToString(), "Error loading game");
                }
            }
        }

        private void BindGrid()
        {
            this.dataGridView1.DataSource = data.database.Players;

            if (this.dataGridView1.Columns.Count > 0)
            {
                this.dataGridView1.Columns[1].Width = 40;
                this.dataGridView1.Columns[2].Width = 40;
                this.dataGridView1.Columns[3].Width = 30;

                this.dataGridView1.Columns[0].Width = this.dataGridView1.Width - 125;
            }
            this.dataGridView1.Refresh();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            Random rand = new Random();
            foreach (var player in data.database.Players)
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
