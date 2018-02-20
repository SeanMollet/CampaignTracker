using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using CampaignData;

namespace CampaignTracker.Controls
{
    public partial class AbilitiesControl : UserControl
    {
        private Abilities abilities;
        public AbilitiesControl()
        {
            InitializeComponent();
            this.Str.TextChanged += (object sender, EventArgs e) => { this.StrBon.Text = Bonus(((TextBox)sender).Text); };
            this.Dex.TextChanged += (object sender, EventArgs e) => { this.DexBon.Text = Bonus(((TextBox)sender).Text); };
            this.Con.TextChanged += (object sender, EventArgs e) => { this.ConBon.Text = Bonus(((TextBox)sender).Text); };
            this.Int.TextChanged += (object sender, EventArgs e) => { this.IntBon.Text = Bonus(((TextBox)sender).Text); };
            this.Wis.TextChanged += (object sender, EventArgs e) => { this.WisBon.Text = Bonus(((TextBox)sender).Text); };
            this.Cha.TextChanged += (object sender, EventArgs e) => { this.ChaBon.Text = Bonus(((TextBox)sender).Text); };
        }

        private bool readOnly = false;
        public bool ReadOnly
        {
            get => readOnly; set
            {
                readOnly = value;
                this.Str.ReadOnly = readOnly;
                this.Dex.ReadOnly = readOnly;
                this.Con.ReadOnly = readOnly;
                this.Int.ReadOnly = readOnly;
                this.Wis.ReadOnly = readOnly;
                this.Cha.ReadOnly = readOnly;
            }
        }
        public void Bind(Abilities Abilities)
        {
            abilities = Abilities;
            DataBind();
        }

        private void DataBind()
        {
            if (abilities != null)
            {
                this.Str.DataBindings.Clear();
                this.Str.DataBindings.Add("Text", abilities, "Str");
                this.Dex.DataBindings.Clear();
                this.Dex.DataBindings.Add("Text", abilities, "Dex");
                this.Con.DataBindings.Clear();
                this.Con.DataBindings.Add("Text", abilities, "Con");

                this.Int.DataBindings.Clear();
                this.Int.DataBindings.Add("Text", abilities, "Int");
                this.Wis.DataBindings.Clear();
                this.Wis.DataBindings.Add("Text", abilities, "Wis");
                this.Cha.DataBindings.Clear();
                this.Cha.DataBindings.Add("Text", abilities, "Cha");
            }
        }

        private string Bonus(string stat)
        {
            int value = 0;
            int.TryParse(stat.Trim(), out value);

            value = (value - 10) / 2;
            if (value < 0)
            {
                return value.ToString();
            }
            else
            {
                return "+"+value.ToString();
            }
        }
    }

}
