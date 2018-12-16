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
package com.malmoset.controls;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

/**
 *
 * @author sean
 */
public class ContextMenus {

    public static ContextMenu AlwaysonTopMenu(BaseForm baseform) {
        final ContextMenu contextMenu = new ContextMenu();
        if (baseform != null) {
            MenuItem add;
            if (baseform.isAlwaysOnTop()) {
                add = new MenuItem("✔ Always on Top");
            } else {
                add = new MenuItem("Always on Top");
            }

            contextMenu.getItems().add(add);
            add.setOnAction((ActionEvent event) -> {
                baseform.invertAlwaysOnTop();
            });
        }
        return contextMenu;

    }

    public static ContextMenu DelContextMenu(EventHandler<ActionEvent> delevent) {
        return AddDelContextMenu(delevent, null);
    }

    public static ContextMenu AddContextMenu(EventHandler<ActionEvent> addevent) {
        return AddDelContextMenu(null, addevent);
    }

    public static ContextMenu AddDelContextMenu(EventHandler<ActionEvent> delevent, EventHandler<ActionEvent> addevent) {
        final ContextMenu contextMenu = new ContextMenu();
        if (addevent != null) {
            MenuItem add = new MenuItem("Add");
            contextMenu.getItems().add(add);
            add.setOnAction(addevent);
        }

        if (delevent != null) {
            MenuItem delete = new MenuItem("Delete");
            contextMenu.getItems().add(delete);
            delete.setOnAction(delevent);
        }
        return contextMenu;
    }
}
