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
        private int _initiative;
        private int _ac;
        private int _current_hp;
        private int _max_hp;
        private int _roll;
        private bool _adv;

        public string Name { get => _name; set { if (_name != value) { _name = value; NotifyPropertyChanged(); } } }
        public int Initiative { get => _initiative; set { if (_initiative != value) { _initiative = value; NotifyPropertyChanged(); } } }
        public int AC { get => _ac; set { if (_ac != value) { _ac = value; NotifyPropertyChanged(); } } }
        public int CurrentHP { get => _current_hp; set { if (_current_hp != value) { _current_hp = value; NotifyPropertyChanged(); } } }
        public int MaxHP { get => _max_hp; set { if (_max_hp != value) { _max_hp = value; NotifyPropertyChanged(); } } }
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
                return this.Initiative.CompareTo(comparison.Initiative);
            }
            return this.Roll.CompareTo(comparison.Roll);
        }
    }
}
