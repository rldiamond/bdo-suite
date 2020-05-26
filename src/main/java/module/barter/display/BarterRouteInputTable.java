package module.barter.display;

import common.jfx.components.EditableComboBoxTableCell;
import common.jfx.components.EditableTextFieldTableCell;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import module.barter.model.Barter;
import module.barter.model.BarterGood;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Editable table allowing for users to enter the routes possible from the in-game UI.
 */
public class BarterRouteInputTable extends TableView<Barter>  {

    /**
     * Construct.
     */
    public BarterRouteInputTable() {
        setPlaceholder(new Label("Add one or more barters"));

        // build the table
        setEditable(true);
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        getColumns().addAll(buildColumns());

    }

    private List<TableColumn<Barter, ?>> buildColumns() {
        TableColumn<Barter, String> exchangesCol = new TableColumn<>("Exchanges");
        exchangesCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getExchanges())));
        exchangesCol.setCellFactory(c -> new EditableTextFieldTableCell<>());
        exchangesCol.setOnEditCommit(edit -> {
            String newContent = edit.getNewValue().trim();
            try {
                edit.getRowValue().setExchanges(Integer.parseInt(newContent));
            } catch (Exception ex) {
                // invalid format
            }
            refresh();
        });

        TableColumn<Barter, String> parleyCol = new TableColumn<>("Parley");
        parleyCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getParley())));
        parleyCol.setCellFactory(c -> new EditableTextFieldTableCell<>());
        parleyCol.setOnEditCommit(edit -> {
            String newContent = edit.getNewValue().trim();
            try {
                edit.getRowValue().setParley(Integer.parseInt(newContent));
            } catch (Exception ex) {
                // invalid format
            }
            refresh();
        });

        TableColumn<Barter, String> acceptGoodCol = new TableColumn<>("Accept Good");
        acceptGoodCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAcceptGoodName()));
        final List<String> barterGoods = BarterGood.getBarterGoods().stream().map(BarterGood::getName).collect(Collectors.toList());
        Collections.sort(barterGoods);
        acceptGoodCol.setCellFactory(c -> new EditableComboBoxTableCell<>(barterGoods));
        acceptGoodCol.setOnEditCommit(edit -> {
            String newContent = edit.getNewValue().trim();
            edit.getRowValue().setAcceptGoodName(newContent);
            refresh();
        });

        TableColumn<Barter, String> acceptAmountCol = new TableColumn<>("Accept Amount");
        acceptAmountCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getAcceptAmount())));
        acceptAmountCol.setCellFactory(c -> new EditableTextFieldTableCell<>());
        acceptAmountCol.setOnEditCommit(edit -> {
            String newContent = edit.getNewValue().trim();
            try {
                edit.getRowValue().setAcceptAmount(Integer.parseInt(newContent));
            } catch (Exception ex) {
                // invalid format
            }
            refresh();
        });

        TableColumn<Barter, String> exchangeGoodCol = new TableColumn<>("Exchange Good");
        exchangeGoodCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getExchangeGoodName()));
        exchangeGoodCol.setCellFactory(c -> new EditableComboBoxTableCell<>(barterGoods));
        exchangeGoodCol.setOnEditCommit(edit -> {
            String newContent = edit.getNewValue().trim();
            edit.getRowValue().setExchangeGoodName(newContent);
            refresh();
        });

        TableColumn<Barter, String> exchangeAmountCol = new TableColumn<>("Exchange Amount");
        exchangeAmountCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getExchangeAmount())));
        exchangeAmountCol.setCellFactory(c -> new EditableTextFieldTableCell<>());
        exchangeAmountCol.setOnEditCommit(edit -> {
            String newContent = edit.getNewValue().trim();
            try {
                edit.getRowValue().setExchangeAmount(Integer.parseInt(newContent));
            } catch (Exception ex) {
                // invalid format
            }
            refresh();
        });

        return Arrays.asList(exchangesCol, parleyCol, acceptAmountCol, acceptGoodCol, exchangeAmountCol, exchangeGoodCol);
    }

}
