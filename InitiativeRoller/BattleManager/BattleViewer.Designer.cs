﻿namespace CampaignTracker
{
    partial class BattleViewer
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
            this.label1 = new System.Windows.Forms.Label();
            this.Started = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.Monsters = new System.Windows.Forms.DataGridView();
            ((System.ComponentModel.ISupportInitialize)(this.Monsters)).BeginInit();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(125, 7);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(41, 13);
            this.label1.TabIndex = 0;
            this.label1.Text = "Started";
            // 
            // Started
            // 
            this.Started.Location = new System.Drawing.Point(172, 4);
            this.Started.Name = "Started";
            this.Started.Size = new System.Drawing.Size(235, 20);
            this.Started.TabIndex = 1;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(9, 11);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(50, 13);
            this.label3.TabIndex = 4;
            this.label3.Text = "Monsters";
            // 
            // Monsters
            // 
            this.Monsters.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.Monsters.AutoSizeRowsMode = System.Windows.Forms.DataGridViewAutoSizeRowsMode.AllCells;
            this.Monsters.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.Monsters.Location = new System.Drawing.Point(12, 30);
            this.Monsters.Name = "Monsters";
            this.Monsters.Size = new System.Drawing.Size(657, 272);
            this.Monsters.TabIndex = 5;
            this.Monsters.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.Monsters_CellClick);
            // 
            // BattleViewer
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(684, 312);
            this.Controls.Add(this.Monsters);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.Started);
            this.Controls.Add(this.label1);
            this.MinimumSize = new System.Drawing.Size(700, 350);
            this.Name = "BattleViewer";
            this.Text = "Battle";
            this.Activated += new System.EventHandler(this.EncounterViewer_Activated);
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.EncounterViewer_FormClosing);
            ((System.ComponentModel.ISupportInitialize)(this.Monsters)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox Started;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.DataGridView Monsters;
    }
}