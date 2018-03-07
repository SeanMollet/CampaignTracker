/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigndata;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author sean
 */
public class Trait {

    public Trait(@JsonProperty("Name") String name, @JsonProperty("Content") String content, @JsonProperty("Usage") String usage) {
        this.name = new SimpleStringProperty(name);
        this.content = new SimpleStringProperty(content);
        this.usage = new SimpleStringProperty(usage);
    }

    public Trait() {
        this.name = new SimpleStringProperty();
        this.content = new SimpleStringProperty();
        this.usage = new SimpleStringProperty();
    }

    @JsonProperty("Name")
    private StringProperty name;
    @JsonProperty("Content")
    private StringProperty content;
    @JsonProperty("Usage")
    private StringProperty usage;

    public final String getName() {
        return name.get();
    }

    public final void setName(String value) {
        name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public final String getContent() {
        return content.get();
    }

    public final void setContent(String value) {
        content.set(value);
    }

    public StringProperty contentProperty() {
        return content;
    }

    public final String getUsage() {
        return usage.get();
    }

    public final void setUsage(String value) {
        usage.set(value);
    }

    public StringProperty usageProperty() {
        return usage;
    }

}
