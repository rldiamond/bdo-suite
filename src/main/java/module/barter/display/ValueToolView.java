package module.barter.display;

import com.jfoenix.controls.JFXSpinner;
import common.jfx.components.ItemImage;
import common.task.ScheduledTaskRunner;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import module.barter.task.CrowCoinValueTask;
import module.barter.task.MostValuableCoinItemTask;
import module.display.ToolView;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class ValueToolView extends ToolView {

    public ValueToolView() {
        super("Values");

        VBox container = new VBox(15);

        HBox crowCoinValueBox = new HBox(10);
        ItemImage crowCoinImage = new ItemImage(10);
        crowCoinImage.setSize(30);
        Label crowCoinLabel = new Label("Crow Coin Value: ");
        crowCoinLabel.setStyle("-fx-font-size: 16px");
        Label crowCoinValueLabel = new Label();
        crowCoinValueLabel.setStyle("-fx-font-size: 16px");
        crowCoinValueBox.setAlignment(Pos.CENTER_LEFT);
        crowCoinValueBox.getChildren().addAll(crowCoinImage, crowCoinLabel, crowCoinValueLabel);
        BooleanProperty loadingCrowCoins = injectLoadingSpinner(crowCoinValueBox, crowCoinValueLabel);

        //schedule the crowcoin update task
        ScheduledTaskRunner.getInstance().scheduleTask(new CrowCoinValueTask(crowCoinValueLabel.textProperty(), loadingCrowCoins), TimeUnit.MINUTES.toMillis(5));

        //most valuable crow coin item
        HBox valuableItem = new HBox(10);
        valuableItem.setAlignment(Pos.CENTER_LEFT);
        Label valuableItemLabel = new Label("Most Valuable Item: ");
        valuableItemLabel.setStyle("-fx-font-size: 16px");
        Label valuableCrowItem = new Label();
        valuableCrowItem.setStyle("-fx-font-size: 16px");
        ImageView valuableCrowCoinImage = new ImageView();
        valuableCrowCoinImage.setFitHeight(30);
        valuableCrowCoinImage.setFitWidth(30);
        valuableItem.getChildren().addAll(valuableItemLabel, valuableCrowCoinImage, valuableCrowItem);
        BooleanProperty loadingItem = injectLoadingSpinner(valuableItem, valuableCrowItem, valuableCrowCoinImage);

        //schedule task
        ScheduledTaskRunner.getInstance().scheduleTask(new MostValuableCoinItemTask(valuableCrowItem.textProperty(), valuableCrowCoinImage.imageProperty(), loadingItem), TimeUnit.MINUTES.toMillis(5));

        container.getChildren().addAll(crowCoinValueBox, valuableItem);
        getCard().setDisplayedContent(container);
    }

    public BooleanProperty injectLoadingSpinner(Pane container, Node... nodesToHide) {
        JFXSpinner loadingSpinner = new JFXSpinner();
        loadingSpinner.setRadius(5);
        BooleanProperty loadingProperty = new SimpleBooleanProperty(false);
        loadingSpinner.visibleProperty().bind(loadingProperty);
        loadingSpinner.managedProperty().bind(loadingProperty);

        container.getChildren().add(loadingSpinner);

        Arrays.stream(nodesToHide).forEach(nodeToHide -> {
            nodeToHide.visibleProperty().bind(loadingProperty.not());
            nodeToHide.managedProperty().bind(loadingProperty.not());
        });


        return loadingProperty;
    }
}
