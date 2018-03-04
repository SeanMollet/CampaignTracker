namespace CampaignTracker
{
    partial class BattleListing
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
            this.BattleListingGrid = new System.Windows.Forms.DataGridView();
            ((System.ComponentModel.ISupportInitialize)(this.BattleListingGrid)).BeginInit();
            this.SuspendLayout();
            // 
            // BattleListingGrid
            // 
            this.BattleListingGrid.AllowUserToAddRows = false;
            this.BattleListingGrid.AllowUserToDeleteRows = false;
            this.BattleListingGrid.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.AllCells;
            this.BattleListingGrid.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.BattleListingGrid.Dock = System.Windows.Forms.DockStyle.Fill;
            this.BattleListingGrid.Location = new System.Drawing.Point(0, 0);
            this.BattleListingGrid.MultiSelect = false;
            this.BattleListingGrid.Name = "BattleListingGrid";
            this.BattleListingGrid.ReadOnly = true;
            this.BattleListingGrid.RowHeadersVisible = false;
            this.BattleListingGrid.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.BattleListingGrid.Size = new System.Drawing.Size(440, 404);
            this.BattleListingGrid.TabIndex = 0;
            this.BattleListingGrid.CellDoubleClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.BattleListingGrid_CellDoubleClick);
            // 
            // BattleListing
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(440, 404);
            this.Controls.Add(this.BattleListingGrid);
            this.Name = "BattleListing";
            this.Text = "Previous Battles";
            ((System.ComponentModel.ISupportInitialize)(this.BattleListingGrid)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.DataGridView BattleListingGrid;
    }
}

