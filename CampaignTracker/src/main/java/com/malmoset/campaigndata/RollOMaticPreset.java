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
package com.malmoset.campaigndata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author sean
 */
public class RollOMaticPreset {

    public RollOMaticPreset(@JsonProperty("Boxes") List<SingleDiceBox> boxes, @JsonProperty("Name") String name) {
        this();
        this.boxes.set(FXCollections.observableArrayList(boxes));
        this.name.set(name);
    }

    public RollOMaticPreset() {
        List<SingleDiceBox> list = new ArrayList<>();
        boxes = new SimpleListProperty(FXCollections.observableArrayList(list));
        name = new SimpleStringProperty("");

    }

    @JsonProperty("Boxes")
    private ListProperty<SingleDiceBox> boxes;
    @JsonProperty("Name")
    private StringProperty name;

    public final ObservableList<SingleDiceBox> getBoxes() {
        return boxes.get();
    }

    public final void setBoxes(ObservableList<SingleDiceBox> value) {
        boxes.set(value);
    }

    public ListProperty<SingleDiceBox> boxesProperty() {
        return boxes;
    }

    public final String getName() {
        return name.get();
    }

    public final void setName(String value) {
        name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public static class SingleDiceBox {

        public SingleDiceBox() {
            diceSize = 10;
            diceCount = 1;
        }

        public SingleDiceBox(int size, int count) {
            diceSize = size;
            diceCount = count;
        }
        private int diceSize;
        private int diceCount;

        public int getDiceSize() {
            return diceSize;
        }

        public void setDiceSize(int diceSize) {
            this.diceSize = diceSize;
        }

        public int getDiceCount() {
            return diceCount;
        }

        public void setDiceCount(int diceCount) {
            this.diceCount = diceCount;
        }

    }

}
