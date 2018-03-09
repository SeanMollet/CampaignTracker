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

/**
 *
 * @author sean
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HoardTable {

    public HoardTable(@JsonProperty("min") Integer min, @JsonProperty("max") Integer max, @JsonProperty("gems") Gems gems,
            @JsonProperty("artobjects") Artobjects artobjects, @JsonProperty("magicitems") MagicitemsTableSelections magicitems) {
        this.min = min;
        this.max = max;
        this.gems = gems;
        this.artobjects = artobjects;
        this.magicitems = magicitems;
    }

    public HoardTable() {
        min = 0;
        max = 0;
        gems = new Gems();
        artobjects = new Artobjects();
        magicitems = new MagicitemsTableSelections();
    }

    private Integer min;
    private Integer max;
    private Gems gems;
    private Artobjects artobjects;
    private MagicitemsTableSelections magicitems;

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

    public Gems getGems() {
        return gems;
    }

    public void setGems(Gems gems) {
        this.gems = gems;
    }

    public Artobjects getArtobjects() {
        return artobjects;
    }

    public void setArtobjects(Artobjects artobjects) {
        this.artobjects = artobjects;
    }

    public MagicitemsTableSelections getMagicitems() {
        return magicitems;
    }

    public void setMagicitems(MagicitemsTableSelections magicitems) {
        this.magicitems = magicitems;
    }

}
