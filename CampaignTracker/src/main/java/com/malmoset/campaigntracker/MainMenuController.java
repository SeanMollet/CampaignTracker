package com.malmoset.campaigntracker;

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

        BaseForm controller = BaseForm.LoadForm(getClass().getResource("/fxml/PC/PlayerEditor.fxml"), "/styles/Styles.css", "Player Editor");
        controller.Show();
    }

    @FXML
    private void NewBattleClick(ActionEvent event) {
    }

    @FXML
    private void ViewBattlesClick(ActionEvent event) {
    }

    @FXML
    private void EncountersClick(ActionEvent event) {
    }

    @FXML
    private void MonstersClick(ActionEvent event) {
    }

    @FXML
    private void SessClick(ActionEvent event) {
    }

    @FXML
    private void RollClick(ActionEvent event) {
    }

    @FXML
    private void LootClick(ActionEvent event) {
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
    }

    @FXML
    private void PlayerStatsClick(ActionEvent event) {
    }

    @FXML
    private void BattleButtonClick(ActionEvent event) {
    }

}
