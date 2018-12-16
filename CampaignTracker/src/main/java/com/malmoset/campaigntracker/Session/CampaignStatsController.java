/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigntracker.Session;

import com.malmoset.campaigndata.Battle;
import com.malmoset.campaigndata.Loot.LootItem;
import com.malmoset.campaigndata.Player;
import com.malmoset.campaigndata.XPEvent;
import com.malmoset.campaigntracker.MainApp;
import com.malmoset.controls.BaseForm;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * FXML Controller class
 *
 * @author sean
 */
public class CampaignStatsController extends BaseForm implements Initializable {

    @FXML
    private TextFlow Output;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        GenerateText();
    }

    private void GenerateText() {
        List<Battle> battles = MainApp.getAppData().getDb().getBattles().sorted((Battle left, Battle right) -> {
            return Integer.compare(left.getBattleNumber(), right.getBattleNumber());
        });
        Map<Integer, ObservableList<XPEvent>> xpEntries = MainApp.getAppData().getDb().getXP();
        Map<Integer, List<Battle>> sessionBattles = battles.stream().collect(Collectors.groupingBy(x -> x.getSession()));
        List<XPEvent> allXPevents = xpEntries.values().stream().flatMap(List::stream).collect(Collectors.toList());
        Map<String, Long> monsterKills = allXPevents.stream().collect(Collectors.groupingBy(XPEvent::getMonster, Collectors.counting()));

        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);

        AppendText("", Color.CORNFLOWERBLUE, true);

        AppendText("Campaign Manager Campaign " + MainApp.getAppData().getDb().getCampaignName(), Color.MEDIUMVIOLETRED, true);
        AppendText("", Color.CORNFLOWERBLUE, true);

        if (battles.size() > 0) {

            AppendText("Began " + battles.get(0).getBegan().toString(), Color.MEDIUMVIOLETRED, true);
            AppendText("Stats as of " + new Date().toString(), Color.MEDIUMVIOLETRED, true);
        }

        AppendText("", Color.CORNFLOWERBLUE, true);

        AppendText("Heroes:", Color.GREENYELLOW, true);

        for (Player player : MainApp.getAppData().getDb().getPlayers()) {
            if (player.getRace() != null && player.getRace().length() > 0) {
                AppendText(player.getName() + " the " + player.getRace() + " " + player.getCharacter_class(), Color.GREENYELLOW, true);
            }
        }

        AppendText("", Color.CORNFLOWERBLUE, true);
        int totalmonsters = battles.stream().collect(Collectors.summingInt(x -> x.getTotalMonsters()));

        AppendText("Monsters killed: " + numberFormat.format(battles.stream().collect(Collectors.summingInt(x -> x.getDeadMonsters()))), Color.CORNFLOWERBLUE, true);
        AppendText("Monsters not killed (XP Given): " + numberFormat.format(battles.stream().collect(Collectors.summingInt(x -> x.getPersuadedMonsters()))), Color.CORNFLOWERBLUE, true);
        AppendText("Monsters not killed (no XP Given): " + numberFormat.format(battles.stream().collect(Collectors.summingInt(x -> x.getUntouchedMonsters()))), Color.CORNFLOWERBLUE, true);
        AppendText("XP Earned: " + numberFormat.format(allXPevents.stream().collect(Collectors.summingInt(x -> x.getXP()))), Color.CORNFLOWERBLUE, true);

        AppendText("", Color.CORNFLOWERBLUE, true);

        AppendText("Stats by session:", Color.CORNFLOWERBLUE, true);

        for (Map.Entry<Integer, List<Battle>> battle : sessionBattles.entrySet()) {
            int xpearned = 0;
            AppendText("Session " + battle.getKey().toString() + ":", Color.CORNFLOWERBLUE, true);
            if (xpEntries.containsKey(battle.getKey())) {
                xpearned = xpEntries.get(battle.getKey()).stream().collect(Collectors.summingInt(x -> x.getXP()));
                for (XPEvent xp : xpEntries.get(battle.getKey())) {
                    AppendText("Earned " + numberFormat.format(xp.getXP()) + " for " + xp.getEvent(), Color.CORNFLOWERBLUE, true);
                }
            }

//            AppendText("Monsters killed: " + battle.getValue().stream().collect(Collectors.summingInt(x -> x.getDeadMonsters())).toString(), Color.CORNFLOWERBLUE, true);
//            AppendText("Monsters not killed (XP Given): " + battle.getValue().stream().collect(Collectors.summingInt(x -> x.getPersuadedMonsters())).toString(), Color.CORNFLOWERBLUE, true);
//            AppendText("Monsters not killed (no XP Given): " + battle.getValue().stream().collect(Collectors.summingInt(x -> x.getUntouchedMonsters())).toString(), Color.CORNFLOWERBLUE, true);
            AppendText("XP Earned: " + numberFormat.format(xpearned), Color.CORNFLOWERBLUE, true);
            AppendText("", Color.CORNFLOWERBLUE, true);
        }

        AppendText("", Color.CORNFLOWERBLUE, true);
        AppendText("Monsters killed:", Color.CORNFLOWERBLUE, true);

        for (Map.Entry<String, Long> kill : monsterKills.entrySet()) {
            AppendText("Earned XP for " + numberFormat.format(kill.getValue()) + " " + kill.getKey(), Color.CORNFLOWERBLUE, true);
        }

        List<LootItem> lootitems = MainApp.getAppData().getDb().getLoot();
        for (LootItem loot : lootitems)
        {
            AppendText("Collected " + loot.getCount() + " " + loot.getItem(), Color.GOLD, true);
        }
