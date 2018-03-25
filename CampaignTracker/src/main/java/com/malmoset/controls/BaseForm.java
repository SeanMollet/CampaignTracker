/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.controls;

import com.malmoset.campaigntracker.PlatformSpecific;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author sean
 */
public class BaseForm {

    private Scene scene;
    public Stage stage;
    private String style;

    public void setScene(Scene scene) {
        this.scene = scene;
        if (scene != null) {
            stage = (Stage) scene.getWindow();
            stage.focusedProperty().addListener((s, ov, nv) -> {
                if (nv) {
                    onActivated();
                }
            });

            PlatformSpecific.SetupIcon(stage);

            //Set the stylesheet if one has been specified or use a default
            if (style == null || style.length() == 0) {
                style = "/styles/Styles.css";
            }
            scene.getStylesheets().add(style);

        }
    }

    public Scene getScene() {
        return scene;
    }

    public void close() {
        if (stage != null) {
            stage.close();
        }
    }

    public void onActivated() {

    }

    public void Show() {
        if (scene != null) {
            stage = (Stage) scene.getWindow();
            stage.show();
        }
    }

    public void setStyle(String stylesheet) {
        style = stylesheet;
    }

    public static BaseForm LoadForm(URL Form, String Name) {
        return LoadForm(Form, Name, true);
    }

    public static BaseForm LoadForm(URL Form, String Name, boolean resizable) {
        return LoadForm(Form, Name, resizable, new Stage());
    }

    public static BaseForm LoadForm(URL Form, String Name, boolean resizable, Stage stage) {
        FXMLLoader loader = new FXMLLoader(Form);
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            BaseForm form = (BaseForm) loader.getController();

            scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                if (event.getCode().equals(KeyCode.ESCAPE)) {
                    //We already have the stage because we're the ones that created it
                    //Stage stage = (Stage) scene.getWindow();
                    if (stage != null) {
                        stage.close();
                    }
                }
            });

            //Done in the FXML
            //scene.getStylesheets().add(Style);
            stage.setTitle(Name);
            stage.setScene(scene);
            form.setScene(scene);
            stage.setResizable(resizable);
            if (resizable) {
                if (root instanceof AnchorPane) {
                    AnchorPane rootPane = (AnchorPane) root;
                    if (rootPane != null && rootPane.getMinWidth() > 0 && rootPane.getMinHeight() > 0) {
                        //Account for the titlebar
                        double titleBar = Double.isNaN(stage.getHeight()) ? 20 : stage.getHeight() - rootPane.getHeight();
                        stage.setMinHeight(rootPane.getMinHeight() + titleBar);
                        stage.setMinWidth(rootPane.getMinWidth());
                    }
                }
            }

            return form;
        } catch (Exception E) {
            GotItDialog.GotIt("Error loading " + Name, E.toString());

        }
        return null;
    }

}
