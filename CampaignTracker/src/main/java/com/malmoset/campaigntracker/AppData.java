/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
