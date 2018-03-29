/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.controls;

import java.util.function.Function;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 *
 * @author sean
 */
public class ActionButtonTableCell<S> extends TableCell<S, Button> {

    private final Button actionButton;
    private ObservableValue<Button> observable;

    public ActionButtonTableCell(String label, String style, Function< S, S> function) {
        this.getStyleClass().add("action-button-table-cell");

        this.actionButton = new Button(label);
        this.actionButton.setStyle(style);
        this.actionButton.setOnAction((ActionEvent e) -> {
            function.apply(getCurrentItem());
        });
        this.actionButton.setMaxWidth(Double.MAX_VALUE);

    }

    public S getCurrentItem() {
        return (S) getTableView().getItems().get(getIndex());
    }

    public static <S> Callback<TableColumn<S, Button>, TableCell<S, Button>> forTableColumn(String label, String style, Function< S, S> function) {
        return param -> new ActionButtonTableCell<>(label, style, function);
    }

    @Override
    public void updateItem(Button item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setGraphic(null);
        } else {
            final TableColumn<S, Button> column = getTableColumn();
            observable = column == null ? null : column.getCellObservableValue(getIndex());
            setGraphic(actionButton);
        }
    }
}