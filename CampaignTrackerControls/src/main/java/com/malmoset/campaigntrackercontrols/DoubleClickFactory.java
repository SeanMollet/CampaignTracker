/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigntrackercontrols;

import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 *
 * @author sean
 */
public class DoubleClickFactory {

    public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> DoubleClickFactory(EventHandler<MouseEvent> event) {
        return new Callback<TableColumn<S, T>, TableCell<S, T>>() {
            @Override
            public TableCell<S, T> call(TableColumn p) {
                TableCell cell = new TableCell<S, T>() {

                    @Override
                    public void updateItem(T item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty ? null : getString());
                        setGraphic(null);
                    }

                    private String getString() {
                        return getItem() == null ? "" : getItem().toString();
                    }
                };
                cell.addEventFilter(MouseEvent.MOUSE_CLICKED, event);
                return cell;
            }
        };
    }

}
