package module.barter.display;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import module.barter.model.BarterRoute;

import java.util.Arrays;
import java.util.List;

/**
 * Editable table allowing for users to enter the routes possible from the in-game UI.
 */
public class BarterRouteInputTable extends TableView<BarterRoute>  {

    /**
     * Construct.
     */
    public BarterRouteInputTable() {
        setPlaceholder(new Label("Add one or more barters"));

        // build the table
        setEditable(true);
        getColumns().addAll(buildColumns());

    }

    private List<TableColumn<BarterRoute, ?>> buildColumns() {
        TableColumn<BarterRoute, Integer> exchangesCol = new TableColumn<>("Exchanges");
        exchangesCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getExchanges()).asObject());

        TableColumn<BarterRoute, String> acceptGoodCol = new TableColumn<>("Accept Good");
        acceptGoodCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAcceptGoodName()));

        TableColumn<BarterRoute, Integer> acceptAmountCol = new TableColumn<>("Accept Amount");
        acceptAmountCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAcceptAmount()).asObject());

        TableColumn<BarterRoute, String> exchangeGoodCol = new TableColumn<>("Exchange Good");
        exchangeGoodCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getExchangeGoodName()));

        TableColumn<BarterRoute, Integer> exchangeAmountCol = new TableColumn<>("Exchange Amount");
        exchangeAmountCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getExchangeAmount()).asObject());

        return Arrays.asList(exchangesCol, acceptGoodCol, acceptAmountCol, exchangeGoodCol, exchangeAmountCol);
    }



}
