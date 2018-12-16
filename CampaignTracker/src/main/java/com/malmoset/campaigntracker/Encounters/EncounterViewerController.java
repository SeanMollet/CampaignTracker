/* 
 * Copyright 2018 Malmoset LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.malmoset.campaigntracker.Encounters;

import com.malmoset.campaigndata.Encounter;
import com.malmoset.campaigndata.Fraction;
import com.malmoset.campaigndata.Monster;
import com.malmoset.campaigntracker.MainApp;
import com.malmoset.campaigntracker.Monsters.MonsterViewerController;
import com.malmoset.controls.BaseForm;
import com.malmoset.controls.TableViewCellFactories;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

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
        col3.setMaxWidth(400);
        col3.setCellFactory(TableViewCellFactories.multiLineCellFactory());
        col5.setCellFactory(tc -> new CheckBoxTableCell<>());
        col6.setCellFactory(tc -> new CheckBoxTableCell<>());

        //Set up the rows
        EventHandler<MouseEvent> doubleClick = (MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() > 1) {
                TableRow row = (TableRow) ((TableCell) event.getSource()).getParent();
                if (row != null) {
                    Monster selectedMonster = (Monster) row.getItem();
                    if (selectedMonster != null) {
                        MonsterViewerController controller = (MonsterViewerController) BaseForm.LoadForm(getClass().getResource("/fxml/Monsters/MonsterViewer.fxml"), "Monster Viewer", true);
                        //Inside an encounter, they can edit the monster to their hearts content
                        selectedMonster.setReadOnly(false);
                        controller.setMonster(encounter.getMonsters(), selectedMonster);

                        controller.Show();
                    }

                }
            }
        };

        col1.setCellFactory(TableViewCellFactories.DoubleClickFactory(doubleClick));
        col2.setCellFactory(TableViewCellFactories.DoubleClickFactory(doubleClick));
        col3.setCellFactory(TableViewCellFactories.DoubleClickFactory(doubleClick));

        MonstersTable.getColumns().addAll(col1, col2, col3, col4, col5, col6);
        MonstersTable.setItems(encounter.getMonsters());
        MonstersTable.setEditable(true);

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
