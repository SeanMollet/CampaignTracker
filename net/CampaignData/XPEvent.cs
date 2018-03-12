using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;

namespace CampaignData
{
    public class XPEvent : INotifyPropertyChanged
    {
        private DateTime timestamp;
        private int session;
        private int battle;
        private string xpEvent;
        private int xP;
        private string monster;
        private bool killed;

        public DateTime Timestamp { get => timestamp; set { timestamp = value; } }
        public int Session { get => session; set { session = value; NotifyPropertyChanged(); } }
        public int Battle { get => battle; set { battle = value; NotifyPropertyChanged(); } }
        public string Event { get => xpEvent; set { xpEvent = value; NotifyPropertyChanged(); } }
        public string Monster { get => monster; set { monster = value; NotifyPropertyChanged(); } }
        public int XP { get => xP; set { xP = value; NotifyPropertyChanged(); } }

        public XPEvent()
        {
            Timestamp = DateTime.Now;
            Event = "";
            XP = 0;
        }

        public event PropertyChangedEventHandler PropertyChanged;
        private void NotifyPropertyChanged([CallerMemberName] String propertyName = "")
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }
    }
}
