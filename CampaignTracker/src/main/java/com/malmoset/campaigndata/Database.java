/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigndata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.io.Files;
import com.malmoset.campaigndata.Loot.LootItem;
import com.malmoset.campaigntracker.MainApp;
import com.malmoset.campaigntracker.Monsters.MonsterManagerController;
import com.malmoset.controls.YesNoDialog;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

/**
 *
 * @author sean
 */
public class Database {

    @JsonProperty("Battles")
    private ListProperty<Battle> battles;
    @JsonProperty("Encounters")
    private ListProperty<Encounter> encounters;
    @JsonProperty("Players")
    private ListProperty<Player> players;
    @JsonProperty("CustomMonsters")
    private ListProperty<Monster> customMonsters;
    @JsonProperty("XP")
    private MapProperty<Integer, ObservableList<XPEvent>> xP;
    @JsonProperty("Session")
    private IntegerProperty session;
    @JsonProperty("Loot")
    private ListProperty<LootItem> loot;
    @JsonProperty("DBVersion")
    private Integer dbVersion = 1;
    @JsonIgnore
    private StringProperty campaignName;
    @JsonIgnore
    private IntegerProperty initiativeRolls;
    @JsonIgnore
    private IntegerProperty monsterReveals;

    public Database(@JsonProperty("Battles") List<Battle> battles, @JsonProperty("Encounters") List<Encounter> encounters, @JsonProperty("Players") List<Player> players,
            @JsonProperty("CustomMonsters") List<Monster> customMonsters, @JsonProperty("Session") Integer session, @JsonProperty("Loot") List<LootItem> loot) {
        this.battles = new SimpleListProperty(FXCollections.observableArrayList(battles));

        this.encounters = new SimpleListProperty(FXCollections.observableArrayList(encounters));

        this.players = new SimpleListProperty(FXCollections.observableArrayList(players));

        //this.xP = new SimpleListProperty(FXCollections.observableArrayList(xP));
        this.customMonsters = new SimpleListProperty(FXCollections.observableArrayList(customMonsters));

        this.session = new SimpleIntegerProperty(session);
        this.campaignName = new SimpleStringProperty("");
        this.loot = new SimpleListProperty(FXCollections.observableArrayList(loot));
        this.initiativeRolls = new SimpleIntegerProperty(0);
        this.monsterReveals = new SimpleIntegerProperty(0);
    }

    public Database() {
        ArrayList<Battle> battle_list = new ArrayList<>();
        battles = new SimpleListProperty(FXCollections.observableArrayList(battle_list));

        ArrayList<Encounter> encounter_list = new ArrayList<>();
        encounters = new SimpleListProperty(FXCollections.observableArrayList(encounter_list));

        ArrayList<Player> player_list = new ArrayList<>();
        players = new SimpleListProperty(FXCollections.observableArrayList(player_list));

        HashMap<Integer, ObservableList<XPEvent>> xp_list = new HashMap<>();
        xP = new SimpleMapProperty<Integer, ObservableList<XPEvent>>(FXCollections.observableMap(xp_list));

        ArrayList<Monster> mon_list = new ArrayList<>();
        customMonsters = new SimpleListProperty(FXCollections.observableArrayList(mon_list));

        session = new SimpleIntegerProperty(1);
        this.campaignName = new SimpleStringProperty("");

        ArrayList<LootItem> loot_list = new ArrayList<>();
        loot = new SimpleListProperty(FXCollections.observableArrayList(loot_list));
        this.initiativeRolls = new SimpleIntegerProperty(0);
        this.monsterReveals = new SimpleIntegerProperty(0);
    }

    public void ImportEncounters(List<Encounter> imported) {
        boolean Replace = false;
        boolean ReplaceAsked = false;

        for (Encounter encounter : imported) {
            List<Encounter> replace = encounters.stream().filter(x -> x.getName().equals(encounter.getName())).collect(Collectors.toList());
            if (replace.size() > 0) {
                if (!ReplaceAsked) {
                    Replace = (YesNoDialog.Display("Duplicate encounters found", "Would you like to replace duplicate encounters?", false) == YesNoDialog.Results.YES);
                    ReplaceAsked = true;
                }
                if (Replace) {
                    for (Encounter repenc : replace) {
                        encounters.remove(repenc);
                    }
                }
            }
            encounters.add(encounter);
        }
    }

