/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigndata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author sean
 */
public class XPEvent {

    public XPEvent(@JsonProperty("Timestamp") LocalDateTime timestamp, @JsonProperty("Session") Integer session,
            @JsonProperty("Battle") Integer battle, @JsonProperty("Event") String event, @JsonProperty("Monster") String monster, @JsonProperty("XP") Integer xP) {
        this.timestamp = new SimpleObjectProperty<>(timestamp);
        this.session = new SimpleIntegerProperty(session);
        this.battle = new SimpleIntegerProperty(battle);
        this.event = new SimpleStringProperty(event);
        this.monster = new SimpleStringProperty(monster);
        this.xP = new SimpleIntegerProperty(xP);
    }

    public XPEvent() {
        this.timestamp = new SimpleObjectProperty<>(LocalDateTime.now());
        this.session = new SimpleIntegerProperty(0);
        this.battle = new SimpleIntegerProperty(0);
        this.event = new SimpleStringProperty("");
        this.monster = new SimpleStringProperty("");
        this.xP = new SimpleIntegerProperty(0);
    }

    @JsonProperty("Timestamp")
    private ObjectProperty<LocalDateTime> timestamp;
    @JsonProperty("Session")
    private IntegerProperty session;
    @JsonProperty("Battle")
    private IntegerProperty battle;
    @JsonProperty("Event")
    private StringProperty event;
    @JsonProperty("Monster")
    private StringProperty monster;
    @JsonProperty("XP")
    private IntegerProperty xP;

    public final LocalDateTime getTimestamp() {
        return timestamp.get();
    }

    public final void setTimestamp(LocalDateTime value) {
        timestamp.set(value);
    }

    public ObjectProperty<LocalDateTime> timestampProperty() {
        return timestamp;
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

    public final int getBattle() {
        return battle.get();
    }

    public final void setBattle(int value) {
        battle.set(value);
    }

    public IntegerProperty battleProperty() {
        return battle;
    }

    public final String getEvent() {
        return event.get();
    }

    public final void setEvent(String value) {
        event.set(value);
    }

    public StringProperty eventProperty() {
        return event;
    }

    public final String getMonster() {
        return monster.get();
    }

    public final void setMonster(String value) {
        monster.set(value);
    }

    public StringProperty monsterProperty() {
        return monster;
    }

    public final int getXP() {
        return xP.get();
    }

    public final void setXP(int value) {
        xP.set(value);
    }

    public IntegerProperty xPProperty() {
        return xP;
    }

}
