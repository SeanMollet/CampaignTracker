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
using CampaignTracker.Controls;


namespace CampaignTracker
{
    public partial class MonsterViewer : Controls.BaseForm, DataBindReload
    {
        private Monster monster;

        public MonsterViewer(Monster Monster)
        {
            InitializeComponent();
            monster = Monster;
            DataBind();
        }
        public void DataBind()
        {
            if (monster != null && monster.Name != null)
            {
                ReadOnly(this, monster.ReadOnly);
                this.NameBox.DataBindings.Clear();
                this.NameBox.DataBindings.Add("Text", monster, "Name");
                this.Source.DataBindings.Clear();
                this.Source.DataBindings.Add("Text", monster, "Source");
                this.Type.DataBindings.Clear();
                this.Type.DataBindings.Add("Text", monster, "Type");
                this.SizeBox.DataBindings.Clear();
                this.SizeBox.DataBindings.Add("Text", monster, "Size.Value");
                this.Alignment.DataBindings.Clear();
                this.Alignment.DataBindings.Add("Text", monster, "Alignment");
                this.HPValue.DataBindings.Clear();
                this.HPValue.DataBindings.Add("Text", monster, "HP.Value");
                this.HPNotes.DataBindings.Clear();
                this.HPNotes.DataBindings.Add("Text", monster, "HP.Notes");
                this.ACValue.DataBindings.Clear();
                this.ACValue.DataBindings.Add("Text", monster, "AC.Value");
                this.ACNotes.DataBindings.Clear();
                this.ACNotes.DataBindings.Add("Text", monster, "AC.Notes");
                this.Initiative.DataBindings.Clear();
                this.Initiative.DataBindings.Add("Text", monster, "InitiativeModifier");
                this.Challenge.DataBindings.Clear();
                this.Challenge.DataBindings.Add("Text", monster, "Challenge.StringValue");

                this.Speed.DataSource = monster.Speed;
                this.Speed.Columns[0].DataPropertyName = "Value";

                this.Saves.DataSource = monster.Saves;
                

                this.DamageVuln.DataSource = monster.DamageVulnerabilities;
                this.DamageVuln.Columns[0].DataPropertyName = "Value";

                this.DamageImmune.DataSource = monster.DamageImmunities;
                this.DamageImmune.Columns[0].DataPropertyName = "Value";

                this.DamageRes.DataSource = monster.DamageResistances;
                this.DamageRes.Columns[0].DataPropertyName = "Value";

                this.ConditionImmune.DataSource = monster.ConditionImmunities;
                this.ConditionImmune.Columns[0].DataPropertyName = "Value";

                this.Skills.DataSource = monster.Skills;

                this.Senses.DataSource = monster.Senses;
                this.Senses.Columns[0].DataPropertyName = "Value";

                this.Traits.DataSource = monster.Traits;

                this.Languages.DataSource = monster.Languages;
                this.Languages.Columns[0].DataPropertyName = "Value";

                this.Actions.DataSource = monster.Actions;
                this.Reactions.DataSource = monster.Reactions;
                this.LegendaryActions.DataSource = monster.LegendaryActions;

                this.statDice1.DataBindings.Clear();

                this.statDice1.DataBindings.Add("DiceCount", monster, "HP.HitDiceCount", true, DataSourceUpdateMode.OnPropertyChanged);
                this.statDice1.DataBindings.Add("DiceSize", monster, "HP.HitDice", true, DataSourceUpdateMode.OnPropertyChanged);
                this.statDice1.DataBindings.Add("Modifier", monster, "HP.HitModifier",true,DataSourceUpdateMode.OnPropertyChanged);

                this.abilitiesControl1.Bind(monster.Abilities);
            }
        }

        public void ReadOnly(System.Windows.Forms.Control Container, bool value)
        {
            try
            {
                this.Save.Visible = !value;
                foreach (Control ctrl in Container.Controls)
                {
                    if (ctrl.GetType() == typeof(TextBox))
                    {
                        ((TextBox)ctrl).ReadOnly = value;
                    }
                    if (ctrl.GetType() == typeof(ComboBox))
                    {
                        ((ComboBox)ctrl).Enabled = !value;
                    }
                    if (ctrl.GetType() == typeof(CheckBox))
                    {
                        ((CheckBox)ctrl).Enabled = !value;
                    }
                    if (ctrl.GetType() == typeof(DataGridView))
                    {
                        ((DataGridView)ctrl).ReadOnly = value;
                        ((DataGridView)ctrl).AllowUserToAddRows = !value;
                        ((DataGridView)ctrl).AllowUserToDeleteRows = !value;
                        ((DataGridView)ctrl).RowHeadersVisible = !value;
                        ((DataGridView)ctrl).RowHeadersWidth = 20;
                        ((DataGridView)ctrl).ColumnHeadersVisible = !value;
                        ((DataGridView)ctrl).AutoResizeColumns();
                    }
                    if (ctrl.GetType() == typeof(DateTimePicker))
                    {
                        ((DateTimePicker)ctrl).Enabled = !value;
                    }
                    if (ctrl.GetType() == typeof(StatDice))
                    {
                        ((StatDice)ctrl).Enabled = !value;
                    }
                    if (ctrl.Controls.Count > 0)
                    {
                        ReadOnly(ctrl, value);
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.ToString());
            }
        }

        private void Save_Click(object sender, EventArgs e)
        {
            if (!monster.ReadOnly)
            {
                if (Program.db.database.CustomMonsters.IndexOf(monster)>-1)
                {
                    if (MessageBox.Show("Replace existing " + monster.Name + "?", "Overwrite Monster?", MessageBoxButtons.YesNo) == DialogResult.Yes)
                    {
                        var oldmonster = Program.db.database.CustomMonsters.IndexOf(monster);
                        Program.db.database.CustomMonsters.RemoveAt(oldmonster);
                        Program.db.database.CustomMonsters.Add(monster.Clone());
                    }
                }
                else
                {
                    Program.db.database.CustomMonsters.Add(monster.Clone());
                }
            }
        }

        private void HPButton_Click(object sender, EventArgs e)
        {
            this.HPValue.Text = this.statDice1.Roll.ToString();
        }

        private void abilitiesControl1_Load(object sender, EventArgs e)
        {

        }
    }
}
