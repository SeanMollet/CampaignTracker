/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigndata;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author sean
 */
public class Monster {

    @JsonProperty("Index")
    private IntegerProperty index;
    @JsonProperty("CurrentHP")
    private IntegerProperty currentHP;
    @JsonProperty("Persuaded")
    private BooleanProperty persuaded;
    @JsonProperty("XPGiven")
    private IntegerProperty xPGiven;
    @JsonProperty("Name")
    private StringProperty name;
    @JsonProperty("Size")
    private ObjectProperty<CreatureSize> size;
    @JsonProperty("Type")
    private StringProperty type;
    @JsonProperty("Tags")
    private ListProperty<StringProperty> tags;
    @JsonProperty("Source")
    private StringProperty source;
    @JsonProperty("Alignment")
    private StringProperty alignment;
    @JsonProperty("AC")
    private ObjectProperty<AC> aC;
    @JsonProperty("HP")
    private ObjectProperty<HP> hP;
    @JsonProperty("InitiativeModifier")
    private IntegerProperty initiativeModifier;
    @JsonProperty("Speed")
    private ListProperty<Speed> speed;
    @JsonProperty("Abilities")
    private ObjectProperty<Abilities> abilities;
    @JsonProperty("DamageVulnerabilities")
    private ListProperty<StringProperty> damageVulnerabilities;
    @JsonProperty("DamageResistances")
    private ListProperty<StringProperty> damageResistances;
    @JsonProperty("DamageImmunities")
    private ListProperty<StringProperty> damageImmunities;
    @JsonProperty("ConditionImmunities")
    private ListProperty<StringProperty> conditionImmunities;
    @JsonProperty("Saves")
    private ListProperty<Save> saves;
    @JsonProperty("Skills")
    private ListProperty<Skill> skills;
    @JsonProperty("Senses")
    private ListProperty<StringProperty> senses;
    @JsonProperty("Languages")
    private ListProperty<StringProperty> languages;
    @JsonProperty("Challenge")
    private ObjectProperty<Fraction> challenge;
    @JsonProperty("Traits")
    private ListProperty<Trait> traits;
    @JsonProperty("Actions")
    private ListProperty<Action> actions;
    @JsonProperty("Reactions")
    private ListProperty<Object> reactions;
    @JsonProperty("LegendaryActions")
    private ListProperty<LegendaryAction> legendaryActions;
    @JsonProperty("Spells")
    private ListProperty<StringProperty> spells;
    @JsonProperty("Hidden")
    private BooleanProperty hidden;
    @JsonProperty("ReadOnly")
    private BooleanProperty readOnly;

}
