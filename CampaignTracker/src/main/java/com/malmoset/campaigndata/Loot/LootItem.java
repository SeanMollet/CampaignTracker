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
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author sean
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LootItem {

    private ObjectProperty<LootType> type;
    private IntegerProperty count;
    private StringProperty item;

    public LootItem(@JsonProperty("type") LootType type, @JsonProperty("count") Integer count, @JsonProperty("item") String item) {
        this();
        this.type.set(type);
        this.count.set(count);
        this.item.set(item);
    }

    public LootItem() {
        type = new SimpleObjectProperty<>(LootType.MONEY);
        count = new SimpleIntegerProperty(0);
        item = new SimpleStringProperty("");
    }

    public final LootType getType() {
        return type.get();
    }

    public final void setType(LootType value) {
        type.set(value);
    }

    public ObjectProperty<LootType> typeProperty() {
        return type;
    }

    public final int getCount() {
        return count.get();
    }

    public final void setCount(int value) {
        count.set(value);
    }

    public IntegerProperty countProperty() {
        return count;
    }

    public final String getItem() {
        return item.get();
    }

    public final void setItem(String value) {
        item.set(value);
    }

    public StringProperty itemProperty() {
        return item;
    }

    public enum LootType {
        MONEY,
        GEMSTONE,
        ARTOBJECT,
        MAGICITEM;

        public static ObservableList<String> getLootTypes() {
            ArrayList<String> results = new ArrayList<>();
            results.add("Money");
            results.add("Gem Stone");
            results.add("Art Object");
            results.add("Magic Item");
            return FXCollections.observableList(results);
        }
    }
}
