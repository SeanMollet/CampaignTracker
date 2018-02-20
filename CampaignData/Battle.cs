using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CampaignData
{

    public class Battle
    {
        DateTime Began;
        public SortableBindingList<BattleMonster> monsters;
        public SortableBindingList<XPEvent> XP;
        public Battle()
        {
            Began = DateTime.Now;
            monsters = new SortableBindingList<BattleMonster>();
            XP = new SortableBindingList<XPEvent>();
        }
    }

    public class BattleMonster : Monster
    {
        public DateTime spawned { get; }
        private int HPOffset;
        public int CurrentHP
        {
            get => HP.Value + HPOffset;
            set
            {
                HPOffset = value - HP.Value;
            }
        }
    }
}

