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
        notes = new SimpleStringProperty("");
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
