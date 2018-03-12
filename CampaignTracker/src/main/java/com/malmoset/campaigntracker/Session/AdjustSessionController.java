/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigntracker.Session;

import com.malmoset.campaigntracker.MainApp;
import com.malmoset.campaigntrackercontrols.SpinnerUtils;
import com.malmoset.controls.BaseForm;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

/**
 * FXML Controller class
 *
 * @author sean
 */
public class AdjustSessionController extends BaseForm implements Initializable {

    @FXML
    private Spinner<Integer> SessionBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        SpinnerValueFactory<Integer> valfact = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100000, 1);
        SessionBox.setValueFactory(valfact);
        SessionBox.getValueFactory().valueProperty().bindBidirectional(MainApp.getAppData().getDb().sessionProperty().asObject());
        SessionBox.focusedProperty().addListener((s, ov, nv) -> {
            if (!nv) {
                SpinnerUtils.commitEditorText(SessionBox);
            }
        });

    }

}
