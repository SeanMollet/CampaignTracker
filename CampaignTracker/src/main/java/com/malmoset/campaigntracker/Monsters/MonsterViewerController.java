/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigntracker.Monsters;

import com.malmoset.campaigndata.CreatureSize.CreatureSizes;
import com.malmoset.campaigndata.GenericValueString;
import com.malmoset.campaigndata.Monster;
import com.malmoset.campaigndata.Save;
import com.malmoset.campaigntrackercontrols.NoHeaderTableView;
import com.malmoset.campaigntrackercontrols.NumberTextField;
import com.malmoset.campaigntrackercontrols.StatBlock;
import com.malmoset.campaigntrackercontrols.StatDice;
import com.malmoset.controls.BaseForm;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private NoHeaderTableView<Save> SavesList;
    @FXML
    private ListView<String> DmgVulList;
    @FXML
    private ListView<String> DmgResList;
    @FXML
    private ListView<String> ConImList;
    @FXML
    private ListView<String> DmgImList;
    @FXML
    private TableView<?> SkillsList;
    @FXML
    private TableView<?> LanguageList;
    @FXML
    private TableView<?> TraitsList;
    @FXML
    private TableView<?> SensesList;
    @FXML
    private TableView<?> ActionsList;
    @FXML
    private TableView<?> ReactionsList;
    @FXML
    private TableView<?> LegActionsList;
    @FXML
    private Button SaveButton;

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
            {
                TableColumn<GenericValueString, String> valueCol = new TableColumn<>("Value");
                valueCol.setCellValueFactory(new PropertyValueFactory<>("Value"));
                SpeedList.setItems(FXCollections.observableArrayList(monster.getSpeed()));
                SpeedList.getColumns().add(valueCol);
            }

            {
                TableColumn<Save, String> statCol = new TableColumn<>("Name");
                TableColumn<Save, Integer> valueCol = new TableColumn<>("Modifier");
                statCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
                valueCol.setCellValueFactory(new PropertyValueFactory<>("Modifier"));
                SavesList.setItems(FXCollections.observableArrayList(monster.getSaves()));
                SavesList.getColumns().addAll(statCol, valueCol);
            }

        }
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
    }

    @FXML
    private void SaveButtonClick(ActionEvent event) {
    }

}
