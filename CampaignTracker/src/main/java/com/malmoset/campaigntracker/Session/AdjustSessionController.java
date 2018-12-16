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
package com.malmoset.campaigntracker.Session;

import com.malmoset.campaigntracker.MainApp;
import com.malmoset.controls.SpinnerUtils;
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
