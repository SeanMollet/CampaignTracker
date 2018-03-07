/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigndata;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author sean
 */
public class HP {

    public HP(@JsonProperty("Value") Integer hpValue, @JsonProperty("HitDiceCount") Integer hitDiceCount,
            @JsonProperty("HitDice") Integer hitDice, @JsonProperty("HitModifier") Integer hitModifier,
            @JsonProperty("Notes") String notes) {
        this.hpValue = new SimpleIntegerProperty(hpValue);
        this.hitDiceCount = new SimpleIntegerProperty(hitDiceCount);
        this.hitDice = new SimpleIntegerProperty(hitDice);
        this.hitModifier = new SimpleIntegerProperty(hitModifier);
        this.notes = new SimpleStringProperty(notes) {
        };
    }

    public HP() {

    }
    @JsonProperty("Value")
    private IntegerProperty hpValue;
    @JsonProperty("HitDiceCount")
    private IntegerProperty hitDiceCount;
    @JsonProperty("HitDice")
    private IntegerProperty hitDice;
    @JsonProperty("HitModifier")
    private IntegerProperty hitModifier;
    @JsonProperty("Notes")
    private StringProperty notes;

    public final int getHpValue() {
        return hpValue.get();
    }

    public final void setHpValue(int value) {
        hpValue.set(value);
    }

    public IntegerProperty hpValueProperty() {
        return hpValue;
    }

    public final int getHitDiceCount() {
        return hitDiceCount.get();
    }

    public final void setHitDiceCount(int value) {
        hitDiceCount.set(value);
    }

    public IntegerProperty hitDiceCountProperty() {
        return hitDiceCount;
    }

    public final int getHitDice() {
        return hitDice.get();
    }

    public final void setHitDice(int value) {
        hitDice.set(value);
    }

    public IntegerProperty hitDiceProperty() {
        return hitDice;
    }

    public final int getHitModifier() {
        return hitModifier.get();
    }

    public final void setHitModifier(int value) {
        hitModifier.set(value);
    }

    public IntegerProperty hitModifierProperty() {
        return hitModifier;
    }

    public final String getNotes() {
        return notes.get();
    }

    public final void setNotes(String value) {
        notes.set(value);
    }

    public StringProperty notesProperty() {
        return notes;
    }

}
