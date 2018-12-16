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
import java.util.Date;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author sean
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class XPEvent {

    public XPEvent(@JsonProperty("Timestamp") Date timestamp, @JsonProperty("Session") Integer session,
            @JsonProperty("Battle") Integer battle, @JsonProperty("Event") String event, @JsonProperty("Monster") String monster, @JsonProperty("XP") Integer xP) {
        this.timestamp = new SimpleObjectProperty<>(timestamp);
        this.session = new SimpleIntegerProperty(session);
        this.battle = new SimpleIntegerProperty(battle);
        this.event = new SimpleStringProperty(event);
        this.monster = new SimpleStringProperty(monster);
        this.xP = new SimpleIntegerProperty(xP);
    }

    public XPEvent() {
        this.timestamp = new SimpleObjectProperty<>(new Date());
        this.session = new SimpleIntegerProperty(0);
        this.battle = new SimpleIntegerProperty(0);
        this.event = new SimpleStringProperty("");
        this.monster = new SimpleStringProperty("");
        this.xP = new SimpleIntegerProperty(0);
    }

    private ObjectProperty<Date> timestamp;
    private IntegerProperty session;
    private IntegerProperty battle;
    private StringProperty event;
    private StringProperty monster;
    private IntegerProperty xP;

    @JsonProperty("Timestamp")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SS")
    public final Date getTimestamp() {
        return timestamp.get();
    }

    @JsonProperty("Timestamp")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SS")
    public final void setTimestamp(Date value) {
        timestamp.set(value);
    }

    public ObjectProperty<Date> timestampProperty() {
        return timestamp;
    }

    @JsonProperty("Session")
    public final int getSession() {
        return session.get();
    }

    @JsonProperty("Session")
    public final void setSession(int value) {
        session.set(value);
    }

    public IntegerProperty sessionProperty() {
        return session;
    }

    @JsonProperty("Battle")
    public final int getBattle() {
        return battle.get();
    }

    @JsonProperty("Battle")
    public final void setBattle(int value) {
        battle.set(value);
    }

    public IntegerProperty battleProperty() {
        return battle;
    }

    @JsonProperty("Event")
    public final String getEvent() {
        return event.get();
    }

    @JsonProperty("Event")
    public final void setEvent(String value) {
        event.set(value);
    }

    public StringProperty eventProperty() {
        return event;
    }

    @JsonProperty("Monster")
    public final String getMonster() {
        return monster.get();
    }

    @JsonProperty("Monster")
    public final void setMonster(String value) {
        monster.set(value);
    }

    public StringProperty monsterProperty() {
        return monster;
    }

    @JsonProperty("XP")
    public final int getXP() {
        return xP.get();
    }

    @JsonProperty("XP")
    public final void setXP(int value) {
        xP.set(value);
    }

    public IntegerProperty xPProperty() {
        return xP;
    }

}
