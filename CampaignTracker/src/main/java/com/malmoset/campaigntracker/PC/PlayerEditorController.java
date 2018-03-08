/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigntracker.PC;

import com.malmoset.campaigndata.Monster;
import com.malmoset.campaigndata.Player;
import com.malmoset.campaigntracker.AppData;
import com.malmoset.campaigntracker.MainApp;
import com.malmoset.campaigntrackercontrols.Styles;
import com.malmoset.controls.BaseForm;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author sean
 */
public class PlayerEditorController extends BaseForm implements Initializable {

    @FXML
    private AnchorPane Window;
    @FXML
    private TableView<Player> PlayerTable;
    @FXML
    private Spinner<Integer> DmgSpinner;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DataBind();
    }

    public void DataBind() {
        AppData data = MainApp.getAppData();
        ObservableList<Player> players = data.getDb().getPlayers();

        PlayerTable.setEditable(false);
        //Create the columns
        TableColumn<Player, String> col1 = new TableColumn<>("Name");
        TableColumn<Player, String> col2 = new TableColumn<>("Race");
        TableColumn<Player, String> col3 = new TableColumn<>("Class");
        TableColumn<Player, String> col4 = new TableColumn<>("AC");
        TableColumn<Player, String> col5 = new TableColumn<>("HP");
        TableColumn<Player, String> col6 = new TableColumn<>("Max HP");
        TableColumn<Player, String> col7 = new TableColumn<>("Dex");
        TableColumn<Player, String> col8 = new TableColumn<>("Roll");
        TableColumn<Player, String> col9 = new TableColumn<>("Adv");
        TableColumn<Player, String> col10 = new TableColumn<>("Dead");
        TableColumn<Player, String> col11 = new TableColumn<>("Stable");
        TableColumn<Player, String> col12 = new TableColumn<>("Appearance");
        TableColumn<Player, String> col13 = new TableColumn<>("HP");

        col1.setCellValueFactory(new PropertyValueFactory<>("Name"));
        col2.setCellValueFactory(new PropertyValueFactory<>("Race"));
        col3.setCellValueFactory(new PropertyValueFactory<>("Class"));
        col4.setCellValueFactory(new PropertyValueFactory<>("AC"));
        col5.setCellValueFactory(new PropertyValueFactory<>("HP"));
        col6.setCellValueFactory(new PropertyValueFactory<>("Max HP"));
        col7.setCellValueFactory(new PropertyValueFactory<>("Dex"));
        col8.setCellValueFactory(new PropertyValueFactory<>("Roll"));
        col9.setCellValueFactory(new PropertyValueFactory<>("Adv"));
        col10.setCellValueFactory(new PropertyValueFactory<>("Dead"));
        col11.setCellValueFactory(new PropertyValueFactory<>("Stable"));
        col12.setCellValueFactory(new PropertyValueFactory<>("Appearance"));
        col13.setCellValueFactory(new PropertyValueFactory<>("HP"));

        //col4.setCellValueFactory(cellData -> cellData.getValue().getChallenge());
        TableColumn<Player, String> col14 = ButtonCol("HP", "Dmg", Styles.getSmall(), event -> {
//                                Person person = getTableView().getItems().get(getIndex());
//                                System.out.println(person.getFirstName()
//                                        + "   " + person.getLastName());
        });
        TableColumn<Player, String> col15 = ButtonCol("HP", "Heal", Styles.getSmall(), event -> {
//                                Person person = getTableView().getItems().get(getIndex());
//                                System.out.println(person.getFirstName()
//                                        + "   " + person.getLastName());
        });
        PlayerTable.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7, col8, col9, col10, col11, col12, col13, col14, col15);

        PlayerTable.setItems(players);

        PlayerTable.setEditable(true);
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
    private void RollInitClick(ActionEvent event) {
    }

    @FXML
    private void DmgAllClick(ActionEvent event) {
    }

    @FXML
    private void HealAllClick(ActionEvent event) {
    }

}
