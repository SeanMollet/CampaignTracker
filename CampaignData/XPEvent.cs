using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CampaignData
{
    public class XPEvent
    {
        public DateTime Timestamp { get; }
        public string Event { get; set; }
        public int XP { get; set; }

        public XPEvent()
        {
            Timestamp = DateTime.Now;
            Event = "";
            XP = 0;
        }
    }
}
