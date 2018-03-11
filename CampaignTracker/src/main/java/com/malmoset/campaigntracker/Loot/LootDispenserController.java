/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigntracker.Loot;

import com.malmoset.campaigndata.Battle;
import com.malmoset.campaigndata.BattleMonster;
import com.malmoset.campaigndata.Loot.Artobject;
import com.malmoset.campaigndata.Loot.Coins;
import com.malmoset.campaigndata.Loot.Gemstone;
import com.malmoset.campaigndata.Loot.HoardTable;
import com.malmoset.campaigndata.Loot.HoardTableMaster;
import com.malmoset.campaigndata.Loot.IndividualCoinTable;
import com.malmoset.campaigndata.Loot.IndividualTableMaster;
import com.malmoset.campaigndata.Loot.LootItem;
import com.malmoset.campaigndata.Loot.LootMonster;
import com.malmoset.campaigndata.Loot.Magicitem;
import com.malmoset.campaigndata.Loot.MagicitemTable;
import com.malmoset.campaigntracker.MainApp;
import com.malmoset.campaigntrackercontrols.AddDeleteContextMenu;
import com.malmoset.campaigntrackercontrols.ChoiceBoxEditCell;
import com.malmoset.campaigntrackercontrols.GotItDialog;
import com.malmoset.campaigntrackercontrols.IntegerStringConverter;
import com.malmoset.campaigntrackercontrols.TextEditCell;
import com.malmoset.controls.BaseForm;
import com.malmoset.dice.Dice;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author sean
 */
public class LootDispenserController extends BaseForm implements Initializable {

    @FXML
    private TableView<LootMonster> MonstersTable;
    @FXML
    private TableView<LootItem> LootTable;

