/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigntracker.PC;

import com.malmoset.campaigndata.Player;
import com.malmoset.campaigntracker.AppData;
import com.malmoset.campaigntracker.MainApp;
import com.malmoset.campaigntracker.Monsters.MonsterViewerController;
import com.malmoset.controls.ActionButtonTableCell;
import com.malmoset.controls.ContextMenus;
import com.malmoset.controls.ChoiceBoxEditCell;
import com.malmoset.controls.DiceStringConverter;
import com.malmoset.controls.IntegerStringConverter;
import com.malmoset.controls.SpinnerUtils;
import com.malmoset.controls.Styles;
import com.malmoset.controls.TextEditCell;
import com.malmoset.controls.BaseForm;
import com.malmoset.dice.Dice;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
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
        DmgSpinner.focusedProperty().addListener((s, ov, nv) -> {
            if (!nv) {
                SpinnerUtils.commitEditorText(DmgSpinner);
            }
        });
    }

    public void DataBind() {

        SpinnerValueFactory<Integer> valfact = new SpinnerValueFactory.IntegerSpinnerValueFactory(-500, 500, 0);
        DmgSpinner.setValueFactory(valfact);
        DmgSpinner.setEditable(true);

        PlayerTable.setEditable(false);

        //Generic editable factory
        Callback<TableColumn<Player, Dice.RollTypes>, TableCell<Player, Dice.RollTypes>> advcellFactory
                = new Callback<TableColumn<Player, Dice.RollTypes>, TableCell<Player, Dice.RollTypes>>() {
            public TableCell call(TableColumn p) {
                return new ChoiceBoxEditCell(new DiceStringConverter(), FXCollections.observableArrayList(Dice.RollTypesList()), 1);
            }
        };
        //Create the columns
        TableColumn<Player, String> col1 = new TableColumn<>("Name");
        TableColumn<Player, String> col2 = new TableColumn<>("Race");
        TableColumn<Player, String> col3 = new TableColumn<>("Class");
        TableColumn<Player, Integer> col4 = new TableColumn<>("AC");
        TableColumn<Player, Integer> col5 = new TableColumn<>("HP");
        TableColumn<Player, Integer> col6 = new TableColumn<>("Max HP");
        TableColumn<Player, Integer> col7 = new TableColumn<>("Dex");
        TableColumn<Player, Integer> col8 = new TableColumn<>("Roll");
        TableColumn<Player, Dice.RollTypes> col9 = new TableColumn<>("Adv");
        TableColumn<Player, Boolean> col10 = new TableColumn<>("Dead");
        TableColumn<Player, Boolean> col11 = new TableColumn<>("Stable");
        TableColumn<Player, String> col12 = new TableColumn<>("Appearance");
        TableColumn<Player, Integer> col13 = new TableColumn<>("HP");

        col1.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        col2.setCellValueFactory(cellData -> cellData.getValue().raceProperty());
        col3.setCellValueFactory(cellData -> cellData.getValue().character_classProperty());
        col4.setCellValueFactory(cellData -> cellData.getValue().aCProperty().asObject());
        col5.setCellValueFactory(cellData -> cellData.getValue().currentHPProperty().asObject());
        col6.setCellValueFactory(cellData -> cellData.getValue().maxHPProperty().asObject());
        col7.setCellValueFactory(cellData -> cellData.getValue().initiativeProperty().asObject());
        col8.setCellValueFactory(cellData -> cellData.getValue().rollProperty().asObject());
        col9.setCellValueFactory(cellData -> cellData.getValue().advProperty());
        col10.setCellValueFactory(cellData -> cellData.getValue().deadProperty());
        col11.setCellValueFactory(cellData -> cellData.getValue().stableProperty());
        col12.setCellValueFactory(cellData -> cellData.getValue().appearanceProperty());
        col13.setCellValueFactory(cellData -> cellData.getValue().hpToChangeProperty().asObject());

        col1.setCellFactory(TextEditCell.editCellFactory());
        col2.setCellFactory(TextEditCell.editCellFactory());
        col3.setCellFactory(TextEditCell.editCellFactory());

        col9.setCellFactory(advcellFactory);

        col4.setCellFactory(TextEditCell.editCellFactory(new IntegerStringConverter()));
        col5.setCellFactory(TextEditCell.editCellFactory(new IntegerStringConverter()));
        col6.setCellFactory(TextEditCell.editCellFactory(new IntegerStringConverter()));
        col7.setCellFactory(TextEditCell.editCellFactory(new IntegerStringConverter()));
        col8.setCellFactory(TextEditCell.editCellFactory(new IntegerStringConverter()));

        col13.setCellFactory(TextEditCell.editCellFactory(new IntegerStringConverter()));

        col10.setCellFactory(tc -> new CheckBoxTableCell<>());
        col11.setCellFactory(tc -> new CheckBoxTableCell<>());

        TableColumn<Player, Button> col14 = new TableColumn<>("HP");
        col14.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        col14.setCellFactory(ActionButtonTableCell.<Player>forTableColumn("Dmg", Styles.getSmall(), (Player player) -> {
            int newHP;
            if (player.getHpToChange() > 0) {
                newHP = player.getCurrentHP() - player.getHpToChange();
            } else {
                newHP = player.getCurrentHP() - DmgSpinner.getValue();
            }
            player.setCurrentHP(newHP);
            return player;
        }));
        TableColumn<Player, Button> col15 = new TableColumn<>("HP");
        col15.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        col15.setCellFactory(ActionButtonTableCell.<Player>forTableColumn("Heal", Styles.getSmall(), (Player player) -> {
            int newHP;
            if (player.getHpToChange() > 0) {
                newHP = player.getCurrentHP() + player.getHpToChange();
            } else {
                newHP = player.getCurrentHP() + DmgSpinner.getValue();
            }
            if (newHP > player.getMaxHP()) {
                newHP = player.getMaxHP();
            }
            //If they have bonus HP or something and are already higher than max
            //This prevents us messing that up
            if (newHP > player.getCurrentHP()) {
                player.setCurrentHP(newHP);
            }
            return player;
        }));

        PlayerTable.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7, col8, col9, col10, col11, col12, col13, col14, col15);

        PlayerTable.setContextMenu(ContextMenus.AddDelContextMenu(
                (ActionEvent event) -> {
                    Player selected = PlayerTable.getSelectionModel().getSelectedItem();
                    if (selected != null) {
                        MainApp.getAppData().getDb().getPlayers().remove(selected);
                    }
                },
                (ActionEvent event) -> {
                    try {
                        Player object = new Player();
                        MainApp.getAppData().getDb().getPlayers().add(object);
                    } catch (Exception ex) {
                        Logger.getLogger(MonsterViewerController.class.getName()).log(Level.INFO, null, ex);
                    }
                }));
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

    @FXML
    private void RollInitClick(ActionEvent event) {
        ObservableList<Player> players = MainApp.getAppData().getDb().getPlayers();
        for (Player player : players) {
            int roll = Dice.roll(20, player.getAdv()) + player.getInitiative();
            player.setRoll(roll);
        }
        //Switch to sorting by roll and initiative modifier
        BindSortedList();
        sortedData.comparatorProperty().setValue(Player.CompareInitiative());
        //Add 1 to the rolls, mostly so it will notify the player visible form
        MainApp.getAppData().getDb().initiativeRollsProperty().set(MainApp.getAppData().getDb().initiativeRollsProperty().get() + 1);
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
