package module.barter.display;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import module.barter.model.PlannedRoute;

public class RouteDisplayPane extends StackPane {

    public RouteDisplayPane(PlannedRoute route) {

        Label text = new Label(route.getDescription());
        text.setStyle("-fx-font-size: 16px");
        setAlignment(Pos.CENTER);
        getChildren().setAll(text);

    }

}
