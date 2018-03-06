/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigndata;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
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

    public final ObservableList<Monster> getCustomMonsters() {
        return customMonsters.get();
    }

    public final void setCustomMonsters(ObservableList<Monster> value) {
        customMonsters.set(value);
    }

    public ListProperty<Monster> customMonstersProperty() {
        return customMonsters;
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

}
