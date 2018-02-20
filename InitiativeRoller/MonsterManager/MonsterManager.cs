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
    public partial class MonsterManager : Controls.BaseForm, DataBindReload
    {
        public MonsterManager()
        {
            InitializeComponent();
            DataBind();

            this.textBox1.Focus();
        }
        public void DataBind()
        {
            DataBind(Program.mon_db.Monsters);
        }
        public void DataBind(CampaignData.SortableBindingList<Monster> list)
        {
            List<string> VisibleColumns = new List<string>( new string[] { "Name", "Source", "Type", "Challenge", "Description" });


            this.dataGridView1.DataSource = list;
            //Hide them all to start
            if (this.dataGridView1.Columns.Count > 0)
            {
                for (int a = 0; a < this.dataGridView1.ColumnCount; a++)
                {
                    //Enable the ones we want
                    if (!VisibleColumns.Contains(this.dataGridView1.Columns[a].Name))
                    {
                        this.dataGridView1.Columns[a].Visible = false;
                    }
                }
            }

        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {
            var search = this.textBox1.Text.Trim();

            if (search.Length>0)
            {
                var list = Program.mon_db.SearchForMonsters(search);
                DataBind(list);
            }
            else
            {
                DataBind();
            }
        }

        private void dataGridView1_DoubleClick(object sender, EventArgs e)
        {
            var rows = this.dataGridView1.SelectedRows;
            if (rows.Count > 0)
            {
                var cells = rows[0].Cells;
                if (cells.Count > 0)
                {
                    var value = cells[0].Value.ToString();
                    MonsterViewer viewer = new MonsterViewer(Program.mon_db.GetMonster(value));
                    viewer.Show();
                }
            }
        }
    }
}
