/* 
 * Copyright 2018 Malmoset LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.malmoset.campaigndata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.malmoset.dice.Dice;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author sean
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HP {

    public HP(@JsonProperty("Value") Integer hpValue, @JsonProperty("HitDiceCount") Integer hitDiceCount,
            @JsonProperty("HitDice") Integer hitDice, @JsonProperty("HitModifier") Integer hitModifier,
            @JsonProperty("Notes") String notes) {
        this.hpValue = new SimpleIntegerProperty(hpValue);
        this.hitDiceCount = new SimpleIntegerProperty(hitDiceCount);
        this.hitDice = new SimpleIntegerProperty(hitDice);
        this.hitModifier = new SimpleIntegerProperty(hitModifier);
        this.notes = new SimpleStringProperty(notes);
    }

    public HP() {
        this.hpValue = new SimpleIntegerProperty();
        this.hitDiceCount = new SimpleIntegerProperty();
        this.hitDice = new SimpleIntegerProperty();
        this.hitModifier = new SimpleIntegerProperty();
        this.notes = new SimpleStringProperty("");
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

    public void RollHP() {
        if (hitDiceCount.get() > 0 && hitDice.get() > 0) {
            hpValue.set(Dice.rollXwithMod(hitDiceCount.get(), hitDice.get(), hitModifier.get()));
        }
    }

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
