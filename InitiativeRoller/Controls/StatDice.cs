using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace CampaignTracker.Controls
{
    public partial class StatDice : UserControl
    {
        private List<DiceItem> possibleDice;
        public StatDice()
        {
            InitializeComponent();

            possibleDice = new List<DiceItem>();

            
            possibleDice.Add(new DiceItem(4));
            possibleDice.Add(new DiceItem(6));
            possibleDice.Add(new DiceItem(8));
            possibleDice.Add(new DiceItem(10));
            possibleDice.Add(new DiceItem(12));
            possibleDice.Add(new DiceItem(20));
            possibleDice.Add(new DiceItem(100));

            this.dicetype.Items.AddRange(possibleDice.ToArray());

        }


        public int DiceCount
        {
            get
            {
                return (int) dicecount.Value;
            }
            set
            {
                dicecount.Value = value;
            }
        }
        public int DiceSize
        {
            get
            {
                return possibleDice[this.dicetype.SelectedIndex].Size;
            }
            set
            {
                try
                {
                    this.dicetype.SelectedIndex = possibleDice.FindIndex(x => x.Size == value);
                }
                catch(Exception E)
                {
                    this.dicetype.SelectedIndex = 0;
                }
            }
        }

        public int Modifier
        {
            get
            {
                return (int)this.modifier.Value;
            }
            set
            {
                if (value > this.modifier.Maximum)
                {
                    this.modifier.Maximum = value + 1;
                }
                if (value < this.modifier.Minimum)
                {
                    this.modifier.Maximum = value - 1;
                }

                this.modifier.Value = value;

            }
        }

        private bool showmodifier = false;
        [Category("Appearance")]
        [Description("Enable the modifier value")]
        [Browsable(true),EditorBrowsable(EditorBrowsableState.Always)]
        [DesignerSerializationVisibility(DesignerSerializationVisibility.Visible)]
        public bool ShowModifier { get => showmodifier; set
            {
                showmodifier = value;
                UpdateSize();
            }
        }

        private void UpdateSize()
        {
            if (showmodifier)
            {
                this.Size = new Size(125, 20);
                this.modifier.Visible = true;
            }
            else
            {
                this.Size = new Size(85, 20);
                this.modifier.Visible = false;
            }
        }

        private bool enabled = true;
        public new bool Enabled
        {
            get
            {
                return enabled;
            }
            set
            {
                enabled = value;
                this.dicecount.Enabled = enabled;
                this.dicetype.Enabled = enabled;
                this.modifier.Enabled = enabled;
            }
        }

    }

    public class DiceItem : IEquatable<int>,IEquatable<DiceItem>, IComparable
    {
        public DiceItem()
        {        }
        public DiceItem(int size)
        {
            Size = size;
        }
        public int Size { get; set; }

        public int CompareTo(object obj)
        {
            return Size.CompareTo(obj);
        }

        public bool Equals(DiceItem other)
        {
            return other.Equals(Size);
        }

        public bool Equals(int other)
        {
            return Size.Equals(other);
        }

        public override string ToString()
        {
            return "D" + Size.ToString();
        }


    }
}
