/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malmoset.campaigntracker.PC;

import com.malmoset.campaigndata.Player;
import com.malmoset.campaigntracker.AppData;
import com.malmoset.campaigntracker.MainApp;
import com.malmoset.campaigntrackercontrols.ActionButtonTableCell;
import com.malmoset.campaigntrackercontrols.IntegerStringConverter;
import com.malmoset.campaigntrackercontrols.SpinnerUtils;
import com.malmoset.campaigntrackercontrols.Styles;
import com.malmoset.controls.BaseForm;
import com.malmoset.dice.Dice;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

/**
 * FXML Controller class
 *
 * @author sean
 */
public class PlayerEditorController extends BaseForm implements Initializable {

    @FXML
    private AnchorPane Window;
    @FXML
    private TableView<Player> PlayerTable;
    @FXML
    private Spinner<Integer> DmgSpinner;
    private SortedList<Player> sortedData;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DataBind();
        DmgSpinner.focusedProperty().addListener((s, ov, nv) -> {
            if (!nv) {
                SpinnerUtils.commitEditorText(DmgSpinner);
            }
        });
    }

    public void DataBind() {

        SpinnerValueFactory<Integer> valfact = new SpinnerValueFactory.IntegerSpinnerValueFactory(-500, 500, 0);
        DmgSpinner.setValueFactory(valfact);
        DmgSpinner.setEditable(true);

        PlayerTable.setEditable(false);

        //Generic editable factory
        Callback<TableColumn<Player, String>, TableCell<Player, String>> strcellFactory = new Callback<TableColumn<Player, String>, TableCell<Player, String>>() {
            public TableCell call(TableColumn p) {
                return new EditingCell();
            }
        };
        Callback<TableColumn<Player, Integer>, TableCell<Player, Integer>> intcellFactory = new Callback<TableColumn<Player, Integer>, TableCell<Player, Integer>>() {
            public TableCell call(TableColumn p) {
                return new EditingCell(new IntegerStringConverter());
            }
        };

        //Create the columns
        TableColumn<Player, String> col1 = new TableColumn<>("Name");
        TableColumn<Player, String> col2 = new TableColumn<>("Race");
        TableColumn<Player, String> col3 = new TableColumn<>("Class");
        TableColumn<Player, Integer> col4 = new TableColumn<>("AC");
        TableColumn<Player, Integer> col5 = new TableColumn<>("HP");
        TableColumn<Player, Integer> col6 = new TableColumn<>("Max HP");
        TableColumn<Player, Integer> col7 = new TableColumn<>("Dex");
        TableColumn<Player, Integer> col8 = new TableColumn<>("Roll");
        TableColumn<Player, Integer> col9 = new TableColumn<>("Adv");
        TableColumn<Player, Boolean> col10 = new TableColumn<>("Dead");
        TableColumn<Player, Boolean> col11 = new TableColumn<>("Stable");
        TableColumn<Player, String> col12 = new TableColumn<>("Appearance");
        TableColumn<Player, Integer> col13 = new TableColumn<>("HP");

        col1.setCellValueFactory(new PropertyValueFactory<>("Name"));
        col2.setCellValueFactory(new PropertyValueFactory<>("Race"));
        col3.setCellValueFactory(cellData -> cellData.getValue().character_classProperty());
        col4.setCellValueFactory(cellData -> cellData.getValue().aCProperty().asObject());
        col5.setCellValueFactory(cellData -> cellData.getValue().currentHPProperty().asObject());
        col6.setCellValueFactory(cellData -> cellData.getValue().maxHPProperty().asObject());
        col7.setCellValueFactory(cellData -> cellData.getValue().initiativeProperty().asObject());
        col8.setCellValueFactory(cellData -> cellData.getValue().rollProperty().asObject());
        col9.setCellValueFactory(cellData -> cellData.getValue().advProperty().asObject());
        col10.setCellValueFactory(cellData -> cellData.getValue().deadProperty());
        col11.setCellValueFactory(cellData -> cellData.getValue().stableProperty());
        col12.setCellValueFactory(cellData -> cellData.getValue().appearanceProperty());
        col13.setCellValueFactory(cellData -> cellData.getValue().hpToChangeProperty().asObject());

        col1.setCellFactory(strcellFactory);
        col2.setCellFactory(strcellFactory);
        col3.setCellFactory(strcellFactory);

        col4.setCellFactory(intcellFactory);
        col5.setCellFactory(intcellFactory);
        col6.setCellFactory(intcellFactory);
        col7.setCellFactory(intcellFactory);
        col8.setCellFactory(intcellFactory);
        col9.setCellFactory(intcellFactory);
        col13.setCellFactory(intcellFactory);

        col10.setCellFactory(tc -> new CheckBoxTableCell<>());
        col11.setCellFactory(tc -> new CheckBoxTableCell<>());

        TableColumn<Player, Button> col14 = new TableColumn<>("HP");
        col14.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        col14.setCellFactory(ActionButtonTableCell.<Player>forTableColumn("Dmg", Styles.getSmall(), (Player player) -> {
            int newHP = player.getCurrentHP() - player.getHpToChange();
            player.setCurrentHP(newHP);
            return player;
        }));
        TableColumn<Player, Button> col15 = new TableColumn<>("HP");
        col15.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        col15.setCellFactory(ActionButtonTableCell.<Player>forTableColumn("Heal", Styles.getSmall(), (Player player) -> {
            int newhp = player.getCurrentHP() + player.getHpToChange();
            if (newhp > player.getMaxHP()) {
                newhp = player.getMaxHP();
            }
            //If they have bonus HP or something and are already higher than max
            //This prevents us messing that up
            if (newhp > player.getCurrentHP()) {
                player.setCurrentHP(newhp);
            }
            return player;
        }));

        PlayerTable.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7, col8, col9, col10, col11, col12, col13, col14, col15);

        // 4. Bind the SortedList comparator to the TableView comparator.
        BindSortedList();

        PlayerTable.comparatorProperty().addListener((e) -> {
            //Switch back to sorting based on user selection
            sortedData.comparatorProperty().setValue(PlayerTable.comparatorProperty().get());
        });

        PlayerTable.setEditable(true);
    }

    private void BindSortedList() {
        AppData data = MainApp.getAppData();
        ObservableList<Player> players = data.getDb().getPlayers();
        sortedData = new SortedList(players);
        PlayerTable.setItems(sortedData);
    }

    @FXML
    private void RollInitClick(ActionEvent event) {
        ObservableList<Player> players = MainApp.getAppData().getDb().getPlayers();
        for (Player player : players) {
            int roll = Dice.roll(20, Dice.RollTypes.values()[player.getAdv()]) + player.getInitiative();
            player.setRoll(roll);
        }
        //Switch to sorting by roll and initiative modifier
        BindSortedList();
        sortedData.comparatorProperty().setValue(Player.CompareInitiative());
    }

    @FXML

    private void DmgAllClick(ActionEvent event) {
        ObservableList<Player> players = MainApp.getAppData().getDb().getPlayers();
        for (Player player : players) {
            int newhp = player.getCurrentHP() - DmgSpinner.getValue();
            player.setCurrentHP(newhp);
        }
    }

    @FXML
    private void HealAllClick(ActionEvent event) {
        ObservableList<Player> players = MainApp.getAppData().getDb().getPlayers();
        for (Player player : players) {
            int newhp = player.getCurrentHP() + DmgSpinner.getValue();
            if (newhp > player.getMaxHP()) {
                newhp = player.getMaxHP();
            }
            //If they have bonus HP or something and are already higher than max
            //This prevents us messing that up
            if (newhp > player.getCurrentHP()) {
                player.setCurrentHP(newhp);
            }
        }
    }

    @FXML
    private void AddClick(ActionEvent event) {
        MainApp.getAppData().getDb().getPlayers().add(new Player());
    }

    //Concept and some layout taken from:
    //https://stackoverflow.com/questions/22211860/javafx-tab-through-editable-cells
    //https://gist.github.com/abhinayagarwal/9383881
    //Thanks!
    class EditingCell<S, T> extends TableCell<S, T> {

        private TextField textField;
        private StringConverter converter;

        public EditingCell(StringConverter converter) {
            this.converter = converter;
        }

        public EditingCell() {
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

            setText((String) getItem());
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
                    setText(getString());
                    setGraphic(null);
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
            return getItem() == null ? "" : getItem().toString();
        }
    }

}
