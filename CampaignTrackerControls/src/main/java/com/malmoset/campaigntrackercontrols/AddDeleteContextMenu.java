/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigntrackercontrols;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

/**
 *
 * @author sean
 */
public class AddDeleteContextMenu {

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
