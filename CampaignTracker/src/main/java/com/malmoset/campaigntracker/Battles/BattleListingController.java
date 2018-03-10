/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigntracker.Battles;

import com.malmoset.campaigndata.Battle;
import com.malmoset.campaigntracker.MainApp;
import com.malmoset.controls.BaseForm;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author sean
 */
public class BattleListingController extends BaseForm implements Initializable {

    @FXML
    private TableView<Battle> BattlesTable;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        DataBind();
    }

    private void DataBind() {

        TableColumn<Battle, String> col1 = new TableColumn<>("Began");
        TableColumn<Battle, Integer> col2 = new TableColumn<>("Session");
        TableColumn<Battle, Integer> col3 = new TableColumn<>("Battle");
        TableColumn<Battle, Integer> col4 = new TableColumn<>("Live");
        TableColumn<Battle, Integer> col5 = new TableColumn<>("Monsters");

        col1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBegan().toString()));
        col2.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getSession()).asObject());
        col3.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getBattleNumber()).asObject());
        col4.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getLiveMonsters()).asObject());
        col5.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getTotalMonsters()).asObject());

        BattlesTable.getColumns().addAll(col1, col2, col3, col4, col5);
        BattlesTable.setItems(MainApp.getAppData().getDb().battlesProperty());

    }
}
