package module.barter.display;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import common.jfx.LayoutBuilder;
import common.jfx.components.ItemImage;
import common.task.BackgroundTaskRunner;
import common.task.ScheduledTaskRunner;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import module.barter.task.CrowCoinValueTask;
import module.barter.task.MostValuableCoinItemTask;
import module.barter.task.TotalValueTask;
import module.display.ToolView;

import java.util.Arrays;

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
        ScheduledTaskRunner.getInstance().scheduleTask(new CrowCoinValueTask(crowCoinValueLabel.textProperty(), loadingCrowCoins),5);

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
        container.getChildren().addAll(crowCoinValueBox, valuableItem);

        //schedule task
        ScheduledTaskRunner.getInstance().scheduleTask(new MostValuableCoinItemTask(valuableCrowItem.textProperty(), valuableCrowCoinImage.imageProperty(), loadingItem), 5);

        //total value
        JFXTextField playerCoinsField = LayoutBuilder.createTextField("Crow Coins: ","Enter the number of crow coins you have to calculate total.", container);

        HBox totalValueContainer = new HBox(10);
        totalValueContainer.setAlignment(Pos.CENTER_LEFT);
        Label totalValueLabel = new Label("Total Value: ");
        totalValueContainer.setStyle("-fx-font-size: 16px");
        Label totalValue = new Label();
        JFXButton refresh = new JFXButton("RECALCULATE");
        refresh.setStyle("button-flat-gray");
        totalValueContainer.getChildren().setAll(totalValueLabel, totalValue, refresh);
        container.getChildren().add(totalValueContainer);
        refresh.setOnMouseClicked(me -> {
            if (me.getButton().equals(MouseButton.PRIMARY)) {
                BackgroundTaskRunner.getInstance().runTask(new TotalValueTask(totalValue.textProperty(), playerCoinsField));
            }
        });

        ScheduledTaskRunner.getInstance().scheduleTask(new TotalValueTask(totalValue.textProperty(), playerCoinsField), 5);



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
