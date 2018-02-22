using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace CampaignTracker { 
    public partial class RollOMatic : Controls.BaseForm
    {
        public RollOMatic()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (this.checkBox1.Checked)
            {
                StringBuilder sb = new StringBuilder();
                if (this.statDice1.DiceCount > 0)
                    sb.AppendLine("Box 1 (D"+this.statDice1.DiceSize.ToString()+"): " + this.statDice1.RollAllString + " ");
                if (this.statDice2.DiceCount > 0)
                    sb.AppendLine("Box 2 (D" + this.statDice1.DiceSize.ToString() + "): " + this.statDice2.RollAllString + " ");
                if (this.statDice3.DiceCount > 0)
                    sb.AppendLine("Box 3 (D" + this.statDice1.DiceSize.ToString() + "): " + this.statDice3.RollAllString + " ");
                if (this.statDice4.DiceCount > 0)
                    sb.AppendLine("Box 4 (D" + this.statDice1.DiceSize.ToString() + "): " + this.statDice4.RollAllString + " ");
                if (this.statDice5.DiceCount > 0)
                    sb.AppendLine("Box 5 (D" + this.statDice1.DiceSize.ToString() + "): " + this.statDice5.RollAllString + " ");
                if (this.statDice6.DiceCount > 0)
                    sb.AppendLine("Box 6 (D" + this.statDice1.DiceSize.ToString() + "): " + this.statDice6.RollAllString + " ");
                if (this.statDice7.DiceCount > 0)
                    sb.AppendLine("Box 7 (D" + this.statDice1.DiceSize.ToString() + "): " + this.statDice7.RollAllString + " ");
                if (this.statDice8.DiceCount > 0)
                    sb.AppendLine("Box 8 (D" + this.statDice1.DiceSize.ToString() + "): " + this.statDice8.RollAllString + " ");
                this.textBox1.Text = sb.ToString();
            }
            else
            {
                StringBuilder sb = new StringBuilder();
                if (this.statDice1.DiceCount > 0)
                    sb.AppendLine("Box 1 (D" + this.statDice1.DiceSize.ToString() + "): " + this.statDice1.Roll.ToString() + " ");
                if (this.statDice2.DiceCount > 0)
                    sb.AppendLine("Box 2 (D" + this.statDice1.DiceSize.ToString() + "): " + this.statDice2.Roll.ToString() + " ");
                if (this.statDice3.DiceCount > 0)
                    sb.AppendLine("Box 3 (D" + this.statDice1.DiceSize.ToString() + "): " + this.statDice3.Roll.ToString() + " ");
                if (this.statDice4.DiceCount > 0)
                    sb.AppendLine("Box 4 (D" + this.statDice1.DiceSize.ToString() + "): " + this.statDice4.Roll.ToString() + " ");
                if (this.statDice5.DiceCount > 0)
                    sb.AppendLine("Box 5 (D" + this.statDice1.DiceSize.ToString() + "): " + this.statDice5.Roll.ToString() + " ");
                if (this.statDice6.DiceCount > 0)
                    sb.AppendLine("Box 6 (D" + this.statDice1.DiceSize.ToString() + "): " + this.statDice6.Roll.ToString() + " ");
                if (this.statDice7.DiceCount > 0)
                    sb.AppendLine("Box 7 (D" + this.statDice1.DiceSize.ToString() + "): " + this.statDice7.Roll.ToString() + " ");
                if (this.statDice8.DiceCount > 0)
                    sb.AppendLine("Box 8 (D" + this.statDice1.DiceSize.ToString() + "): " + this.statDice8.Roll.ToString() + " ");

                this.textBox1.Text = sb.ToString();
            }
            this.textBox1.Text += Environment.NewLine;
        }
    }
}
