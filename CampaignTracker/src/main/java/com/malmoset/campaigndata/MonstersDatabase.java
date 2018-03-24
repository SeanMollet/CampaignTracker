/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigndata;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.malmoset.controls.GotItDialog;
import com.malmoset.controls.YesNoDialog;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author sean
 */
public final class MonstersDatabase {

    private ArrayList<Monster> monsters;

    private ListProperty<Monster> monstersBind;

    public MonstersDatabase() {
        monsters = new ArrayList<>();

        monstersBind = new SimpleListProperty<Monster>(FXCollections.observableArrayList(monsters));

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
                monstersBind.addAll(newmonsters);
            }
        } catch (Exception E) {
            //If this blows up, they won't get any entries. That's acceptable
            GotItDialog.GotIt("Could not load monsters", "Error in \" + File");
        }
    }

    public void ImportMonsters(ObservableList<Monster> list, List<Monster> newmonsters) {
        boolean Replace = false;
        boolean ReplaceAsked = false;

        for (Monster monster : newmonsters) {
            //See if it already exists
            List<Monster> replace = list.stream().filter(x -> x.nameProperty().get().trim().equalsIgnoreCase(monster.getName().trim())
                    && x.sourceProperty().get().trim().equalsIgnoreCase(monster.getSource().trim())).collect(Collectors.toList());

            if (replace.size() > 0) {
                if (!ReplaceAsked) {
                    Replace = (YesNoDialog.Display("Duplicate monsters found", "Would you like to replace duplicate monsters?", false) == YesNoDialog.Results.YES);
                    ReplaceAsked = true;
                }
                if (Replace) {
                    for (Monster rep : replace) {
                        list.remove(rep);
                    }
                }
            }
            list.add(monster);
        }
    }

    public final ObservableList<Monster> getMonstersBind() {
        return monstersBind.get();
    }

    public final void setMonstersBind(ObservableList<Monster> value) {
        monstersBind.set(value);
    }

    public ListProperty<Monster> monstersBindProperty() {
        return monstersBind;
    }

}
