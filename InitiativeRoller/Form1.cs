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



namespace InitiativeRoller
{
    public partial class Form1 : Form
    {
        private BindingList<Player> players;
        private Random dice;

        private static readonly IntPtr HWND_TOPMOST = new IntPtr(-1);
        private const UInt32 SWP_NOSIZE = 0x0001;
        private const UInt32 SWP_NOMOVE = 0x0002;
        private const UInt32 TOPMOST_FLAGS = SWP_NOMOVE | SWP_NOSIZE;
        [DllImport("user32.dll")]
        [return: MarshalAs(UnmanagedType.Bool)]
        public static extern bool SetWindowPos(IntPtr hWnd, IntPtr hWndInsertAfter, int X, int Y, int cx, int cy, uint uFlags);

        public Form1()
        {
            InitializeComponent();
            players = new BindingList<Player>();
            dice = new Random();
            BindGrid(players);

            SetWindowPos(this.Handle, HWND_TOPMOST, 0, 0, 0, 0, TOPMOST_FLAGS);
        }


        private void button3_Click(object sender, EventArgs e)
        {
            SaveFileDialog dlg = new SaveFileDialog();
            dlg.DefaultExt = "json";
            dlg.Filter = "*.json|*.*";
            dlg.FilterIndex = 0;

            if (dlg.ShowDialog() == DialogResult.OK)
            {
                try
                {
                    string json = Newtonsoft.Json.JsonConvert.SerializeObject(players);
                    File.WriteAllText(dlg.FileName, json);
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
            dlg.DefaultExt = "json";
            if (dlg.ShowDialog() == DialogResult.OK)
            {
                try
                {
                    string json = File.ReadAllText(dlg.FileName);
                    var result = Newtonsoft.Json.JsonConvert.DeserializeObject<BindingList<Player>>(json);
                    this.players = result;
                    BindGrid(result);
                    this.dataGridView1.Refresh();
                    this.textBox1.Text = dlg.FileName;
                }
                catch(Exception E)
                {
                    MessageBox.Show(E.ToString(), "Error loading game");
                }
            }
        }

        private void BindGrid(BindingList<Player> source)
        {
            this.dataGridView1.DataSource = source;

            this.dataGridView1.Columns[1].Width = 40;
            this.dataGridView1.Columns[2].Width = 40;
            this.dataGridView1.Columns[3].Width = 30;

            this.dataGridView1.Columns[0].Width = this.dataGridView1.Width - 125;
            this.dataGridView1.Refresh();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            Random rand = new Random();
            foreach (var player in players)
            {
                if (player.Adv)
                {
                    int first = Roll(20);
                    int second = Roll(20);
                    if (first > second)
                    {
                        player.Roll = first + player.Stat;
                    }
                    else
                    {
                        player.Roll = second + player.Stat;
                    }
                }
                else
                {
                    player.Roll = Roll(20) + player.Stat;
                }
            }

            List<Player> sort = new List<Player>();
            sort.AddRange(players);
            sort.Sort();
            sort.Reverse();
            players.Clear();
            foreach (var player in sort)
            {
                players.Add(player);
            }
            BindGrid(players);
        }


        private int Roll(int D)
        {
            return dice.Next(1, D+1);
        }
    }


    public class Player : IComparable<Player>
    {
        public string Name { get; set; }
        public int Stat { get; set; }
        public int Roll { get; set; }
        public bool Adv { get; set; }
        public int CompareTo(Player comparison)
        {
            if (comparison == null)
            {
                return -1;
            }
            if(comparison.Roll == this.Roll)
            {
                return this.Stat.CompareTo(comparison.Stat);
            }
            return this.Roll.CompareTo(comparison.Roll);
        }
    }
}
