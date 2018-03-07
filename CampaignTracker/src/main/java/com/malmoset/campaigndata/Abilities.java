/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigndata;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author sean
 */
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

}
