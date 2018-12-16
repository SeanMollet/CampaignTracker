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
package com.malmoset.campaigntracker;

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
        MainApp.getAppData().getDb().campaignNameProperty().addListener((arg, oldVal, newVal) -> {
            HeaderLabel.setText("Main Menu - " + newVal);
        });

        PlatformSpecific.SetupMenu();

    }

    @FXML
    private void PlayersClick(ActionEvent event) throws IOException {
        MenuActions.PlayersClick();
    }

    @FXML
    private void NewBattleClick(ActionEvent event) {
        MenuActions.NewBattleClick();
    }

    @FXML
    private void ViewBattlesClick(ActionEvent event) {
        MenuActions.ViewBattlesClick();
    }

    @FXML
    private void EncountersClick(ActionEvent event) {
        MenuActions.EncountersClick();
    }

    @FXML
    private void MonstersClick(ActionEvent event) {
        MenuActions.MonstersClick();
    }

    @FXML
    private void SessClick(ActionEvent event) {
        MenuActions.SessClick();
    }

    @FXML
    private void RollClick(ActionEvent event) {
        MenuActions.RollClick();
    }

    @FXML
    private void LootClick(ActionEvent event) {
        MenuActions.LootClick();
    }

    @FXML
    private void NewClick(ActionEvent event) {
        MenuActions.NewClick();
    }

    @FXML
    private void LoadClick(ActionEvent event) {
        MenuActions.LoadClick();
    }

    @FXML
    private void SaveClick(ActionEvent event) {
        MenuActions.SaveClick();
    }

    @FXML
    private void StatsClick(ActionEvent event) {
        MenuActions.StatsClick();
    }

    @FXML
    private void PlayerStatsClick(ActionEvent event) {
        MenuActions.PlayerStatsClick();
    }

    @FXML
    private void BattleButtonClick(ActionEvent event) {
        MenuActions.BattleButtonClick();
    }

}
