/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigndata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author sean
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Abilities {

    public Abilities(@JsonProperty("Str") Integer str, @JsonProperty("Dex") Integer dex, @JsonProperty("Con") Integer con,
            @JsonProperty("Int") Integer intelligence, @JsonProperty("Wis") Integer wis, @JsonProperty("Cha") Integer cha) {
        this.str = new SimpleIntegerProperty(str);
        this.dex = new SimpleIntegerProperty(dex);
        this.con = new SimpleIntegerProperty(con);
        this.intelligence = new SimpleIntegerProperty(intelligence);
        this.wis = new SimpleIntegerProperty(wis);
        this.cha = new SimpleIntegerProperty(cha);
    }

    public Abilities() {
        this.str = new SimpleIntegerProperty(10);
        this.dex = new SimpleIntegerProperty(10);
        this.con = new SimpleIntegerProperty(10);
        this.intelligence = new SimpleIntegerProperty(10);
        this.wis = new SimpleIntegerProperty(10);
        this.cha = new SimpleIntegerProperty(10);
    }
    @JsonProperty("Str")
    private IntegerProperty str;
    @JsonProperty("Dex")
    private IntegerProperty dex;
    @JsonProperty("Con")
    private IntegerProperty con;
    @JsonProperty("Int")
    private IntegerProperty intelligence;
    @JsonProperty("Wis")
    private IntegerProperty wis;
    @JsonProperty("Cha")
    private IntegerProperty cha;

    public final int getStr() {
        return str.get();
    }

    public final void setStr(int value) {
        str.set(value);
    }

    public IntegerProperty strProperty() {
        return str;
    }

    public final int getDex() {
        return dex.get();
    }

    public final void setDex(int value) {
        dex.set(value);
    }

    public IntegerProperty dexProperty() {
        return dex;
    }

    public final int getCon() {
        return con.get();
    }

    public final void setCon(int value) {
        con.set(value);
    }

    public IntegerProperty conProperty() {
        return con;
    }

    public final int getIntelligence() {
        return intelligence.get();
    }

    public final void setIntelligence(int value) {
        intelligence.set(value);
    }

    public IntegerProperty intelligenceProperty() {
        return intelligence;
    }

    public final int getWis() {
        return wis.get();
    }

    public final void setWis(int value) {
        wis.set(value);
    }

    public IntegerProperty wisProperty() {
        return wis;
    }

    public final int getCha() {
        return cha.get();
    }

    public final void setCha(int value) {
        cha.set(value);
    }

    public IntegerProperty chaProperty() {
        return cha;
    }

    public int getModifier(String ability) {
        int mod = 10;
        switch (ability) {
            case "Str":
                mod = str.get();
                break;
            case "Dex":
                mod = dex.get();
                break;
            case "Con":
                mod = con.get();
                break;
            case "Int":
                mod = intelligence.get();
                break;
            case "Wis":
                mod = wis.get();
                break;
            case "Cha":
                mod = cha.get();
                break;
        }
        return (mod - 10) / 2;
    }

    @JsonIgnore
    public static ObservableList<String> AbilitiesList() {
        List<String> abil = new ArrayList<>();
        abil.add("Str");
        abil.add("Dex");
        abil.add("Con");
        abil.add("Int");
        abil.add("Wis");
        abil.add("Cha");
        return FXCollections.observableArrayList(abil);
    }
}
