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
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author sean
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatWithModifier {

    public StatWithModifier(@JsonProperty("Name") String name, @JsonProperty("Modifier") Integer modifier) {
        this.name = new SimpleStringProperty(name);
        this.modifier = new SimpleIntegerProperty(modifier);
    }

    public StatWithModifier() {
        this.name = new SimpleStringProperty();
        this.modifier = new SimpleIntegerProperty();
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
