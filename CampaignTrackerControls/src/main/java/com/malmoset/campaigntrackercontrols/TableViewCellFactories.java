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
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 *
 * @author sean
 */
public class TableViewCellFactories {

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

    public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> multiLineCellFactory() {
        return multiLineCellFactory(null);
    }

    public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> multiLineCellFactory(EventHandler<MouseEvent> event) {
        return new Callback<TableColumn<S, T>, TableCell<S, T>>() {
            @Override
            public TableCell call(TableColumn param) {
                final TableCell cell = new TableCell() {
                    private Text text;

                    @Override
                    public void updateItem(Object item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!isEmpty() && item != null) {
                            text = new Text(item.toString());
                            text.wrappingWidthProperty().bind(this.widthProperty());
                            //text.setWrappingWidth(200);
                            setGraphic(text);
                        }
                    }
                };

                if (event != null) {
                    cell.addEventFilter(MouseEvent.MOUSE_CLICKED, event);
                }
                return cell;
            }
        };
    }

}
