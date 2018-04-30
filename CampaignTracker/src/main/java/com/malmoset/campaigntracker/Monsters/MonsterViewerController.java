/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigntracker.Monsters;

import com.malmoset.campaigndata.Action;
import com.malmoset.campaigndata.CreatureSize.CreatureSizes;
import com.malmoset.campaigndata.GenericValueString;
import com.malmoset.campaigndata.Monster;
import com.malmoset.campaigndata.StatWithModifier;
import com.malmoset.campaigntracker.MainApp;
import com.malmoset.controls.BaseForm;
import com.malmoset.controls.ContextMenus;
import com.malmoset.controls.IntegerStringConverter;
import com.malmoset.controls.NoHeaderTableView;
import com.malmoset.controls.NumberTextField;
import com.malmoset.controls.StatBlock;
import com.malmoset.controls.StatDice;
import com.malmoset.controls.TableViewCellFactories;
import com.malmoset.controls.TextEditCell;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.StyleConverter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.NumberStringConverter;

/**
 * FXML Controller class
 *
 * @author sean
 */
public class MonsterViewerController extends BaseForm implements Initializable {

    private Monster monster;
    @FXML
    private TextField NameField;
    @FXML
    private TextField SourceField;
    @FXML
    private TextField TypeField;
    @FXML
    private ChoiceBox<String> SizeField;
    @FXML
    private TextField AlignField;
    @FXML
    private Button HPButton;
    @FXML
    private TextField HPField;
    @FXML
    private StatDice HPDice;
    @FXML
    private TextField HPNotesField;
    @FXML
    private NumberTextField ACField;
    @FXML
    private TextField ACNotesField;
    @FXML
    private NumberTextField InitField;
    @FXML
    private TextField CHField;
    @FXML
    private StatBlock StrField;
    @FXML
    private StatBlock DexField;
    @FXML
    private StatBlock ConField;
    @FXML
    private StatBlock IntField;
    @FXML
    private StatBlock WisField;
    @FXML
    private StatBlock ChaField;
    @FXML
    private NoHeaderTableView<GenericValueString> SpeedList;
    @FXML
    private NoHeaderTableView<StatWithModifier> SavesList;
    @FXML
    private NoHeaderTableView<GenericValueString> DmgVulList;
    @FXML
    private NoHeaderTableView<GenericValueString> DmgResList;
    @FXML
    private NoHeaderTableView<GenericValueString> ConImList;
    @FXML
    private NoHeaderTableView<GenericValueString> DmgImList;
    @FXML
    private NoHeaderTableView<StatWithModifier> SkillsList;
    @FXML
    private NoHeaderTableView<GenericValueString> LanguageList;
    @FXML
    private TableView<Action> TraitsList;
    @FXML
    private NoHeaderTableView<GenericValueString> SensesList;
    @FXML
    private TableView<Action> ActionsList;
    @FXML
    private TableView<Action> ReactionsList;
    @FXML
    private TableView<Action> LegActionsList;
    @FXML
    private Button SaveButton;

    private ObservableList<Monster> saveList;

    private boolean tablesRedrawn = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void onActivated() {
        System.out.println(this.getStage().getTitle() + "Activated");
        //On MacOS, this can run right away.
        //Windows has a little delay from initial display until the form actually works
        //So, we use runLater to add a little delay to this
        //Seems to work well on Mac and Windows
        if (!tablesRedrawn) {
            Platform.runLater(() -> {
                //Voodoo to force re-drawing the tables
                //This fixes the line wrapping and makes them look good
                TraitsList.getColumns().get(0).setVisible(false);
                TraitsList.getColumns().get(0).setVisible(true);

                ActionsList.getColumns().get(0).setVisible(false);
                ActionsList.getColumns().get(0).setVisible(true);

                ReactionsList.getColumns().get(0).setVisible(false);
                ReactionsList.getColumns().get(0).setVisible(true);

                LegActionsList.getColumns().get(0).setVisible(false);
                LegActionsList.getColumns().get(0).setVisible(true);
            });
            tablesRedrawn = true;
        }

    }

