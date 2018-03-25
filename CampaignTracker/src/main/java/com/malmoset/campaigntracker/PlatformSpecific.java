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

import de.codecentric.centerdevice.MenuToolkit;
import java.io.InputStream;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author sean
 */
public class PlatformSpecific {

    private static MenuBar bar;

    public static MenuBar getBar() {
        return bar;
    }

    public static void setBar(MenuBar bar) {
        PlatformSpecific.bar = bar;
    }

    public static void SetupIcon(Stage stage) {
        //This only matters on windows and sometimes on linux
        final String os = System.getProperty("os.name");
        if (os != null && (os.startsWith("Windows") || os.startsWith("Linux"))) {
            InputStream icon = PlatformSpecific.class.getResourceAsStream("/Icons/128x128.png");
            if (icon != null) {
                stage.getIcons().add(new Image(icon));
            }
        }
    }

    public static MenuItem getMenuItem(String name, EventHandler<ActionEvent> action) {
        MenuItem item = new MenuItem(name);
        item.setOnAction(action);
        return item;
    }

    public static void SetupMenu() {

        final String os = System.getProperty("os.name");
//On Macs, we need to set the system menu
        if (os != null && os.startsWith("Mac")) {

            // Get the toolkit
            MenuToolkit tk = MenuToolkit.toolkit();

// Create the default Application menu
            Menu defaultApplicationMenu = tk.createDefaultApplicationMenu("CampaignTracker");

// Update the existing Application menu
            tk.setApplicationMenu(defaultApplicationMenu);

// Since we now have a reference to the menu, we can rename items
            defaultApplicationMenu.getItems().get(0).setText("Hide all the others");
            // Create a new menu bar
            bar = new MenuBar();

// Add the default application menu
            bar.getMenus().add(tk.createDefaultApplicationMenu("CampaignTracker"));

            Menu Campaign = new Menu("Campaign");
            Campaign.getItems().add(getMenuItem("New Campaign", p -> MenuActions.NewClick()));
            Campaign.getItems().add(new SeparatorMenuItem());
            Campaign.getItems().add(getMenuItem("Load Campaign", p -> MenuActions.LoadClick()));
            Campaign.getItems().add(getMenuItem("Save Campaign", p -> MenuActions.SaveClick()));
            Campaign.getItems().add(new SeparatorMenuItem());
            Campaign.getItems().add(getMenuItem("Campaign Statistics", p -> MenuActions.StatsClick()));

            Menu GM = new Menu("GM Views");
            GM.getItems().add(getMenuItem("Players", p -> MenuActions.PlayersClick()));
            GM.getItems().add(new SeparatorMenuItem());
            GM.getItems().add(getMenuItem("New Battle", p -> MenuActions.NewBattleClick()));
            GM.getItems().add(getMenuItem("View Battles", p -> MenuActions.ViewBattlesClick()));
            GM.getItems().add(new SeparatorMenuItem());
            GM.getItems().add(getMenuItem("Encounters", p -> MenuActions.EncountersClick()));
            GM.getItems().add(new SeparatorMenuItem());
            GM.getItems().add(getMenuItem("Monsters Library", p -> MenuActions.MonstersClick()));
            GM.getItems().add(new SeparatorMenuItem());
            GM.getItems().add(getMenuItem("Session", p -> MenuActions.SessClick()));
            GM.getItems().add(new SeparatorMenuItem());
            GM.getItems().add(getMenuItem("Roll-o-matic", p -> MenuActions.RollClick()));
            GM.getItems().add(getMenuItem("Loot Dispenser", p -> MenuActions.LootClick()));

            Menu Player = new Menu("Player Views");
            Player.getItems().add(getMenuItem("Player Stats View", p -> MenuActions.PlayerStatsClick()));
            Player.getItems().add(getMenuItem("Player Battle Log", p -> MenuActions.BattleButtonClick()));

            Menu Window = new Menu("Window");
            Window.getItems().add(tk.createMinimizeMenuItem());
            //Window.getItems().add(tk.createZoomMenuItem());
            Window.getItems().add(tk.createCycleWindowsItem());
            Window.getItems().add(new SeparatorMenuItem());
            Window.getItems().add(tk.createBringAllToFrontItem());
            Window.getItems().add(new SeparatorMenuItem());
            tk.autoAddWindowMenuItems(Window);

            bar.getMenus().add(Campaign);
            bar.getMenus().add(GM);
            bar.getMenus().add(Player);
            bar.getMenus().add(Window);

// Add some more Menus...
// Use the menu bar for all stages including new ones
            tk.setGlobalMenuBar(bar);
        }
    }
}
