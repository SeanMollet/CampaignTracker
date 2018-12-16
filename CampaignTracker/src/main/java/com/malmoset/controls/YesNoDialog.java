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

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;

/**
 *
 * @author sean
 */
public class YesNoDialog {

    public enum Results {
        YES,
        NO,
        CANCEL
    }

    public static Results Display(String title, String text, boolean AllowCancel) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        //alert.setHeaderText("header text");
        alert.setContentText(text);

        ButtonType buttonTypeOne = new ButtonType("Yes");
        ButtonType buttonTypeTwo = new ButtonType("No");
        if (AllowCancel) {
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);
        } else {
            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
        }

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne) {
            return Results.YES;
        } else if (result.get() == buttonTypeTwo) {
            return Results.NO;
        } else {
            return Results.CANCEL;
        }
    }
}
