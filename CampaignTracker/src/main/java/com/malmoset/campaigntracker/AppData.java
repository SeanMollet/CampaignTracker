/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigntracker;

import com.malmoset.campaigndata.Database;
import com.malmoset.campaigndata.Loot.LootTables;
import com.malmoset.campaigndata.MonstersDatabase;

/**
 *
 * @author sean
 */
public class AppData {

    public AppData() {
        db = new Database();
        mon_db = new MonstersDatabase();
        loot = LootTables.loadLoot();

    }

    private Database db;
    private MonstersDatabase mon_db;
    private LootTables loot;

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

}
