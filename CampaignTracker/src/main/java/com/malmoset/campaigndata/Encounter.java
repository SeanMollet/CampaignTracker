/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigndata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author sean
 */
public class Encounter {

    public Encounter(@JsonProperty("Name") String name, @JsonProperty("Challenge") Fraction challenge,
            @JsonProperty("Description") String description, @JsonProperty("Monsters") List<Monster> monsters) {
        this.name = new SimpleStringProperty(name);
        this.challenge = new SimpleObjectProperty<>(challenge);
        this.description = new SimpleStringProperty(description);
        this.monsters = monsters;
    }

    public Encounter() {
        challenge = new SimpleObjectProperty<>(new Fraction(1));
        monsters = new ArrayList<Monster>();
    }

    @JsonProperty("Name")
    private StringProperty name;
    @JsonProperty("Challenge")
    private ObjectProperty<Fraction> challenge;
    @JsonProperty("Description")
    private StringProperty description;
    @JsonProperty("Monsters")
    private List<Monster> monsters;

    public final String getName() {
        return name.get();
    }

    public final void setName(String value) {
        name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public final Fraction getChallenge() {
        return challenge.get();
    }

    public final void setChallenge(Fraction value) {
        challenge.set(value);
    }

    public ObjectProperty<Fraction> challengeProperty() {
        return challenge;
    }

    public final String getDescription() {
        return description.get();
    }

    public final void setDescription(String value) {
        description.set(value);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(List<Monster> monsters) {
        this.monsters = monsters;
    }

}
