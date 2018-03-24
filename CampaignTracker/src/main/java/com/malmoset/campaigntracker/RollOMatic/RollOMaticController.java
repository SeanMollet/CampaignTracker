/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigntracker.RollOMatic;

import com.malmoset.controls.StatDice;
import com.malmoset.controls.BaseForm;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author sean
 */
public class RollOMaticController extends BaseForm implements Initializable {

    @FXML
    private StatDice Dice1;
    @FXML
    private StatDice Dice2;
    @FXML
    private StatDice Dice3;
    @FXML
    private StatDice Dice4;
    @FXML
    private StatDice Dice5;
    @FXML
    private StatDice Dice6;
    @FXML
    private StatDice Dice7;
    @FXML
    private StatDice Dice8;
    @FXML
    private CheckBox ShowAllRolls;

    private List<StatDice> dice;
    @FXML
    private TextArea Results;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ShowAllRolls.setIndeterminate(false);
        ShowAllRolls.setSelected(true);

        dice = new ArrayList<>();
        dice.add(Dice1);
        dice.add(Dice2);
        dice.add(Dice3);
        dice.add(Dice4);
        dice.add(Dice5);
        dice.add(Dice6);
        dice.add(Dice7);
        dice.add(Dice8);

    }

    @FXML
    private void RollClicked(ActionEvent event) {
        StringBuilder sb = new StringBuilder();
        int a = 1;
        for (StatDice die : dice) {
            if (die.getStatDiceCount() > 0) {
                sb.append("Box ");
                sb.append(a);
                sb.append(" (");
                sb.append(die.getStatDiceCount());
                sb.append("D");
                sb.append(die.getStatDiceSize());
                sb.append("): ");
                if (ShowAllRolls.isSelected()) {
                    List<Integer> rolls = die.RollAll();
                    for (Integer roll : rolls) {
                        sb.append(" ");
                        sb.append(roll);
                    }
                } else {
                    sb.append(die.RollTotal());
                }
                sb.append("\n");
            }
        }
        Results.setText(sb.toString());
    }

}
