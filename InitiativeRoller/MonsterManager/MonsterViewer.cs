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
                this.Speed.DataSource = monster.Speed;


                this.abilitiesControl1.Bind(monster.Abilities);
            }
        }

        public void ReadOnly(System.Windows.Forms.Control Container, bool value)
        {
            try
            {
                foreach (Control ctrl in Container.Controls)
                {
                    if (ctrl.GetType() == typeof(TextBox))
                        ((TextBox)ctrl).ReadOnly = value;
                    if (ctrl.GetType() == typeof(ComboBox))
                        ((ComboBox)ctrl).Enabled = !value;
                    if (ctrl.GetType() == typeof(CheckBox))
                        ((CheckBox)ctrl).Enabled = !value;
                    if(ctrl.GetType() == typeof(DataGridView))
                        ((DataGridView)ctrl).ReadOnly = value;
                    if (ctrl.GetType() == typeof(DateTimePicker))
                        ((DateTimePicker)ctrl).Enabled = !value;

                    if (ctrl.Controls.Count > 0)
                        ReadOnly(ctrl,value);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.ToString());
            }
        }

        private void label13_Click(object sender, EventArgs e)
        {

        }
    }
}
