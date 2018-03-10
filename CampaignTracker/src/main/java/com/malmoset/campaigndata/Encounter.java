/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigndata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 *
 * @author sean
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Encounter {

    public Encounter(@JsonProperty("Name") String name, @JsonProperty("Challenge") Fraction challenge,
            @JsonProperty("Description") String description, @JsonProperty("Monsters") List<Monster> monsters) {
        this.name = new SimpleStringProperty(name);
        this.challenge = new SimpleObjectProperty<>(challenge);
        this.description = new SimpleStringProperty(description);
        this.monsters = new SimpleListProperty(FXCollections.observableArrayList(monsters));;
    }

    public Encounter() {
        this.name = new SimpleStringProperty();
        challenge = new SimpleObjectProperty<>(new Fraction(1));
        this.description = new SimpleStringProperty();
        monsters = new SimpleListProperty(FXCollections.observableArrayList(new ArrayList<Monster>()));
    }

    private void BindData() {
        monsters.get().addListener((ListChangeListener<Monster>) c
                -> {
            setChallenge(null);
        });
    }

    @JsonProperty("Name")
    private StringProperty name;
    @JsonProperty("Challenge")
    private ObjectProperty<Fraction> challenge;
    @JsonProperty("Description")
    private StringProperty description;
    @JsonProperty("Monsters")
    private ListProperty<Monster> monsters;

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
        Fraction sum = new Fraction(0);
        for (Monster monster : monsters) {
            sum = Fraction.add(sum, monster.getChallenge(), true);
        }
        challenge.set(sum);
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

    public final ObservableList<Monster> getMonsters() {
        return monsters.get();
    }

    public final void setMonsters(ObservableList<Monster> value) {
        monsters.set(value);
    }

    public ListProperty<Monster> monstersProperty() {
        return monsters;
    }

    public Encounter Clone() {
        ObjectMapper mapper = new ObjectMapper();

        String json;
        try {
            json = mapper.writeValueAsString(this);
            Encounter newencounter = mapper.readValue(json, Encounter.class);
            return newencounter;
        } catch (Exception ex) {
            Logger.getLogger(Monster.class.getName()).log(Level.INFO, null, ex);
        }

        return new Encounter();
    }
}
