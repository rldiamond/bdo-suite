package module.barter.display.optimizer;

import common.jfx.components.AutoCompleteComboBoxTableCell;
import common.jfx.components.EditableTextFieldTableCell;
import common.logging.AppLogger;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import module.barter.model.Barter;
import module.barter.model.BarterGood;
import module.barter.model.BarterLevelType;
import module.barter.model.BarterModuleEvent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Editable table allowing for users to enter the routes possible from the in-game UI.
 */
public class BarterRouteInputTable extends TableView<Barter>  {

    private static final AppLogger logger = AppLogger.getLogger();

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
        acceptGoodCol.setCellFactory(c -> new AutoCompleteComboBoxTableCell<>(barterGoods));
        acceptGoodCol.setOnEditCommit(edit -> {
            try {
                if (edit.getNewValue() == null) {
                    return;
                }
                String newContent = edit.getNewValue().trim();
                edit.getRowValue().setAcceptGoodName(newContent);

                BarterGood.getBarterGoodByName(newContent).ifPresent(barterGood -> {
                    if (barterGood.getBarterLevel().getLevel().equals(BarterLevelType.FIVE)) {
                        if (edit.getRowValue().getExchangeGoodName() == null || "".equalsIgnoreCase(edit.getRowValue().getExchangeGoodName())) {
                            edit.getRowValue().setExchangeGoodName("Crow Coin");
                        }
                    }
                });

                refresh();
            } catch (Exception ex) {
                logger.error("Error setting new value on edit commit for accept good.", ex);
            }
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
        exchangeGoodCol.setCellFactory(c -> new BarterGoodAutoCompleteTableCell<>());
        exchangeGoodCol.setOnEditCommit(edit -> {
            try {
                if (edit.getNewValue() != null) {
                    String newContent = edit.getNewValue().trim();
                    edit.getRowValue().setExchangeGoodName(newContent);
                    refresh();
                }
            }
            catch (Exception ex) {
                logger.debug("Error setting new value on edit commit for exchange good.", ex);
            }
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

    @Override
    public void refresh() {
        super.refresh();
        fireEvent(new BarterModuleEvent(BarterModuleEvent.ModuleEventType.PLAYERSAVE));
    }
}
