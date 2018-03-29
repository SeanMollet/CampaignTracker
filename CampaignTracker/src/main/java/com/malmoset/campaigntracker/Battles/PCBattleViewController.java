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
import com.malmoset.controls.BaseForm;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author sean
 */
public class PCBattleViewController extends BaseForm implements Initializable {

    @FXML
    private TableView<BattleMonster> MonstersTable;
    @FXML
    private TableView<XPEvent> XPTable;
    private Battle battle;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //If we have a battle going already, load it up
        //Otherwise, our listener below will handle it when we do have one
        battle = MainApp.getAppData().current_battleProperty().get();
        if (battle != null) {
            BindData();
        }
        //When the session or the current battle changes, we need to refresh ourselves
        MainApp.getAppData().getDb().sessionProperty().addListener((obs, oldval, newval) -> {
            BindData();
        });
        MainApp.getAppData().current_battleProperty().addListener((ObservableValue<? extends Battle> observable, Battle oldValue, Battle newValue) -> {
            if (newValue != null) {
                battle = newValue;
                BindData();
            }
        });
        //If the DM changes a hidden property, we need to reload
        MainApp.getAppData().getDb().monsterRevealsProperty().addListener((obs, oldval, newval) -> {
            BindBattle();
        });
        setAlwaysOnTop(true);
    }

    private void BindData() {
        BindBattle();
        BindXP();

        //If the session changes, rebind our data
        MainApp.getAppData().getDb().sessionProperty().addListener((obs, old, newval)
                -> {
            BindXP();
        });
    }

    private void BindBattle() {

        FilteredList<BattleMonster> filteredData = new FilteredList<>(battle.monstersProperty(), p -> true);

        filteredData.setPredicate(monster -> {
            return !monster.isHidden();
        });

        MonstersTable.setItems(filteredData);

        TableColumn<BattleMonster, Integer> col3 = new TableColumn<>("ID");
        TableColumn<BattleMonster, String> col4 = new TableColumn<>("Name");
        TableColumn<BattleMonster, String> col5 = new TableColumn<>("Type");
        TableColumn<BattleMonster, String> col6 = new TableColumn<>("Appearance");
        TableColumn<BattleMonster, Boolean> col7 = new TableColumn<>("Hidden");

        col3.setCellValueFactory(cellData -> cellData.getValue().indexProperty().asObject());
        col4.setCellValueFactory(cellData -> cellData.getValue().pcNameProperty());
        col5.setCellValueFactory(cellData -> cellData.getValue().pcTypeProperty());
        col6.setCellValueFactory(cellData -> cellData.getValue().appearanceProperty());
        col7.setCellValueFactory(cellData -> cellData.getValue().hiddenProperty());

        col7.setVisible(false);

        MonstersTable.getColumns().clear();
        MonstersTable.getColumns().addAll(col3, col4, col5, col6, col7);

    }

    private void BindXP() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyy HH:mm:ss");

        TableColumn<XPEvent, String> col1 = new TableColumn<>("Timestamp");
        TableColumn<XPEvent, Integer> col2 = new TableColumn<>("Session");
        TableColumn<XPEvent, Integer> col3 = new TableColumn<>("Battle");
        TableColumn<XPEvent, String> col4 = new TableColumn<>("Event");
        TableColumn<XPEvent, String> col5 = new TableColumn<>("Monster");

        col1.setCellValueFactory(cellData -> new SimpleStringProperty(sdf.format(cellData.getValue().timestampProperty().get())));
        col2.setCellValueFactory(cellData -> cellData.getValue().sessionProperty().asObject());
        col3.setCellValueFactory(cellData -> cellData.getValue().battleProperty().asObject());
        col4.setCellValueFactory(cellData -> cellData.getValue().eventProperty());
        col5.setCellValueFactory(cellData -> cellData.getValue().monsterProperty());

        col1.setPrefWidth(140);
        col4.setPrefWidth(190);

        XPTable.getColumns().clear();
        XPTable.getColumns().addAll(col1, col2, col3, col4, col5);

        SortedList<XPEvent> sortedData = new SortedList<>(MainApp.getAppData().getDb().getSessionXP());

        //Sort them in Descending order by date
        sortedData.comparatorProperty().set((Comparator) (Object o1, Object o2) -> {
            XPEvent left = (XPEvent) o1;
            XPEvent right = (XPEvent) o2;
            return left.getTimestamp().compareTo(right.getTimestamp()) * -1;
        });

        XPTable.setItems(sortedData);
    }

}
