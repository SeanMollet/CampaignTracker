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
import javafx.collections.ObservableList;

/**
 *
 * @author sean
 */
public class Monster {

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

    public final ObservableList<StringProperty> getTags() {
        return tags.get();
    }

    public final void setTags(ObservableList<StringProperty> value) {
        tags.set(value);
    }

    public ListProperty<StringProperty> tagsProperty() {
        return tags;
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

    public final ObservableList<Speed> getSpeed() {
        return speed.get();
    }

    public final void setSpeed(ObservableList<Speed> value) {
        speed.set(value);
    }

    public ListProperty<Speed> speedProperty() {
        return speed;
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

    public final ObservableList<StringProperty> getDamageVulnerabilities() {
        return damageVulnerabilities.get();
    }

    public final void setDamageVulnerabilities(ObservableList<StringProperty> value) {
        damageVulnerabilities.set(value);
    }

    public ListProperty<StringProperty> damageVulnerabilitiesProperty() {
        return damageVulnerabilities;
    }

    public final ObservableList<StringProperty> getDamageResistances() {
        return damageResistances.get();
    }

    public final void setDamageResistances(ObservableList<StringProperty> value) {
        damageResistances.set(value);
    }

    public ListProperty<StringProperty> damageResistancesProperty() {
        return damageResistances;
    }

    public final ObservableList<StringProperty> getDamageImmunities() {
        return damageImmunities.get();
    }

    public final void setDamageImmunities(ObservableList<StringProperty> value) {
        damageImmunities.set(value);
    }

    public ListProperty<StringProperty> damageImmunitiesProperty() {
        return damageImmunities;
    }

    public final ObservableList<StringProperty> getConditionImmunities() {
        return conditionImmunities.get();
    }

    public final void setConditionImmunities(ObservableList<StringProperty> value) {
        conditionImmunities.set(value);
    }

    public ListProperty<StringProperty> conditionImmunitiesProperty() {
        return conditionImmunities;
    }

    public final ObservableList<Save> getSaves() {
        return saves.get();
    }

    public final void setSaves(ObservableList<Save> value) {
        saves.set(value);
    }

    public ListProperty<Save> savesProperty() {
        return saves;
    }

    public final ObservableList<Skill> getSkills() {
        return skills.get();
    }

    public final void setSkills(ObservableList<Skill> value) {
        skills.set(value);
    }

    public ListProperty<Skill> skillsProperty() {
        return skills;
    }

    public final ObservableList<StringProperty> getSenses() {
        return senses.get();
    }

    public final void setSenses(ObservableList<StringProperty> value) {
        senses.set(value);
    }

    public ListProperty<StringProperty> sensesProperty() {
        return senses;
    }

    public final ObservableList<StringProperty> getLanguages() {
        return languages.get();
    }

    public final void setLanguages(ObservableList<StringProperty> value) {
        languages.set(value);
    }

    public ListProperty<StringProperty> languagesProperty() {
        return languages;
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

    public final ObservableList<Trait> getTraits() {
        return traits.get();
    }

    public final void setTraits(ObservableList<Trait> value) {
        traits.set(value);
    }

    public ListProperty<Trait> traitsProperty() {
        return traits;
    }

    public final ObservableList<Action> getActions() {
        return actions.get();
    }

    public final void setActions(ObservableList<Action> value) {
        actions.set(value);
    }

    public ListProperty<Action> actionsProperty() {
        return actions;
    }

    public final ObservableList<Object> getReactions() {
        return reactions.get();
    }

    public final void setReactions(ObservableList<Object> value) {
        reactions.set(value);
    }

    public ListProperty<Object> reactionsProperty() {
        return reactions;
    }

    public final ObservableList<LegendaryAction> getLegendaryActions() {
        return legendaryActions.get();
    }

    public final void setLegendaryActions(ObservableList<LegendaryAction> value) {
        legendaryActions.set(value);
    }

    public ListProperty<LegendaryAction> legendaryActionsProperty() {
        return legendaryActions;
    }

    public final ObservableList<StringProperty> getSpells() {
        return spells.get();
    }

    public final void setSpells(ObservableList<StringProperty> value) {
        spells.set(value);
    }

    public ListProperty<StringProperty> spellsProperty() {
        return spells;
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
