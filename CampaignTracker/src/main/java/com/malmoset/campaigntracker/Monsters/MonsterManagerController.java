/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigntracker.Monsters;

import com.malmoset.campaigndata.Fraction;
import com.malmoset.campaigndata.Monster;
import com.malmoset.campaigndata.MonstersDatabase;
import com.malmoset.campaigntracker.AppData;
import com.malmoset.campaigntracker.MainApp;
import com.malmoset.controls.BaseForm;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author sean
 */
public class MonsterManagerController extends BaseForm implements Initializable {

    @FXML
    private TableView<Monster> MonstersTable;
    @FXML
    private TextField SearchBox;
    @FXML
    private Button ExportButton;
    @FXML
    private Button ImportButton;
    @FXML
    private Button NewButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DataBind();
    }

    private void DataBind() {
        AppData data = MainApp.getAppData();
        MonstersDatabase monsters = data.getMon_db();
        ObservableList<Monster> list = monsters.getMonstersBind();

        TableColumn<Monster, String> col1 = new TableColumn<>("Name");
        TableColumn<Monster, String> col2 = new TableColumn<>("Source");
        TableColumn<Monster, String> col3 = new TableColumn<>("Type");
        TableColumn<Monster, Fraction> col4 = new TableColumn<>("Challenge");
        col1.setCellValueFactory(new PropertyValueFactory<>("Name"));
        col2.setCellValueFactory(new PropertyValueFactory<>("Source"));
        col3.setCellValueFactory(new PropertyValueFactory<>("DisplayType"));
        col4.setCellValueFactory(cellData -> cellData.getValue().getChallenge());
        MonstersTable.getColumns().addAll(col1, col2, col3, col4);

        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Monster> filteredData = new FilteredList<>(list, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        SearchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(monster -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (monster.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                if (monster.getSource().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                if (monster.getDisplayType().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }

                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Monster> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(MonstersTable.comparatorProperty());

        MonstersTable.setItems(sortedData);

    }

    private void LoadMonster() {
        AppData data = MainApp.getAppData();
        MonstersDatabase monsters = data.getMon_db();
        ObservableList<Monster> list = monsters.getMonstersBind();
        Monster monster = list.get(0);

        MonsterViewerController controller = (MonsterViewerController) BaseForm.LoadForm(getClass().getResource("/fxml/Monsters/MonsterViewer.fxml"), "Monster Manager", false);
        controller.setMonster(monster);

        controller.Show();
    }

    @FXML
    private void ExportButtonClick(ActionEvent event) {
    }

    @FXML
    private void ImportButtonClick(ActionEvent event) {
    }

    @FXML
    private void NewButtonClick(ActionEvent event) {
    }

}
