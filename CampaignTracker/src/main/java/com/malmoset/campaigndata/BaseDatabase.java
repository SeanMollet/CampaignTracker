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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.malmoset.campaigndata.Loot.LootItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author sean
 */
//Mirrors the setup of Database since Jackson doesn't understand the new bindable types
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseDatabase {

    public BaseDatabase(@JsonProperty("Battles") List<Battle> battles, @JsonProperty("Encounters") List<Encounter> encounters,
            @JsonProperty("Players") List<Player> players, @JsonProperty("CustomMonsters") List<Monster> customMonsters,
            @JsonProperty("XP") Map<Integer, List<XPEvent>> xP, @JsonProperty("Session") Integer session, @JsonProperty("Loot") List<LootItem> loot) {
        this();
        if (battles != null) {
            this.battles = battles;
        }
        if (encounters != null) {
            this.encounters = encounters;
        }
        if (players != null) {
            this.players = players;
        }
        if (customMonsters != null) {
            this.customMonsters = customMonsters;
        }
        if (xP != null) {
            this.xP = xP;
        }
        if (session != null) {
            this.session = session;
        }
        if (loot != null) {
            this.loot = loot;
        }
    }

    public BaseDatabase() {
        battles = new ArrayList<>();
        encounters = new ArrayList<>();
        players = new ArrayList<>();
        customMonsters = new ArrayList<>();
        xP = new HashMap<>();
        session = 1;
        loot = new ArrayList<>();
    }
    @JsonProperty("Battles")
    private List<Battle> battles;
    @JsonProperty("Encounters")
    private List<Encounter> encounters;
    @JsonProperty("Players")
    private List<Player> players;
    @JsonProperty("CustomMonsters")
    private List<Monster> customMonsters;
    @JsonProperty("XP")
    private Map<Integer, List<XPEvent>> xP;
    @JsonProperty("Session")
    private Integer session;
    @JsonProperty("Loot")
    private List<LootItem> loot;

    public List<Battle> getBattles() {
        return battles;
    }

    public void setBattles(List<Battle> battles) {
        this.battles = battles;
    }

    public List<Encounter> getEncounters() {
        return encounters;
    }

    public void setEncounters(List<Encounter> encounters) {
        this.encounters = encounters;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Monster> getCustomMonsters() {
        return customMonsters;
    }

    public void setCustomMonsters(List<Monster> customMonsters) {
        this.customMonsters = customMonsters;
    }

    public Map<Integer, List<XPEvent>> getxP() {
        return xP;
    }

    public void setxP(Map<Integer, List<XPEvent>> xP) {
        this.xP = xP;
    }

    public Integer getSession() {
        return session;
    }

    public void setSession(Integer session) {
        this.session = session;
    }

    public List<LootItem> getLoot() {
        return loot;
    }

    public void setLoot(List<LootItem> loot) {
        this.loot = loot;
    }

}
