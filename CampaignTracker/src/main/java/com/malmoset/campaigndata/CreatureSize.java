/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigndata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author sean
 */
public class CreatureSize {

    public CreatureSize(@JsonProperty("Value") String value) {
        this.value = value;
    }

    public CreatureSize() {
        this.creatureSize = new SimpleObjectProperty<>(CreatureSizes.MEDIUM);
    }
    @JsonIgnore
    private ObjectProperty<CreatureSizes> creatureSize;

    public final CreatureSizes getCreatureSize() {
        return creatureSize.get();
    }

    public final void setCreatureSize(CreatureSizes value) {
        creatureSize.set(value);
    }

    public ObjectProperty<CreatureSizes> creatureSizeProperty() {
        return creatureSize;
    }

    @JsonProperty("Value")
    private String value;

    public String getValue() {
        return creatureSize.toString();
    }

    public void setValue(String value) {
        creatureSize = new SimpleObjectProperty<>(CreatureSizes.valueOf(value));
    }

    public enum CreatureSizes {
        TINY("Tiny"),
        SMALL("Small"),
        MEDIUM("Medium"),
        LARGE("Large"),
        HUGE("Huge"),
        GARGANTUAN("Gargantuan");

        private String string;

        CreatureSizes(String size) {
            string = size;
        }

        CreatureSizes() {
            string = "Medium";
        }

        @Override
        public String toString() {
            return string;
        }
    }
}