    private void BindData() {
        //        Modifier.getValueFactory().valueProperty().bindBidirectional(statModifier.asObject());

        if (this.monster != null) {
            NameField.textProperty().bindBidirectional(monster.nameProperty());
            SourceField.textProperty().bindBidirectional(monster.sourceProperty());
            TypeField.textProperty().bindBidirectional(monster.typeProperty());
            SizeField.setItems(CreatureSizes.getSizes());
            SizeField.valueProperty().bindBidirectional(monster.getSize().valueProperty());
            AlignField.textProperty().bindBidirectional(monster.alignmentProperty());
            HPField.textProperty().bindBidirectional(monster.getHP().hpValueProperty(), new NumberStringConverter());
            HPNotesField.textProperty().bindBidirectional(monster.getHP().notesProperty());
            ACField.textProperty().bindBidirectional(monster.getAC().acValueProperty(), new NumberStringConverter());
            ACNotesField.textProperty().bindBidirectional(monster.getAC().notesProperty());
            InitField.textProperty().bindBidirectional(monster.initiativeModifierProperty(), new NumberStringConverter());
            CHField.textProperty().bindBidirectional(monster.getChallenge().strValueProperty());
            StrField.statValueProperty().bindBidirectional(monster.getAbilities().strProperty());
            DexField.statValueProperty().bindBidirectional(monster.getAbilities().dexProperty());
            ConField.statValueProperty().bindBidirectional(monster.getAbilities().conProperty());
            IntField.statValueProperty().bindBidirectional(monster.getAbilities().intelligenceProperty());
            WisField.statValueProperty().bindBidirectional(monster.getAbilities().wisProperty());
            ChaField.statValueProperty().bindBidirectional(monster.getAbilities().chaProperty());
            HPDice.statDiceCountProperty().bindBidirectional(monster.getHP().hitDiceCountProperty());
            HPDice.statDiceSizeProperty().bindBidirectional(monster.getHP().hitDiceProperty());
            HPDice.statModifierProperty().bindBidirectional(monster.getHP().hitModifierProperty());
            configGenericTable(SpeedList, FXCollections.observableArrayList(monster.getSpeed()));
            configGenericTable(DmgVulList, FXCollections.observableArrayList(monster.getDamageVulnerabilities()));
            configGenericTable(DmgResList, FXCollections.observableArrayList(monster.getDamageResistances()));
            configGenericTable(ConImList, FXCollections.observableArrayList(monster.getConditionImmunities()));
            configGenericTable(DmgImList, FXCollections.observableArrayList(monster.getDamageImmunities()));
            configGenericTable(LanguageList, FXCollections.observableArrayList(monster.getLanguages()));
            configGenericTable(SensesList, FXCollections.observableArrayList(monster.getSenses()));

            configModifierTable(SavesList, FXCollections.observableArrayList(monster.getSaves()));
            configModifierTable(SkillsList, FXCollections.observableArrayList(monster.getSkills()));

            configActionsTable(TraitsList, FXCollections.observableArrayList(monster.getTraits()));
            configActionsTable(ActionsList, FXCollections.observableArrayList(monster.getActions()));
            configActionsTable(ReactionsList, FXCollections.observableArrayList(monster.getReactions()));
            configActionsTable(LegActionsList, FXCollections.observableArrayList(monster.getLegendaryActions()));

        }
    }

    private void configActionsTable(TableView table, ObservableList<Action> list) {
        table.setItems(list);

        TableColumn<Action, String> nameCol = new TableColumn<>("Name");
        TableColumn<Action, String> attackCol = new TableColumn<>("Attack");
        TableColumn<Action, String> contentCol = new TableColumn<>("Content");
        nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        attackCol.setCellValueFactory(cellData -> cellData.getValue().attackProperty());
        contentCol.setCellValueFactory(cellData -> cellData.getValue().contentProperty());

        nameCol.setCellFactory(TextEditCell.editCellFactory(new DefaultStringConverter(), true));
        attackCol.setCellFactory(TextEditCell.editCellFactory(new DefaultStringConverter(), true));
        contentCol.setCellFactory(TextEditCell.editCellFactory(new DefaultStringConverter(), true));

        table.getColumns().addAll(nameCol, attackCol, contentCol);

        boolean hasAttack = false;
        boolean hasContent = false;
        String longestAttack = "";
        String longestName = "";
        for (Action ac : list) {
            if (ac.getName() != null && ac.getName().length() > longestName.length()) {
                longestName = ac.getName();
            }
            if (ac.getAttack() != null && ac.getAttack().length() > 0) {
                //Find the longest one to use for sizing
                if (ac.getAttack().length() > longestAttack.length()) {
                    longestAttack = ac.getAttack();
                }
                hasAttack = true;
            }
            if (ac.getContent() != null && ac.getContent().length() > 0) {
                hasContent = true;
            }
        }

        {
            //Measure the size needed to hold the longest name string
            final Text text = new Text(longestName);
            text.applyCss();
            double size = text.getLayoutBounds().getWidth() + 5;
            double maxwidth = 200;

            nameCol.setMinWidth(100);
            nameCol.setPrefWidth(size < maxwidth ? size : maxwidth);
            nameCol.maxWidthProperty().bind(table.widthProperty().divide(4));
        }
        if (hasAttack) {
            //Measure the size needed to hold the longest attack string
            final Text text = new Text(longestAttack);
            text.applyCss();

            attackCol.setMinWidth(65);
            attackCol.setPrefWidth(text.getLayoutBounds().getWidth() + 10);
            attackCol.maxWidthProperty().bind(table.widthProperty().divide(3));
        } else {
            attackCol.setPrefWidth(20);
            attackCol.maxWidthProperty().bind(table.widthProperty().divide(3));
        }
        if (hasContent) {
            contentCol.prefWidthProperty().bind(table.widthProperty().subtract(20).subtract(nameCol.widthProperty()).subtract(attackCol.widthProperty()));
//            contentCol.setPrefWidth(500);
//            contentCol.setMaxWidth(500);
        } else {
            contentCol.setPrefWidth(20);
            contentCol.maxWidthProperty().bind(table.widthProperty().multiply(0.7));
        }

        if (!monster.isReadOnly()) {
            table.setEditable(true);
            table.setContextMenu(ContextMenus.AddDelContextMenu(
                    (ActionEvent event) -> {
                        Object selected = table.getSelectionModel().getSelectedItem();
                        if (selected != null) {
                            list.remove(selected);
                        }
                    },
                    (ActionEvent event) -> {
                        Action object = new Action();
                        list.add(object);
                    }));
        }

    }

