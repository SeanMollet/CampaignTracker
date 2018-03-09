/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigndata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.malmoset.campaigntrackercontrols.HPAppearance;
import java.util.Comparator;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class Player {

    public Player(@JsonProperty("Name") String name, @JsonProperty("Race") String race, @JsonProperty("Class") String character_class,
            @JsonProperty("Initiative") Integer initiative, @JsonProperty("AC") Integer aC, @JsonProperty("CurrentHP") Integer currentHP,
            @JsonProperty("MaxHP") Integer maxHP, @JsonProperty("Roll") Integer roll, @JsonProperty("Adv") Integer adv,
            @JsonProperty("Dead") Boolean dead, @JsonProperty("Stable") Boolean stable) {
        this();
        this.name.set(name);
        this.race.set(race);
        this.character_class.set(character_class);
        this.initiative.set(initiative);
        this.aC.set(aC);
        this.currentHP.set(currentHP);
        this.maxHP.set(maxHP);
        this.roll.set(roll);
        this.adv.set(adv);
        this.dead.set(dead);
        this.stable.set(stable);
        setAppearance("");
        BindProperties();
    }

    public Player() {
        this.name = new SimpleStringProperty("New Player");
        this.race = new SimpleStringProperty("");
        this.character_class = new SimpleStringProperty();
        this.initiative = new SimpleIntegerProperty(0);
        this.aC = new SimpleIntegerProperty(0);
        this.currentHP = new SimpleIntegerProperty(0);
        this.maxHP = new SimpleIntegerProperty(0);
        this.roll = new SimpleIntegerProperty(0);
        this.adv = new SimpleIntegerProperty(0);
        this.dead = new SimpleBooleanProperty(false);
        this.stable = new SimpleBooleanProperty(false);
        this.hpToChange = new SimpleIntegerProperty(0);
        setAppearance("");
        BindProperties();
    }

    private void BindProperties() {
        this.currentHP.addListener((e) -> {
            setAppearance("");
        });
        this.maxHP.addListener((e) -> {
            setAppearance("");
        });
        this.dead.addListener((e) -> {
            setAppearance("");
        });
        this.stable.addListener((e) -> {
            setAppearance("");
        });
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
    @JsonIgnore
    private StringProperty appearance;
    @JsonIgnore
    private IntegerProperty hpToChange;

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
        if (currentHP.get() != value) {
            currentHP.set(value);
            if (currentHP.get() > 0) {
                this.stable.set(false);
                this.dead.set(false);
            }
            if (currentHP.get() <= maxHP.get() * -2) {
                this.stable.set(false);
                this.dead.set(true);
            }
        }

        setAppearance("");
    }

    public IntegerProperty currentHPProperty() {
        return currentHP;
    }

    public final int getMaxHP() {
        return maxHP.get();
    }

    public final void setMaxHP(int value) {
        maxHP.set(value);
        setAppearance("");
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
        setAppearance("");
    }

    public BooleanProperty deadProperty() {
        return dead;
    }

    public final boolean isStable() {
        return stable.get();
    }

    public final void setStable(boolean value) {
        stable.set(value);
        setAppearance("");
    }

    public BooleanProperty stableProperty() {
        return stable;
    }

    public final String getAppearance() {
        return appearance.get();
    }

    public final void setAppearance(String value) {
        String app = HPAppearance.Appearance(this.currentHP.get(), this.maxHP.get());
        if (this.dead.get()) {
            app = "Dead";
        } else if (currentHP.get() <= 0 && this.stable.get()) {
            app = "Stable";
        } else if (currentHP.get() <= 0) {
            app = "Unconscious";
        }
        if (appearance == null) {
            appearance = new SimpleStringProperty();
        }
        appearance.set(app);
    }

    public StringProperty appearanceProperty() {
        return appearance;
    }

    public final int getHpToChange() {
        return hpToChange.get();
    }

    public final void setHpToChange(int value) {
        hpToChange.set(value);
    }

    public IntegerProperty hpToChangeProperty() {
        return hpToChange;
    }

    public static Comparator<Player> CompareInitiative() {
        return (Player p1, Player p2) -> {
            if (p1.getRoll() < p2.getRoll()) {
                return 1;
            }
            if (p1.getRoll() > p2.getRoll()) {
                return -1;
            }
            if (p1.getRoll() == p2.getRoll()) {
                return -1 * Integer.compare(p1.getInitiative(), p2.getInitiative());
            }
            return 0;
        };
    }
}
