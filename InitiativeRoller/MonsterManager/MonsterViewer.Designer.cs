namespace CampaignTracker
{
    partial class MonsterViewer
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
            this.tabControl1 = new System.Windows.Forms.TabControl();
            this.tabPage1 = new System.Windows.Forms.TabPage();
            this.Speed = new System.Windows.Forms.DataGridView();
            this.label7 = new System.Windows.Forms.Label();
            this.Initiative = new System.Windows.Forms.TextBox();
            this.label6 = new System.Windows.Forms.Label();
            this.ACNotes = new System.Windows.Forms.TextBox();
            this.ACValue = new System.Windows.Forms.TextBox();
            this.label5 = new System.Windows.Forms.Label();
            this.HPNotes = new System.Windows.Forms.TextBox();
            this.HPValue = new System.Windows.Forms.TextBox();
            this.label4 = new System.Windows.Forms.Label();
            this.Type = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.Source = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.NameBox = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.tabPage2 = new System.Windows.Forms.TabPage();
            this.tabPage3 = new System.Windows.Forms.TabPage();
            this.tabPage4 = new System.Windows.Forms.TabPage();
            this.abilitiesControl1 = new CampaignTracker.Controls.AbilitiesControl();
            this.tabControl1.SuspendLayout();
            this.tabPage1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.Speed)).BeginInit();
            this.SuspendLayout();
            // 
            // tabControl1
            // 
            this.tabControl1.Controls.Add(this.tabPage1);
            this.tabControl1.Controls.Add(this.tabPage2);
            this.tabControl1.Controls.Add(this.tabPage3);
            this.tabControl1.Controls.Add(this.tabPage4);
            this.tabControl1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tabControl1.Location = new System.Drawing.Point(0, 0);
            this.tabControl1.Name = "tabControl1";
            this.tabControl1.SelectedIndex = 0;
            this.tabControl1.Size = new System.Drawing.Size(355, 482);
            this.tabControl1.TabIndex = 4;
            // 
            // tabPage1
            // 
            this.tabPage1.Controls.Add(this.abilitiesControl1);
            this.tabPage1.Controls.Add(this.Speed);
            this.tabPage1.Controls.Add(this.label7);
            this.tabPage1.Controls.Add(this.Initiative);
            this.tabPage1.Controls.Add(this.label6);
            this.tabPage1.Controls.Add(this.ACNotes);
            this.tabPage1.Controls.Add(this.ACValue);
            this.tabPage1.Controls.Add(this.label5);
            this.tabPage1.Controls.Add(this.HPNotes);
            this.tabPage1.Controls.Add(this.HPValue);
            this.tabPage1.Controls.Add(this.label4);
            this.tabPage1.Controls.Add(this.Type);
            this.tabPage1.Controls.Add(this.label3);
            this.tabPage1.Controls.Add(this.Source);
            this.tabPage1.Controls.Add(this.label2);
            this.tabPage1.Controls.Add(this.NameBox);
            this.tabPage1.Controls.Add(this.label1);
            this.tabPage1.Location = new System.Drawing.Point(4, 22);
            this.tabPage1.Name = "tabPage1";
            this.tabPage1.Padding = new System.Windows.Forms.Padding(3);
            this.tabPage1.Size = new System.Drawing.Size(347, 456);
            this.tabPage1.TabIndex = 0;
            this.tabPage1.Text = "General Stats";
            this.tabPage1.UseVisualStyleBackColor = true;
            // 
            // Speed
            // 
            this.Speed.AllowUserToResizeRows = false;
            this.Speed.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.Speed.Location = new System.Drawing.Point(70, 165);
            this.Speed.Name = "Speed";
            this.Speed.RowHeadersVisible = false;
            this.Speed.Size = new System.Drawing.Size(249, 101);
            this.Speed.TabIndex = 20;
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Location = new System.Drawing.Point(7, 165);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(38, 13);
            this.label7.TabIndex = 19;
            this.label7.Text = "Speed";
            // 
            // Initiative
            // 
            this.Initiative.Location = new System.Drawing.Point(70, 136);
            this.Initiative.Name = "Initiative";
            this.Initiative.Size = new System.Drawing.Size(51, 20);
            this.Initiative.TabIndex = 18;
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(7, 139);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(46, 13);
            this.label6.TabIndex = 17;
            this.label6.Text = "Initiative";
            // 
            // ACNotes
            // 
            this.ACNotes.Location = new System.Drawing.Point(127, 110);
            this.ACNotes.Name = "ACNotes";
            this.ACNotes.Size = new System.Drawing.Size(192, 20);
            this.ACNotes.TabIndex = 16;
            // 
            // ACValue
            // 
            this.ACValue.Location = new System.Drawing.Point(70, 110);
            this.ACValue.Name = "ACValue";
            this.ACValue.Size = new System.Drawing.Size(51, 20);
            this.ACValue.TabIndex = 15;
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(7, 113);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(21, 13);
            this.label5.TabIndex = 14;
            this.label5.Text = "AC";
            // 
            // HPNotes
            // 
            this.HPNotes.Location = new System.Drawing.Point(127, 84);
            this.HPNotes.Name = "HPNotes";
            this.HPNotes.Size = new System.Drawing.Size(192, 20);
            this.HPNotes.TabIndex = 13;
            // 
            // HPValue
            // 
            this.HPValue.Location = new System.Drawing.Point(70, 84);
            this.HPValue.Name = "HPValue";
            this.HPValue.Size = new System.Drawing.Size(51, 20);
            this.HPValue.TabIndex = 11;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(7, 87);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(22, 13);
            this.label4.TabIndex = 10;
            this.label4.Text = "HP";
            // 
            // Type
            // 
            this.Type.Location = new System.Drawing.Point(70, 58);
            this.Type.Name = "Type";
            this.Type.Size = new System.Drawing.Size(249, 20);
            this.Type.TabIndex = 9;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(7, 61);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(31, 13);
            this.label3.TabIndex = 8;
            this.label3.Text = "Type";
            // 
            // Source
            // 
            this.Source.Location = new System.Drawing.Point(70, 32);
            this.Source.Name = "Source";
            this.Source.Size = new System.Drawing.Size(249, 20);
            this.Source.TabIndex = 7;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(7, 35);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(41, 13);
            this.label2.TabIndex = 6;
            this.label2.Text = "Source";
            // 
            // NameBox
            // 
            this.NameBox.Location = new System.Drawing.Point(70, 6);
            this.NameBox.Name = "NameBox";
            this.NameBox.Size = new System.Drawing.Size(249, 20);
            this.NameBox.TabIndex = 5;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(7, 9);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(35, 13);
            this.label1.TabIndex = 4;
            this.label1.Text = "Name";
            // 
            // tabPage2
            // 
            this.tabPage2.Location = new System.Drawing.Point(4, 22);
            this.tabPage2.Name = "tabPage2";
            this.tabPage2.Padding = new System.Windows.Forms.Padding(3);
            this.tabPage2.Size = new System.Drawing.Size(347, 456);
            this.tabPage2.TabIndex = 1;
            this.tabPage2.Text = "Advanced Stats";
            this.tabPage2.UseVisualStyleBackColor = true;
            // 
            // tabPage3
            // 
            this.tabPage3.Location = new System.Drawing.Point(4, 22);
            this.tabPage3.Name = "tabPage3";
            this.tabPage3.Size = new System.Drawing.Size(347, 456);
            this.tabPage3.TabIndex = 2;
            this.tabPage3.Text = "Saves/Actions";
            this.tabPage3.UseVisualStyleBackColor = true;
            // 
            // tabPage4
            // 
            this.tabPage4.Location = new System.Drawing.Point(4, 22);
            this.tabPage4.Name = "tabPage4";
            this.tabPage4.Size = new System.Drawing.Size(347, 456);
            this.tabPage4.TabIndex = 3;
            this.tabPage4.Text = "Other";
            this.tabPage4.UseVisualStyleBackColor = true;
            // 
            // abilitiesControl1
            // 
            this.abilitiesControl1.Location = new System.Drawing.Point(10, 272);
            this.abilitiesControl1.Name = "abilitiesControl1";
            this.abilitiesControl1.Size = new System.Drawing.Size(303, 46);
            this.abilitiesControl1.TabIndex = 21;
            // 
            // MonsterViewer
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(355, 482);
            this.Controls.Add(this.tabControl1);
            this.Name = "MonsterViewer";
            this.Text = "Monster Viewer";
            this.tabControl1.ResumeLayout(false);
            this.tabPage1.ResumeLayout(false);
            this.tabPage1.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.Speed)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TabControl tabControl1;
        private System.Windows.Forms.TabPage tabPage1;
        private System.Windows.Forms.TextBox HPNotes;
        private System.Windows.Forms.TextBox HPValue;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.TextBox Type;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TextBox Source;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox NameBox;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TabPage tabPage2;
        private System.Windows.Forms.TabPage tabPage3;
        private System.Windows.Forms.TabPage tabPage4;
        private System.Windows.Forms.TextBox ACNotes;
        private System.Windows.Forms.TextBox ACValue;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.TextBox Initiative;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.DataGridView Speed;
        private Controls.AbilitiesControl abilitiesControl1;
    }
}