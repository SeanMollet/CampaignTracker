/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigndata;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author sean
 */
public class Player {

    public Player(@JsonProperty("Name") String name, @JsonProperty("Race") String race, @JsonProperty("Class") String character_class,
            @JsonProperty("Initiative") Integer initiative, @JsonProperty("AC") Integer aC, @JsonProperty("CurrentHP") Integer currentHP,
            @JsonProperty("MaxHP") Integer maxHP, @JsonProperty("Roll") Integer roll, @JsonProperty("Adv") Integer adv,
            @JsonProperty("Dead") Boolean dead, @JsonProperty("Stable") Boolean stable) {
        this.name = new SimpleStringProperty(name);
        this.race = new SimpleStringProperty(race);
        this.character_class = new SimpleStringProperty(character_class);
        this.initiative = new SimpleIntegerProperty(initiative);
        this.aC = new SimpleIntegerProperty(aC);
        this.currentHP = new SimpleIntegerProperty(currentHP);
        this.maxHP = new SimpleIntegerProperty(maxHP);
        this.roll = new SimpleIntegerProperty(roll);
        this.adv = new SimpleIntegerProperty(adv);
        this.dead = new SimpleBooleanProperty(dead);
        this.stable = new SimpleBooleanProperty(stable);

    }

    public Player() {

    }
    @JsonProperty("Name")
    private StringProperty name;
    @JsonProperty("Race")
    private StringProperty race;
    @JsonProperty("Class")
    private StringProperty character_class;
    @JsonProperty("Initiative")
    private IntegerProperty initiative;
    @JsonProperty("AC")
    private IntegerProperty aC;
    @JsonProperty("CurrentHP")
    private IntegerProperty currentHP;
    @JsonProperty("MaxHP")
    private IntegerProperty maxHP;
    @JsonProperty("Roll")
    private IntegerProperty roll;
    @JsonProperty("Adv")
    private IntegerProperty adv;
    @JsonProperty("Dead")
    private BooleanProperty dead;
    @JsonProperty("Stable")
    private BooleanProperty stable;

    private StringProperty appearance;

    public final String getName() {
        return name.get();
    }

    public final void setName(String value) {
        name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public final String getRace() {
        return race.get();
    }

    public final void setRace(String value) {
        race.set(value);
    }

    public StringProperty raceProperty() {
        return race;
    }

    public final String getCharacter_class() {
        return character_class.get();
    }

    public final void setCharacter_class(String value) {
        character_class.set(value);
    }

    public StringProperty character_classProperty() {
        return character_class;
    }

    public final int getInitiative() {
        return initiative.get();
    }

    public final void setInitiative(int value) {
        initiative.set(value);
    }

    public IntegerProperty initiativeProperty() {
        return initiative;
    }

    public final int getAC() {
        return aC.get();
    }

    public final void setAC(int value) {
        aC.set(value);
    }

    public IntegerProperty aCProperty() {
        return aC;
    }

    public final int getCurrentHP() {
        return currentHP.get();
    }

    public final void setCurrentHP(int value) {
        currentHP.set(value);
    }

    public IntegerProperty currentHPProperty() {
        return currentHP;
    }

    public final int getMaxHP() {
        return maxHP.get();
    }

    public final void setMaxHP(int value) {
        maxHP.set(value);
    }

    public IntegerProperty maxHPProperty() {
        return maxHP;
    }

    public final int getRoll() {
        return roll.get();
    }

    public final void setRoll(int value) {
        roll.set(value);
    }

    public IntegerProperty rollProperty() {
        return roll;
    }

    public final int getAdv() {
        return adv.get();
    }

    public final void setAdv(int value) {
        adv.set(value);
    }

    public IntegerProperty advProperty() {
        return adv;
    }

    public final boolean isDead() {
        return dead.get();
    }

    public final void setDead(boolean value) {
        dead.set(value);
    }

    public BooleanProperty deadProperty() {
        return dead;
    }

    public final boolean isStable() {
        return stable.get();
    }

    public final void setStable(boolean value) {
        stable.set(value);
    }

    public BooleanProperty stableProperty() {
        return stable;
    }

    public final String getAppearance() {
        return appearance.get();
    }

    public final void setAppearance(String value) {
        appearance.set(value);
    }

    public StringProperty appearanceProperty() {
        return appearance;
    }

}
