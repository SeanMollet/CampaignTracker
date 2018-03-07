/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigndata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
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
public class BattleMonster extends Monster {

    public BattleMonster(@JsonProperty("Spawned") LocalDateTime spawned, @JsonProperty("Index") Integer index,
            @JsonProperty("Persuaded") Boolean persuaded, @JsonProperty("XPGiven") Integer xPGiven) {
        this.spawned = new SimpleObjectProperty<>(spawned);
        this.index = new SimpleIntegerProperty(index);
        this.persuaded = new SimpleBooleanProperty(persuaded);
        this.xPGiven = new SimpleIntegerProperty(xPGiven);
    }

    public BattleMonster(Monster monster) {

    }
    @JsonProperty("Spawned")
    private ObjectProperty<LocalDateTime> spawned;
    @JsonProperty("Index")
    private IntegerProperty index;
    @JsonProperty("Persuaded")
    private BooleanProperty persuaded;
    @JsonProperty("XPGiven")
    private IntegerProperty xPGiven;
    private IntegerProperty savingRoll;
    private IntegerProperty hPtoChange;

    private boolean currentHPSet = false;
    private int currentHP;

    public int getCurrentHP() {
        if (!currentHPSet) {
            currentHP = this.getHP().getHpValue();
        }
        return currentHP;
    }

    public void setCurrentHP(int value) {
        currentHP = value;
    }

    public final LocalDateTime getSpawned() {
        return spawned.get();
    }

    public final void setSpawned(LocalDateTime value) {
        spawned.set(value);
    }

    public ObjectProperty<LocalDateTime> spawnedProperty() {
        return spawned;
    }

    public final int getIndex() {
        return index.get();
    }

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

    public final int getXPGiven() {
        return xPGiven.get();
    }

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
