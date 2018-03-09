/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigndata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author sean
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BattleMonster extends Monster {

    public BattleMonster(@JsonProperty("Spawned") Date spawned, @JsonProperty("Index") Integer index,
            @JsonProperty("Persuaded") Boolean persuaded, @JsonProperty("XPGiven") Integer xPGiven, @JsonProperty("CurrentHP") Integer currenthp) {
        super();
        this.spawned = new SimpleObjectProperty<>(spawned);
        this.index = new SimpleIntegerProperty(index);
        this.persuaded = new SimpleBooleanProperty(persuaded);
        this.xPGiven = new SimpleIntegerProperty(xPGiven);
        this.savingRoll = new SimpleIntegerProperty();
        this.hPtoChange = new SimpleIntegerProperty();
        this.currentHP = new SimpleIntegerProperty(currenthp);
    }

    public BattleMonster() {
        super();
        this.spawned = new SimpleObjectProperty<>(new Date());
        this.index = new SimpleIntegerProperty();
        this.persuaded = new SimpleBooleanProperty();
        this.xPGiven = new SimpleIntegerProperty();
        this.savingRoll = new SimpleIntegerProperty();
        this.hPtoChange = new SimpleIntegerProperty();
        this.currentHP = new SimpleIntegerProperty();
    }

    private ObjectProperty<Date> spawned;
    private IntegerProperty index;
    @JsonProperty("Persuaded")
    private BooleanProperty persuaded;
    private IntegerProperty xPGiven;
    @JsonIgnore
    private IntegerProperty savingRoll;
    @JsonIgnore
    private IntegerProperty hPtoChange;
    @JsonIgnore
    private boolean currentHPSet = false;

    @JsonProperty("CurrentHP")
    private IntegerProperty currentHP;

    public int getCurrentHP() {
        if (!currentHPSet) {
            currentHP.set(this.getHP().getHpValue());
        }
        return currentHP.get();
    }

    public void setCurrentHP(int value) {
        currentHP.set(value);
        currentHPSet = true;
    }

    @JsonProperty("Spawned")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SS")
    public final Date getSpawned() {
        return spawned.get();
    }

    @JsonProperty("Spawned")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SS")
    public final void setSpawned(Date value) {
        spawned.set(value);
    }

    public ObjectProperty<Date> spawnedProperty() {
        return spawned;
    }

    @JsonProperty("Index")
    public final int getIndex() {
        return index.get();
    }

    @JsonProperty("Index")
    public final void setIndex(int value) {
        index.set(value);
    }

    public IntegerProperty indexProperty() {
        return index;
    }

    public final boolean isPersuaded() {
        return persuaded.get();
    }

    public final void setPersuaded(boolean value) {
        persuaded.set(value);
    }

    public BooleanProperty persuadedProperty() {
        return persuaded;
    }

    @JsonProperty("XPGiven")
    public final int getXPGiven() {
        return xPGiven.get();
    }

    @JsonProperty("XPGiven")
    public final void setXPGiven(int value) {
        xPGiven.set(value);
    }

    public IntegerProperty xPGivenProperty() {
        return xPGiven;
    }

    public final int getSavingRoll() {
        return savingRoll.get();
    }

    public final void setSavingRoll(int value) {
        savingRoll.set(value);
    }

    public IntegerProperty savingRollProperty() {
        return savingRoll;
    }

    public final int getHPtoChange() {
        return hPtoChange.get();
    }

    public final void setHPtoChange(int value) {
        hPtoChange.set(value);
    }

    public IntegerProperty hPtoChangeProperty() {
        return hPtoChange;
    }

}
