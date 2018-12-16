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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.malmoset.campaigntracker.MainApp;
import com.malmoset.controls.HPAppearance;
import com.malmoset.dice.Dice;
import java.util.Date;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class BattleMonster extends Monster {

    public BattleMonster(@JsonProperty("Spawned") Date spawned, @JsonProperty("Index") Integer index,
            @JsonProperty("Persuaded") Boolean persuaded, @JsonProperty("XPGiven") Integer xPGiven, @JsonProperty("CurrentHP") Integer currenthp,
            //Below here are directly from moster
            @JsonProperty("Name") String name, @JsonProperty("Size") CreatureSize size, @JsonProperty("Type") String type,
            @JsonProperty("Tags") List<String> tags, @JsonProperty("Source") String source, @JsonProperty("Alignment") String alignment,
            @JsonProperty("AC") AC aC, @JsonProperty("HP") HP hP, @JsonProperty("InitiativeModifier") Integer initiativeModifier,
            @JsonProperty("Speed") List<GenericValueString> speed, @JsonProperty("Abilities") Abilities abilities,
            @JsonProperty("DamageVulnerabilities") List<GenericValueString> damageVulnerabilities, @JsonProperty("DamageResistances") List<GenericValueString> damageResistances,
            @JsonProperty("DamageImmunities") List<GenericValueString> damageImmunities, @JsonProperty("ConditionImmunities") List<GenericValueString> conditionImmunities,
            @JsonProperty("Saves") List<StatWithModifier> saves, @JsonProperty("Skills") List<StatWithModifier> skills, @JsonProperty("Senses") List<GenericValueString> senses,
            @JsonProperty("Languages") List<GenericValueString> languages, @JsonProperty("Challenge") Fraction challenge, @JsonProperty("Traits") List<Action> traits,
            @JsonProperty("Actions") List<Action> actions, @JsonProperty("Reactions") List<Action> reactions, @JsonProperty("LegendaryActions") List<Action> legendaryActions,
            @JsonProperty("Spells") List<GenericValueString> spells, @JsonProperty("Hidden") Boolean hidden, @JsonProperty("Unknown") Boolean unknown, @JsonProperty("ReadOnly") Boolean readOnly) {
        super(name, size, type,
                tags, source, alignment,
                aC, hP, initiativeModifier,
                speed, abilities,
                damageVulnerabilities, damageResistances,
                damageImmunities, conditionImmunities,
                saves, skills, senses,
                languages, challenge, traits,
                actions, reactions, legendaryActions,
                spells, hidden, unknown, readOnly);
        this.spawned = new SimpleObjectProperty<>(spawned != null ? spawned : new Date());
        this.index = new SimpleIntegerProperty(index != null ? index : 0);
        this.persuaded = new SimpleBooleanProperty(persuaded != null ? persuaded : false);
        this.xPGiven = new SimpleIntegerProperty(xPGiven != null ? xPGiven : 0);
        this.savingRoll = new SimpleIntegerProperty(0);
        this.hPtoChange = new SimpleIntegerProperty(0);
        this.currentHP = new SimpleIntegerProperty(currenthp != null ? currenthp : hP.getHpValue());
        this.pcName = new SimpleStringProperty();
        this.pcType = new SimpleStringProperty();
        currentHPSet = true;
        setAppearance("");
        BindFields();
    }

    public BattleMonster() {
        super();
        this.spawned = new SimpleObjectProperty<>(new Date());
        this.index = new SimpleIntegerProperty();
        this.persuaded = new SimpleBooleanProperty();
        this.xPGiven = new SimpleIntegerProperty();
        this.savingRoll = new SimpleIntegerProperty();
        this.hPtoChange = new SimpleIntegerProperty();
        this.currentHP = new SimpleIntegerProperty();
        this.pcName = new SimpleStringProperty();
        this.pcType = new SimpleStringProperty();
        setAppearance("");
        BindFields();
    }

    private void BindFields() {
        this.unknownProperty().addListener((obv, oldval, newval) -> {
            setAppearance("");
        });
        this.hiddenProperty().addListener((obv, oldval, newval) -> {
            MainApp.getAppData().getDb().monsterRevealsProperty().set(MainApp.getAppData().getDb().monsterRevealsProperty().get() + 1);
        });
        this.getHP().hpValueProperty().addListener((obv, oldval, newval) -> {
            setAppearance("");
        });
        this.currentHP.addListener((obv, oldval, newval) -> {
            setAppearance("");
        });
        this.persuaded.addListener((obv, oldval, newval) -> {
            setAppearance("");
        });
    }
    private ObjectProperty<Date> spawned;
    private IntegerProperty index;
    @JsonProperty("Persuaded")
    private BooleanProperty persuaded;
    private IntegerProperty xPGiven;
    @JsonIgnore
    private StringProperty appearance;
    @JsonIgnore
    private IntegerProperty savingRoll;
    @JsonIgnore
    private IntegerProperty hPtoChange;
    @JsonIgnore
    private boolean currentHPSet = false;
    @JsonIgnore
    private StringProperty pcName;
    @JsonIgnore
    private StringProperty pcType;

    @JsonProperty("CurrentHP")
    private IntegerProperty currentHP;

    public void RollSave(String type) {
        int statmod = this.getAbilities().getModifier(type);
        int savemod = 0;
        for (StatWithModifier save : this.getSaves()) {
            if (save.getName().toLowerCase().trim().startsWith(type.toLowerCase().trim())) {
                savemod = save.getModifier();
            }
        }
        //They're only allowed one of the two
        //If the specific saving properties are greater than the stat, use them instead
        if (savemod > statmod) {
            statmod = savemod;
        }
        //Roll it!
        this.savingRollProperty().set(Dice.roll(20, Dice.RollTypes.Normal) + statmod);

    }

    public int getCurrentHP() {
        if (!currentHPSet) {
            currentHP.set(this.getHP().getHpValue());
            currentHPSet = true;
        }
        return currentHP.get();
    }

    public void setCurrentHP(int value) {
        currentHP.set(value);
        currentHPSet = true;
    }

    @JsonProperty("Spawned")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SS")
    public final Date getSpawned() {
        return spawned.get();
    }

    @JsonProperty("Spawned")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SS")
    public final void setSpawned(Date value) {
        spawned.set(value);
    }

    public ObjectProperty<Date> spawnedProperty() {
        return spawned;
    }

    @JsonProperty("Index")
    public final int getIndex() {
        return index.get();
    }

    @JsonProperty("Index")
    public final void setIndex(int value) {
        index.set(value);
    }

    public IntegerProperty indexProperty() {
        return index;
    }

    public final boolean isPersuaded() {
        return persuaded.get();
    }

    public final void setPersuaded(boolean value) {
        persuaded.set(value);
    }

    public BooleanProperty persuadedProperty() {
        return persuaded;
    }

    @JsonProperty("XPGiven")
    public final int getXPGiven() {
        return xPGiven.get();
    }

    @JsonProperty("XPGiven")
    public final void setXPGiven(int value) {
        xPGiven.set(value);
    }

    public IntegerProperty xPGivenProperty() {
        return xPGiven;
    }

    public final int getSavingRoll() {
        return savingRoll.get();
    }

    public final void setSavingRoll(int value) {
        savingRoll.set(value);
    }

    public IntegerProperty savingRollProperty() {
        return savingRoll;
    }

    public final int getHPtoChange() {
        return hPtoChange.get();
    }

    public final void setHPtoChange(int value) {
        hPtoChange.set(value);
    }

    public IntegerProperty hPtoChangeProperty() {
        return hPtoChange;
    }

    public final String getAppearance() {
        return appearance.get();
    }

    public final void setAppearance(String value) {
        String app = HPAppearance.Appearance(this.currentHP.get(), this.getHP().getHpValue());
        if (this.persuaded.get()) {
            app = "Persuaded";
        } else if (currentHP.get() <= 0) {
            app = "Dead";
        }

        if (appearance == null) {
            appearance = new SimpleStringProperty();
        }
        appearance.set(app);

        //Handle PC view of name and type
        if (this.unknownProperty().get()) {
            this.pcName.set("Unknown");
            this.pcType.set(this.getPCDisplayType());
        } else {
            this.pcName.set(this.getName());
            this.pcType.set(this.getPCDisplayType());
        }
    }

    public StringProperty appearanceProperty() {
        return appearance;
    }

    public IntegerProperty currentHPProperty() {
        return currentHP;
    }

    public final String getPcName() {
        return pcName.get();
    }

    public final void setPcName(String value) {
        pcName.set(value);
    }

    public StringProperty pcNameProperty() {
        return pcName;
    }

    public final String getPcType() {
        return pcType.get();
    }

    public final void setPcType(String value) {
        pcType.set(value);
    }

    public StringProperty pcTypeProperty() {
        return pcType;
    }

}
