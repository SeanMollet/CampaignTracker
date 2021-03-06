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

import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Cell;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

/**
 *
 * @author sean
 */
//Concept and some layout taken from:
//https://stackoverflow.com/questions/22211860/javafx-tab-through-editable-cells
//https://gist.github.com/abhinayagarwal/9383881
//Thanks!
public class TextEditCell<S, T> extends TableCell<S, T> {

    private boolean isMultiline = false;
    private Text text;

    public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> editCellFactory() {
        return editCellFactory(new DefaultStringConverter());
    }

    public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> editCellFactory(StringConverter converter) {
        return editCellFactory(converter, false);
    }

    public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> editCellFactory(StringConverter converter, boolean multiline) {
        return new Callback<TableColumn<S, T>, TableCell<S, T>>() {
            public TableCell call(TableColumn p) {
                return new TextEditCell(converter, multiline);
            }
        };
    }

    private TextField textField;
    private StringConverter converter;

    public TextEditCell(StringConverter converter) {
        this(converter, false);
    }

    public TextEditCell(StringConverter converter, boolean multiline) {
        this.isMultiline = multiline;
        this.converter = converter;
    }

    public TextEditCell() {
        this.converter = new DefaultStringConverter();
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            textField = createTextField(this, converter);
            setText(null);
            setGraphic(textField);
            textField.selectAll();
            textField.requestFocus();
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText(converter.toString(getItem()));
        setGraphic(null);
    }

    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setText(null);
                setGraphic(textField);
            } else {
                if (isMultiline) {
                    if (!isEmpty() && item != null) {
                        text = new Text(item.toString());
                        text.wrappingWidthProperty().bind(this.widthProperty().subtract(5));
                        setGraphic(text);
                    }
                } else {
                    setText(getString());
                    setGraphic(null);
                }
            }
        }
    }

    private <T> TextField createTextField(final Cell<T> cell, final StringConverter<T> converter) {
        final TextField textField = new TextField(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0,
                    Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    cell.commitEdit(converter.fromString(textField.getText()));
                }
            }
        });
        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ENTER) {
                    cell.commitEdit(converter.fromString(textField.getText()));
                } else if (t.getCode() == KeyCode.ESCAPE) {
                    cell.cancelEdit();
                } else if (t.getCode() == KeyCode.TAB) {
                    cell.commitEdit(converter.fromString(textField.getText()));
                    TableColumn nextColumn = getNextColumn(!t.isShiftDown());
                    if (nextColumn != null) {
                        getTableView().edit(getTableRow().getIndex(), nextColumn);
                    }
                }
            }
        });
        return textField;
    }

    private TableColumn<S, ?> getNextColumn(boolean forward) {
        List<TableColumn<S, ?>> columns = new ArrayList<>();
        for (TableColumn<S, ?> column : getTableView().getColumns()) {
            columns.addAll(getLeaves(column));
        }
        // There is no other column that supports editing.
        if (columns.size() < 2) {
            return null;
        }
        int currentIndex = columns.indexOf(getTableColumn());
        int nextIndex = currentIndex;
        if (forward) {
            nextIndex++;
            if (nextIndex > columns.size() - 1) {
                nextIndex = 0;
            }
        } else {
            nextIndex--;
            if (nextIndex < 0) {
                nextIndex = columns.size() - 1;
            }
        }
        return columns.get(nextIndex);
    }

    private List<TableColumn<S, ?>> getLeaves(
            TableColumn<S, ?> root) {
        List<TableColumn<S, ?>> columns = new ArrayList<>();
        if (root.getColumns().isEmpty()) {
            // We only want the leaves that are editable.
            if (root.isEditable()) {
                columns.add(root);
            }
            return columns;
        } else {
            for (TableColumn<S, ?> column : root.getColumns()) {
                columns.addAll(getLeaves(column));
            }
            return columns;
        }
    }

    private String getString() {
        return getItem() == null ? "" : converter.toString(getItem());
    }
}
