/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigntracker.Encounters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.malmoset.campaigndata.Battle;
import com.malmoset.campaigndata.Encounter;
import com.malmoset.campaigndata.Monster;
import com.malmoset.campaigntracker.Battles.BattleViewerController;
import com.malmoset.campaigntracker.MainApp;
import com.malmoset.campaigntracker.Monsters.MonsterManagerController;
import com.malmoset.campaigntrackercontrols.ActionButtonTableCell;
import com.malmoset.campaigntrackercontrols.Styles;
import com.malmoset.campaigntrackercontrols.TableViewCellFactories;
import com.malmoset.controls.BaseForm;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sean
 */
public class EncounterManagerController extends BaseForm implements Initializable {

    @FXML
    private TextField SearchBox;
    @FXML
    private Button ExportButton;
    @FXML
    private Button ImportButton;
    @FXML
    private Button NewButton;
    @FXML
    private TableView<Encounter> EncountersBox;
    @FXML
    private Button NewButton1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        DataBind();
    }

    private void DataBind() {

        TableColumn<Encounter, String> col1 = new TableColumn<>("Name");
        TableColumn<Encounter, String> col2 = new TableColumn<>("Challenge");
        TableColumn<Encounter, String> col3 = new TableColumn<>("Description");
        TableColumn<Encounter, Button> col4 = new TableColumn<>("Start");

        col1.setCellValueFactory(new PropertyValueFactory<>("Name"));
        col2.setCellValueFactory(cellData -> cellData.getValue().getChallenge().strValueProperty());
        col3.setCellValueFactory(new PropertyValueFactory<>("Description"));
        col4.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        col4.setCellFactory(ActionButtonTableCell.<Encounter>forTableColumn("Battle", Styles.getSmall(), (Encounter encounter) -> {
            Battle battle = new Battle();
            BattleViewerController controller = (BattleViewerController) BaseForm.LoadForm(getClass().getResource("/fxml/Battles/BattleViewer.fxml"), "Battle");
            MainApp.getAppData().getDb().battlesProperty().add(battle);
            controller.setBattle(battle);
            for (Monster monster : encounter.getMonsters()) {
                battle.AddMonster(monster, false);
            }

            controller.Show();
            return encounter;
        }));

        EncountersBox.getColumns().clear();
        EncountersBox.getColumns().addAll(col1, col2, col3, col4);

        //Set up the rows
        EventHandler<MouseEvent> doubleClick = (MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() > 1) {
                TableRow row = (TableRow) ((TableCell) event.getSource()).getParent();
                if (row != null) {
                    Encounter selectedEncounter = (Encounter) row.getItem();
                    if (selectedEncounter != null) {
                        LoadEncounter(selectedEncounter);
                    }
                }
            }
        };

        col1.setCellFactory(TableViewCellFactories.DoubleClickFactory(doubleClick));
        col2.setCellFactory(TableViewCellFactories.DoubleClickFactory(doubleClick));
        col3.setCellFactory(TableViewCellFactories.DoubleClickFactory(doubleClick));

        col3.prefWidthProperty().bind(EncountersBox.widthProperty().subtract(5).subtract(col1.widthProperty()).subtract(col2.widthProperty()));
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Encounter> filteredData = new FilteredList<>(MainApp.getAppData().getDb().getEncounters(), p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        SearchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(encounter -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (encounter.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                if (encounter.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }

                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Encounter> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(EncountersBox.comparatorProperty());

        EncountersBox.setItems(sortedData);
    }

    private void LoadEncounter(Encounter encounter) {
        EncounterViewerController controller = (EncounterViewerController) BaseForm.LoadForm(getClass().getResource("/fxml/Encounters/EncounterViewer.fxml"), "Encounter Viewer", true);
        controller.setEncounter(encounter.Clone());

        controller.Show();

    }

    @FXML
    private void ExportButtonClick(ActionEvent event) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Encounters");
        FileChooser.ExtensionFilter encounterExtensionFilter
                = new FileChooser.ExtensionFilter(
                        "Encounter Files (.cten)", "*.cten");
        fileChooser.getExtensionFilters().add(encounterExtensionFilter);
        fileChooser.setSelectedExtensionFilter(encounterExtensionFilter);
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {

            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            try {
                mapper.writeValue(file, MainApp.getAppData().getDb().getEncounters());
            } catch (JsonProcessingException ex) {
                Logger.getLogger(MonsterManagerController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MonsterManagerController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    @FXML
    private void ImportButtonClick(ActionEvent event) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Encounters");
        FileChooser.ExtensionFilter encounterExtensionFilter
                = new FileChooser.ExtensionFilter(
                        "Encounter Files (.cten)", "*.cten");
        fileChooser.getExtensionFilters().add(encounterExtensionFilter);
        fileChooser.setSelectedExtensionFilter(encounterExtensionFilter);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {

            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            try {
                List<Encounter> imported = mapper.readValue(file, new TypeReference<List<Encounter>>() {
                });
                MainApp.getAppData().getDb().ImportEncounters(imported);
            } catch (JsonProcessingException ex) {
                Logger.getLogger(MonsterManagerController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MonsterManagerController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @FXML
    private void NewButtonClick(ActionEvent event) {
        LoadEncounter(new Encounter());
    }

    @FXML
    private void DeleteClick(ActionEvent event) {
        Encounter encounter = EncountersBox.getSelectionModel().getSelectedItem();
        if (encounter != null) {
            MainApp.getAppData().getDb().getEncounters().remove(encounter);
        }
    }

}
