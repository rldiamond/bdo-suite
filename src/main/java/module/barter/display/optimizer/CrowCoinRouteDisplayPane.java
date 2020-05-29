package module.barter.display.optimizer;

import javafx.scene.layout.VBox;
import module.barter.model.PlannedRoute;

import java.util.List;

public class CrowCoinRouteDisplayPane extends RouteDisplayPane {

    public CrowCoinRouteDisplayPane(List<PlannedRoute> routes) {
        VBox crowCoinRoutes = new VBox(5);
        routes.stream().map(RouteDisplayPane::new).forEach(crowCoinRoutes.getChildren()::add);
        getChildren().setAll(crowCoinRoutes);
    }
}
