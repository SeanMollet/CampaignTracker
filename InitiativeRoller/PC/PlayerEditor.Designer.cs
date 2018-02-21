namespace CampaignTracker
{
    partial class PlayerEditor
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
            this.PlayerGrid = new System.Windows.Forms.DataGridView();
            this.button1 = new System.Windows.Forms.Button();
            this.DmgButton = new System.Windows.Forms.Button();
            this.numericUpDown1 = new System.Windows.Forms.NumericUpDown();
            this.HealButton = new System.Windows.Forms.Button();
            this.DmgAll = new System.Windows.Forms.Button();
            this.HealAll = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.PlayerGrid)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDown1)).BeginInit();
            this.SuspendLayout();
            // 
            // PlayerGrid
            // 
            this.PlayerGrid.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.PlayerGrid.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.AllCells;
            this.PlayerGrid.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.PlayerGrid.Location = new System.Drawing.Point(13, 13);
            this.PlayerGrid.Name = "PlayerGrid";
            this.PlayerGrid.Size = new System.Drawing.Size(630, 235);
            this.PlayerGrid.TabIndex = 0;
            // 
            // button1
            // 
            this.button1.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.button1.Location = new System.Drawing.Point(13, 254);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(630, 23);
            this.button1.TabIndex = 1;
            this.button1.Text = "Roll Initiative";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // DmgButton
            // 
            this.DmgButton.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.DmgButton.Location = new System.Drawing.Point(70, 283);
            this.DmgButton.Name = "DmgButton";
            this.DmgButton.Size = new System.Drawing.Size(42, 23);
            this.DmgButton.TabIndex = 3;
            this.DmgButton.Text = "Dmg";
            this.DmgButton.UseVisualStyleBackColor = true;
            this.DmgButton.Click += new System.EventHandler(this.DmgButton_Click);
            // 
            // numericUpDown1
            // 
            this.numericUpDown1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.numericUpDown1.Location = new System.Drawing.Point(13, 284);
            this.numericUpDown1.Maximum = new decimal(new int[] {
            400,
            0,
            0,
            0});
            this.numericUpDown1.Minimum = new decimal(new int[] {
            400,
            0,
            0,
            -2147483648});
            this.numericUpDown1.Name = "numericUpDown1";
            this.numericUpDown1.Size = new System.Drawing.Size(51, 20);
            this.numericUpDown1.TabIndex = 2;
            // 
            // HealButton
            // 
            this.HealButton.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.HealButton.Location = new System.Drawing.Point(118, 283);
            this.HealButton.Name = "HealButton";
            this.HealButton.Size = new System.Drawing.Size(42, 23);
            this.HealButton.TabIndex = 4;
            this.HealButton.Text = "Heal";
            this.HealButton.UseVisualStyleBackColor = true;
            this.HealButton.Click += new System.EventHandler(this.HealButton_Click);
            // 
            // DmgAll
            // 
            this.DmgAll.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.DmgAll.Location = new System.Drawing.Point(505, 283);
            this.DmgAll.Name = "DmgAll";
            this.DmgAll.Size = new System.Drawing.Size(66, 23);
            this.DmgAll.TabIndex = 5;
            this.DmgAll.Text = "Dmg All";
            this.DmgAll.UseVisualStyleBackColor = true;
            this.DmgAll.Click += new System.EventHandler(this.DmgAll_Click);
            // 
            // HealAll
            // 
            this.HealAll.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.HealAll.Location = new System.Drawing.Point(577, 283);
            this.HealAll.Name = "HealAll";
            this.HealAll.Size = new System.Drawing.Size(66, 23);
            this.HealAll.TabIndex = 6;
            this.HealAll.Text = "Heal All";
            this.HealAll.UseVisualStyleBackColor = true;
            this.HealAll.Click += new System.EventHandler(this.HealAll_Click);
            // 
            // PlayerEditor
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(659, 312);
            this.Controls.Add(this.HealAll);
            this.Controls.Add(this.DmgAll);
            this.Controls.Add(this.HealButton);
            this.Controls.Add(this.numericUpDown1);
            this.Controls.Add(this.DmgButton);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.PlayerGrid);
            this.MinimumSize = new System.Drawing.Size(675, 350);
            this.Name = "PlayerEditor";
            this.Text = "Player Manager";
            ((System.ComponentModel.ISupportInitialize)(this.PlayerGrid)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.numericUpDown1)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.DataGridView PlayerGrid;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.Button DmgButton;
        private System.Windows.Forms.NumericUpDown numericUpDown1;
        private System.Windows.Forms.Button HealButton;
        private System.Windows.Forms.Button DmgAll;
        private System.Windows.Forms.Button HealAll;
    }
}

