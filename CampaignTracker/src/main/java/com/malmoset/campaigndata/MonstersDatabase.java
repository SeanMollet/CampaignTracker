/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigndata;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;

/**
 *
 * @author sean
 */
public class MonstersDatabase {

    private ListProperty<Monster> monsters;
    private ListProperty<Monster> customMonsters;

    public MonstersDatabase() {
        LoadMonsters();
        monsters = new SimpleListProperty<Monster>();
        customMonsters = new SimpleListProperty<Monster>();
    }

    public void LoadMonsters() {
        Map<String, String> files = Utilities.FindFiles("^Monsters.*\\.json");

        for (Map.Entry<String, String> file : files.entrySet()) {
            ObjectMapper mapper = new ObjectMapper();

        }

    }
}