    private ObservableList<LootMonster> lootsources;
    private ObservableList<LootItem> lootdrops;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        lootsources = FXCollections.observableArrayList(new ArrayList<LootMonster>());
        lootdrops = FXCollections.observableArrayList(new ArrayList<LootItem>());
        BindData();
    }

    public void LoadBattle(Battle battle) {
        for (BattleMonster monster : battle.getMonsters()) {
            if (monster.getCurrentHP() <= 0 || monster.isPersuaded()) {
                String name = monster.getName();
                if (monster.isPersuaded()) {
                    name += " Persuaded";
                }
                lootsources.add(new LootMonster(1, name, monster.getChallenge(), false));
            }
        }
    }

    private void BindData() {
        SpinnerValueFactory<Integer> valfact = new SpinnerValueFactory.IntegerSpinnerValueFactory(-500, 500, 0);

        MonstersTable.setEditable(true);
        LootTable.setEditable(true);

        //Generic editable factory
        Callback<TableColumn<LootItem, LootItem.LootType>, TableCell<LootItem, LootItem.LootType>> advcellFactory
                = new Callback<TableColumn<LootItem, LootItem.LootType>, TableCell<LootItem, LootItem.LootType>>() {
            public TableCell call(TableColumn p) {
                return new ChoiceBoxEditCell(new LootTypeStringConverter(), FXCollections.observableArrayList(LootItem.LootType.getLootTypes()), -1);
            }
        };
        //Create the columns
        {
            MonstersTable.setItems(lootsources);
            TableColumn<LootMonster, Integer> col1 = new TableColumn<>("Qty");
            TableColumn<LootMonster, String> col2 = new TableColumn<>("Monster");
            TableColumn<LootMonster, String> col3 = new TableColumn<>("Challenge");
            TableColumn<LootMonster, Boolean> col4 = new TableColumn<>("Hoard");

            col1.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
            col2.setCellValueFactory(cellData -> cellData.getValue().monsterNameProperty());
            col3.setCellValueFactory(cellData -> cellData.getValue().getChallenge().strValueProperty());
            col4.setCellValueFactory(cellData -> cellData.getValue().hoardProperty());
            col2.setPrefWidth(300);

            col1.setCellFactory(TextEditCell.editCellFactory(new IntegerStringConverter()));
            col2.setCellFactory(TextEditCell.editCellFactory());
            col3.setCellFactory(TextEditCell.editCellFactory());
            col4.setCellFactory(tc -> new CheckBoxTableCell<>());
            MonstersTable.getColumns().addAll(col1, col2, col3, col4);
            MonstersTable.setContextMenu(AddDeleteContextMenu.AddDelContextMenu(
                    (ActionEvent event) -> {
                        Object selected = MonstersTable.getSelectionModel().getSelectedItem();
                        if (selected != null) {
                            lootsources.remove(selected);
                        }
                    },
                    (ActionEvent event) -> {
                        LootMonster object = new LootMonster();
                        lootsources.add(object);
                    }));
        }

        {
            LootTable.setItems(lootdrops);
            TableColumn<LootItem, LootItem.LootType> col1 = new TableColumn<>("Type");
            TableColumn<LootItem, Integer> col2 = new TableColumn<>("Count");
            TableColumn<LootItem, String> col3 = new TableColumn<>("Name");

            col1.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
            col2.setCellValueFactory(cellData -> cellData.getValue().countProperty().asObject());
            col3.setCellValueFactory(cellData -> cellData.getValue().itemProperty());

            col1.setCellFactory(advcellFactory);
            col2.setCellFactory(TextEditCell.editCellFactory(new IntegerStringConverter()));
            col3.setCellFactory(TextEditCell.editCellFactory());
            col3.prefWidthProperty().bind(LootTable.widthProperty().subtract(5).subtract(col1.widthProperty()).subtract(col2.widthProperty()));

            LootTable.getColumns().addAll(col1, col2, col3);
            LootTable.setContextMenu(AddDeleteContextMenu.AddDelContextMenu(
                    (ActionEvent event) -> {
                        Object selected = LootTable.getSelectionModel().getSelectedItem();
                        if (selected != null) {
                            lootdrops.remove(selected);
                        }
                    },
                    (ActionEvent event) -> {
                        LootItem object = new LootItem();
                        lootdrops.add(object);
                    }));

        }

    }

    private void LootCoins(Coins coins) {
        HashMap<String, Integer> rolled = new HashMap<>();

        rolled.put("cp", Dice.RollLoot(coins.getCp()));
        rolled.put("sp", Dice.RollLoot(coins.getSp()));
        rolled.put("ep", Dice.RollLoot(coins.getEp()));
        rolled.put("gp", Dice.RollLoot(coins.getGp()));
        rolled.put("pp", Dice.RollLoot(coins.getPp()));

        for (Map.Entry< String, Integer> entry : rolled.entrySet()) {
            if (entry.getValue() > 0) {
                //Check if we have this already
                Optional<LootItem> existing = lootdrops.stream().filter(x -> x.getType() == LootItem.LootType.MONEY && x.getItem() == entry.getKey()).findFirst();
                if (existing.isPresent()) {
                    existing.get().setCount(existing.get().getCount() + entry.getValue());
                } else {
                    LootItem newitem = new LootItem();
                    newitem.setCount(entry.getValue());
                    newitem.setItem(entry.getKey());
                    newitem.setType(LootItem.LootType.MONEY);
                    lootdrops.add(newitem);

                }

            }
        }
    }

    @FXML
    private void RollClick(ActionEvent event) {
        lootdrops.clear();
        for (LootMonster lootsource : lootsources) {
            for (int sourcecount = 0; sourcecount < lootsource.getQuantity(); sourcecount++) {
                if (!lootsource.isHoard()) {
                    List<IndividualTableMaster> tables = MainApp.getAppData().getLoot().getIndividual().stream().filter(x -> x.getMincr() <= lootsource.getChallenge().toInt() && x.getMaxcr() >= lootsource.getChallenge().toInt()).collect(Collectors.toList());
                    //var tables = LootDataTables.tables.individual.Where(x =  > x.mincr <= lootsource.Challenge && x.maxcr >= lootsource.Challenge).ToArray();
                    for (IndividualTableMaster table : tables) {
                        //roll a D100
                        int d100 = Dice.roll(100, Dice.RollTypes.Normal);
                        List<IndividualCoinTable> rolltables = table.getTable().stream().filter(x -> x.getMin() <= d100 && x.getMax() >= d100).collect(Collectors.toList());
                        for (IndividualCoinTable rolltable : rolltables) {
                            LootCoins(rolltable.getCoins());
                        }
                    }
                } else {
                    List<HoardTableMaster> tables = MainApp.getAppData().getLoot().getHoard().stream().filter(x -> x.getMincr() <= lootsource.getChallenge().toInt() && x.getMaxcr() >= lootsource.getChallenge().toInt()).collect(Collectors.toList());
                    for (HoardTableMaster table : tables) {
                        int d100 = Dice.roll(100, Dice.RollTypes.Normal);
                        List<HoardTable> rolltables = table.getTable().stream().filter(x -> x.getMin() <= d100 && x.getMax() >= d100).collect(Collectors.toList());

                        LootCoins(table.getCoins());
                        for (HoardTable rolltable : rolltables) {
                            if (rolltable.getGems() != null) {
                                int gemsCount = Dice.RollLoot(rolltable.getGems().getAmount());
                                Optional<Gemstone> gemchoices = MainApp.getAppData().getLoot().getGemstones().stream().filter(x -> x.getType().equals(rolltable.getGems().getType())).findFirst();
                                if (gemsCount > 0 && gemchoices.isPresent()) {
                                    //Save the table we got this from
                                    lootdrops.add(new LootItem(LootItem.LootType.GEMSTONE, 0, gemchoices.get().getName()));
                                    LootItem newitem = new LootItem();
                                    newitem.setType(LootItem.LootType.GEMSTONE);
                                    newitem.setItem(gemchoices.get().getTable().get(Dice.roll(gemchoices.get().getTable().size(), Dice.RollTypes.Normal) - 1));
                                    newitem.setCount(gemsCount);
                                }
                            }
                            if (rolltable.getArtobjects() != null) {
                                int artCount = Dice.RollLoot(rolltable.getArtobjects().getAmount());
                                Optional<Artobject> artchoices = MainApp.getAppData().getLoot().getArtobjects().stream().filter(x -> x.getType().equals(rolltable.getArtobjects().getType())).findFirst();
                                if (artCount > 0 && artchoices.isPresent()) {
                                    //Save the table we got this from
                                    lootdrops.add(new LootItem(LootItem.LootType.ARTOBJECT, 0, artchoices.get().getName()));
                                    LootItem newitem = new LootItem();
                                    newitem.setType(LootItem.LootType.ARTOBJECT);
                                    newitem.setItem(artchoices.get().getTable().get(Dice.roll(artchoices.get().getTable().size(), Dice.RollTypes.Normal) - 1));
                                    newitem.setCount(artCount);
                                }
                            }

                            if (rolltable.getMagicitems() != null) {
                                final String[] magicAmounts = rolltable.getMagicitems().getAmount().split(",");
                                final String[] magicTypes = rolltable.getMagicitems().getType().split(",");
                                for (int usetables = 0; usetables < magicAmounts.length; usetables++) {
                                    Integer magicCount = Dice.RollLoot(rolltable.getMagicitems().getAmount());
                                    final String magictype = magicTypes[usetables];

                                    Optional<Magicitem> magicchoices = MainApp.getAppData().getLoot().getMagicitems().stream().filter(x -> x.getType().equals(magictype)).findFirst();
                                    if (magicCount > 0 && magicchoices.isPresent()) {
                                        //Save the table we got this from
                                        lootdrops.add(new LootItem(LootItem.LootType.MAGICITEM, 0, rolltable.getMagicitems().getAmount().toString() + "(" + magicCount.toString() + ") " + magicchoices.get().getName()));
                                        for (int a = 0; a < magicCount; a++) {
                                            int d100_2 = Dice.roll(100, Dice.RollTypes.Normal);
                                            Optional<MagicitemTable> item = magicchoices.get().getTable().stream().filter(x -> x.getMin() <= d100_2 && x.getMax() >= d100_2).findFirst();
                                            if (item.isPresent()) {
                                                if (item.get().getTable() != null) {
                                                    LootItem newitem = new LootItem();
                                                    newitem.setType(LootItem.LootType.MAGICITEM);
                                                    newitem.setItem(item.get().getTable().get(Dice.roll(item.get().getTable().size(), Dice.RollTypes.Normal) - 1));
                                                    newitem.setCount(1);
                                                    lootdrops.add(newitem);

                                                } else {
                                                    LootItem newitem = new LootItem();
                                                    newitem.setType(LootItem.LootType.MAGICITEM);
                                                    newitem.setItem(item.get().getItem());
                                                    newitem.setCount(1);
                                                    lootdrops.add(newitem);

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @FXML
    private void SaveClick(ActionEvent event
    ) {
        MainApp.getAppData().getDb().getLoot().addAll(lootdrops);
        GotItDialog.GotIt("Loot Saved", "Loot added to campaign!");
    }

}
