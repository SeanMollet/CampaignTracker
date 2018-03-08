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
import com.malmoset.campaigndata.MonstersDatabase;
import com.malmoset.campaigndata.StatWithModifier;
import com.malmoset.campaigntracker.AppData;
import com.malmoset.campaigntracker.MainApp;
import com.malmoset.campaigntrackercontrols.IntegerStringConverter;
import com.malmoset.campaigntrackercontrols.NoHeaderTableView;
import com.malmoset.campaigntrackercontrols.NumberTextField;
import com.malmoset.campaigntrackercontrols.StatBlock;
import com.malmoset.campaigntrackercontrols.StatDice;
import com.malmoset.controls.BaseForm;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.util.Callback;
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
    @FXML
    private Button AddSpeedButton;
    @FXML
    private Button AddSavesButton;
    @FXML
    private Button AddDmgVulnButton;
    @FXML
    private Button AddDmsResButton;
    @FXML
    private Button AddConImmunButton;
    @FXML
    private Button AddDmgImmunButton;
    @FXML
    private Button AddSkilsButton;
    @FXML
    private Button AddLangButton;
    @FXML
    private Button AddTraitButton;
    @FXML
    private Button AddSenseButton;
    @FXML
    private Button AddActionButton;
    @FXML
    private Button AddReactionButton;
    @FXML
    private Button AddLegActionButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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

    Callback<TableColumn<Action, String>, TableCell<Action, String>> multiLineCellFactory() {
        return new Callback<TableColumn<Action, String>, TableCell<Action, String>>() {
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

    private void configActionsTable(TableView table, ObservableList list) {

        TableColumn<Action, String> nameCol = new TableColumn<>("Name");
        TableColumn<Action, String> attackCol = new TableColumn<>("Attack");
        TableColumn<Action, String> contentCol = new TableColumn<>("Content");
        nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        attackCol.setCellValueFactory(cellData -> cellData.getValue().attackProperty());
        contentCol.setCellValueFactory(cellData -> cellData.getValue().contentProperty());

        nameCol.setMaxWidth(150);
        attackCol.setMaxWidth(250);
        contentCol.setMaxWidth(500);
        nameCol.setCellFactory(multiLineCellFactory());
        attackCol.setCellFactory(multiLineCellFactory());
        contentCol.setCellFactory(multiLineCellFactory());
        table.setItems(list);
        table.getColumns().addAll(nameCol, attackCol, contentCol);
        table.setEditable(true);
    }

    private void configModifierTable(TableView table, ObservableList list) {
        TableColumn<StatWithModifier, String> statCol = new TableColumn<>("Name");
        TableColumn<StatWithModifier, Integer> valueCol = new TableColumn<>("Modifier");
        statCol.setMaxWidth(100.0);
        valueCol.setMaxWidth(50.0);
        statCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        valueCol.setCellValueFactory(cellData -> cellData.getValue().modifierProperty().asObject());
        statCol.setCellFactory(TextFieldTableCell.forTableColumn());
        valueCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        table.setItems(list);
        table.getColumns().addAll(statCol, valueCol);
        table.setEditable(true);
    }

    private void configGenericTable(TableView table, ObservableList list) {
        TableColumn<GenericValueString, String> valueCol = new TableColumn<>("Value");
        valueCol.setCellValueFactory(cellData -> cellData.getValue().valueProperty());
        valueCol.setCellFactory(TextFieldTableCell.forTableColumn());

        table.setItems(list);
        table.getColumns().add(valueCol);
        table.setEditable(true);
    }

    public Monster getMonster() {
        return monster;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
        BindData();
    }

    @FXML
    private void HPButtonClick(ActionEvent event) {
        monster.getHP().RollHP();
    }

    @FXML
    private void SaveButtonClick(ActionEvent event) {
        AppData data = MainApp.getAppData();
        MonstersDatabase mondb = data.getMon_db();
        ArrayList<Monster> newmonsters = new ArrayList<>();
        newmonsters.add(monster);
        mondb.ImportMonsters(newmonsters);
    }

    @FXML
    private void AddSpeed(ActionEvent event) {
        SpeedList.getItems().add(new GenericValueString("New"));
    }

    @FXML
    private void AddSaves(ActionEvent event) {
        SavesList.getItems().add(new StatWithModifier("New", 0));
    }

    @FXML
    private void AddDmgVuln(ActionEvent event) {
        DmgVulList.getItems().add(new GenericValueString("New"));
    }

    @FXML
    private void AddDmgRes(ActionEvent event) {
        DmgResList.getItems().add(new GenericValueString("New"));
    }

    @FXML
    private void AddConImmun(ActionEvent event) {
        ConImList.getItems().add(new GenericValueString("New"));
    }

    @FXML
    private void AddDmgImmun(ActionEvent event) {
        DmgImList.getItems().add(new GenericValueString("New"));
    }

    @FXML
    private void AddSkils(ActionEvent event) {
        SkillsList.getItems().add(new StatWithModifier("New", 0));
    }

    @FXML
    private void AddLang(ActionEvent event) {
        LanguageList.getItems().add(new GenericValueString("New"));
    }

    @FXML
    private void AddTrait(ActionEvent event) {
        TraitsList.getItems().add(new Action("New", "", "", ""));
    }

    @FXML
    private void AddSense(ActionEvent event) {
        SensesList.getItems().add(new GenericValueString("New"));
    }

    @FXML
    private void AddAction(ActionEvent event) {
        ActionsList.getItems().add(new Action("New", "", "", ""));
    }

    @FXML
    private void AddReaction(ActionEvent event) {
        ReactionsList.getItems().add(new Action("New", "", "", ""));
    }

    @FXML
    private void AddLegAction(ActionEvent event) {
        LegActionsList.getItems().add(new Action("New", "", "", ""));
    }

}
