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

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sean
 */
public class MagicitemTable {

    public MagicitemTable(@JsonProperty("min") Integer min, @JsonProperty("max") Integer max, @JsonProperty("item") String item, @JsonProperty("table") List<String> table) {
        this.min = min;
        this.max = max;
        this.item = item;
        this.table = table;
    }

    public MagicitemTable() {
        min = 0;
        max = 0;
        item = "";
        table = new ArrayList<>();
    }

    private Integer min;
    private Integer max;
    private String item;
    private List<String> table;

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public List<String> getTable() {
        return table;
    }

    public void setTable(List<String> table) {
        this.table = table;
    }

}
