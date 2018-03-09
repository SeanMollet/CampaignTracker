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
import java.util.List;

/**
 *
 * @author sean
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IndividualTableMaster {

    public IndividualTableMaster(@JsonProperty("name") String name, @JsonProperty("mincr") Integer mincr,
            @JsonProperty("maxcr") Integer maxcr, @JsonProperty("table") List<IndividualCoinTable> table) {
        this.name = name;
        this.mincr = mincr;
        this.maxcr = maxcr;
        this.table = table;
    }

    public IndividualTableMaster() {
        this.name = "";
        this.mincr = 0;
        this.maxcr = 0;
        this.table = new ArrayList<>();
    }

    private String name;
    private Integer mincr;
    private Integer maxcr;
    private List<IndividualCoinTable> table;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMincr() {
        return mincr;
    }

    public void setMincr(Integer mincr) {
        this.mincr = mincr;
    }

    public Integer getMaxcr() {
        return maxcr;
    }

    public void setMaxcr(Integer maxcr) {
        this.maxcr = maxcr;
    }

    public List<IndividualCoinTable> getTable() {
        return table;
    }

    public void setTable(List<IndividualCoinTable> table) {
        this.table = table;
    }

}