//        List<BattleMonster> kills = new ArrayList<>();
//        for (Battle battle : battles) {
//            kills.addAll(battle.getMonsters());
//        }
//
//        Dictionary<string, killstats> monsters = new Dictionary<string, killstats>();
//        for (var xp : xpEntries.SelectMany(x =  > x.Value)) {
//            if (xp.Monster.Length > 0) {
//                if (monsters.ContainsKey(xp.Monster)) {
//                    monsters[xp.Monster].Count++;
//                    monsters[xp.Monster].XP += xp.XP;
//                } else {
//                    monsters.Add(xp.Monster, new killstats
//                    {
//                        Count = 1
//                        , XP = xp.XP });
//                }
//            }
//        }
//
//        for (var monster : monsters) {
//            AppendText("Earned XP for " + monster.Value.Count + " " + monster.Key + " for " + monster.Value.XP + " XP", Color.CornflowerBlue, true);
//        }
//        AppendText("", Color.CornflowerBlue, true);
//        AppendText("Loot", Color.Gold, true);
//
//        for (var loot : Program.db.database.Loot.OrderBy(x =  > x.type)) {
//            if (loot.count > 0) {
//                AppendText("Collected " + loot.count.ToString("N0") + " " + loot.item, Color.Gold, true);
//            }
//        }
    }

    private StringBuilder sb = new StringBuilder();

    public void AppendText(String text, Color color, boolean addNewLine) {
//        sb.append(text);
//        if (addNewLine) {
//            sb.append("\n");
//        }
        Text text1;
        if (addNewLine) {
            text1 = new Text(text + "\n");
        } else {
            text1 = new Text(text);
        }

        text1.setFill(color);
        text1.setFont(Font.font("Helvetica", FontWeight.NORMAL, 18));
        Output.getChildren().add(text1);

//            var box = richTextBox1;
//        box.SuspendLayout();
//        box.SelectionColor = color;
//
//        box.AppendText(addNewLine
//                ? $  "{text}{Environment.NewLine}"
//                : text
//        );
//            box.ScrollToCaret();
//        box.ResumeLayout();
    }

    @FXML
    private void PrintClick(ActionEvent event) {
    }

}
