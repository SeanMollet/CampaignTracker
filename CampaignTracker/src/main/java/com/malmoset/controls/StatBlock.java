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
package com.malmoset.controls;

import com.malmoset.dice.Dice;
import java.io.IOException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.NumberStringConverter;

/**
 * FXML Controller class
 *
 * @author sean
 */
public class StatBlock extends AnchorPane {

    @FXML
    private Label StatName;
    @FXML
    private TextField StatValue;
    @FXML
    private Label StatBonus;

    private IntegerProperty statValue;
    private StringProperty statName;

    public StatBlock() {
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Controls/StatBlock.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        SetupControls();
        BindFields();
    }

    private void BindFields() {
        statValue = new SimpleIntegerProperty(0);
        statName = new SimpleStringProperty("");

        StatValue.textProperty().bindBidirectional(statValue, new NumberStringConverter());
        StatName.textProperty().bindBidirectional(statName);

    }

    private void SetupControls() {
        if (StatValue != null && StatValue.textProperty() != null) {
            StatValue.textProperty().addListener((observable, oldValue, newValue) -> {
                if (Dice.tryParseInt(newValue)) {
                    Integer stat = Integer.parseInt(newValue);
                    stat = (stat - 10) / 2;
                    if (stat > 0) {
                        StatBonus.setText("+" + stat.toString());
                    } else {
                        StatBonus.setText(stat.toString());
                    }
                } else {
                    if (Dice.tryParseInt(oldValue)) {
                        StatValue.setText(oldValue);
                    } else {
                        StatValue.setText("0");
                    }
                }
            });
        }
    }

    public final int getStatValue() {
        return statValue.get();
    }

    public final void setStatValue(int value) {
        statValue.set(value);
    }

    public IntegerProperty statValueProperty() {
        return statValue;
    }

    public final String getStatName() {
        return statName.get();
    }

    public final void setStatName(String value) {
        statName.set(value);
    }

    public StringProperty statNameProperty() {
        return statName;
    }

}
