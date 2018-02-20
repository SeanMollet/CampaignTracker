using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
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
            //If they reload the monsters, update the form
            Program.mon_db.onMonstersUpdated += (object sender) => { DataBind(); };
            this.textBox1.Focus();
        }


        public void DataBind()
        {
            DataBind(Program.mon_db.Monsters);
        }
        public void DataBind(CampaignData.SortableBindingList<Monster> list)
        {
            List<string> VisibleColumns = new List<string>( new string[] { "Name", "Source", "Type", "Challenge", "Description" });

            this.dataGridView1.Columns.Clear();
            
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


                DataGridViewButtonColumn EncounterCol = new DataGridViewButtonColumn();
                EncounterCol.HeaderText = "Encounter";
                EncounterCol.Text = "Add";
                EncounterCol.UseColumnTextForButtonValue = true;

                this.dataGridView1.Columns.Add(EncounterCol);

                DataGridViewButtonColumn BattleCol = new DataGridViewButtonColumn();
                BattleCol.HeaderText = "Battle";
                BattleCol.Text = "Add";
                BattleCol.UseColumnTextForButtonValue = true;

                this.dataGridView1.Columns.Add(BattleCol);



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

        private void dataGridView1_CellDoubleClick(object sender, DataGridViewCellEventArgs e)
        {
            //Don't do this on the button column
            if (dataGridView1.Rows.Count > e.RowIndex &&
                dataGridView1.Rows[e.RowIndex].Cells.Count> e.ColumnIndex)
            {
                var Cell = dataGridView1.Rows[e.RowIndex].Cells[e.ColumnIndex];
                if(!(Cell is DataGridViewButtonCell))
                {
                    //So we're on a good row, now we just need the name column
                    foreach (DataGridViewColumn col in dataGridView1.Columns)
                    {
                        if(col.HeaderText == "Name")
                        {
                            var value = dataGridView1.Rows[e.RowIndex].Cells[col.Index].Value.ToString();
                            MonsterViewer viewer = new MonsterViewer(Program.mon_db.GetMonster(value).Clone());
                            viewer.Show();
                            break;
                        }
                    }
                }
            }
            
            //if (e.ColumnIndex>1)
            //{
            //    var rows = this.dataGridView1.SelectedRows;
            //    if (rows.Count > 0)
            //    {
            //        var cells = rows[0].Cells;
            //        if (cells.Count > 1)
            //        {
            //            var value = cells[2].Value.ToString();
            //            MonsterViewer viewer = new MonsterViewer(Program.mon_db.GetMonster(value).Clone());
            //            viewer.Show();
            //        }
            //    }
            //}

        }

        private void button1_Click(object sender, EventArgs e)
        {
            MonsterViewer viewer = new MonsterViewer(new Monster { Name = "New Monster" });
            viewer.Show();
        }

        private void ImportMonsters_Click(object sender, EventArgs e)
        {
            OpenFileDialog open = new OpenFileDialog();
            open.Filter = "Json Files (*.json)|*.json|All Files(*.*)|*.*";
            open.FilterIndex = 0;
            open.AddExtension = true;
            open.DefaultExt = "json";

            if(open.ShowDialog() == DialogResult.OK)
            {
                try
                {
                    bool? replace = null;

                    var data = File.ReadAllText(open.FileName);
                    var monsters = Newtonsoft.Json.JsonConvert.DeserializeObject<SortableBindingList<Monster>>(data);
                    foreach (var monster in monsters)
                    {
                        if (Program.db.database.CustomMonsters.IndexOf(monster) > -1)
                        {
                            if(!replace.HasValue)
                            {
                                if (MessageBox.Show("Overwrite existing with same name?", "Replace monsters?", MessageBoxButtons.YesNo) == DialogResult.Yes)
                                {
                                    replace = true;
                                }
                                else
                                {
                                    replace = false;
                                }
                            }
                            if (replace.HasValue && (bool) replace)
                            {
                                Program.db.database.CustomMonsters.RemoveAt(Program.db.database.CustomMonsters.IndexOf(monster));
                                Program.db.database.CustomMonsters.Add(monster.Clone());
                            }
                        }
                        else
                        {
                            Program.db.database.CustomMonsters.Add(monster.Clone());
                        }
                    }
                }
                catch(Exception E)
                {
                    MessageBox.Show(E.ToString(),"Error loading file");
                }
            }
        }

        private void ExportMonsters_Click(object sender, EventArgs e)
        {
            SaveFileDialog save = new SaveFileDialog();
            save.Filter = "Json Files (*.json)|*.json|All Files(*.*)|*.*";
            save.FilterIndex = 0;
            save.AddExtension = true;
            save.DefaultExt = "json";

            if (save.ShowDialog() == DialogResult.OK) {
                try
                {
                    var monsterjson = Newtonsoft.Json.JsonConvert.SerializeObject(Program.db.database.CustomMonsters,Newtonsoft.Json.Formatting.Indented);
                    File.WriteAllText(save.FileName, monsterjson);
                }
                catch (Exception E)
                {
                    MessageBox.Show(E.ToString(), "Error loading file");
                }
            }
        }
    }
}
