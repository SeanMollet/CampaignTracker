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
public class Skill {

    public Skill(@JsonProperty("Name") String name, @JsonProperty("Modifier") Integer modifier) {
        this.name = new SimpleStringProperty(name);
        this.modifier = new SimpleIntegerProperty(modifier);
    }

    public Skill() {

    }
    @JsonProperty("Name")
    private StringProperty name;
    @JsonProperty("Modifier")
    private IntegerProperty modifier;

    public final String getName() {
        return name.get();
    }

    public final void setName(String value) {
        name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public final int getModifier() {
        return modifier.get();
    }

    public final void setModifier(int value) {
        modifier.set(value);
    }

    public IntegerProperty modifierProperty() {
        return modifier;
    }

}
