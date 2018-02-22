namespace CampaignTracker
{
    partial class MonsterManager
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
            this.MonstersGrid = new System.Windows.Forms.DataGridView();
            this.textBox1 = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.button1 = new System.Windows.Forms.Button();
            this.ExportMonsters = new System.Windows.Forms.Button();
            this.ImportMonsters = new System.Windows.Forms.Button();
            this.label2 = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.MonstersGrid)).BeginInit();
            this.SuspendLayout();
            // 
            // MonstersGrid
            // 
            this.MonstersGrid.AllowUserToAddRows = false;
            this.MonstersGrid.AllowUserToDeleteRows = false;
            this.MonstersGrid.AllowUserToResizeRows = false;
            this.MonstersGrid.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.MonstersGrid.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.AllCells;
            this.MonstersGrid.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.MonstersGrid.Location = new System.Drawing.Point(10, 36);
            this.MonstersGrid.MultiSelect = false;
            this.MonstersGrid.Name = "MonstersGrid";
            this.MonstersGrid.ReadOnly = true;
            this.MonstersGrid.RowHeadersVisible = false;
            this.MonstersGrid.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.MonstersGrid.Size = new System.Drawing.Size(719, 414);
            this.MonstersGrid.TabIndex = 0;
            this.MonstersGrid.CellContentClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.MonstersGrid_CellContentClick);
            this.MonstersGrid.CellDoubleClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dataGridView1_CellDoubleClick);
            // 
            // textBox1
            // 
            this.textBox1.Location = new System.Drawing.Point(54, 9);
            this.textBox1.Name = "textBox1";
            this.textBox1.Size = new System.Drawing.Size(204, 20);
            this.textBox1.TabIndex = 1;
            this.textBox1.TextChanged += new System.EventHandler(this.textBox1_TextChanged);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(7, 12);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(41, 13);
            this.label1.TabIndex = 2;
            this.label1.Text = "Search";
            // 
            // button1
            // 
            this.button1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.button1.Location = new System.Drawing.Point(460, 7);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(75, 23);
            this.button1.TabIndex = 3;
            this.button1.Text = "New Custom";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // ExportMonsters
            // 
            this.ExportMonsters.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.ExportMonsters.Location = new System.Drawing.Point(638, 7);
            this.ExportMonsters.Name = "ExportMonsters";
            this.ExportMonsters.Size = new System.Drawing.Size(91, 23);
            this.ExportMonsters.TabIndex = 4;
            this.ExportMonsters.Text = "Export Custom";
            this.ExportMonsters.UseVisualStyleBackColor = true;
            this.ExportMonsters.Click += new System.EventHandler(this.ExportMonsters_Click);
            // 
            // ImportMonsters
            // 
            this.ImportMonsters.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.ImportMonsters.Location = new System.Drawing.Point(541, 7);
            this.ImportMonsters.Name = "ImportMonsters";
            this.ImportMonsters.Size = new System.Drawing.Size(91, 23);
            this.ImportMonsters.TabIndex = 5;
            this.ImportMonsters.Text = "Import Custom";
            this.ImportMonsters.UseVisualStyleBackColor = true;
            this.ImportMonsters.Click += new System.EventHandler(this.ImportMonsters_Click);
            // 
            // label2
            // 
            this.label2.Anchor = System.Windows.Forms.AnchorStyles.Top;
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(297, 12);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(124, 13);
            this.label2.TabIndex = 6;
            this.label2.Text = "Doubleclick to View/Edit";
            // 
            // MonsterManager
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(741, 462);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.ImportMonsters);
            this.Controls.Add(this.ExportMonsters);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.textBox1);
            this.Controls.Add(this.MonstersGrid);
            this.MinimumSize = new System.Drawing.Size(690, 200);
            this.Name = "MonsterManager";
            this.Text = "Monster Manager";
            ((System.ComponentModel.ISupportInitialize)(this.MonstersGrid)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.DataGridView MonstersGrid;
        private System.Windows.Forms.TextBox textBox1;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.Button ExportMonsters;
        private System.Windows.Forms.Button ImportMonsters;
        private System.Windows.Forms.Label label2;
    }
}