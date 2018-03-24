/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
