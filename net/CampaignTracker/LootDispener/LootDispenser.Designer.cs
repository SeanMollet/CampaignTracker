namespace CampaignTracker
{
    partial class LootDispenser
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
            this.LootMonstersGrid = new System.Windows.Forms.DataGridView();
            this.button1 = new System.Windows.Forms.Button();
            this.button2 = new System.Windows.Forms.Button();
            this.LootGrid = new System.Windows.Forms.DataGridView();
            ((System.ComponentModel.ISupportInitialize)(this.LootMonstersGrid)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.LootGrid)).BeginInit();
            this.SuspendLayout();
            // 
            // LootMonstersGrid
            // 
            this.LootMonstersGrid.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.LootMonstersGrid.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.LootMonstersGrid.Location = new System.Drawing.Point(3, 31);
            this.LootMonstersGrid.Name = "LootMonstersGrid";
            this.LootMonstersGrid.RowHeadersWidth = 20;
            this.LootMonstersGrid.Size = new System.Drawing.Size(378, 192);
            this.LootMonstersGrid.TabIndex = 0;
            this.LootMonstersGrid.CellParsing += new System.Windows.Forms.DataGridViewCellParsingEventHandler(this.LootMonstersGrid_CellParsing);
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(3, 2);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(106, 23);
            this.button1.TabIndex = 1;
            this.button1.Text = "Roll";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // button2
            // 
            this.button2.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.button2.Location = new System.Drawing.Point(275, 2);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(106, 23);
            this.button2.TabIndex = 2;
            this.button2.Text = "Send to Party";
            this.button2.UseVisualStyleBackColor = true;
            this.button2.Click += new System.EventHandler(this.button2_Click);
            // 
            // LootGrid
            // 
            this.LootGrid.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.LootGrid.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.LootGrid.Location = new System.Drawing.Point(3, 229);
            this.LootGrid.Name = "LootGrid";
            this.LootGrid.RowHeadersWidth = 20;
            this.LootGrid.Size = new System.Drawing.Size(378, 192);
            this.LootGrid.TabIndex = 3;
            // 
            // LootDispenser
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(384, 427);
            this.Controls.Add(this.LootGrid);
            this.Controls.Add(this.button2);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.LootMonstersGrid);
            this.MinimumSize = new System.Drawing.Size(300, 200);
            this.Name = "LootDispenser";
            this.Text = "Loot Dispenser";
            ((System.ComponentModel.ISupportInitialize)(this.LootMonstersGrid)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.LootGrid)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.DataGridView LootMonstersGrid;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.Button button2;
        private System.Windows.Forms.DataGridView LootGrid;
    }
}