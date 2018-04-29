/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigntracker.PC;

import com.malmoset.campaigndata.Player;
import com.malmoset.campaigntracker.AppData;
import com.malmoset.campaigntracker.MainApp;
import com.malmoset.controls.BaseForm;
import com.malmoset.dice.Dice;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author sean
 */
public class PCViewController extends BaseForm implements Initializable {

    @FXML
    private TableView<Player> PlayerTable;
    private SortedList<Player> sortedData;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        BindData();
        setAlwaysOnTop(true);
    }

    private void BindData() {
        PlayerTable.setEditable(false);

        //Create the columns
        TableColumn<Player, String> col1 = new TableColumn<>("Name");
        TableColumn<Player, String> col2 = new TableColumn<>("Appearance");
        TableColumn<Player, Integer> col3 = new TableColumn<>("Dex");
        TableColumn<Player, Integer> col4 = new TableColumn<>("Roll");
        TableColumn<Player, Dice.RollTypes> col5 = new TableColumn<>("Adv");

        col1.setCellValueFactory(new PropertyValueFactory<>("Name"));
        col2.setCellValueFactory(cellData -> cellData.getValue().appearanceProperty());
        col3.setCellValueFactory(cellData -> cellData.getValue().initiativeProperty().asObject());
        col4.setCellValueFactory(cellData -> cellData.getValue().rollProperty().asObject());
        col5.setCellValueFactory(cellData -> cellData.getValue().advProperty());

        PlayerTable.getColumns().addAll(col1, col2, col3, col4, col5);

        // 4. Bind the SortedList comparator to the TableView comparator.
        BindSortedList();

        PlayerTable.comparatorProperty().addListener((e) -> {
            //Switch back to sorting based on user selection
            sortedData.comparatorProperty().setValue(PlayerTable.comparatorProperty().get());
        });

        //When the list is updated, rebind so we re-sort
        MainApp.getAppData().getDb().initiativeRollsProperty().addListener((c) -> {
            // TODO Auto-generated method stub
            BindSortedList();
        });

    }

    private void BindSortedList() {
        AppData data = MainApp.getAppData();
        ObservableList<Player> players = data.getDb().getPlayers();
        FilteredList<Player> filtered = new FilteredList<>(players, p -> !p.isHidden());

        sortedData = new SortedList(filtered);
        PlayerTable.setItems(sortedData);
        sortedData.comparatorProperty().setValue(Player.CompareInitiative());
    }
}
