package com.malmoset.campaigntracker;

import com.malmoset.campaigndata.Utilities;
import com.malmoset.controls.BaseForm;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class MainMenuController implements Initializable {

    @FXML
    private Label HeaderLabel;
    @FXML
    private Button PlayersButton;
    @FXML
    private Button NewBattleButton;
    @FXML
    private Button ViewBattleButton;
    @FXML
    private Button EncountersButton;
    @FXML
    private Button MonstersLibrary;
    @FXML
    private Button SessionButton;
    @FXML
    private Button RollButton;
    @FXML
    private Button LootButton;
    @FXML
    private Button NewButton;
    @FXML
    private Button LoadButton;
    @FXML
    private Button SaveButton;
    @FXML
    private Button StatsButton;
    @FXML
    private Button PlayerStatsButton;
    @FXML
    private Button PlayerBattleButton;
    @FXML
    private AnchorPane MainMenu;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @FXML
    private void PlayersClick(ActionEvent event) throws IOException {
        Utilities.TestJson();
//            for (Map.Entry<String, String> file : files.entrySet()) {
//                Monster[] newmonsters = mapper.readValue(file.getValue(), Monster[].class);
//                monsters.addAll(newmonsters);
//            }
        BaseForm controller = BaseForm.LoadForm(getClass().getResource("/fxml/PC/PlayerEditor.fxml"), "Player Editor");
        controller.Show();
    }

    @FXML
    private void NewBattleClick(ActionEvent event) {
        BaseForm controller = BaseForm.LoadForm(getClass().getResource("/fxml/Battles/BattleViewer.fxml"), "Battle");
        controller.Show();

    }

    @FXML
    private void ViewBattlesClick(ActionEvent event) {
        BaseForm controller = BaseForm.LoadForm(getClass().getResource("/fxml/Battles/BattleListing.fxml"), "Battle Listing");
        controller.Show();

    }

    @FXML
    private void EncountersClick(ActionEvent event) {
        BaseForm controller = BaseForm.LoadForm(getClass().getResource("/fxml/Encounters/EncounterManager.fxml"), "Encounter Manager");
        controller.Show();

    }

    @FXML
    private void MonstersClick(ActionEvent event) {
        AppData data = MainApp.getAppData();

        BaseForm controller = BaseForm.LoadForm(getClass().getResource("/fxml/Monsters/MonsterManager.fxml"), "Monster Manager");
        controller.Show();

    }

    @FXML
    private void SessClick(ActionEvent event) {
        BaseForm controller = BaseForm.LoadForm(getClass().getResource("/fxml/Session/AdjustSession.fxml"), "Session Selector");
        controller.Show();

    }

    @FXML
    private void RollClick(ActionEvent event) {
        BaseForm controller = BaseForm.LoadForm(getClass().getResource("/fxml/RollOMatic/RollOMatic.fxml"), "Roll-O-Matic");
        controller.Show();

    }

    @FXML
    private void LootClick(ActionEvent event) {
        BaseForm controller = BaseForm.LoadForm(getClass().getResource("/fxml/Loot/LootDispenser.fxml"), "Loot Dispenser");
        controller.Show();

    }

    @FXML
    private void NewClick(ActionEvent event) {

    }

    @FXML
    private void LoadClick(ActionEvent event) {
    }

    @FXML
    private void SaveClick(ActionEvent event) {
    }

    @FXML
    private void StatsClick(ActionEvent event) {
        BaseForm controller = BaseForm.LoadForm(getClass().getResource("/fxml/Session/CampaignStats.fxml"), "Campaign Stats");
        controller.Show();

    }

    @FXML
    private void PlayerStatsClick(ActionEvent event) {
        BaseForm controller = BaseForm.LoadForm(getClass().getResource("/fxml/PC/PCView.fxml"), "Player Status");
        controller.Show();

    }

    @FXML
    private void BattleButtonClick(ActionEvent event) {
        BaseForm controller = BaseForm.LoadForm(getClass().getResource("/fxml/Battles/PCBattleView.fxml"), "Battle View");
        controller.Show();

    }

}
