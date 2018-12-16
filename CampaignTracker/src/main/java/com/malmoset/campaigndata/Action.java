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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author sean
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Action {

    public Action(@JsonProperty("Name") String name, @JsonProperty("Content") String content, @JsonProperty("Attack") String attack, @JsonProperty("Usage") String usage) {
        this.name = new SimpleStringProperty(name);
        this.content = new SimpleStringProperty(content);
        this.attack = new SimpleStringProperty(attack);
        this.usage = new SimpleStringProperty(usage);
    }

    public Action() {
        this.name = new SimpleStringProperty();
        this.content = new SimpleStringProperty();
        this.attack = new SimpleStringProperty();
        this.usage = new SimpleStringProperty();
    }
    @JsonProperty("Name")
    private StringProperty name;
    @JsonProperty("Content")
    private StringProperty content;
    @JsonProperty("Attack")
    private StringProperty attack;
    @JsonProperty("Usage")
    private StringProperty usage;

    public final String getName() {
        return name.get();
    }

    public final void setName(String value) {
        name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public final String getContent() {
        return content.get();
    }

    public final void setContent(String value) {
        content.set(value);
    }

    public StringProperty contentProperty() {
        return content;
    }

    public final String getAttack() {
        return attack.get();
    }

    public final void setAttack(String value) {
        attack.set(value);
    }

    public StringProperty attackProperty() {
        return attack;
    }

    public final String getUsage() {
        return usage.get();
    }

    public final void setUsage(String value) {
        usage.set(value);
    }

    public StringProperty usageProperty() {
        return usage;
    }

}
