/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.controls;

import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 *
 * @author sean
 */
public class BaseForm {

    private Scene scene;

    public void setScene(Scene scene) {
        this.scene = scene;

    }

    public void Show() {
        if (scene != null) {
            Stage stage = (Stage) scene.getWindow();
            stage.show();
        }
    }

    public static BaseForm LoadForm(URL Form, String Style, String Name) {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(Form);
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            BaseForm form = (BaseForm) loader.getController();
            form.setScene(scene);

            scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                if (event.getCode().equals(KeyCode.ESCAPE)) {
                    //We already have the stage because we're the ones that created it
                    //Stage stage = (Stage) scene.getWindow();
                    if (stage != null) {
                        stage.close();
                    }
                }
            });

            scene.getStylesheets().add(Style);

            stage.setTitle(Name);
            stage.setScene(scene);

            return form;
        } catch (Exception E) {
            Dialog dialog = new Dialog();
            dialog.setTitle("Error");
            dialog.setContentText(E.toString());

            dialog.show();

        }
        return null;
    }

}
