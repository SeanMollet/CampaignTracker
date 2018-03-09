/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigndata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author sean
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AC {

    @JsonCreator
    public AC(@JsonProperty("Value") int Value, @JsonProperty("Notes") String Notes) {
        this.acValue = new SimpleIntegerProperty(Value);
        this.notes = new SimpleStringProperty(Notes);
    }

    public AC() {
        acValue = new SimpleIntegerProperty();
        notes = new SimpleStringProperty();
    }

    @JsonProperty("Value")
    private IntegerProperty acValue;
    @JsonProperty("Notes")
    private StringProperty notes;

    public final int getAcValue() {
        return acValue.get();
    }

    public final void setAcValue(int value) {
        acValue.set(value);
    }

    public IntegerProperty acValueProperty() {
        return acValue;
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
