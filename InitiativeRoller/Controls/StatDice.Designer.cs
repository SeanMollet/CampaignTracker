namespace CampaignTracker.Controls
{
    partial class StatDice
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Component Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify 
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.dicecount = new System.Windows.Forms.NumericUpDown();
            this.dicetype = new System.Windows.Forms.ComboBox();
            this.modifier = new System.Windows.Forms.NumericUpDown();
            ((System.ComponentModel.ISupportInitialize)(this.dicecount)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.modifier)).BeginInit();
            this.SuspendLayout();
            // 
            // dicecount
            // 
            this.dicecount.Location = new System.Drawing.Point(0, 0);
            this.dicecount.Name = "dicecount";
            this.dicecount.Size = new System.Drawing.Size(40, 20);
            this.dicecount.TabIndex = 0;
            // 
            // dicetype
            // 
            this.dicetype.FormattingEnabled = true;
            this.dicetype.Location = new System.Drawing.Point(40, 0);
            this.dicetype.Name = "dicetype";
            this.dicetype.Size = new System.Drawing.Size(45, 21);
            this.dicetype.TabIndex = 1;
            // 
            // modifier
            // 
            this.modifier.Location = new System.Drawing.Point(85, 0);
            this.modifier.Maximum = new decimal(new int[] {
            200,
            0,
            0,
            0});
            this.modifier.Minimum = new decimal(new int[] {
            200,
            0,
            0,
            -2147483648});
            this.modifier.Name = "modifier";
            this.modifier.Size = new System.Drawing.Size(40, 20);
            this.modifier.TabIndex = 3;
            // 
            // StatDice
            // 
            this.Controls.Add(this.modifier);
            this.Controls.Add(this.dicetype);
            this.Controls.Add(this.dicecount);
            this.MinimumSize = new System.Drawing.Size(125, 20);
            this.Name = "StatDice";
            this.Size = new System.Drawing.Size(125, 20);
            ((System.ComponentModel.ISupportInitialize)(this.dicecount)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.modifier)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.NumericUpDown dicecount;
        private System.Windows.Forms.ComboBox dicetype;
        private System.Windows.Forms.NumericUpDown modifier;
    }
}
