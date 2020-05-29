package module.barter.display.optimizer;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.StackPane;
import module.barter.model.Barter;

import java.util.Collections;
import java.util.List;

/**
 * Pane allowing the user to input all possible routes from the in-game UI.
 * TODO: Validate Data
 */
public class BarterRouteInputPane extends StackPane {

    private final ObservableList<Barter> barters = FXCollections.observableArrayList();
    private final BarterRouteInputTable routeInputTable;

    public BarterRouteInputPane() {
        // table with: Exchanges Available, Accepted Good, Accepted Amount, Exchanged Good, Exchanged Amount
        // Tier can be linked to the good itself.
        routeInputTable = new BarterRouteInputTable();
        routeInputTable.setItems(barters);
        this.getChildren().add(routeInputTable);
    }

    /**
     * Get all barter routes entered in the table.
     * @return The barter routes entered by the user.
     */
    public List<Barter> getEnteredRoutes() {
        return barters;
    }

    /**
     * Resets all data entered.
     */
    public void reset() {
        barters.clear();
    }

    public void addRoutes(Barter... barters) {
        Collections.addAll(this.barters, barters);
    }

    public Barter getSelectedRoute() {
        return routeInputTable.getSelectionModel().getSelectedItem();
    }

    public ReadOnlyObjectProperty<Barter> selectedItemProperty() {
        return routeInputTable.getSelectionModel().selectedItemProperty();
    }

    public ObservableList<Barter> getBarters() {
        return barters;
    }

    public void bindDisableProperty(BooleanProperty property) {
        routeInputTable.disableProperty().unbind();
        routeInputTable.disableProperty().bind(property);
    }

    public void setItems(List<Barter> barters) {
        barters.forEach(this::addRoutes);
    }
}
