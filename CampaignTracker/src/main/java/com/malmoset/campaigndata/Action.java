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
public class Action {

    public Action(@JsonProperty("Name") String name, @JsonProperty("Content") String content, @JsonProperty("Attack") String attack) {
        this.name = new SimpleStringProperty(name);
        this.content = new SimpleStringProperty(content);
        this.attack = new SimpleStringProperty(attack);
    }

    public Action() {

    }
    @JsonProperty("Name")
    private StringProperty name;
    @JsonProperty("Content")
    private StringProperty content;
    @JsonProperty("Attack")
    private StringProperty attack;

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

}
