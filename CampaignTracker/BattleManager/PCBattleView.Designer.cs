namespace CampaignTracker
{
    partial class PCBattleView
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

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.splitContainer1 = new System.Windows.Forms.SplitContainer();
            this.Monsters = new System.Windows.Forms.DataGridView();
            this.XP = new System.Windows.Forms.DataGridView();
            ((System.ComponentModel.ISupportInitialize)(this.splitContainer1)).BeginInit();
            this.splitContainer1.Panel1.SuspendLayout();
            this.splitContainer1.Panel2.SuspendLayout();
            this.splitContainer1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.Monsters)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.XP)).BeginInit();
            this.SuspendLayout();
            // 
            // splitContainer1
            // 
            this.splitContainer1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.splitContainer1.Location = new System.Drawing.Point(0, 0);
            this.splitContainer1.Name = "splitContainer1";
            this.splitContainer1.Orientation = System.Windows.Forms.Orientation.Horizontal;
            // 
            // splitContainer1.Panel1
            // 
            this.splitContainer1.Panel1.Controls.Add(this.Monsters);
            // 
            // splitContainer1.Panel2
            // 
            this.splitContainer1.Panel2.Controls.Add(this.XP);
            this.splitContainer1.Size = new System.Drawing.Size(484, 362);
            this.splitContainer1.SplitterDistance = 178;
            this.splitContainer1.TabIndex = 0;
            // 
            // Monsters
            // 
            this.Monsters.AllowUserToAddRows = false;
            this.Monsters.AllowUserToDeleteRows = false;
            this.Monsters.AutoSizeRowsMode = System.Windows.Forms.DataGridViewAutoSizeRowsMode.AllCells;
            this.Monsters.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.Monsters.Dock = System.Windows.Forms.DockStyle.Fill;
            this.Monsters.Location = new System.Drawing.Point(0, 0);
            this.Monsters.Name = "Monsters";
            this.Monsters.RowHeadersVisible = false;
            this.Monsters.Size = new System.Drawing.Size(484, 178);
            this.Monsters.TabIndex = 6;
            // 
            // XP
            // 
            this.XP.AllowUserToAddRows = false;
            this.XP.AllowUserToDeleteRows = false;
            this.XP.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.XP.Dock = System.Windows.Forms.DockStyle.Fill;
            this.XP.Location = new System.Drawing.Point(0, 0);
            this.XP.Name = "XP";
            this.XP.RowHeadersVisible = false;
            this.XP.Size = new System.Drawing.Size(484, 180);
            this.XP.TabIndex = 0;
            // 
            // PCBattleView
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(484, 362);
            this.Controls.Add(this.splitContainer1);
            this.MinimumSize = new System.Drawing.Size(500, 400);
            this.Name = "PCBattleView";
            this.Text = "Battle View";
            this.splitContainer1.Panel1.ResumeLayout(false);
            this.splitContainer1.Panel2.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.splitContainer1)).EndInit();
            this.splitContainer1.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.Monsters)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.XP)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.SplitContainer splitContainer1;
        private System.Windows.Forms.DataGridView XP;
        private System.Windows.Forms.DataGridView Monsters;
    }
}