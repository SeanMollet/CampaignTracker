/*
 * Copyright 2018 Malmoset, LLC.
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

import com.malmoset.campaigndata.Battle;
import com.malmoset.campaigntracker.Battles.BattleViewerController;
import com.malmoset.controls.BaseForm;
import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author sean
 */
public class MenuActions {

    public static void PlayersClick() {
        BaseForm controller = BaseForm.LoadForm(MenuActions.class.getResource("/fxml/PC/PlayerEditor.fxml"), "Player Editor");
        controller.Show();
    }

    public static void NewBattleClick() {
        Battle battle = new Battle();
        BattleViewerController controller = (BattleViewerController) BaseForm.LoadForm(MenuActions.class.getResource("/fxml/Battles/BattleViewer.fxml"), "Battle");
        MainApp.getAppData().getDb().battlesProperty().add(battle);
        controller.setBattle(battle);
        controller.Show();
    }

    public static void ViewBattlesClick() {
        BaseForm controller = BaseForm.LoadForm(MenuActions.class.getResource("/fxml/Battles/BattleListing.fxml"), "Battle Listing");
        controller.Show();

    }

    public static void EncountersClick() {
        BaseForm controller = BaseForm.LoadForm(MenuActions.class.getResource("/fxml/Encounters/EncounterManager.fxml"), "Encounter Manager");
        controller.Show();

    }

    public static void MonstersClick() {

        BaseForm controller = BaseForm.LoadForm(MenuActions.class.getResource("/fxml/Monsters/MonsterManager.fxml"), "Monster Manager", true);
        controller.Show();
    }

    public static void SessClick() {
        BaseForm controller = BaseForm.LoadForm(MenuActions.class.getResource("/fxml/Session/AdjustSession.fxml"), "Session Selector");
        controller.Show();

    }

    public static void RollClick() {
        BaseForm controller = BaseForm.LoadForm(MenuActions.class.getResource("/fxml/RollOMatic/RollOMatic.fxml"), "Roll-O-Matic");
        controller.Show();

    }

    public static void LootClick() {
        BaseForm controller = BaseForm.LoadForm(MenuActions.class.getResource("/fxml/Loot/LootDispenser.fxml"), "Loot Dispenser");
        controller.Show();

    }

    public static void NewClick() {
        MainApp.getAppData().getDb().NewDB();
    }

    public static void LoadClick() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Custom Monsters");
        FileChooser.ExtensionFilter monsterExtensionFilter
                = new FileChooser.ExtensionFilter(
                        "Campaign Files (.ctct)", "*.ctct");
        fileChooser.getExtensionFilters().add(monsterExtensionFilter);
        fileChooser.setSelectedExtensionFilter(monsterExtensionFilter);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null && file.length() > 0) {
            MainApp.getAppData().getDb().LoadFile(file);
        }
    }

    public static void SaveClick() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Custom Monsters");
        FileChooser.ExtensionFilter monsterExtensionFilter
                = new FileChooser.ExtensionFilter(
                        "Campaign Files (.ctct)", "*.ctct");
        fileChooser.getExtensionFilters().add(monsterExtensionFilter);
        fileChooser.setSelectedExtensionFilter(monsterExtensionFilter);
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            MainApp.getAppData().getDb().SaveFile(file);
        }
    }

    public static void StatsClick() {
        BaseForm controller = BaseForm.LoadForm(MenuActions.class.getResource("/fxml/Session/CampaignStats.fxml"), "Campaign Stats");
        controller.Show();

    }

    public static void PlayerStatsClick() {
        BaseForm controller = BaseForm.LoadForm(MenuActions.class.getResource("/fxml/PC/PCView.fxml"), "Player Status");
        controller.Show();

    }

    public static void BattleButtonClick() {
        BaseForm controller = BaseForm.LoadForm(MenuActions.class.getResource("/fxml/Battles/PCBattleView.fxml"), "Battle View");
        controller.Show();

    }
}
