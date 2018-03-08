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
import java.util.List;

/**
 *
 * @author sean
 */
//Mirrors the setup of Database since Jackson doesn't understand the new bindable types
public class BaseDatabase {

    public BaseDatabase(@JsonProperty("Battles") List<Battle> battles, @JsonProperty("Encounters") List<Encounter> encounters, @JsonProperty("Players") List<Player> players,
            @JsonProperty("CustomMonsters") List<Monster> customMonsters, @JsonProperty("Session") Integer session) {
        this.battles = battles;
        this.encounters = encounters;
        this.players = players;
        this.customMonsters = customMonsters;
        this.xP = xP;
        this.session = session;
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
    private List<XPEvent> xP;
    @JsonProperty("Session")
    private Integer session;

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

    public List<XPEvent> getxP() {
        return xP;
    }

    public void setxP(List<XPEvent> xP) {
        this.xP = xP;
    }

    public Integer getSession() {
        return session;
    }

    public void setSession(Integer session) {
        this.session = session;
    }

}
