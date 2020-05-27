package module.barter.display.optimizer;

import common.jfx.components.ItemImage;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import module.barter.model.PlannedRoute;

public class RouteDisplayPane extends StackPane {

    public RouteDisplayPane(PlannedRoute route) {

        HBox wrapper = new HBox(5);
        wrapper.setAlignment(Pos.CENTER);

        Label text = new Label(route.getDescription());
        text.setStyle("-fx-font-size: 16px");
        setAlignment(Pos.CENTER);
        getChildren().setAll(wrapper);

        wrapper.getChildren().addAll(new ItemImage(route.getTurnInGood().getItemId()), text, new ItemImage(route.getReceivedGood().getItemId()));

    }

}
