/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigntrackercontrols;

import com.malmoset.dice.Dice;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private ChoiceBox<String> DiceType;
    @FXML
    private Spinner<Integer> Modifier;

    private IntegerProperty statDiceCount;
    private IntegerProperty statDiceSize;
    private IntegerProperty statModifier;
    private BooleanProperty modifierVisible;

    private ObservableList<String> diceText;
    private ObservableList<Integer> diceVal;

    public StatDice() {
        super();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Controls/StatDice.fxml"));
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

    public int RollTotal() {
        int total = 0;
        for (int a = 0; a < statDiceCount.get(); a++) {
            total += Dice.roll(statDiceSize.get(), Dice.RollTypes.Normal);
        }
        return total;
    }

    public List<Integer> RollAll() {
        List<Integer> rolls = new ArrayList<>();

        for (int a = 0; a < statDiceCount.get(); a++) {
            rolls.add(Dice.roll(statDiceSize.get(), Dice.RollTypes.Normal));
        }
        return rolls;
    }

    private void BindFields() {
        statDiceCount = new SimpleIntegerProperty(0);
        statDiceSize = new SimpleIntegerProperty(10);
        statModifier = new SimpleIntegerProperty(0);

        DiceCount.getValueFactory().valueProperty().bindBidirectional(statDiceCount.asObject());
        Modifier.getValueFactory().valueProperty().bindBidirectional(statModifier.asObject());

        statDiceSize.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue ov, Number value, Number new_value) {
                if (diceVal.contains((int) new_value)) {
                    int location = diceVal.indexOf((int) new_value);
                    DiceType.getSelectionModel().select(location);
                }
            }
        });

    }

    private void SetupControls() {
        modifierVisible = new SimpleBooleanProperty(true);
        modifierVisible.addListener((obs, oldval, newval) -> {
            if (newval) {
                this.setPrefWidth(185);
                this.Modifier.setVisible(true);
            } else {
                this.setPrefWidth(125);
                this.Modifier.setVisible(false);
            }
        });

        SpinnerValueFactory<Integer> valfact = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1);
        SpinnerValueFactory<Integer> valfact2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(-500, 500, 0);

        DiceCount.setValueFactory(valfact);
        Modifier.setValueFactory(valfact2);

        DiceCount.setEditable(true);
        Modifier.setEditable(true);

        IntegerStringConverter.createFor(DiceCount);
        IntegerStringConverter.createFor(Modifier);

        diceText = FXCollections.observableArrayList("D4", "D6", "D8", "D10", "D12", "D20", "D100");
        diceVal = FXCollections.observableArrayList(4, 6, 8, 10, 12, 20, 100);

        DiceType.setItems(diceText);
        DiceType.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue ov, Number value, Number new_value) {
                //Make sure we don't repeatedly trigger each other
                statDiceSize.set(diceVal.get(new_value.intValue()));
            }
        });

        DiceCount.focusedProperty().addListener((s, ov, nv) -> {
            if (!nv) {
                SpinnerUtils.commitEditorText(DiceCount);
            }
        });
        Modifier.focusedProperty().addListener((s, ov, nv) -> {
            if (!nv) {
                SpinnerUtils.commitEditorText(Modifier);
            }
        });
    }

    public final int getStatDiceCount() {
        return statDiceCount.get();
    }

    public final void setStatDiceCount(int value) {
        statDiceCount.set(value);
    }

    public IntegerProperty statDiceCountProperty() {
        return statDiceCount;
    }

    public final int getStatDiceSize() {
        return statDiceSize.get();
    }

    public final void setStatDiceSize(int value) {
        statDiceSize.set(value);
    }

    public IntegerProperty statDiceSizeProperty() {
        return statDiceSize;
    }

    public final int getStatModifier() {
        return statModifier.get();
    }

    public final void setStatModifier(int value) {
        statModifier.set(value);
    }

    public IntegerProperty statModifierProperty() {
        return statModifier;
    }

    public final boolean isModifierVisible() {
        return modifierVisible.get();
    }

    public final void setModifierVisible(boolean value) {
        modifierVisible.set(value);
    }

    public BooleanProperty modifierVisibleProperty() {
        return modifierVisible;
    }

}
