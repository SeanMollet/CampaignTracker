/* 
 * Copyright 2018 Malmoset LLC.
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.malmoset.campaigntracker.MainApp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author sean
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Battle {

    public Battle(@JsonProperty("Began") Date began, @JsonProperty("Monsters") List<BattleMonster> monsters,
            @JsonProperty("BattleNumber") Integer battleNumber, @JsonProperty("Session") Integer session) {
        this.began = new SimpleObjectProperty<Date>(began);
        this.monsters = new SimpleListProperty(FXCollections.observableArrayList(monsters));
        this.battleNumber = new SimpleIntegerProperty(battleNumber);
        this.session = new SimpleIntegerProperty(session);
    }

    public Battle() {
        monsters = new SimpleListProperty(FXCollections.observableArrayList(new ArrayList<BattleMonster>()));
        this.began = new SimpleObjectProperty<Date>(new Date());
        if (MainApp.isAppDataUP()) {
            this.battleNumber = new SimpleIntegerProperty(MainApp.getAppData().getDb().getBattleNumber());
            this.session = new SimpleIntegerProperty(MainApp.getAppData().getDb().getSession());
        } else {
            this.battleNumber = new SimpleIntegerProperty(1);
            this.session = new SimpleIntegerProperty(1);
        }
    }
    @JsonProperty("Began")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SS")
    private ObjectProperty<Date> began;
    @JsonProperty("Monsters")
    private SimpleListProperty<BattleMonster> monsters;
    @JsonProperty("BattleNumber")
    private IntegerProperty battleNumber;
    @JsonProperty("Session")
    private IntegerProperty session;

    public void GrantXP(Integer Session, String Reason, BattleMonster monster) {
        if (monster.xPGivenProperty().get() <= 0) {
            XPEvent xp = new XPEvent();
            xp.setEvent(monster.getName() + " " + ((Integer) monster.getIndex()).toString() + " " + Reason);
            xp.setSession(Session);
            xp.setBattle(this.getBattleNumber());
            xp.setMonster(monster.getName());
            xp.setTimestamp(new Date());

            xp.setXP(BattleXP.GetXP(monster.getChallenge()));
            monster.setXPGiven(xp.getXP());
            MainApp.getAppData().getDb().getSessionXP().add(xp);
        }
    }

    public void AddMonster(Monster monster, boolean Hidden) {
        BattleMonster converted = monster.readyForBattle();
        converted.indexProperty().set(monsters.getSize() + 1);
        //If they're adding directly to a battle, we hide
        //If not, then we allow the encounter to dictate what we do
        if (Hidden) {
            converted.setHidden(Hidden);
            converted.setUnknown(Hidden);
        }
        monsters.add(converted);
    }

    public final Date getBegan() {
        return began.get();
    }

    public final void setBegan(Date value) {
        began.set(value);
    }

    public ObjectProperty<Date> beganProperty() {
        return began;
    }

    @JsonIgnore
    public final int getLiveMonsters() {
        return (int) monsters.stream().filter(x -> x.getCurrentHP() > 0 && !x.isPersuaded()).count();
    }

    @JsonIgnore
    public final int getTotalMonsters() {
        return (int) monsters.stream().count();
    }

    @JsonIgnore
    public final int getPersuadedMonsters() {
        return (int) monsters.stream().filter(x -> x.isPersuaded()).count();
    }

    @JsonIgnore
    public final int getUntouchedMonsters() {
        return (int) monsters.stream().filter(x -> x.getCurrentHP() > 0 && !x.isPersuaded()).count();
    }

    @JsonIgnore
    public final int getDeadMonsters() {
        return (int) monsters.stream().filter(x -> x.getCurrentHP() <= 0 && !x.isPersuaded()).count();
    }

    @JsonIgnore
    public final int getTotalXP() {
        return (int) monsters.stream().collect(Collectors.summingInt(x -> x.getXPGiven()));
    }

    public final ObservableList<BattleMonster> getMonsters() {
        return monsters.get();
    }

    public final void setMonsters(ObservableList<BattleMonster> value) {
        monsters.set(value);
    }

    public final ListProperty monstersProperty() {
        return monsters;
    }

    public final int getBattleNumber() {
        return battleNumber.get();
    }

    public final void setBattleNumber(int value) {
        battleNumber.set(value);
    }

    public IntegerProperty battleNumberProperty() {
        return battleNumber;
    }

    public final int getSession() {
        return session.get();
    }

    public final void setSession(int value) {
        session.set(value);
    }

    public IntegerProperty sessionProperty() {
        return session;
    }

    public static class BattleXP {

        private static HashMap<Fraction, Integer> XPDic;

        public static int GetXP(Fraction CR) {
            if (XPDic == null) {
                LoadXP();
            }
            //Java doesn't use equality in a useful way for this lookup, so we do it by hand
            //Optional<Map.Entry<Fraction, Integer>> entry = XPDic.entrySet().stream().filter(x -> x.getKey().equals(CR)).findFirst();
            //if (entry.isPresent()) {
            if (XPDic.containsKey(CR)) {
                return XPDic.get(CR);
            }
            if (CR.compareTo(30) < 0) {
                int levels = CR.toInt() - 30;
                int basexp = XPDic.get(new Fraction("30"));
                return basexp + (levels * 15000);
            }
            return 0;
        }

        private static void LoadXP() {
            XPDic = new HashMap<>();
            XPDic.put(new Fraction("0"), 10);
            XPDic.put(new Fraction("1/8"), 25);
            XPDic.put(new Fraction("1/4"), 50);
            XPDic.put(new Fraction("1/2"), 100);
            XPDic.put(new Fraction("1"), 200);
            XPDic.put(new Fraction("2"), 450);
            XPDic.put(new Fraction("3"), 700);
            XPDic.put(new Fraction("4"), 1100);
            XPDic.put(new Fraction("5"), 1800);
            XPDic.put(new Fraction("6"), 2300);
            XPDic.put(new Fraction("7"), 2900);
            XPDic.put(new Fraction("8"), 3900);
            XPDic.put(new Fraction("9"), 5000);
            XPDic.put(new Fraction("10"), 5900);
            XPDic.put(new Fraction("11"), 7200);
            XPDic.put(new Fraction("12"), 8400);
            XPDic.put(new Fraction("13"), 10000);
            XPDic.put(new Fraction("14"), 11500);
            XPDic.put(new Fraction("15"), 13000);
            XPDic.put(new Fraction("16"), 15000);
            XPDic.put(new Fraction("17"), 18000);
            XPDic.put(new Fraction("18"), 20000);
            XPDic.put(new Fraction("19"), 22000);
            XPDic.put(new Fraction("20"), 25000);
            XPDic.put(new Fraction("21"), 33000);
            XPDic.put(new Fraction("22"), 41000);
            XPDic.put(new Fraction("23"), 50000);
            XPDic.put(new Fraction("24"), 62000);
            XPDic.put(new Fraction("25"), 75000);
            XPDic.put(new Fraction("26"), 90000);
            XPDic.put(new Fraction("27"), 105000);
            XPDic.put(new Fraction("28"), 120000);
            XPDic.put(new Fraction("29"), 135000);
            XPDic.put(new Fraction("30 "), 155000);
        }
    }
}
