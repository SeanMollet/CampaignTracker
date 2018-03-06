/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigntracker;

import com.malmoset.campaigndata.Database;
import com.malmoset.campaigndata.MonstersDatabase;

/**
 *
 * @author sean
 */
public class AppData {

    public AppData() {
        db = new Database();
        mon_db = new MonstersDatabase();
    }

    private Database db;
    private MonstersDatabase mon_db;

}
