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
import javafx.scene.control.Dialog;

/**
 *
 * @author sean
 */
public class MonstersDatabase {

    private ListProperty<Monster> monsters;
    private ListProperty<Monster> customMonsters;

    public MonstersDatabase() {
        monsters = new SimpleListProperty<Monster>();
        customMonsters = new SimpleListProperty<Monster>();
        LoadMonsters();
    }

    public void LoadMonsters() {
        try {
            Map<String, String> files = Utilities.FindFiles("^Monsters.*\\.json");

            ObjectMapper mapper = new ObjectMapper();

            for (Map.Entry<String, String> file : files.entrySet()) {
                Monster[] newmonsters = mapper.readValue(file.getValue(), Monster[].class);
                monsters.addAll(newmonsters);
            }
        } catch (Exception E) {
            //If this blows up, they won't get any entries. That's acceptable
            Dialog dialog = new Dialog();
            dialog.setTitle("Error");
            dialog.setContentText(E.toString());

            dialog.show();
        }
    }
}
