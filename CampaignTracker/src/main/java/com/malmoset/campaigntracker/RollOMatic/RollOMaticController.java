/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigntracker.RollOMatic;

import com.malmoset.campaigndata.RollOMaticPreset;
import com.malmoset.campaigndata.RollOMaticPreset.SingleDiceBox;
import com.malmoset.campaigntracker.MainApp;
import com.malmoset.controls.BaseForm;
import com.malmoset.controls.ContextMenus;
import com.malmoset.controls.StatDice;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

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
    @FXML
    private TextArea Results;
    @FXML
    private TextField SaveName;
    @FXML
    private Button SaveButton;
    @FXML
    private ListView<RollOMaticPreset> SavedRolls;
    private List<StatDice> dice;
    private boolean nameUpdatedAutomatically = false;
    private boolean userChangedName = false;

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

        SavedRolls.setCellFactory(param -> new ListCell<RollOMaticPreset>() {
            @Override
            protected void updateItem(RollOMaticPreset item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getName() == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }

        });

        SavedRolls.setEditable(false);
        //Loading a saved preset
        SavedRolls.getSelectionModel().selectedItemProperty().addListener((obv, oldval, newval) -> {
            if (newval != null && newval.getBoxes() != null) {
                int a = 0;
                for (StatDice die : dice) {
                    if (newval.getBoxes().size() > a) {
                        die.setStatDiceSize(newval.getBoxes().get(a).getDiceSize());
                        die.setStatDiceCount(newval.getBoxes().get(a).getDiceCount());
                    } else {
                        die.setStatDiceSize(10);
                        die.setStatDiceCount(0);
                    }
                    a++;
                }
                this.SaveName.setText(newval.getName());
            }
        });

        //Context menu to delete one
        SavedRolls.addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            if (e.getButton() == MouseButton.SECONDARY && e.getClickCount() == 1) {

                ContextMenu contextMenu = ContextMenus.DelContextMenu(
                        (ActionEvent event) -> {
                            RollOMaticPreset preset = SavedRolls.getSelectionModel().getSelectedItem();
                            if (preset != null) {
                                MainApp.getAppData().getDb().getRollOMatics().remove(preset);
                            }
                        });
                contextMenu.show(stage, e.getScreenX(), e.getScreenY());
            }
        });

        //Bind our data and make sure it's rebinded as necessary
        MainApp.getAppData().getDb().RollOMaticsProperty().addListener((ListChangeListener) (c) -> {
            BindList();
        });
        BindList();

        for (StatDice die : dice) {
            die.statDiceCountProperty().addListener((obv, oldval, newval) -> {
                UpdateName();
            });
            die.statDiceSizeProperty().addListener((obv, oldval, newval) -> {
                UpdateName();
            });
        }

        this.SaveName.textProperty().addListener((obv, oldval, newval) -> {
            if (!nameUpdatedAutomatically) {
                //If the user changes the name, then we'll stop automatically editing it
                //If they clear it or put it back to default, we'll go back to auto
                if (newval.trim().length() == 0 || newval.equals(getAutoName())) {
                    userChangedName = false;
                } else {
                    userChangedName = true;
                }
            }
            nameUpdatedAutomatically = false;
        });

    }

    private void UpdateName() {
        if (!userChangedName) {
            nameUpdatedAutomatically = true;
            this.SaveName.setText(getAutoName());
        }
    }

    private String getAutoName() {
        StringBuilder sb = new StringBuilder();
        for (StatDice die : dice) {
            if (die.getStatDiceCount() > 0) {
                if (sb.length() > 0) {
                    sb.append(" ");
                }
                sb.append(die.getStatDiceCount());
                sb.append("D");
                sb.append(die.getStatDiceSize());
            }
        }
        return sb.toString();
    }

    private void BindList() {
        SortedList<RollOMaticPreset> orderedPresets = new SortedList(MainApp.getAppData().getDb().getRollOMatics());
        //Sort by the name
        orderedPresets.comparatorProperty().setValue((left, right) -> left.getName().compareTo(right.getName()));
        SavedRolls.setItems(orderedPresets);
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
            a++;
        }
        Results.setText(sb.toString());
    }

    @FXML
    private void SaveClick(ActionEvent event) {
        if (this.SaveName.getText().length() > 0) {
            List<SingleDiceBox> preset_dice = new ArrayList<>();
            for (StatDice die : dice) {
                if (die.getStatDiceCount() > 0) {
                    SingleDiceBox box = new SingleDiceBox(die.getStatDiceSize(), die.getStatDiceCount());
                    preset_dice.add(box);
                }
            }
            RollOMaticPreset preset = new RollOMaticPreset(preset_dice, this.SaveName.getText());

            //Overwrite if it already exists
            List<RollOMaticPreset> existing = MainApp.getAppData().getDb().getRollOMatics().stream().filter(x -> x.getName().equals(preset.getName())).collect(Collectors.toList());
            if (existing != null) {
                for (RollOMaticPreset exist : existing) {
                    MainApp.getAppData().getDb().getRollOMatics().remove(exist);
                }
            }
            MainApp.getAppData().getDb().getRollOMatics().add(preset);
        }
    }

}
