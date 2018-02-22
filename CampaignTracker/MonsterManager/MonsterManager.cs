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

            this.MonstersGrid.Columns.Clear();

            this.MonstersGrid.AutoGenerateColumns = false;
            this.MonstersGrid.DataSource = list;
            this.MonstersGrid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Name", DataPropertyName = "Name", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.MonstersGrid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Source", DataPropertyName = "Source", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCellsExceptHeader });
            this.MonstersGrid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Type", DataPropertyName = "DisplayType", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells, Resizable = DataGridViewTriState.True });
            this.MonstersGrid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Challenge", DataPropertyName = "Challenge", ReadOnly = true, AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCellsExceptHeader });
            

            DataGridViewButtonColumn EncounterCol = new DataGridViewButtonColumn();
            EncounterCol.HeaderText = "Add";
            EncounterCol.Text = "Encounter";
            EncounterCol.UseColumnTextForButtonValue = true;

            this.MonstersGrid.Columns.Add(EncounterCol);

            DataGridViewButtonColumn BattleCol = new DataGridViewButtonColumn();
            BattleCol.HeaderText = "Add";
            BattleCol.Text = "Battle";
            BattleCol.UseColumnTextForButtonValue = true;

            this.MonstersGrid.Columns.Add(BattleCol);

            this.MonstersGrid.AutoResizeColumns();


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
        private void MonstersGrid_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {
            //Only on the button columns
            if (e.RowIndex > -1 && e.ColumnIndex > -1 && MonstersGrid.Rows.Count > e.RowIndex &&
                MonstersGrid.Rows[e.RowIndex].Cells.Count > e.ColumnIndex )
            {
                var Cell = MonstersGrid.Rows[e.RowIndex].Cells[e.ColumnIndex];
                if (Cell is DataGridViewButtonCell)
                {
                    var row = MonstersGrid.Rows[e.RowIndex];
                    if (row.DataBoundItem is Monster)
                    {
                        var monster = (Monster)row.DataBoundItem;
                        //Check which one clicked us
                        if (Cell.Value.ToString() == "Encounter")
                        {
                            if (Program.Active_encounter != null)
                            {
                                Program.Active_encounter.monsters.Add(monster.Clone());
                            }
                        }
                        else
                        {
                            if (Program.Active_battle != null)
                            {
                                Program.Active_battle.AddMonster(monster);
                            }
                        }


                    }
                }
            }
        }
        private void dataGridView1_CellDoubleClick(object sender, DataGridViewCellEventArgs e)
        {
            //Don't do this on the button column
            if (e.RowIndex > -1 && e.ColumnIndex > -1 && MonstersGrid.Rows.Count > e.RowIndex &&
                MonstersGrid.Rows[e.RowIndex].Cells.Count> e.ColumnIndex)
            {
                var Cell = MonstersGrid.Rows[e.RowIndex].Cells[e.ColumnIndex];
                if(!(Cell is DataGridViewButtonCell))
                {
                    var row = MonstersGrid.Rows[e.RowIndex];
                    if (row.DataBoundItem is Monster)
                    {
                        var monster = ((Monster)row.DataBoundItem).Clone();
                        MonsterViewer viewer = new MonsterViewer(monster);
                        viewer.Show();
                    }

                }
            }


        }

        private void button1_Click(object sender, EventArgs e)
        {
            MonsterViewer viewer = new MonsterViewer(new Monster { Name = "New Monster" });
            viewer.Show();
        }

        private void ImportMonsters_Click(object sender, EventArgs e)
        {
            OpenFileDialog open = new OpenFileDialog();
            open.Filter = "Monster Files (*.ctmo)|*.ctmo|All Files(*.*)|*.*";
            open.FilterIndex = 0;
            open.AddExtension = true;
            open.DefaultExt = "ctmo";

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
                                Program.db.database.CustomMonsters.Add(monster);
                            }
                        }
                        else
                        {
                            Program.db.database.CustomMonsters.Add(monster);
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
            save.Filter = "Monster Files (*.ctmo)|*.ctmo|All Files(*.*)|*.*";
            save.FilterIndex = 0;
            save.AddExtension = true;
            save.DefaultExt = "ctmo";

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
