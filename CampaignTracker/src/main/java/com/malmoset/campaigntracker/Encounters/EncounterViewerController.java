/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigntracker.Encounters;

import com.malmoset.campaigndata.Encounter;
import com.malmoset.campaigndata.Fraction;
import com.malmoset.campaigndata.Monster;
import com.malmoset.campaigntracker.MainApp;
import com.malmoset.controls.BaseForm;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author sean
 */
public class EncounterViewerController extends BaseForm implements Initializable {

    private Encounter encounter;
    @FXML
    private TextField NameBox;
    @FXML
    private TextArea DescBox;
    @FXML
    private TableView<Monster> MonstersTable;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @Override
    public void onActivated() {
        SetActiveEncounter();
    }

    public void SetActiveEncounter() {
        if (this.encounter != null) {
            MainApp.getAppData().current_encounterProperty().set(encounter);
        }
    }

    public void BindData() {
        NameBox.textProperty().bindBidirectional(encounter.nameProperty());
        DescBox.textProperty().bindBidirectional(encounter.descriptionProperty());

        TableColumn<Monster, String> col1 = new TableColumn<>("Name");
        TableColumn<Monster, String> col2 = new TableColumn<>("Source");
        TableColumn<Monster, String> col3 = new TableColumn<>("Type");
        TableColumn<Monster, Fraction> col4 = new TableColumn<>("Challenge");
        TableColumn<Monster, Boolean> col5 = new TableColumn<>("Hidden");
        TableColumn<Monster, Boolean> col6 = new TableColumn<>("Unknown");

        col1.setCellValueFactory(new PropertyValueFactory<>("Name"));
        col2.setCellValueFactory(new PropertyValueFactory<>("Source"));
        col3.setCellValueFactory(new PropertyValueFactory<>("DisplayType"));
        col4.setCellValueFactory(cellData -> cellData.getValue().getChallenge());
        col5.setCellValueFactory(cellData -> cellData.getValue().hiddenProperty());
        col6.setCellValueFactory(cellData -> cellData.getValue().unknownProperty());

        col3.setPrefWidth(250);
        col3.setCellFactory(multiLineCellFactory());
        col5.setCellFactory(tc -> new CheckBoxTableCell<>());
        col6.setCellFactory(tc -> new CheckBoxTableCell<>());

        MonstersTable.getColumns().addAll(col1, col2, col3, col4, col5, col6);
        MonstersTable.setItems(encounter.getMonsters());
        MonstersTable.setEditable(true);

    }

    Callback<TableColumn<Monster, String>, TableCell<Monster, String>> multiLineCellFactory() {
        return new Callback<TableColumn<Monster, String>, TableCell<Monster, String>>() {
            @Override
            public TableCell call(TableColumn param) {
                final TableCell cell = new TableCell() {
                    private Text text;

                    @Override
                    public void updateItem(Object item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!isEmpty() && item != null) {
                            text = new Text(item.toString());
                            text.wrappingWidthProperty().bind(this.widthProperty());
                            //text.setWrappingWidth(250);
                            setGraphic(text);
                        }
                    }
                };
                cell.setMaxWidth(400.0);
                return cell;
            }
        };
    }

    public Encounter getEncounter() {
        return encounter;
    }

    public void setEncounter(Encounter encounter) {
        this.encounter = encounter;
        BindData();
        SetActiveEncounter();
    }

    @FXML
    private void SaveClick(ActionEvent event) {
        List<Encounter> imported = new ArrayList<>();
        imported.add(encounter.Clone());
        MainApp.getAppData().getDb().ImportEncounters(imported);
    }

    @FXML
    private void DeleteClick(ActionEvent event) {
        Monster selected = MonstersTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            encounter.getMonsters().remove(selected);
        }
    }

}
