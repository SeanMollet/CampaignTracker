/*
 * Copyright 2018 Malmoset, LLC.
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
package com.malmoset.campaigndata.Loot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.malmoset.campaigndata.Utilities;
import com.malmoset.controls.GotItDialog;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author sean
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LootTables {

    private final ListProperty<IndividualTableMaster> individual;
    private final ListProperty<HoardTableMaster> hoard;
    private final ListProperty<Gemstone> gemstones;
    private final ListProperty<Artobject> artobjects;
    private final ListProperty<Magicitem> magicitems;

    public LootTables(@JsonProperty("individual") List<IndividualTableMaster> individual, @JsonProperty("hoard") List<HoardTableMaster> hoard,
            @JsonProperty("gemstones") List<Gemstone> gemstones, @JsonProperty("artobjects") List<Artobject> artobjects,
            @JsonProperty("magicitems") List<Magicitem> magicitems) {
        this.individual = new SimpleListProperty<>(FXCollections.observableList(individual));
        this.hoard = new SimpleListProperty<>(FXCollections.observableList(hoard));
        this.gemstones = new SimpleListProperty<>(FXCollections.observableList(gemstones));
        this.artobjects = new SimpleListProperty<>(FXCollections.observableList(artobjects));
        this.magicitems = new SimpleListProperty<>(FXCollections.observableList(magicitems));

    }

    public LootTables() {
        this.individual = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>()));
        this.hoard = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>()));
        this.gemstones = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>()));
        this.artobjects = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>()));
        this.magicitems = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>()));
    }

    public static LootTables loadLoot() {
        String File = "";
        try {
            Map<String, String> files = Utilities.FindFiles("^Loot.*\\.json");

            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

            for (Map.Entry<String, String> file : files.entrySet()) {
                File = file.getKey();
                LootTables newloot = mapper.readValue(file.getValue(), LootTables.class);
                return newloot;
            }
        } catch (Exception E) {
            //If this blows up, they won't get any entries. That's acceptable
            GotItDialog.GotIt("Could not load loot", "Error in \" + File");
        }
        return new LootTables();
    }

    public final ObservableList<IndividualTableMaster> getIndividual() {
        return individual.get();
    }

    public final void setIndividual(ObservableList<IndividualTableMaster> value) {
        individual.set(value);
    }

    public ListProperty<IndividualTableMaster> individualProperty() {
        return individual;
    }

    public final ObservableList<HoardTableMaster> getHoard() {
        return hoard.get();
    }

    public final void setHoard(ObservableList<HoardTableMaster> value) {
        hoard.set(value);
    }

    public ListProperty<HoardTableMaster> hoardProperty() {
        return hoard;
    }

    public final ObservableList<Gemstone> getGemstones() {
        return gemstones.get();
    }

    public final void setGemstones(ObservableList<Gemstone> value) {
        gemstones.set(value);
    }

    public ListProperty<Gemstone> gemstonesProperty() {
        return gemstones;
    }

    public final ObservableList<Artobject> getArtobjects() {
        return artobjects.get();
    }

    public final void setArtobjects(ObservableList<Artobject> value) {
        artobjects.set(value);
    }

    public ListProperty<Artobject> artobjectsProperty() {
        return artobjects;
    }

    public final ObservableList<Magicitem> getMagicitems() {
        return magicitems.get();
    }

    public final void setMagicitems(ObservableList<Magicitem> value) {
        magicitems.set(value);
    }

    public ListProperty<Magicitem> magicitemsProperty() {
        return magicitems;
    }
}
