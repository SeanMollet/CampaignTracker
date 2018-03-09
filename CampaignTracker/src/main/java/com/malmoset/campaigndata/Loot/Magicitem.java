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
public class Magicitem {

    public Magicitem(@JsonProperty("name") String name, @JsonProperty("type") String type, @JsonProperty("table") List<MagicitemTable> table) {
        this.name = name;
        this.type = type;
        this.table = table;
    }

    public Magicitem() {
        name = "";
        type = "";
        table = new ArrayList<>();
    }

    private String name;
    private String type;
    private List<MagicitemTable> table;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<MagicitemTable> getTable() {
        return table;
    }

    public void setTable(List<MagicitemTable> table) {
        this.table = table;
    }
}
