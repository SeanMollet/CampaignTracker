using CampaignData;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace CampaignTracker
{
    public partial class LootDispenser : Controls.BaseForm, DataBindReload
    {
        private SortableBindingList<LootMonster> lootsources;
        private SortableBindingList<LootItem> lootdrops;

        public LootDispenser()
        {
            InitializeComponent();
            lootsources = new SortableBindingList<LootMonster>();
            lootdrops = new SortableBindingList<LootItem>();

            DataBind();
        }

        public void LoadBattle(Battle battle)
        {
            foreach (var monster in battle.monsters)
            {
                if (monster.CurrentHP <= 0 || monster.Persuaded)
                {
                    string name = monster.Name;
                    if (monster.Persuaded)
                    {
                        name += " Persuaded";
                    }
                    lootsources.Add(new LootMonster { MonsterName = name, Challenge = monster.Challenge,Quantity=1 });
                }
            }
            DataBind();
        }
        public void DataBind()
        {
            this.LootMonstersGrid.Columns.Clear();
            this.LootMonstersGrid.AutoGenerateColumns = false;
            this.LootMonstersGrid.DataSource = lootsources;
            this.LootMonstersGrid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Qty", DataPropertyName = "Quantity", AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.LootMonstersGrid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Monster", DataPropertyName = "MonsterName", AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.LootMonstersGrid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Challenge", DataPropertyName = "Challenge", AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.LootMonstersGrid.Columns.Add(new DataGridViewCheckBoxColumn { HeaderText = "Hoard", DataPropertyName = "Hoard", AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.LootMonstersGrid.AutoResizeColumns();


            this.LootGrid.Columns.Clear();
            this.LootGrid.AutoGenerateColumns = false;
            this.LootGrid.DataSource = lootdrops;
            this.LootGrid.Columns.Add(new DataGridViewComboBoxColumn
            {
                HeaderText = "Type",
                DataPropertyName = "type",
                AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells,
                DataSource = LootDataTables.LootTypes,
                ValueMember = "Type",
                DisplayMember = "Description",
                DropDownWidth = 80,
            });
            this.LootGrid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Count", DataPropertyName = "count", AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.LootGrid.Columns.Add(new DataGridViewTextBoxColumn { HeaderText = "Name", DataPropertyName = "item", AutoSizeMode = DataGridViewAutoSizeColumnMode.AllCells });
            this.LootGrid.AutoResizeColumns();
        }

        private void LootMonstersGrid_CellParsing(object sender, DataGridViewCellParsingEventArgs e)
        {
            //Only do this for the loot column
            if (e.DesiredType == typeof(Fraction))
            {
                try
                {
                    var frac = new Fraction(e.Value.ToString());
                    e.Value = frac;
                    e.ParsingApplied = true;
                }
                catch (Exception E)
                {
                    e.Value = new Fraction(0);
                    e.ParsingApplied = true;
                }

            }

        }

        private void button1_Click(object sender, EventArgs e)
        {
            lootdrops.Clear();
            foreach (var lootsource in lootsources)
            {
                for (int sourcecount = 0; sourcecount < lootsource.Quantity; sourcecount++)
                {
                    if (!lootsource.Hoard)
                    {
                        var tables = LootDataTables.tables.individual.Where(x => x.mincr <= lootsource.Challenge && x.maxcr >= lootsource.Challenge).ToArray();
                        foreach (var table in tables)
                        {
                            //roll a D100
                            var d100 = Dice.Roll(100, RollType.Normal);
                            var rolltables = table.table.Where(x => x.min <= d100 && x.max >= d100).ToArray();
                            foreach (var rolltable in rolltables)
                            {
                                LootCoins(rolltable.coins);
                            }
                        }
                    }
                    else
                    {
                        var tables = LootDataTables.tables.hoard.Where(x => x.mincr <= lootsource.Challenge && x.maxcr >= lootsource.Challenge).ToArray();
                        foreach (var table in tables)
                        {
                            var d100 = Dice.Roll(100, RollType.Normal);
                            var rolltables = table.table.Where(x => x.min <= d100 && x.max >= d100).ToArray();
                            LootCoins(table.coins);

                            foreach (var rolltable in rolltables)
                            {
                                if (rolltable.gems != null)
                                {
                                    int gemsCount = Dice.RollLoot(rolltable.gems.amount);
                                    var gemchoices = LootDataTables.tables.gemstones.Where(x => x.type == rolltable.gems.type).ToArray();
                                    if (gemsCount > 0)
                                    {
                                        lootdrops.Add(new LootItem { type = LootType.Gemstones, item = gemchoices[0].name });
                                        var chosen = gemchoices[0].table[Dice.Roll(gemchoices[0].table.Count, RollType.Normal) - 1];
                                        lootdrops.Add(new LootItem { count = gemsCount, type = LootType.Gemstones, item = chosen });
                                    }
                                }

                                if (rolltable.artobjects != null)
                                {
                                    int artCount = Dice.RollLoot(rolltable.artobjects.amount);
                                    var artchoices = LootDataTables.tables.artobjects.Where(x => x.type == rolltable.artobjects.type).ToArray();
                                    if (artchoices.Length > 0)
                                    {
                                        lootdrops.Add(new LootItem { type = LootType.ArtObjects, item = artchoices[0].name });
                                        for (int a = 0; a < artCount; a++)
                                        {
                                            var chosen = artchoices[0].table[Dice.Roll(artchoices[0].table.Count, RollType.Normal) - 1];
                                            lootdrops.Add(new LootItem { count = 1, type = LootType.ArtObjects, item = chosen });
                                        }
                                    }
                                }

                                if (rolltable.magicitems != null)
                                {
                                    var magicamounts = rolltable.magicitems.amount.Split(',');
                                    var magictypes = rolltable.magicitems.type.Split(',');
                                    for (int usetables = 0; usetables < magicamounts.Length; usetables++)
                                    {

                                        int magicCount = Dice.RollLoot(magicamounts[usetables]);
                                        var magicchoices = LootDataTables.tables.magicitems.Where(x => x.type == magictypes[usetables]).ToArray();

                                        if (magicchoices.Length > 0)
                                        {
                                            lootdrops.Add(new LootItem { type = LootType.MagicItems, item = magicchoices[0].name });
                                            for (int a = 0; a < magicCount; a++)
                                            {
                                                var d100_2 = Dice.Roll(100, RollType.Normal);
                                                var chosen = magicchoices[0].table.Where(x => x.min <= d100_2 && x.max >= d100_2).ToArray();
                                                if (chosen.Length > 0)
                                                {
                                                    if (chosen[0].table != null)
                                                    {
                                                        var finalchosen = chosen[0].table[Dice.Roll(chosen[0].table.Count, RollType.Normal) - 1];
                                                        lootdrops.Add(new LootItem { count = 1, type = LootType.MagicItems, item = finalchosen });
                                                    }
                                                    else
                                                    {
                                                        var item = chosen[0].item;
                                                        lootdrops.Add(new LootItem { count = 1, type = LootType.MagicItems, item = item });
                                                    }

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            this.LootGrid.Sort(this.LootGrid.Columns[0], ListSortDirection.Ascending);
            this.LootGrid.AutoResizeColumns();
            this.LootGrid.Refresh();
        }

        private void LootCoins(CampaignData.Coins coins)
        {
            Dictionary<string, int> rolled = new Dictionary<string, int>();

            rolled.Add("cp", Dice.RollLoot(coins.cp));
            rolled.Add("sp", Dice.RollLoot(coins.sp));
            rolled.Add("ep", Dice.RollLoot(coins.ep));
            rolled.Add("gp", Dice.RollLoot(coins.gp));
            rolled.Add("pp", Dice.RollLoot(coins.pp));

            foreach (var entry in rolled)
            {
                if (entry.Value > 0)
                {
                    //Check if we have this already
                    var existing = lootdrops.Where(x => x.type == LootType.Money && x.item == entry.Key).ToArray();
                    if (existing.Length > 0)
                    {
                        existing[0].count += entry.Value;
                    }
                    else
                    {
                        lootdrops.Add(new LootItem { count = entry.Value, item = entry.Key, type = LootType.Money });
                    }

                }
            }
        }

        private void button2_Click(object sender, EventArgs e)
        {
            if (lootdrops != null) {
                foreach (var drop in lootdrops)
                {
                    var existing = Program.db.database.Loot.Where(x => x.type == drop.type && x.item == drop.item).ToArray();
                    if (existing.Length > 0)
                    {
                        existing[0].count += drop.count;
                    }
                    else
                    {
                        Program.db.database.Loot.Add(drop);
                    }
                }
                lootdrops.Clear();
            }
        }
    }


}
