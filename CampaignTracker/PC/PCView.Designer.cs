namespace CampaignTracker
{
    partial class PCView
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
            ((System.ComponentModel.ISupportInitialize)(this.PlayerGrid)).BeginInit();
            this.SuspendLayout();
            // 
            // PlayerGrid
            // 
            this.PlayerGrid.AllowUserToAddRows = false;
            this.PlayerGrid.AllowUserToDeleteRows = false;
            this.PlayerGrid.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.PlayerGrid.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.AllCells;
            this.PlayerGrid.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.PlayerGrid.Location = new System.Drawing.Point(13, 13);
            this.PlayerGrid.MultiSelect = false;
            this.PlayerGrid.Name = "PlayerGrid";
            this.PlayerGrid.ReadOnly = true;
            this.PlayerGrid.RowHeadersVisible = false;
            this.PlayerGrid.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.PlayerGrid.Size = new System.Drawing.Size(304, 277);
            this.PlayerGrid.TabIndex = 0;
            // 
            // Initiative
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(333, 302);
            this.Controls.Add(this.PlayerGrid);
            this.Name = "Initiative";
            this.Text = "Initiative";
            ((System.ComponentModel.ISupportInitialize)(this.PlayerGrid)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.DataGridView PlayerGrid;
    }
}

