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
package com.malmoset.campaigntracker.Battles;

import com.malmoset.campaigndata.Battle;
import com.malmoset.campaigntracker.MainApp;
import com.malmoset.campaigntracker.MenuActions;
import com.malmoset.controls.BaseForm;
import com.malmoset.controls.TableViewCellFactories;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

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

        //Set up the rows
        EventHandler<MouseEvent> doubleClick = (MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() > 1) {
                TableRow row = (TableRow) ((TableCell) event.getSource()).getParent();
                if (row != null) {
                    Battle selectedBattle = (Battle) row.getItem();
                    if (selectedBattle != null) {
                        LoadBattle(selectedBattle);
                    }
                }
            }
        };

        col1.setCellFactory(TableViewCellFactories.DoubleClickFactory(doubleClick));
        col2.setCellFactory(TableViewCellFactories.DoubleClickFactory(doubleClick));
        col3.setCellFactory(TableViewCellFactories.DoubleClickFactory(doubleClick));
        col4.setCellFactory(TableViewCellFactories.DoubleClickFactory(doubleClick));
        col5.setCellFactory(TableViewCellFactories.DoubleClickFactory(doubleClick));

        SortedList<Battle> sortedData = new SortedList<>(MainApp.getAppData().getDb().battlesProperty());

        //Sort them in Descending order by date
        sortedData.comparatorProperty().set((Comparator) (Object o1, Object o2) -> {
            Battle left = (Battle) o1;
            Battle right = (Battle) o2;
            return left.getBegan().compareTo(right.getBegan()) * -1;
        });

        BattlesTable.setItems(sortedData);

    }

    private void LoadBattle(Battle battle) {
        BattleViewerController controller = (BattleViewerController) BaseForm.LoadForm(getClass().getResource("/fxml/Battles/BattleViewer.fxml"), "Battle");
        controller.setBattle(battle);
        controller.Show();
    }

    @FXML
    private void NewBattleClick(ActionEvent event) {
        MenuActions.NewBattleClick();
    }

}
