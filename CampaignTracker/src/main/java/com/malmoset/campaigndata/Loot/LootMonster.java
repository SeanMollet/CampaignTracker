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
import com.malmoset.campaigndata.Fraction;

/**
 *
 * @author sean
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LootMonster {

    public LootMonster(@JsonProperty("quantity") Integer quantity, @JsonProperty("monsterName") String monsterName, @JsonProperty("challenge") Fraction challenge, @JsonProperty("hoard") boolean hoard) {
        this.quantity = quantity;
        this.monsterName = monsterName;
        this.challenge = challenge;
        this.hoard = hoard;
    }

    public LootMonster() {
        this.quantity = 0;
        this.monsterName = "";
        this.challenge = new Fraction("0");
        this.hoard = false;
    }
    private Integer quantity;
    private String monsterName;
    private Fraction challenge;
    private boolean hoard;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getMonsterName() {
        return monsterName;
    }

    public void setMonsterName(String monsterName) {
        this.monsterName = monsterName;
    }

    public Fraction getChallenge() {
        return challenge;
    }

    public void setChallenge(Fraction challenge) {
        this.challenge = challenge;
    }

    public boolean isHoard() {
        return hoard;
    }

    public void setHoard(boolean hoard) {
        this.hoard = hoard;
    }

}
