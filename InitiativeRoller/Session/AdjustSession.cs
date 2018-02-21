using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace CampaignTracker
{
    public partial class AdjustSession : Controls.BaseForm
    {
        public AdjustSession()
        {
            InitializeComponent();
            this.textBox1.Text = Program.db.database.Session.ToString();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            Program.db.database.Session++;
            this.textBox1.Text = Program.db.database.Session.ToString();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            Program.db.database.Session--;
            this.textBox1.Text = Program.db.database.Session.ToString();

        }
    }
}
