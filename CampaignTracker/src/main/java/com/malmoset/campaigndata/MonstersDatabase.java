/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigndata;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Dialog;

/**
 *
 * @author sean
 */
public class MonstersDatabase {

    private ArrayList<Monster> monsters;
    private ArrayList<Monster> customMonsters;

    private ListProperty<Monster> monstersBind;
    private ListProperty<Monster> customMonstersBind;

    public MonstersDatabase() {
        monsters = new ArrayList<>();
        customMonsters = new ArrayList<>();

        monstersBind = new SimpleListProperty<Monster>(FXCollections.observableArrayList(monsters));
        customMonstersBind = new SimpleListProperty<Monster>(FXCollections.observableArrayList(customMonsters));
        LoadMonsters();
    }

    public void LoadMonsters() {
        String File = "";
        try {
            Map<String, String> files = Utilities.FindFiles("^Monsters.*\\.json");

            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(Include.NON_NULL);

            for (Map.Entry<String, String> file : files.entrySet()) {
                File = file.getKey();
                List<Monster> newmonsters = mapper.readValue(file.getValue(), new TypeReference<List<Monster>>() {
                });
                monsters.addAll(newmonsters);
            }
        } catch (Exception E) {
            //If this blows up, they won't get any entries. That's acceptable
            Dialog dialog = new Dialog();
            dialog.setTitle("Error in " + File);
            dialog.setContentText(E.toString());

            dialog.show();
        }
    }
}
