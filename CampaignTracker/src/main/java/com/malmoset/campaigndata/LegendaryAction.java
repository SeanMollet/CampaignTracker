/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigndata;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author sean
 */
public class LegendaryAction {

    @JsonProperty("Name")
    private StringProperty name;
    @JsonProperty("Content")
    private StringProperty content;
}
