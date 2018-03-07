/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigndata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author sean
 */
public class Monster {

    public Monster(@JsonProperty("Name") String name, @JsonProperty("Size") CreatureSize size, @JsonProperty("Type") String type,
            @JsonProperty("Tags") List<String> tags, @JsonProperty("Source") String source, @JsonProperty("Alignment") String alignment,
            @JsonProperty("AC") AC aC, @JsonProperty("HP") HP hP, @JsonProperty("InitiativeModifier") Integer initiativeModifier,
            @JsonProperty("Speed") List<Speed> speed, @JsonProperty("Abilities") Abilities abilities,
            @JsonProperty("DamageVulnerabilities") List<String> damageVulnerabilities, @JsonProperty("DamageResistances") List<String> damageResistances,
            @JsonProperty("DamageImmunities") List<String> damageImmunities, @JsonProperty("ConditionImmunities") List<String> conditionImmunities,
            @JsonProperty("Saves") List<Save> saves, @JsonProperty("Skills") List<Skill> skills, @JsonProperty("Senses") List<GenericValueString> senses,
            @JsonProperty("Languages") List<GenericValueString> languages, @JsonProperty("Challenge") Fraction challenge, @JsonProperty("Traits") List<Trait> traits,
            @JsonProperty("Actions") List<Action> actions, @JsonProperty("Reactions") List<Reaction> reactions, @JsonProperty("LegendaryActions") List<LegendaryAction> legendaryActions,
            @JsonProperty("Spells") List<String> spells, @JsonProperty("Hidden") Boolean hidden, @JsonProperty("ReadOnly") Boolean readOnly) {
        this.name = new SimpleStringProperty(name);
        this.size = new SimpleObjectProperty<>(size);
        this.type = new SimpleStringProperty(type);
        this.tags = tags;
        this.source = new SimpleStringProperty(source);
        this.alignment = new SimpleStringProperty(alignment);
        this.aC = new SimpleObjectProperty<>(aC);
        this.hP = new SimpleObjectProperty<>(hP);
        this.initiativeModifier = new SimpleIntegerProperty(initiativeModifier);
        this.speed = speed;
        this.abilities = new SimpleObjectProperty<>(abilities);
        this.damageVulnerabilities = damageVulnerabilities;
        this.damageResistances = damageResistances;
        this.damageImmunities = damageImmunities;
        this.conditionImmunities = conditionImmunities;
        this.saves = saves;
        this.skills = skills;
        this.senses = senses;
        this.languages = languages;
        this.challenge = new SimpleObjectProperty<>(challenge);
        this.traits = traits;
        this.actions = actions;
        this.reactions = reactions;
        this.legendaryActions = legendaryActions;
        this.spells = spells;
        this.hidden = new SimpleBooleanProperty(hidden);
        this.readOnly = new SimpleBooleanProperty(readOnly);
    }

    public Monster() {
    }

    @JsonProperty("Name")
    private StringProperty name;
    @JsonProperty("Size")
    private ObjectProperty<CreatureSize> size;
    @JsonProperty("Type")
    private StringProperty type;
    @JsonProperty("Tags")
    private List<String> tags;
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
    private List<Speed> speed;
    @JsonProperty("Abilities")
    private ObjectProperty<Abilities> abilities;
    @JsonProperty("DamageVulnerabilities")
    private List<String> damageVulnerabilities;
    @JsonProperty("DamageResistances")
    private List<String> damageResistances;
    @JsonProperty("DamageImmunities")
    private List<String> damageImmunities;
    @JsonProperty("ConditionImmunities")
    private List<String> conditionImmunities;
    @JsonProperty("Saves")
    private List<Save> saves;
    @JsonProperty("Skills")
    private List<Skill> skills;
    @JsonProperty("Senses")
    private List<GenericValueString> senses;
    @JsonProperty("Languages")
    private List<GenericValueString> languages;
    @JsonProperty("Challenge")
    private ObjectProperty<Fraction> challenge;
    @JsonProperty("Traits")
    private List<Trait> traits;
    @JsonProperty("Actions")
    private List<Action> actions;
    @JsonProperty("Reactions")
    private List<Reaction> reactions;
    @JsonProperty("LegendaryActions")
    private List<LegendaryAction> legendaryActions;
    @JsonProperty("Spells")
    private List<String> spells;
    @JsonProperty("Hidden")
    private BooleanProperty hidden;
    @JsonProperty("ReadOnly")
    private BooleanProperty readOnly;

    public final String getName() {
        return name.get();
    }

    public final void setName(String value) {
        name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public final CreatureSize getSize() {
        return size.get();
    }

    public final void setSize(CreatureSize value) {
        size.set(value);
    }

    public ObjectProperty<CreatureSize> sizeProperty() {
        return size;
    }

    public final String getType() {
        return type.get();
    }

    public final void setType(String value) {
        type.set(value);
    }

    public StringProperty typeProperty() {
        return type;
    }

    public final String getSource() {
        return source.get();
    }

    public final void setSource(String value) {
        source.set(value);
    }

    public StringProperty sourceProperty() {
        return source;
    }

    public final String getAlignment() {
        return alignment.get();
    }

    public final void setAlignment(String value) {
        alignment.set(value);
    }

    public StringProperty alignmentProperty() {
        return alignment;
    }

    public final AC getAC() {
        return aC.get();
    }

    public final void setAC(AC value) {
        aC.set(value);
    }

    public ObjectProperty<AC> aCProperty() {
        return aC;
    }

    public final HP getHP() {
        return hP.get();
    }

    public final void setHP(HP value) {
        hP.set(value);
    }

    public ObjectProperty<HP> hPProperty() {
        return hP;
    }

    public final int getInitiativeModifier() {
        return initiativeModifier.get();
    }

    public final void setInitiativeModifier(int value) {
        initiativeModifier.set(value);
    }

    public IntegerProperty initiativeModifierProperty() {
        return initiativeModifier;
    }

    public final Abilities getAbilities() {
        return abilities.get();
    }

    public final void setAbilities(Abilities value) {
        abilities.set(value);
    }

    public ObjectProperty<Abilities> abilitiesProperty() {
        return abilities;
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

    public final boolean isHidden() {
        return hidden.get();
    }

    public final void setHidden(boolean value) {
        hidden.set(value);
    }

    public BooleanProperty hiddenProperty() {
        return hidden;
    }

    public final boolean isReadOnly() {
        return readOnly.get();
    }

    public final void setReadOnly(boolean value) {
        readOnly.set(value);
    }

    public BooleanProperty readOnlyProperty() {
        return readOnly;
    }

}
