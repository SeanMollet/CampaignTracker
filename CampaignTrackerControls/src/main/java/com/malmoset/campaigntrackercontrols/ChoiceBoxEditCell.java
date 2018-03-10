/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigntrackercontrols;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Cell;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

/**
 *
 * @author sean
 */
public class ChoiceBoxEditCell<S, T> extends TableCell<S, T> {

    private ChoiceBox choiceField;
    private StringConverter converter;
    private ObservableList<String> list;
    private int trimStringLength = -1;

    public ChoiceBoxEditCell() {
        this(new DefaultStringConverter(), FXCollections.observableArrayList(new ArrayList<String>()), -1);
    }

    public ChoiceBoxEditCell(ObservableList<String> list) {
        this(new DefaultStringConverter(), list, -1);
    }

    public ChoiceBoxEditCell(StringConverter converter, ObservableList<String> list, int TrimLength) {
        this.converter = converter;
        this.list = list;
        this.trimStringLength = TrimLength;
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            choiceField = createChoiceField(this, list, converter);
            setText(null);
            setGraphic(choiceField);
            choiceField.requestFocus();
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
                if (choiceField != null) {
                    choiceField.setValue(converter.toString(getItem()));
                }
                setText(null);
                setGraphic(choiceField);
            } else {
                String text = getString();
                if (trimStringLength > 0) {
                    text = text.substring(0, trimStringLength);
                }
                setText(text);
                setGraphic(null);
            }
        }
    }

    private <T> ChoiceBox createChoiceField(final Cell<T> cell, final ObservableList<String> choices, final StringConverter<T> converter) {
        final ChoiceBox<String> newchoiceField = new ChoiceBox<>();
        newchoiceField.setItems(choices);
        newchoiceField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        newchoiceField.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cell.commitEdit(converter.fromString(newValue));
            }
        });
//        newchoiceField.focusedProperty().addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> arg0,
//                    Boolean oldValue, Boolean newValue) {
//                if (!newValue) {
//                    if (newchoiceField.getValue() != null) {
//                        if (newchoiceField.getValue() instanceof String) {
//                            cell.commitEdit(converter.fromString(newchoiceField.getValue()));
//                        }
//                    }
//                }
//            }
//        });
        newchoiceField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (null != t.getCode()) {
                    switch (t.getCode()) {
                        case ENTER:
                            cell.commitEdit(converter.fromString(newchoiceField.getValue()));
                            break;
                        case ESCAPE:
                            cell.cancelEdit();
                            break;
                        case TAB:
                            if (newchoiceField.getValue() != null) {
                                cell.commitEdit(converter.fromString(newchoiceField.getValue()));
                            } else {
                                cell.cancelEdit();
                            }
                            TableColumn nextColumn = getNextColumn(!t.isShiftDown());
                            if (nextColumn != null) {
                                getTableView().edit(getTableRow().getIndex(), nextColumn);
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        });
        return newchoiceField;
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
        if (getItem() != null) {
            try {
                return converter.toString(getItem());
            } catch (Exception E) {
                return "";
            }
        }
        return "";
    }

}
