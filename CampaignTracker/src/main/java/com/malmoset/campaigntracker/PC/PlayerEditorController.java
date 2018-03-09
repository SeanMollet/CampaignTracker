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
import com.malmoset.controls.BaseForm;
import com.malmoset.dice.Dice;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
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
    private SortedList<Player> sortedData;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DataBind();
    }

    public void DataBind() {

        SpinnerValueFactory<Integer> valfact = new SpinnerValueFactory.IntegerSpinnerValueFactory(-500, 500, 0);
        DmgSpinner.setValueFactory(valfact);
        DmgSpinner.setEditable(true);

        PlayerTable.setEditable(false);
        //Create the columns
        TableColumn<Player, String> col1 = new TableColumn<>("Name");
        TableColumn<Player, String> col2 = new TableColumn<>("Race");
        TableColumn<Player, String> col3 = new TableColumn<>("Class");
        TableColumn<Player, Integer> col4 = new TableColumn<>("AC");
        TableColumn<Player, Integer> col5 = new TableColumn<>("HP");
        TableColumn<Player, Integer> col6 = new TableColumn<>("Max HP");
        TableColumn<Player, Integer> col7 = new TableColumn<>("Dex");
        TableColumn<Player, Integer> col8 = new TableColumn<>("Roll");
        TableColumn<Player, Integer> col9 = new TableColumn<>("Adv");
        TableColumn<Player, Boolean> col10 = new TableColumn<>("Dead");
        TableColumn<Player, Boolean> col11 = new TableColumn<>("Stable");
        TableColumn<Player, String> col12 = new TableColumn<>("Appearance");
        TableColumn<Player, Integer> col13 = new TableColumn<>("HP");

        col1.setCellValueFactory(new PropertyValueFactory<>("Name"));
        col2.setCellValueFactory(new PropertyValueFactory<>("Race"));
        col3.setCellValueFactory(cellData -> cellData.getValue().character_classProperty());
        col4.setCellValueFactory(cellData -> cellData.getValue().aCProperty().asObject());
        col5.setCellValueFactory(cellData -> cellData.getValue().currentHPProperty().asObject());
        col6.setCellValueFactory(cellData -> cellData.getValue().maxHPProperty().asObject());
        col7.setCellValueFactory(cellData -> cellData.getValue().initiativeProperty().asObject());
        col8.setCellValueFactory(cellData -> cellData.getValue().rollProperty().asObject());
        col9.setCellValueFactory(cellData -> cellData.getValue().advProperty().asObject());
        col10.setCellValueFactory(cellData -> cellData.getValue().deadProperty());
        col11.setCellValueFactory(cellData -> cellData.getValue().stableProperty());
        col12.setCellValueFactory(cellData -> cellData.getValue().appearanceProperty());
        col13.setCellValueFactory(cellData -> cellData.getValue().hpToChangeProperty().asObject());

        col10.setCellFactory(tc -> new CheckBoxTableCell<>());
        col11.setCellFactory(tc -> new CheckBoxTableCell<>());

//        //col4.setCellValueFactory(cellData -> cellData.getValue().getChallenge());
//        TableColumn<Player, String> col14 = ButtonCol("HP", "Dmg", Styles.getSmall(), event -> {
////                                Person person = getTableView().getItems().get(getIndex());
////                                System.out.println(person.getFirstName()
////                                        + "   " + person.getLastName());
//        });
//        TableColumn<Player, String> col15 = ButtonCol("HP", "Heal", Styles.getSmall(), event -> {
////                                Person person = getTableView().getItems().get(getIndex());
////                                System.out.println(person.getFirstName()
////                                        + "   " + person.getLastName());
//        });
        //PlayerTable.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7, col8, col9, col10, col11, col12, col13, col14, col15);
        PlayerTable.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7, col8, col9, col10, col11, col12, col13);

        // 4. Bind the SortedList comparator to the TableView comparator.
        BindSortedList();

        PlayerTable.comparatorProperty().addListener((e) -> {
            //Switch back to sorting based on user selection
            sortedData.comparatorProperty().setValue(PlayerTable.comparatorProperty().get());
        });

        PlayerTable.setEditable(true);
    }

    private void BindSortedList() {
        AppData data = MainApp.getAppData();
        ObservableList<Player> players = data.getDb().getPlayers();
        sortedData = new SortedList(players);
        PlayerTable.setItems(sortedData);
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
        ObservableList<Player> players = MainApp.getAppData().getDb().getPlayers();
        for (Player player : players) {
            int roll = Dice.roll(20, Dice.RollTypes.values()[player.getAdv()]) + player.getInitiative();
            player.setRoll(roll);
        }
        //Switch to sorting by roll and initiative modifier
        BindSortedList();
        sortedData.comparatorProperty().setValue(Player.CompareInitiative());
    }

    @FXML

    private void DmgAllClick(ActionEvent event) {
        ObservableList<Player> players = MainApp.getAppData().getDb().getPlayers();
        for (Player player : players) {
            int newhp = player.getCurrentHP() - DmgSpinner.getValue();
            player.setCurrentHP(newhp);
        }
    }

    @FXML
    private void HealAllClick(ActionEvent event) {
        ObservableList<Player> players = MainApp.getAppData().getDb().getPlayers();
        for (Player player : players) {
            int newhp = player.getCurrentHP() + DmgSpinner.getValue();
            if (newhp > player.getMaxHP()) {
                newhp = player.getMaxHP();
            }
            //If they have bonus HP or something and are already higher than max
            //This prevents us messing that up
            if (newhp > player.getCurrentHP()) {
                player.setCurrentHP(newhp);
            }
        }
    }

    @FXML
    private void AddClick(ActionEvent event) {
        MainApp.getAppData().getDb().getPlayers().add(new Player());
    }

}
