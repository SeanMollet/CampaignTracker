/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigndata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author sean
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Monster {

    @JsonCreator
    public Monster(@JsonProperty("Name") String name, @JsonProperty("Size") CreatureSize size, @JsonProperty("Type") String type,
            @JsonProperty("Tags") List<String> tags, @JsonProperty("Source") String source, @JsonProperty("Alignment") String alignment,
            @JsonProperty("AC") AC aC, @JsonProperty("HP") HP hP, @JsonProperty("InitiativeModifier") Integer initiativeModifier,
            @JsonProperty("Speed") List<GenericValueString> speed, @JsonProperty("Abilities") Abilities abilities,
            @JsonProperty("DamageVulnerabilities") List<GenericValueString> damageVulnerabilities, @JsonProperty("DamageResistances") List<GenericValueString> damageResistances,
            @JsonProperty("DamageImmunities") List<GenericValueString> damageImmunities, @JsonProperty("ConditionImmunities") List<GenericValueString> conditionImmunities,
            @JsonProperty("Saves") List<StatWithModifier> saves, @JsonProperty("Skills") List<StatWithModifier> skills, @JsonProperty("Senses") List<GenericValueString> senses,
            @JsonProperty("Languages") List<GenericValueString> languages, @JsonProperty("Challenge") Fraction challenge, @JsonProperty("Traits") List<Action> traits,
            @JsonProperty("Actions") List<Action> actions, @JsonProperty("Reactions") List<Action> reactions, @JsonProperty("LegendaryActions") List<Action> legendaryActions,
            @JsonProperty("Spells") List<GenericValueString> spells, @JsonProperty("Hidden") Boolean hidden, @JsonProperty("Unknown") Boolean unknown, @JsonProperty("ReadOnly") Boolean readOnly) {
        this();
        this.name = new SimpleStringProperty(name);
        this.size = size;
        this.type = new SimpleStringProperty(type);
        if (tags != null) {
            this.tags = tags;
        }
        this.source = new SimpleStringProperty(source);
        this.alignment = new SimpleStringProperty(alignment);
        if (aC != null) {
            this.aC = aC;
        }
        if (hP != null) {
            this.hP = hP;
        }
        this.initiativeModifier = new SimpleIntegerProperty(initiativeModifier);
        if (speed != null) {
            this.speed = speed;
        }
        if (abilities != null) {
            this.abilities = abilities;
        }
        if (damageVulnerabilities != null) {
            this.damageVulnerabilities = damageVulnerabilities;
        }
        if (damageResistances != null) {
            this.damageResistances = damageResistances;
        }
        if (damageImmunities != null) {
            this.damageImmunities = damageImmunities;
        }
        if (conditionImmunities != null) {
            this.conditionImmunities = conditionImmunities;
        }
        if (saves != null) {
            this.saves = saves;
        }
        if (skills != null) {
            this.skills = skills;
        }
        if (senses != null) {
            this.senses = senses;
        }
        this.languages = languages;
        if (challenge != null) {
            this.challenge = challenge;
        }
        if (traits != null) {
            this.traits = traits;
        }
        if (actions != null) {
            this.actions = actions;
        }
        if (reactions != null) {
            this.reactions = reactions;
        }
        if (legendaryActions != null) {
            this.legendaryActions = legendaryActions;
        }
        if (spells != null) {
            this.spells = spells;
        }
        this.hidden = new SimpleBooleanProperty(hidden == null ? false : hidden);
        this.unknown = new SimpleBooleanProperty(unknown == null ? false : unknown);
        this.readOnly = new SimpleBooleanProperty(readOnly == null ? false : readOnly);
    }

    public Monster() {
        this.name = new SimpleStringProperty("New Monster");
        this.size = new CreatureSize();
        this.type = new SimpleStringProperty("");
        this.tags = new ArrayList<>();
        this.source = new SimpleStringProperty("Custom Monster");
        this.alignment = new SimpleStringProperty("");
        this.aC = new AC();
        this.hP = new HP();
        this.initiativeModifier = new SimpleIntegerProperty();
        this.speed = new ArrayList<>();
        this.abilities = new Abilities();
        this.damageVulnerabilities = new ArrayList<>();
        this.damageResistances = new ArrayList<>();
        this.damageImmunities = new ArrayList<>();
        this.conditionImmunities = new ArrayList<>();
        this.saves = new ArrayList<>();
        this.skills = new ArrayList<>();
        this.senses = new ArrayList<>();
        this.languages = new ArrayList<>();
        this.challenge = new Fraction(1);
        this.traits = new ArrayList<>();
        this.actions = new ArrayList<>();
        this.reactions = new ArrayList<>();
        this.legendaryActions = new ArrayList<>();
        this.spells = spells;
        this.hidden = new SimpleBooleanProperty(false);
        this.unknown = new SimpleBooleanProperty(false);
        this.readOnly = new SimpleBooleanProperty(false);
    }

    @JsonProperty("Name")
    private StringProperty name;
    @JsonProperty("Size")
    private CreatureSize size;
    @JsonProperty("Type")
    private StringProperty type;
    @JsonProperty("Tags")
    private List<String> tags;
    @JsonProperty("Source")
    private StringProperty source;
    @JsonProperty("Alignment")
    private StringProperty alignment;
    @JsonProperty("AC")
    private AC aC;
    @JsonProperty("HP")
    private HP hP;
    @JsonProperty("InitiativeModifier")
    private IntegerProperty initiativeModifier;
    @JsonProperty("Speed")
    private List<GenericValueString> speed;
    @JsonProperty("Abilities")
    private Abilities abilities;
    @JsonProperty("DamageVulnerabilities")
    private List<GenericValueString> damageVulnerabilities;
    @JsonProperty("DamageResistances")
    private List<GenericValueString> damageResistances;
    @JsonProperty("DamageImmunities")
    private List<GenericValueString> damageImmunities;
    @JsonProperty("ConditionImmunities")
    private List<GenericValueString> conditionImmunities;
    @JsonProperty("Saves")
    private List<StatWithModifier> saves;
    @JsonProperty("Skills")
    private List<StatWithModifier> skills;
    @JsonProperty("Senses")
    private List<GenericValueString> senses;
    @JsonProperty("Languages")
    private List<GenericValueString> languages;
    @JsonProperty("Challenge")
    private Fraction challenge;
    @JsonProperty("Traits")
    private List<Action> traits;
    @JsonProperty("Actions")
    private List<Action> actions;
    @JsonProperty("Reactions")
    private List<Action> reactions;
    @JsonProperty("LegendaryActions")
    private List<Action> legendaryActions;
    @JsonProperty("Spells")
    private List<GenericValueString> spells;
    @JsonProperty("Hidden")
    private BooleanProperty hidden;
    @JsonProperty("Unknown")
    private BooleanProperty unknown;
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
        return aC;
    }

    public final void setAC(AC value) {
        aC = value;
    }

    public final HP getHP() {
        return hP;
    }

    public final void setHP(HP value) {
        hP = value;
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
        return abilities;
    }

    public final void setAbilities(Abilities value) {
        abilities = value;
    }

    public final Fraction getChallenge() {
        return challenge;
    }

    public final void setChallenge(Fraction value) {
        challenge = value;
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<GenericValueString> getSpeed() {
        return speed;
    }

    public void setSpeed(List<GenericValueString> speed) {
        this.speed = speed;
    }

    public List<GenericValueString> getDamageVulnerabilities() {
        return damageVulnerabilities;
    }

    public void setDamageVulnerabilities(List<GenericValueString> damageVulnerabilities) {
        this.damageVulnerabilities = damageVulnerabilities;
    }

    public List<GenericValueString> getDamageResistances() {
        return damageResistances;
    }

    public void setDamageResistances(List<GenericValueString> damageResistances) {
        this.damageResistances = damageResistances;
    }

    public List<GenericValueString> getDamageImmunities() {
        return damageImmunities;
    }

    public void setDamageImmunities(List<GenericValueString> damageImmunities) {
        this.damageImmunities = damageImmunities;
    }

    public List<GenericValueString> getConditionImmunities() {
        return conditionImmunities;
    }

    public void setConditionImmunities(List<GenericValueString> conditionImmunities) {
        this.conditionImmunities = conditionImmunities;
    }

    public List<StatWithModifier> getSaves() {
        return saves;
    }

    public void setSaves(List<StatWithModifier> saves) {
        this.saves = saves;
    }

    public List<StatWithModifier> getSkills() {
        return skills;
    }

    public void setSkills(List<StatWithModifier> skills) {
        this.skills = skills;
    }

    public List<GenericValueString> getSenses() {
        return senses;
    }

    public void setSenses(List<GenericValueString> senses) {
        this.senses = senses;
    }

    public List<GenericValueString> getLanguages() {
        return languages;
    }

    public void setLanguages(List<GenericValueString> languages) {
        this.languages = languages;
    }

    public List<Action> getTraits() {
        return traits;
    }

    public void setTraits(List<Action> traits) {
        this.traits = traits;
    }

    public List<Action> getReactions() {
        return reactions;
    }

    public void setReactions(List<Action> reactions) {
        this.reactions = reactions;
    }

    public List<GenericValueString> getSpells() {
        return spells;
    }

    public void setSpells(List<GenericValueString> spells) {
        this.spells = spells;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public List<Action> getLegendaryActions() {
        return legendaryActions;
    }

    public void setLegendaryActions(List<Action> legendaryActions) {
        this.legendaryActions = legendaryActions;
    }

    public final boolean isUnknown() {
        return unknown.get();
    }

    public final void setUnknown(boolean value) {
        unknown.set(value);
    }

    public BooleanProperty unknownProperty() {
        return unknown;
    }

    @JsonIgnore
    public String getDisplayType() {
        return this.size.getValue() + " " + this.type.get() + " " + this.alignment.get();
    }

    public Monster clone() {
        ObjectMapper mapper = new ObjectMapper();
        //mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
        String json;
        try {
            json = mapper.writeValueAsString(this);
            Monster newmonster = mapper.readValue(json, Monster.class);
            return newmonster;
        } catch (Exception ex) {
            Logger.getLogger(Monster.class.getName()).log(Level.INFO, null, ex);
        }

        return new Monster();
    }

    public BattleMonster readyForBattle() {
        ObjectMapper mapper = new ObjectMapper();
        //mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
        String json;
        try {
            json = mapper.writeValueAsString(this);
            BattleMonster newmonster = mapper.readValue(json, BattleMonster.class);
            return newmonster;
        } catch (Exception ex) {
            Logger.getLogger(Monster.class.getName()).log(Level.INFO, null, ex);
        }

        return new BattleMonster();
    }
}
