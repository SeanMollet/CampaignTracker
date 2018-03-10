/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigntracker.Monsters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.malmoset.campaigndata.Fraction;
import com.malmoset.campaigndata.Monster;
import com.malmoset.campaigndata.MonstersDatabase;
import com.malmoset.campaigntracker.AppData;
import com.malmoset.campaigntracker.MainApp;
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
import java.util.stream.Collectors;
import javafx.collections.ObservableList;
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
import javafx.util.Callback;

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

        MonstersTable.setEditable(false);
        //Create the columns
        TableColumn<Monster, String> col1 = new TableColumn<>("Name");
        TableColumn<Monster, String> col2 = new TableColumn<>("Source");
        TableColumn<Monster, String> col3 = new TableColumn<>("Type");
        TableColumn<Monster, Fraction> col4 = new TableColumn<>("Challenge");
        col1.setCellValueFactory(new PropertyValueFactory<>("Name"));
        col2.setCellValueFactory(new PropertyValueFactory<>("Source"));
        col3.setCellValueFactory(new PropertyValueFactory<>("DisplayType"));
        col4.setCellValueFactory(cellData -> cellData.getValue().getChallenge());

//        TableColumn<Monster, String> col5 = ButtonCol("Battle", "Add", Styles.getSmall(), event -> {
//                                Monster monster = getTableView().getItems().get(getIndex());
////                                System.out.println(person.getFirstName()
////                                        + "   " + person.getLastName());
//        });
        TableColumn<Monster, Button> col5 = new TableColumn<>("Add");
        col5.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        col5.setCellFactory(ActionButtonTableCell.<Monster>forTableColumn("Battle", Styles.getSmall(), (Monster monster) -> {
            if (MainApp.getAppData().current_battleProperty().get() != null) {
                MainApp.getAppData().current_battleProperty().get().AddMonster(monster, true);
            }
            return monster;
        }));
        TableColumn<Monster, Button> col6 = new TableColumn<>("Add");
        col6.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        col6.setCellFactory(ActionButtonTableCell.<Monster>forTableColumn("Encounter", Styles.getSmall(), (Monster monster) -> {
            if (MainApp.getAppData().current_encounterProperty().get() != null) {
                MainApp.getAppData().current_encounterProperty().get().getMonsters().add(monster.readyForBattle());
            }
            return monster;
        }));
        MonstersTable.getColumns().addAll(col1, col2, col3, col4, col5, col6);

        //Set up the rows
        EventHandler<MouseEvent> doubleClick = (MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() > 1) {
                TableRow row = (TableRow) ((TableCell) event.getSource()).getParent();
                if (row != null) {
                    Monster selectedMonster = (Monster) row.getItem();
                    if (selectedMonster != null) {
                        LoadMonster(selectedMonster);
                    }
                }
            }
        };
        col1.setCellFactory(TableViewCellFactories.DoubleClickFactory(doubleClick));
        col2.setCellFactory(TableViewCellFactories.DoubleClickFactory(doubleClick));
        col3.setCellFactory(TableViewCellFactories.DoubleClickFactory(doubleClick));

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

    private void LoadMonster(Monster monster) {

        MonsterViewerController controller = (MonsterViewerController) BaseForm.LoadForm(getClass().getResource("/fxml/Monsters/MonsterViewer.fxml"), "Monster Viewer", true);
        controller.setMonster(monster.clone());

        controller.Show();
    }

    private TableColumn ButtonCol(String text, String header, String style, EventHandler<ActionEvent> event) {
        TableColumn actionCol = new TableColumn(header);
        actionCol.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        Callback<TableColumn<Monster, String>, TableCell<Monster, String>> cellFactory
                = //
                new Callback<TableColumn<Monster, String>, TableCell<Monster, String>>() {
            @Override
            public TableCell call(final TableColumn<Monster, String> param) {
                final TableCell<Monster, String> cell = new TableCell<Monster, String>() {

                    final Button btn = new Button(text);

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setStyle(style);
                            btn.setOnAction(event);
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };

        actionCol.setCellFactory(cellFactory);
        return actionCol;
    }

    @FXML
    private void ExportButtonClick(ActionEvent event) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Custom Monsters");
        FileChooser.ExtensionFilter monsterExtensionFilter
                = new FileChooser.ExtensionFilter(
                        "Monster Files (.ctmo)", "*.ctmo");
        fileChooser.getExtensionFilters().add(monsterExtensionFilter);
        fileChooser.setSelectedExtensionFilter(monsterExtensionFilter);
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            AppData data = MainApp.getAppData();
            MonstersDatabase monsters = data.getMon_db();
            ObservableList<Monster> list = monsters.getMonstersBind();
            List<Monster> customMonsters = list.stream().filter(x -> x.readOnlyProperty().get() == false).collect(Collectors.toList());

            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            try {
                mapper.writeValue(file, customMonsters);
            } catch (JsonProcessingException ex) {
                Logger.getLogger(MonsterManagerController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MonsterManagerController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    @FXML
    private void ImportButtonClick(ActionEvent event) {
        AppData data = MainApp.getAppData();
        MonstersDatabase monsters = data.getMon_db();
        ObservableList<Monster> list = monsters.getMonstersBind();

        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Custom Monsters");
        FileChooser.ExtensionFilter monsterExtensionFilter
                = new FileChooser.ExtensionFilter(
                        "Monster Files (.ctmo)", "*.ctmo");
        fileChooser.getExtensionFilters().add(monsterExtensionFilter);
        fileChooser.setSelectedExtensionFilter(monsterExtensionFilter);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {

            ObjectMapper mapper = new ObjectMapper();
            try {
                //mapper.writeValue(file, customMonsters);
                List<Monster> newmonsters = mapper.readValue(file, new TypeReference<List<Monster>>() {
                });

                monsters.ImportMonsters(newmonsters);

            } catch (JsonProcessingException ex) {
                Logger.getLogger(MonsterManagerController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MonsterManagerController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @FXML
    private void NewButtonClick(ActionEvent event) {
        LoadMonster(new Monster());
    }

}
