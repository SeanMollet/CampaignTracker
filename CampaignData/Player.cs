using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;

namespace CampaignData
{

    public class Player : IComparable<Player>, INotifyPropertyChanged
    {


        public event PropertyChangedEventHandler PropertyChanged;

        private void NotifyPropertyChanged([CallerMemberName] String propertyName = "")
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }

        private string _name;
        private int _stat;
        private int _roll;
        private bool _adv;

        public string Name { get => _name; set { if (_name != value) { _name = value; NotifyPropertyChanged(); } } }
        public int Stat { get => _stat; set { if (_stat != value) { _stat = value; NotifyPropertyChanged(); } } }
        public int Roll { get => _roll; set { if (_roll != value) { _roll = value; NotifyPropertyChanged(); } } }
        public bool Adv { get => _adv; set { if (_adv != value) { _adv = value; NotifyPropertyChanged(); } } }
        public int CompareTo(Player comparison)
        {
            if (comparison == null)
            {
                return -1;
            }
            if (comparison.Roll == this.Roll)
            {
                return this.Stat.CompareTo(comparison.Stat);
            }
            return this.Roll.CompareTo(comparison.Roll);
        }
    }
}
