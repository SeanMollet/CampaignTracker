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
import com.malmoset.dice.Dice;
import java.util.Comparator;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
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
            @JsonProperty("MaxHP") Integer maxHP, @JsonProperty("Roll") Integer roll, @JsonProperty("Adv") Dice.RollTypes adv,
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
        this.adv = new SimpleObjectProperty(Dice.RollTypes.NORMAL);
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

    private StringProperty name;
    private StringProperty race;
    private StringProperty character_class;
    private IntegerProperty initiative;
    private IntegerProperty aC;
    private IntegerProperty currentHP;
    private IntegerProperty maxHP;
    private IntegerProperty roll;
    private ObjectProperty<Dice.RollTypes> adv;
    private BooleanProperty dead;
    private BooleanProperty stable;
    @JsonIgnore
    private StringProperty appearance;
    @JsonIgnore
    private IntegerProperty hpToChange;

    @JsonProperty("Name")
    public final String getName() {
        return name.get();
    }

    @JsonProperty("Name")
    public final void setName(String value) {
        name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
    }

    @JsonProperty("Race")
    public final String getRace() {
        return race.get();
    }

    @JsonProperty("Race")
    public final void setRace(String value) {
        race.set(value);
    }

    public StringProperty raceProperty() {
        return race;
    }

    @JsonProperty("Class")
    public final String getCharacter_class() {
        return character_class.get();
    }

    @JsonProperty("Class")
    public final void setCharacter_class(String value) {
        character_class.set(value);
    }

    public StringProperty character_classProperty() {
        return character_class;
    }

    @JsonProperty("Initiative")
    public final int getInitiative() {
        return initiative.get();
    }

    @JsonProperty("Initiative")
    public final void setInitiative(int value) {
        initiative.set(value);
    }

    public IntegerProperty initiativeProperty() {
        return initiative;
    }

    @JsonProperty("AC")
    public final int getAC() {
        return aC.get();
    }

    @JsonProperty("AC")
    public final void setAC(int value) {
        aC.set(value);
    }

    public IntegerProperty aCProperty() {
        return aC;
    }

    @JsonProperty("CurrentHP")
    public final int getCurrentHP() {
        return currentHP.get();
    }

    @JsonProperty("CurrentHP")
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

    @JsonProperty("MaxHP")
    public final int getMaxHP() {
        return maxHP.get();
    }

    @JsonProperty("MaxHP")
    public final void setMaxHP(int value) {
        maxHP.set(value);
        setAppearance("");
    }

    public IntegerProperty maxHPProperty() {
        return maxHP;
    }

    @JsonProperty("Roll")
    public final int getRoll() {
        return roll.get();
    }

    @JsonProperty("Roll")
    public final void setRoll(int value) {
        roll.set(value);
    }

    public IntegerProperty rollProperty() {
        return roll;
    }

    @JsonProperty("Adv")
    public final Dice.RollTypes getAdv() {
        return adv.get();
    }

    @JsonProperty("Adv")
    public final void setAdv(Dice.RollTypes value) {
        adv.set(value);
    }

    public ObjectProperty<Dice.RollTypes> advProperty() {
        return adv;
    }

    @JsonProperty("Dead")
    public final boolean isDead() {
        return dead.get();
    }

    @JsonProperty("Dead")
    public final void setDead(boolean value) {
        dead.set(value);
        setAppearance("");
    }

    public BooleanProperty deadProperty() {
        return dead;
    }

    @JsonProperty("Stable")
    public final boolean isStable() {
        return stable.get();
    }

    @JsonProperty("Stable")
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
