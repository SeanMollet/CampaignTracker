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
                            text.wrappingWidthProperty().bind(this.widthProperty().subtract(5));
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
