/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigndata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.malmoset.campaigntracker.MainApp;
import com.malmoset.campaigntracker.Monsters.MonsterManagerController;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
    private ListProperty<XPEvent> xP;
    @JsonProperty("Session")
    private IntegerProperty session;

    public Database(@JsonProperty("Battles") List<Battle> battles, @JsonProperty("Encounters") List<Encounter> encounters, @JsonProperty("Players") List<Player> players,
            @JsonProperty("CustomMonsters") List<Monster> customMonsters, @JsonProperty("Session") Integer session) {
        this.battles = new SimpleListProperty(FXCollections.observableArrayList(battles));

        this.encounters = new SimpleListProperty(FXCollections.observableArrayList(encounters));

        this.players = new SimpleListProperty(FXCollections.observableArrayList(players));

        //this.xP = new SimpleListProperty(FXCollections.observableArrayList(xP));
        this.customMonsters = new SimpleListProperty(FXCollections.observableArrayList(customMonsters));

        this.session = new SimpleIntegerProperty(session);
    }

    public Database() {
        ArrayList<Battle> battle_list = new ArrayList<>();
        battles = new SimpleListProperty(FXCollections.observableArrayList(battle_list));

        ArrayList<Encounter> encounter_list = new ArrayList<>();
        encounters = new SimpleListProperty(FXCollections.observableArrayList(encounter_list));

        ArrayList<Player> player_list = new ArrayList<>();
        players = new SimpleListProperty(FXCollections.observableArrayList(player_list));

        ArrayList<XPEvent> xp_list = new ArrayList<>();
        xP = new SimpleListProperty(FXCollections.observableArrayList(xp_list));

        ArrayList<Monster> mon_list = new ArrayList<>();
        customMonsters = new SimpleListProperty(FXCollections.observableArrayList(mon_list));

        session = new SimpleIntegerProperty(1);

    }

    public void SaveFile(File file) {
        if (file != null) {

            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            try {
                mapper.writeValue(file, this);
            } catch (JsonProcessingException ex) {
                Logger.getLogger(MonsterManagerController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MonsterManagerController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void LoadFile(File file) {

        if (file != null) {

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JSR310Module());
            mapper.registerModule(new JavaTimeModule());

            try {
                BaseDatabase newdb = mapper.readValue(file, BaseDatabase.class);
                battles.set(FXCollections.observableArrayList(newdb.getBattles()));
                encounters.set(FXCollections.observableArrayList(newdb.getEncounters()));
                players.set(FXCollections.observableArrayList(newdb.getPlayers()));
                xP.set(FXCollections.observableArrayList(newdb.getxP()));
                session.set(newdb.getSession());
                List<Monster> custom = newdb.getCustomMonsters();
                if (custom != null && custom.size() > 0) {
                    MainApp.getAppData().getMon_db().ImportMonsters(custom);
                }
            } catch (JsonProcessingException ex) {
                Logger.getLogger(MonsterManagerController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MonsterManagerController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
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

    public final ObservableList<XPEvent> getXP() {
        return xP.get();
    }

    public final void setXP(ObservableList<XPEvent> value) {
        xP.set(value);
    }

    public ListProperty<XPEvent> xPProperty() {
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

}
