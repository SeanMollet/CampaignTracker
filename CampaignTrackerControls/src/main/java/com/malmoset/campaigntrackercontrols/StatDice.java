/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigntrackercontrols;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author sean
 */
public class StatDice extends AnchorPane {

    @FXML
    private AnchorPane StatDicePane;
    @FXML
    private Spinner<Integer> DiceCount;
    @FXML
    private ChoiceBox<?> DiceType;
    @FXML
    private Spinner<Integer> Modifier;

    public StatDice() {
        super();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/StatDice.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        SetupSpinners();
    }

    private void SetupSpinners() {
        SpinnerValueFactory<Integer> valfact = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1);
        SpinnerValueFactory<Integer> valfact2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(-500, 500, 0);

        DiceCount.setValueFactory(valfact);
        Modifier.setValueFactory(valfact2);

        DiceCount.setEditable(true);
        Modifier.setEditable(true);

        IntegerStringConverter.createFor(DiceCount);
        IntegerStringConverter.createFor(Modifier);
    }

}
