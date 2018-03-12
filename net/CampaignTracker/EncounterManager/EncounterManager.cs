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
    public partial class EncounterManager : Controls.BaseForm, DataBindReload
    {
        public EncounterManager()
        {
            InitializeComponent();
            DataBind();
            Program.db.onEncountersUpdated += (object sender) => { DataBind(); };
        }


        public void DataBind()
        {
            DataBind(Program.db.database.Encounters);
        }
        public void DataBind(CampaignData.SortableBindingList<Encounter> list)
        {
            List<string> VisibleColumns = new List<string>(new string[] { "Name", "Challenge", "Description" });

            //this.EncounterList.Columns.Clear();

            this.EncounterList.DataSource = list;
            //Hide them all to start
            if (this.EncounterList.Columns.Count > 0)
            {
                for (int a = 0; a < this.EncounterList.ColumnCount; a++)
                {
                    //Enable the ones we want
                    if (!VisibleColumns.Contains(this.EncounterList.Columns[a].Name))
                    {
                        this.EncounterList.Columns[a].Visible = false;
                    }
                }
            }


            DataGridViewButtonColumn BattleCol = new DataGridViewButtonColumn();
            BattleCol.HeaderText = "Start";
            BattleCol.Text = "Battle";
            BattleCol.UseColumnTextForButtonValue = true;

            this.EncounterList.Columns.Add(BattleCol);





        }

        private void NewButton_Click(object sender, EventArgs e)
        {
            Encounter encounter = new Encounter { Name = "New Encounter" };
            EncounterViewer viewer = new EncounterViewer(encounter);
            viewer.Show();

        }
        
        private void ImportButton_Click(object sender, EventArgs e)
        {
            OpenFileDialog open = new OpenFileDialog();
            open.Filter = "Ecounter Files (*.cten)|*.cten|All Files(*.*)|*.*";
            open.FilterIndex = 0;
            open.AddExtension = true;
            open.DefaultExt = "cten";

            if (open.ShowDialog() == DialogResult.OK)
            {
                try
                {
                    bool? replace = null;

                    var data = File.ReadAllText(open.FileName);
                    var encounters = Newtonsoft.Json.JsonConvert.DeserializeObject<SortableBindingList<Encounter>>(data);
                    foreach (var encounter in encounters)
                    {
                        if (Program.db.database.Encounters.IndexOf(encounter) > -1)
                        {
                            if (!replace.HasValue)
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
                            if (replace.HasValue && (bool)replace)
                            {
                                Program.db.database.Encounters.RemoveAt(Program.db.database.Encounters.IndexOf(encounter));
                                Program.db.database.Encounters.Add(encounter.Clone());
                            }
                        }
                        else
                        {
                            Program.db.database.Encounters.Add(encounter.Clone());
                        }
                    }
                }
                catch (Exception E)
                {
                    MessageBox.Show(E.ToString(), "Error loading file");
                }
            }
        }

        private void ExportButton_Click(object sender, EventArgs e)
        {
            SaveFileDialog save = new SaveFileDialog();
            save.Filter = "Ecounter Files (*.cten)|*.cten|All Files(*.*)|*.*";
            save.FilterIndex = 0;
            save.AddExtension = true;
            save.DefaultExt = "cten";

            if (save.ShowDialog() == DialogResult.OK)
            {
                try
                {
                    var encounterjson = Newtonsoft.Json.JsonConvert.SerializeObject(Program.db.database.Encounters, Newtonsoft.Json.Formatting.Indented);
                    File.WriteAllText(save.FileName, encounterjson);
                }
                catch (Exception E)
                {
                    MessageBox.Show(E.ToString(), "Error loading file");
                }
            }
        }


        private void EncounterList_CellDoubleClick(object sender, DataGridViewCellEventArgs e)
        {
            //Don't do this on the button column
            if (e.RowIndex > -1 && e.ColumnIndex > -1 && EncounterList.Rows.Count > e.RowIndex &&
                EncounterList.Rows[e.RowIndex].Cells.Count > e.ColumnIndex )
            {
                var Cell = EncounterList.Rows[e.RowIndex].Cells[e.ColumnIndex];
                if (!(Cell is DataGridViewButtonCell))
                {
                    var row = EncounterList.Rows[e.RowIndex];
                    if (row.DataBoundItem is Encounter)
                    {
                        var encounterview = (Encounter)row.DataBoundItem;
                        EncounterViewer viewer = new EncounterViewer(encounterview.Clone());
                        viewer.Show();
                    }

                }
            }

        }

        private void EncounterList_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {
            //do this on the button column
            if (e.RowIndex > -1 && e.ColumnIndex > -1 && EncounterList.Rows.Count > e.RowIndex &&
                EncounterList.Rows[e.RowIndex].Cells.Count > e.ColumnIndex)
            {
                var Cell = EncounterList.Rows[e.RowIndex].Cells[e.ColumnIndex];
                if (Cell is DataGridViewButtonCell)
                {
                    var row = EncounterList.Rows[e.RowIndex];
                    if (row.DataBoundItem is Encounter)
                    {
                        
                        var encounterview = (Encounter)row.DataBoundItem;
                        Battle battle = new Battle(Program.db.database.Session, Program.db.database.Battles.Count + 1,encounterview.monsters);
                        Program.db.database.Battles.Add(battle);

                        BattleViewer viewer = new BattleViewer(battle);
                        viewer.Show();
                    }

                }
            }
        }
    }
}
