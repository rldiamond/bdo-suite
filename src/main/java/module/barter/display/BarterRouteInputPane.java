package module.barter.display;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.StackPane;
import module.barter.model.BarterRoute;

import java.util.Collections;
import java.util.List;

/**
 * Pane allowing the user to input all possible routes from the in-game UI.
 * TODO: Validate Data
 */
public class BarterRouteInputPane extends StackPane {

    private final ObservableList<BarterRoute> barterRoutes = FXCollections.observableArrayList();

    public BarterRouteInputPane() {
        // table with: Exchanges Available, Accepted Good, Accepted Amount, Exchanged Good, Exchanged Amount
        // Tier can be linked to the good itself.
        BarterRouteInputTable routeInputTable = new BarterRouteInputTable();
        routeInputTable.setItems(barterRoutes);
        this.getChildren().add(routeInputTable);
    }

    /**
     * Get all barter routes entered in the table.
     * @return The barter routes entered by the user.
     */
    public List<BarterRoute> getEnteredRoutes() {
        return barterRoutes;
    }

    /**
     * Resets all data entered.
     */
    public void reset() {
        barterRoutes.clear();
    }

    public void addRoutes(BarterRoute... barterRoutes) {
        Collections.addAll(this.barterRoutes, barterRoutes);
    }

}