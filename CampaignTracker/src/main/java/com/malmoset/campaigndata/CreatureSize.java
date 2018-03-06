/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigndata;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author sean
 */
public class CreatureSize {

    private ObjectProperty<CreatureSizes> creatureSize = new SimpleObjectProperty<CreatureSizes>();

    public final CreatureSizes getCreatureSize() {
        return creatureSize.get();
    }

    public final void setCreatureSize(CreatureSizes value) {
        creatureSize.set(value);
    }

    public ObjectProperty<CreatureSizes> creatureSizeProperty() {
        return creatureSize;
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
