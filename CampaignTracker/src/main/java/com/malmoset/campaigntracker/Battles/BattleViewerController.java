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

import com.malmoset.campaigndata.Abilities;
import com.malmoset.campaigndata.Battle;
import com.malmoset.campaigndata.BattleMonster;
import com.malmoset.campaigndata.Monster;
import com.malmoset.campaigndata.XPEvent;
import com.malmoset.campaigntracker.Loot.LootDispenserController;
import com.malmoset.campaigntracker.MainApp;
import com.malmoset.campaigntracker.Monsters.MonsterViewerController;
import com.malmoset.controls.ActionButtonTableCell;
import com.malmoset.controls.BaseForm;
import com.malmoset.controls.ContextMenus;
import com.malmoset.controls.IntegerStringConverter;
import com.malmoset.controls.SpinnerUtils;
import com.malmoset.controls.Styles;
import com.malmoset.controls.TableViewCellFactories;
import com.malmoset.controls.TextEditCell;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author sean
 */
public class BattleViewerController extends BaseForm implements Initializable {

    private Battle battle;

    @FXML
    private Label StartedLabel;
    @FXML
    private ChoiceBox<String> SaveType;
    @FXML
    private TableView<BattleMonster> MonstersTable;
    @FXML
    private TableView<XPEvent> XPTable;
    @FXML
    private Spinner<Integer> HPBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void onActivated() {
        SetActiveBattle();
    }

    public void SetActiveBattle() {
        if (this.battle != null) {
            MainApp.getAppData().current_battleProperty().set(battle);
        }
    }

    private void DataBind() {
        BindBattle();
        BindXP();

        SaveType.setItems(Abilities.AbilitiesList());
        SpinnerValueFactory<Integer> valfact = new SpinnerValueFactory.IntegerSpinnerValueFactory(-500, 500, 1);
        HPBox.setValueFactory(valfact);
        HPBox.setEditable(true);
        HPBox.focusedProperty().addListener((s, ov, nv) -> {
            if (!nv) {
                SpinnerUtils.commitEditorText(HPBox);
            }
        });

        //Clear all the monsters HPChange if this value has changed
        HPBox.valueProperty().addListener((s, ov, nv) -> {
            if (ov != nv) {

                for (BattleMonster monster : battle.getMonsters()) {
                    monster.setHPtoChange(0);
                }
            }
        });

        battle.monstersProperty().addListener(new ListChangeListener<BattleMonster>() {
            @Override
            public void onChanged(
                    javafx.collections.ListChangeListener.Change<? extends BattleMonster> c) {
                //Rebind to get the columns to auto-size. This sucks, but I don't have a better idea
                BindBattle();
            }
        });
    }

