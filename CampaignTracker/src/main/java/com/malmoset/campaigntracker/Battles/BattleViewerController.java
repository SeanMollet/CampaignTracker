/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigntracker.Battles;

import com.malmoset.campaigndata.Battle;
import com.malmoset.campaigndata.BattleMonster;
import com.malmoset.campaigndata.XPEvent;
import com.malmoset.campaigntracker.MainApp;
import com.malmoset.campaigntrackercontrols.ActionButtonTableCell;
import com.malmoset.campaigntrackercontrols.Styles;
import com.malmoset.controls.BaseForm;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author sean
 */
public class BattleViewerController extends BaseForm implements Initializable {

    private Battle battle;

    @FXML
    private Label StartedLabel;
    @FXML
    private ChoiceBox<?> SaveType;
    @FXML
    private TableView<BattleMonster> MonstersTable;
    @FXML
    private TableView<XPEvent> XPTable;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void onActivated() {
        SetActiveBattle();
    }

    public void SetActiveBattle() {
        if (this.battle != null) {
            MainApp.getAppData().current_battleProperty().set(battle);
        }
    }

    private void DataBind() {
        BindBattle();
        BindXP();
    }

    private void BindBattle() {

        TableColumn<BattleMonster, Boolean> col1 = new TableColumn<>("Hidden");
        TableColumn<BattleMonster, Boolean> col2 = new TableColumn<>("Unknown");
        TableColumn<BattleMonster, Integer> col3 = new TableColumn<>("ID");
        TableColumn<BattleMonster, String> col4 = new TableColumn<>("Name");
        TableColumn<BattleMonster, String> col5 = new TableColumn<>("Type");
        TableColumn<BattleMonster, String> col6 = new TableColumn<>("Appearance");
        TableColumn<BattleMonster, Integer> col7 = new TableColumn<>("Save");
        TableColumn<BattleMonster, Integer> col8 = new TableColumn<>("CurrentHP");
        TableColumn<BattleMonster, Integer> col9 = new TableColumn<>("MaxHP");
        TableColumn<BattleMonster, Integer> col10 = new TableColumn<>("HP");

        col1.setCellValueFactory(cellData -> cellData.getValue().hiddenProperty());
        col2.setCellValueFactory(cellData -> cellData.getValue().unknownProperty());
        col3.setCellValueFactory(cellData -> cellData.getValue().indexProperty().asObject());
        col4.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        col5.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        col6.setCellValueFactory(cellData -> cellData.getValue().appearanceProperty());
        col7.setCellValueFactory(cellData -> cellData.getValue().savingRollProperty().asObject());
        col8.setCellValueFactory(cellData -> cellData.getValue().currentHPProperty().asObject());
        col9.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getHP().getHpValue()).asObject());
        col10.setCellValueFactory(cellData -> cellData.getValue().hPtoChangeProperty().asObject());

        col5.setPrefWidth(100);

        col1.setCellFactory(tc -> new CheckBoxTableCell<>());
        col2.setCellFactory(tc -> new CheckBoxTableCell<>());

        TableColumn<BattleMonster, Button> col11 = new TableColumn<>("HP");
        col11.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        col11.setCellFactory(ActionButtonTableCell.<BattleMonster>forTableColumn("Dmg", Styles.getSmall(), (BattleMonster monster) -> {

            return monster;
        }));
        TableColumn<BattleMonster, Button> col12 = new TableColumn<>("HP");
        col12.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        col12.setCellFactory(ActionButtonTableCell.<BattleMonster>forTableColumn("Heal", Styles.getSmall(), (BattleMonster monster) -> {

            return monster;
        }));

        TableColumn<BattleMonster, Button> col13 = new TableColumn<>("Grant");
        col13.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        col13.setCellFactory(ActionButtonTableCell.<BattleMonster>forTableColumn("XP", Styles.getSmall(), (BattleMonster monster) -> {

            return monster;
        }));

        MonstersTable.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7, col8, col9, col10, col11, col12, col13);
        MonstersTable.setItems(battle.monstersProperty());
        MonstersTable.setEditable(true);

    }

    private void BindXP() {
        TableColumn<XPEvent, String> col1 = new TableColumn<>("Timestamp");
        TableColumn<XPEvent, Integer> col2 = new TableColumn<>("Session");
        TableColumn<XPEvent, Integer> col3 = new TableColumn<>("Battle");
        TableColumn<XPEvent, String> col4 = new TableColumn<>("Event");
        TableColumn<XPEvent, String> col5 = new TableColumn<>("Monster");
        TableColumn<XPEvent, Integer> col6 = new TableColumn<>("XP");

        col1.setCellValueFactory(cellData -> cellData.getValue().timestampProperty().asString());
        col2.setCellValueFactory(cellData -> cellData.getValue().sessionProperty().asObject());
        col3.setCellValueFactory(cellData -> cellData.getValue().battleProperty().asObject());
        col4.setCellValueFactory(cellData -> cellData.getValue().eventProperty());
        col5.setCellValueFactory(cellData -> cellData.getValue().monsterProperty());
        col6.setCellValueFactory(cellData -> cellData.getValue().xPProperty().asObject());

        XPTable.getColumns().clear();
        XPTable.getColumns().addAll(col1, col2, col3, col4, col5, col6);

        //If the session changes, rebind our data
        MainApp.getAppData().getDb().sessionProperty().addListener((obs, old, newval)
                -> {
            BindXP();
        });

        XPTable.setItems(MainApp.getAppData().getDb().getSessionXP());
    }

    @FXML
    private void LootClick(ActionEvent event) {
    }

    @FXML
    private void SavesClick(ActionEvent event) {
    }

    public Battle getBattle() {
        return battle;
    }

    public void setBattle(Battle battle) {
        this.battle = battle;
        DataBind();
        SetActiveBattle();
    }

}
