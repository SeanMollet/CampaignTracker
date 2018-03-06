/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigndata;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author sean
 */
public class Encounter {

    @JsonProperty("Name")
    private StringProperty name;
    @JsonProperty("Challenge")
    private ObjectProperty<Fraction> challenge;
    @JsonProperty("Description")
    private StringProperty description;
    private ListProperty<Monster> monsters;

}
