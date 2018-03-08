/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigndata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author sean
 */
public class CreatureSize {

    public CreatureSize(@JsonProperty("Value") String value) {
        this.sizeValue = new SimpleStringProperty();
        this.setValue(value);
    }

    public CreatureSize() {
        this("MEDIUM");
    }

    @JsonIgnore
    private CreatureSizes size;
    @JsonIgnore
    private StringProperty sizeValue;

    @JsonProperty("Value")
    public final String getValue() {
        return sizeValue.get();
    }

    public final void setValue(String value) {
        this.size = CreatureSizes.fromString(value);
        this.sizeValue.set(this.size.toString());
    }

    public StringProperty valueProperty() {
        return sizeValue;
    }

    public CreatureSizes getSize() {
        return size;
    }

    public void setSize(CreatureSizes size) {
        //Make sure both variables are set
        setValue(size.toString());
    }

    public enum CreatureSizes {
        TINY("Tiny"),
        SMALL("Small"),
        MEDIUM("Medium"),
        LARGE("Large"),
        HUGE("Huge"),
        GARGANTUAN("Gargantuan");

        private String string;

        private CreatureSizes(String size) {
            string = size;
        }

        CreatureSizes() {
            string = "Medium";
        }

        @Override
        public String toString() {
            return string;
        }

        public static CreatureSizes fromString(String text) {
            for (CreatureSizes b : CreatureSizes.values()) {
                if (b.toString().equalsIgnoreCase(text)) {
                    return b;
                }
            }
            return CreatureSizes.MEDIUM;
        }

        public static ObservableList<String> getSizes() {
            ArrayList<String> output = new ArrayList<>();
            for (CreatureSizes b : CreatureSizes.values()) {
                output.add(b.toString());
            }
            return FXCollections.observableArrayList(output);
        }
    }
}