    private void BindBattle() {
        MonstersTable.setItems(battle.monstersProperty());

        TableColumn<BattleMonster, Boolean> col1 = new TableColumn<>("Hidden");
        TableColumn<BattleMonster, Boolean> col2 = new TableColumn<>("Unknown");
        TableColumn<BattleMonster, Integer> col3 = new TableColumn<>("ID");
        TableColumn<BattleMonster, String> col4 = new TableColumn<>("Name");
        TableColumn<BattleMonster, String> col5 = new TableColumn<>("Type");
        TableColumn<BattleMonster, Integer> col14 = new TableColumn<>("AC");
        TableColumn<BattleMonster, String> col6 = new TableColumn<>("Appearance");
        TableColumn<BattleMonster, Integer> col7 = new TableColumn<>("Save");
        TableColumn<BattleMonster, Integer> col8 = new TableColumn<>("CurrentHP");
        TableColumn<BattleMonster, Integer> col9 = new TableColumn<>("MaxHP");
        TableColumn<BattleMonster, Integer> col10 = new TableColumn<>("HP");

        col1.setCellValueFactory(cellData -> cellData.getValue().hiddenProperty());
        col2.setCellValueFactory(cellData -> cellData.getValue().unknownProperty());
        col3.setCellValueFactory(cellData -> cellData.getValue().indexProperty().asObject());
        col4.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        col5.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        col6.setCellValueFactory(cellData -> cellData.getValue().appearanceProperty());
        col7.setCellValueFactory(cellData -> cellData.getValue().savingRollProperty().asObject());
        col8.setCellValueFactory(cellData -> cellData.getValue().currentHPProperty().asObject());
        col9.setCellValueFactory(cellData -> cellData.getValue().getHP().hpValueProperty().asObject());
        col10.setCellValueFactory(cellData -> cellData.getValue().hPtoChangeProperty().asObject());
        col14.setCellValueFactory(cellData -> cellData.getValue().getAC().acValueProperty().asObject());

        col5.setPrefWidth(100);

        col1.setCellFactory(tc -> new CheckBoxTableCell<>());
        col2.setCellFactory(tc -> new CheckBoxTableCell<>());

        TableColumn<BattleMonster, Button> col11 = new TableColumn<>("HP");
        col11.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        col11.setCellFactory(ActionButtonTableCell.<BattleMonster>forTableColumn("Dmg", Styles.getSmall(), (BattleMonster monster) -> {
            HandleBattleEvent(monster, EventType.Damage);
            return monster;
        }));
        TableColumn<BattleMonster, Button> col12 = new TableColumn<>("HP");
        col12.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        col12.setCellFactory(ActionButtonTableCell.<BattleMonster>forTableColumn("Heal", Styles.getSmall(), (BattleMonster monster) -> {
            HandleBattleEvent(monster, EventType.Healing);
            return monster;
        }));

        TableColumn<BattleMonster, Button> col13 = new TableColumn<>("Grant");
        col13.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        col13.setCellFactory(ActionButtonTableCell.<BattleMonster>forTableColumn("XP", Styles.getSmall(), (BattleMonster monster) -> {
            HandleBattleEvent(monster, EventType.Grant);
            return monster;
        }));

        //Set up the rows
        EventHandler<MouseEvent> doubleClick = (MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() > 1) {
                TableRow row = (TableRow) ((TableCell) event.getSource()).getParent();
                if (row != null) {
                    BattleMonster selectedMonster = (BattleMonster) row.getItem();
                    if (selectedMonster != null) {
                        //Inside a battle, they can edit the monster to their hearts content
                        selectedMonster.setReadOnly(false);
                        LoadMonster((Monster) selectedMonster);
                    }
                }
            }
        };

        col3.setCellFactory(TextEditCell.editCellFactory(new IntegerStringConverter()));
        col7.setCellFactory(TextEditCell.editCellFactory(new IntegerStringConverter()));
        col8.setCellFactory(TextEditCell.editCellFactory(new IntegerStringConverter()));
        col9.setCellFactory(TextEditCell.editCellFactory(new IntegerStringConverter()));
        col10.setCellFactory(TextEditCell.editCellFactory(new IntegerStringConverter()));
        col14.setCellFactory(TextEditCell.editCellFactory(new IntegerStringConverter()));

        col4.setCellFactory(TableViewCellFactories.DoubleClickFactory(doubleClick));
        col5.setCellFactory(TableViewCellFactories.DoubleClickFactory(doubleClick));
        col6.setCellFactory(TableViewCellFactories.DoubleClickFactory(doubleClick));

        MonstersTable.setContextMenu(ContextMenus.DelContextMenu((ActionEvent event) -> {
            BattleMonster monster = MonstersTable.getSelectionModel().getSelectedItem();
            if (monster != null) {
                battle.monstersProperty().remove(monster);
            }
        }));

        MonstersTable.getColumns().clear();
        MonstersTable.getColumns().addAll(col1, col2, col3, col4, col5, col14, col6, col7, col8, col9, col10, col11, col12, col13);