    public void SaveFile(File file) {
        if (file != null) {

            //Copy over any custom monsters we have
            if (customMonsters != null) {
                customMonsters.clear();
            }
            List<Monster> customs = MainApp.getAppData().getMon_db().getMonstersBind().stream().filter(x -> x.isReadOnly() == false).collect(Collectors.toList());
            if (customs != null) {
                customMonsters = new SimpleListProperty(FXCollections.observableArrayList(customs));
            }

            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
            try {
                mapper.writeValue(file, this);
                this.campaignName.set(Files.getNameWithoutExtension(file.toString()));
            } catch (JsonProcessingException ex) {
                Logger.getLogger(MonsterManagerController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MonsterManagerController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public boolean LoadFile(File file) {

        if (file != null) {

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);

            try {
                //Replace .net formatted datetime with ISO standard (also removes timezone)
                String json = new String(java.nio.file.Files.readAllBytes(file.toPath()));
                json = json.replaceAll("(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{2})[\\d:-]+", "$1");
                //We renamed this field, typo in the .net version
                json = json.replaceAll("\"monsters\"", "\"Monsters\"");
                BaseDatabase newdb = mapper.readValue(json, BaseDatabase.class);
                battles.set(FXCollections.observableArrayList(newdb.getBattles()));
                encounters.set(FXCollections.observableArrayList(newdb.getEncounters()));
                players.set(FXCollections.observableArrayList(newdb.getPlayers()));
                session.set(newdb.getSession() + 1);

                xP.get().clear();
                for (Map.Entry<Integer, List<XPEvent>> entry : newdb.getxP().entrySet()) {
                    xP.get().put(entry.getKey(), FXCollections.observableList(entry.getValue()));
                }

                List<Monster> custom = newdb.getCustomMonsters();
                if (custom != null && custom.size() > 0) {
                    MainApp.getAppData().getMon_db().ImportMonsters(MainApp.getAppData().getMon_db().getMonstersBind(), custom);
                }
                loot.set(FXCollections.observableArrayList(newdb.getLoot()));

                this.campaignName.set(Files.getNameWithoutExtension(file.toString()));
                return true;
            } catch (JsonProcessingException ex) {
                Logger.getLogger(MonsterManagerController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MonsterManagerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    public int getBattleNumber() {
        if (battles != null) {
            return battles.getSize() + 1;
        }
        return 1;
    }

    public final ObservableList<Battle> getBattles() {
        return battles.get();
    }

    public final void setBattles(ObservableList<Battle> value) {
        battles.set(value);
    }

    public ListProperty<Battle> battlesProperty() {
        return battles;
    }

    public final ObservableList<Encounter> getEncounters() {
        return encounters.get();
    }

    public final void setEncounters(ObservableList<Encounter> value) {
        encounters.set(value);
    }

    public ListProperty<Encounter> encountersProperty() {
        return encounters;
    }

    public final ObservableList<Player> getPlayers() {
        return players.get();
    }

    public final void setPlayers(ObservableList<Player> value) {
        players.set(value);
    }

    public ListProperty<Player> playersProperty() {
        return players;
    }

    public final ObservableList<XPEvent> getSessionXP() {
        if (!xP.containsKey(session.get())) {
            xP.put(session.get(), FXCollections.observableArrayList(new ArrayList<>()));
        }
        return xP.get(session.get());
    }

    public final ObservableMap<Integer, ObservableList<XPEvent>> getXP() {
        return xP.get();
    }

    public final void setXP(ObservableMap<Integer, ObservableList<XPEvent>> value) {
        xP.set(value);
    }

    public MapProperty<Integer, ObservableList<XPEvent>> xPProperty() {
        return xP;
    }

    public final int getSession() {
        return session.get();
    }

    public final void setSession(int value) {
        session.set(value);
    }

    public IntegerProperty sessionProperty() {
        return session;
    }

    public final ObservableList<Monster> getCustomMonsters() {
        return customMonsters.get();
    }

    public final void setCustomMonsters(ObservableList<Monster> value) {
        customMonsters.set(value);
    }

    public ListProperty<Monster> customMonstersProperty() {
        return customMonsters;
    }

    public final String getCampaignName() {
        return campaignName.get();
    }

    public final void setCampaignName(String value) {
        campaignName.set(value);
    }

    public StringProperty campaignNameProperty() {
        return campaignName;
    }

    public Integer getDbVersion() {
        return dbVersion;
    }

    public void setDbVersion(Integer dbVersion) {
        this.dbVersion = dbVersion;
    }

    public final ObservableList<LootItem> getLoot() {
        return loot.get();
    }

    public final void setLoot(ObservableList<LootItem> value) {
        loot.set(value);
    }

    public ListProperty<LootItem> lootProperty() {
        return loot;
    }

    public final int getInitiativeRolls() {
        return initiativeRolls.get();
    }

    public final void setInitiativeRolls(int value) {
        initiativeRolls.set(value);
    }

    public IntegerProperty initiativeRollsProperty() {
        return initiativeRolls;
    }

    public final int getMonsterReveals() {
        return monsterReveals.get();
    }

    public final void setMonsterReveals(int value) {
        monsterReveals.set(value);
    }

    public IntegerProperty monsterRevealsProperty() {
        return monsterReveals;
    }

}
