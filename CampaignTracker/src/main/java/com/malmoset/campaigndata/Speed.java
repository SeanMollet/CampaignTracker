/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigndata;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author sean
 */
public class Speed {

    public Speed(@JsonProperty("Value") String speedValue) {
        this.speedValue = new SimpleStringProperty(speedValue);
    }

    @JsonProperty("Value")
    private StringProperty speedValue;

    public final String getSpeedValue() {
        return speedValue.get();
    }

    public final void setSpeedValue(String value) {
        speedValue.set(value);
    }

    public StringProperty speedValueProperty() {
        return speedValue;
    }

}
