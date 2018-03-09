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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author sean
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LootItem {

    private LootType type;
    private Integer count;
    private String item;

    public LootItem(@JsonProperty("type") LootType type, @JsonProperty("count") Integer count, @JsonProperty("item") String item) {
        this.type = type;
        this.count = count;
        this.item = item;
    }

    public LootItem() {
        type = LootType.MONEY;
        count = 0;
        item = "";
    }

    public LootType getType() {
        return type;
    }

    public void setType(LootType type) {
        this.type = type;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public enum LootType {
        MONEY("Money"),
        GEMSTONES("Gem Stone"),
        ARTOBJECTS("Art Object"),
        MAGICITEMS("Magic Item");

        private String type;

        LootType(String input) {
            this.type = input;
        }

        @Override
        public String toString() {
            return type;
        }

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
