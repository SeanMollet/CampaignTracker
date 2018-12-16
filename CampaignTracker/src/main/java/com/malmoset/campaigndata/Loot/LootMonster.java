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
package com.malmoset.campaigndata.Loot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.malmoset.campaigndata.Fraction;
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
public class LootMonster {

    public LootMonster(@JsonProperty("quantity") Integer quantity, @JsonProperty("monsterName") String monsterName, @JsonProperty("challenge") Fraction challenge, @JsonProperty("hoard") boolean hoard) {
        this();
        this.quantity.set(quantity);
        this.monsterName.set(monsterName);
        this.challenge = challenge;
        this.hoard.set(hoard);
    }

    public LootMonster() {
        this.quantity = new SimpleIntegerProperty(0);
        this.monsterName = new SimpleStringProperty("");
        this.challenge = new Fraction("0");
        this.hoard = new SimpleBooleanProperty(false);
    }
    private IntegerProperty quantity;
    private StringProperty monsterName;
    private Fraction challenge;
    private BooleanProperty hoard;

    public final int getQuantity() {
        return quantity.get();
    }

    public final void setQuantity(int value) {
        quantity.set(value);
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public final String getMonsterName() {
        return monsterName.get();
    }

    public final void setMonsterName(String value) {
        monsterName.set(value);
    }

    public StringProperty monsterNameProperty() {
        return monsterName;
    }

    public final boolean isHoard() {
        return hoard.get();
    }

    public final void setHoard(boolean value) {
        hoard.set(value);
    }

    public BooleanProperty hoardProperty() {
        return hoard;
    }

    public Fraction getChallenge() {
        return challenge;
    }

    public void setChallenge(Fraction challenge) {
        this.challenge = challenge;
    }

}
