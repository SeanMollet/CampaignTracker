using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json;

namespace CampaignData
{
    public static class LootDataTables
    {
        public static SortableBindingList<LootTypeDescription> LootTypes;
        public static LootTables tables;
        static LootDataTables()
        {
            var bytes = LootData.loot;
            var stream = new MemoryStream(bytes, false);
            var reader = new JsonTextReader(new StreamReader(stream));

            tables = JsonSerializer.Create().Deserialize< LootTables>(reader);

            LootTypes = new SortableBindingList<LootTypeDescription>();
            LootTypes.Add(new LootTypeDescription { Type = LootType.Money, Description = "Money" });
            LootTypes.Add(new LootTypeDescription { Type = LootType.ArtObjects, Description = "Art Object" });
            LootTypes.Add(new LootTypeDescription { Type = LootType.Gemstones, Description = "Gem Stone" });
            LootTypes.Add(new LootTypeDescription { Type = LootType.MagicItems, Description = "Magic Item" });
        }
    }

    public class LootMonster
    {
        public int Quantity { get; set; }
        public string MonsterName { get; set; }
        public Fraction Challenge { get; set; }
        public bool Hoard { get; set; }
    }

    public class LootItem
    {
        public LootItem()
        {
            type = LootType.Money;
            count = 0;
            item = "cp";
        }

        public LootType type { get; set; }
        public int count { get; set; }
        public string item { get; set; }

        public override string ToString()
        {
            return count.ToString() + " " + item;
        }
    }

    public class LootTypeDescription
    {
        public LootType Type { get; set; }
        public string Description { get; set; }
    }

    public enum LootType
    {
        Money,
        Gemstones,
        ArtObjects,
        MagicItems
    }

    public class LootTables
    {
        public IList<Individual> individual { get; set; }
        public IList<Hoard> hoard { get; set; }
        public IList<Gemstone> gemstones { get; set; }
        public IList<Artobject> artobjects { get; set; }
        public IList<Magicitem> magicitems { get; set; }
    }

    public class Coins
    {
        public string cp { get; set; }
        public string sp { get; set; }
        public string ep { get; set; }
        public string gp { get; set; }
        public string pp { get; set; }
    }

    public class IndividualTable
    {
        public int min { get; set; }
        public int max { get; set; }
        public Coins coins { get; set; }
    }

    public class Individual
    {
        public string name { get; set; }
        public int mincr { get; set; }
        public int maxcr { get; set; }
        public IList<IndividualTable> table { get; set; }
    }

    public class Gems
    {
        public string type { get; set; }
        public string amount { get; set; }
    }

    public class Artobjects
    {
        public string type { get; set; }
        public string amount { get; set; }
    }

    public class Magicitems
    {
        public string type { get; set; }
        public string amount { get; set; }
    }

    public class HoardTable
    {
        public int min { get; set; }
        public int max { get; set; }
        public Gems gems { get; set; }
        public Artobjects artobjects { get; set; }
        public Magicitems magicitems { get; set; }
    }

    public class Hoard
    {
        public string name { get; set; }
        public int mincr { get; set; }
        public int maxcr { get; set; }
        public Coins coins { get; set; }
        public IList<HoardTable> table { get; set; }
    }

    public class Gemstone
    {
        public string name { get; set; }
        public string type { get; set; }
        public IList<string> table { get; set; }
    }

    public class Artobject
    {
        public string name { get; set; }
        public string type { get; set; }
        public IList<string> table { get; set; }
    }

    public class MagicitemTable
    {
        public int min { get; set; }
        public int max { get; set; }
        public string item { get; set; }
        public IList<string> table { get; set; }
    }

    public class Magicitem
    {
        public string name { get; set; }
        public string type { get; set; }
        public IList<MagicitemTable> table { get; set; }
    }


}