    private void configModifierTable(TableView table, ObservableList list) {
        table.setItems(list);

        TableColumn<StatWithModifier, String> statCol = new TableColumn<>("Name");
        TableColumn<StatWithModifier, Integer> valueCol = new TableColumn<>("Modifier");
        statCol.prefWidthProperty().bind(table.widthProperty().multiply(0.7).subtract(7));
        //statCol.setMaxWidth(100.0);
        valueCol.prefWidthProperty().bind(table.widthProperty().multiply(0.3).subtract(7));
        //valueCol.setMaxWidth(50.0);
        statCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        valueCol.setCellValueFactory(cellData -> cellData.getValue().modifierProperty().asObject());
        statCol.setCellFactory(TextEditCell.editCellFactory());
        valueCol.setCellFactory(TextEditCell.editCellFactory(new IntegerStringConverter()));

        table.getColumns().addAll(statCol, valueCol);

        if (!monster.isReadOnly()) {
            table.setEditable(true);
            table.setContextMenu(ContextMenus.AddDelContextMenu(
                    (ActionEvent event) -> {
                        Object selected = table.getSelectionModel().getSelectedItem();
                        if (selected != null) {
                            list.remove(selected);
                        }
                    },
                    (ActionEvent event) -> {
                        try {
                            StatWithModifier object = new StatWithModifier();
                            list.add(object);
                        } catch (Exception ex) {
                            Logger.getLogger(MonsterViewerController.class.getName()).log(Level.INFO, null, ex);
                        }
                    }));
        }
    }

    private void configGenericTable(TableView table, ObservableList list) {
        table.setItems(list);
        TableColumn<GenericValueString, String> valueCol = new TableColumn<>("Value");
        valueCol.setCellValueFactory(cellData -> cellData.getValue().valueProperty());
        valueCol.setCellFactory(TextEditCell.editCellFactory());

        table.getColumns().add(valueCol);

        if (!monster.isReadOnly()) {
            table.setEditable(true);
            table.setContextMenu(ContextMenus.AddDelContextMenu(
                    (ActionEvent event) -> {
                        Object selected = table.getSelectionModel().getSelectedItem();
                        if (selected != null) {
                            list.remove(selected);
                        }
                    },
                    (ActionEvent event) -> {
                        try {
                            GenericValueString object = new GenericValueString();
                            list.add(object);
                        } catch (Exception ex) {
                            Logger.getLogger(MonsterViewerController.class.getName()).log(Level.INFO, null, ex);
                        }
                    }));
        }

    }

    public void SetRO(boolean readonly) {
        NameField.setEditable(!readonly);
        SourceField.setEditable(!readonly);
        TypeField.setEditable(!readonly);
        SizeField.setDisable(readonly);
        AlignField.setEditable(!readonly);
        HPField.setEditable(!readonly);
        HPDice.setDisable(readonly);
        HPNotesField.setEditable(!readonly);
        ACField.setEditable(!readonly);
        ACNotesField.setEditable(!readonly);
        InitField.setEditable(!readonly);
        CHField.setEditable(!readonly);
        StrField.setDisable(readonly);
        DexField.setDisable(readonly);
        ConField.setDisable(readonly);
        IntField.setDisable(readonly);
        WisField.setDisable(readonly);
        ChaField.setDisable(readonly);
        SpeedList.setEditable(!readonly);
        SavesList.setEditable(!readonly);
        DmgVulList.setEditable(!readonly);
        DmgResList.setEditable(!readonly);
        ConImList.setEditable(!readonly);
        DmgImList.setEditable(!readonly);
        SkillsList.setEditable(!readonly);
        LanguageList.setEditable(!readonly);
        TraitsList.setEditable(!readonly);
        SensesList.setEditable(!readonly);
        ActionsList.setEditable(!readonly);
        ReactionsList.setEditable(!readonly);
        LegActionsList.setEditable(!readonly);
        SaveButton.setDisable(readonly);
        SaveButton.setText(readonly ? "Read Only" : "Save");

    }

    public Monster getMonster() {
        return monster;
    }

    public void setMonster(ObservableList<Monster> list, Monster monster) {
        saveList = list;
        this.monster = monster;
        BindData();
        SetRO(monster.isReadOnly());
    }

    @FXML
    private void HPButtonClick(ActionEvent event) {
        monster.getHP().RollHP();
    }

    @FXML
    private void SaveButtonClick(ActionEvent event) {
        ArrayList<Monster> newmonsters = new ArrayList<>();
        newmonsters.add(monster);
        MainApp.getAppData().getMon_db().ImportMonsters(saveList, newmonsters);
    }

}
