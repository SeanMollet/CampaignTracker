/* 
 * Copyright 2018 Malmoset LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.malmoset.campaigntracker;

import com.malmoset.campaigndata.Battle;
import com.malmoset.campaigndata.Database;
import com.malmoset.campaigndata.Encounter;
import com.malmoset.campaigndata.Loot.LootTables;
import com.malmoset.campaigndata.MonstersDatabase;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author sean
 */
public class AppData {

    public AppData() {
        db = new Database();
        mon_db = new MonstersDatabase();
        loot = LootTables.loadLoot();
        current_encounter = new SimpleObjectProperty<>(new Encounter());
        current_battle = new SimpleObjectProperty<>(new Battle());

    }

    private Database db;
    private MonstersDatabase mon_db;
    private LootTables loot;
    private ObjectProperty<Encounter> current_encounter;
    private ObjectProperty<Battle> current_battle;

    public Database getDb() {
        return db;
    }

    public void setDb(Database db) {
        this.db = db;
    }

    public MonstersDatabase getMon_db() {
        return mon_db;
    }

    public void setMon_db(MonstersDatabase mon_db) {
        this.mon_db = mon_db;
    }

    public final Encounter getCurrent_encounter() {
        return current_encounter.get();
    }

    public final void setCurrent_encounter(Encounter value) {
        current_encounter.set(value);
    }

    public ObjectProperty<Encounter> current_encounterProperty() {
        return current_encounter;
    }

    public final Battle getCurrent_battle() {
        return current_battle.get();
    }

    public final void setCurrent_battle(Battle value) {
        current_battle.set(value);
    }

    public ObjectProperty<Battle> current_battleProperty() {
        return current_battle;
    }

    public LootTables getLoot() {
        return loot;
    }

    public void setLoot(LootTables loot) {
        this.loot = loot;
    }

}