        MonstersTable.setEditable(true);
    }

    private void BindXP() {
        TableColumn<XPEvent, String> col1 = new TableColumn<>("Timestamp");
        TableColumn<XPEvent, Integer> col2 = new TableColumn<>("Session");
        TableColumn<XPEvent, Integer> col3 = new TableColumn<>("Battle");
        TableColumn<XPEvent, String> col4 = new TableColumn<>("Event");
        TableColumn<XPEvent, String> col5 = new TableColumn<>("Monster");
        TableColumn<XPEvent, Integer> col6 = new TableColumn<>("XP");

        col1.setCellValueFactory(cellData -> cellData.getValue().timestampProperty().asString());
        col2.setCellValueFactory(cellData -> cellData.getValue().sessionProperty().asObject());
        col3.setCellValueFactory(cellData -> cellData.getValue().battleProperty().asObject());
        col4.setCellValueFactory(cellData -> cellData.getValue().eventProperty());
        col5.setCellValueFactory(cellData -> cellData.getValue().monsterProperty());
        col6.setCellValueFactory(cellData -> cellData.getValue().xPProperty().asObject());

        col1.setPrefWidth(200);
        col4.setPrefWidth(300);

        col1.setCellFactory(TextEditCell.editCellFactory());
        col2.setCellFactory(TextEditCell.editCellFactory(new IntegerStringConverter()));
        col3.setCellFactory(TextEditCell.editCellFactory(new IntegerStringConverter()));
        col4.setCellFactory(TextEditCell.editCellFactory());
        col5.setCellFactory(TextEditCell.editCellFactory());
        col6.setCellFactory(TextEditCell.editCellFactory(new IntegerStringConverter()));

        XPTable.getColumns().clear();
        XPTable.getColumns().addAll(col1, col2, col3, col4, col5, col6);

        //If the session changes, rebind our data
        MainApp.getAppData().getDb().sessionProperty().addListener((obs, old, newval)
                -> {
            BindXP();
        });

        SortedList<XPEvent> sortedData = new SortedList<>(MainApp.getAppData().getDb().getSessionXP());

        //Sort them in Descending order by date
        sortedData.comparatorProperty().set((Comparator) (Object o1, Object o2) -> {
            XPEvent left = (XPEvent) o1;
            XPEvent right = (XPEvent) o2;
            return left.getTimestamp().compareTo(right.getTimestamp()) * -1;
        });
        XPTable.setEditable(true);
        XPTable.setItems(sortedData);
    }

    @FXML
    private void HideAllClick(ActionEvent event) {
        for (BattleMonster monster : battle.getMonsters()) {
            monster.setHidden(true);
        }
    }

    @FXML
    private void ShowAllClick(ActionEvent event) {
        for (BattleMonster monster : battle.getMonsters()) {
            monster.setHidden(false);
        }
    }

    @FXML
    private void UnknownAllClick(ActionEvent event) {
        for (BattleMonster monster : battle.getMonsters()) {
            monster.setUnknown(true);
        }
    }

    @FXML
    private void KnowAllClick(ActionEvent event) {
        for (BattleMonster monster : battle.getMonsters()) {
            monster.setUnknown(false);
        }
    }

    public enum EventType {
        Damage,
        Healing,
        Grant
    }

    private void HandleBattleEvent(BattleMonster monster, EventType type) {
        int hp = monster.getHPtoChange();
        if (hp == 0) {
            hp = this.HPBox.getValue();
        }
        switch (type) {
            case Damage:
                monster.setCurrentHP(monster.getCurrentHP() - hp);
                if (monster.getCurrentHP() <= 0 && !monster.isPersuaded()) {
                    battle.GrantXP(MainApp.getAppData().getDb().getSession(), "Killed", monster);
                }
                break;
            case Healing:
                int newhp = monster.getCurrentHP() + hp;
                if (newhp > monster.getHP().getHpValue()) {
                    newhp = monster.getHP().getHpValue();
                }
                monster.setCurrentHP(newhp);
                break;
            case Grant:
                monster.setPersuaded(true);
                battle.GrantXP(MainApp.getAppData().getDb().getSession(), "Granted", monster);
                break;
        }
    }

    private void LoadMonster(Monster monster) {

        MonsterViewerController controller = (MonsterViewerController) BaseForm.LoadForm(getClass().getResource("/fxml/Monsters/MonsterViewer.fxml"), "Monster Viewer", true);
        controller.setMonster(battle.monstersProperty(), monster.clone());

        controller.Show();
    }

    @FXML
    private void LootClick(ActionEvent event) {
        LootDispenserController controller = (LootDispenserController) BaseForm.LoadForm(getClass().getResource("/fxml/Loot/LootDispenser.fxml"), "Loot Dispenser");
        controller.LoadBattle(battle);
        controller.Show();
    }

    @FXML
    private void SavesClick(ActionEvent event) {
        String save = this.SaveType.getValue();
        if (save != null) {
            for (BattleMonster monster : battle.getMonsters()) {
                monster.RollSave(save);
            }
        }
    }

    public Battle getBattle() {
        return battle;
    }

    public void setBattle(Battle battle) {
        this.battle = battle;
        DataBind();
        SetActiveBattle();
        this.StartedLabel.setText("Started: " + battle.getBegan().toString());
    }

}
