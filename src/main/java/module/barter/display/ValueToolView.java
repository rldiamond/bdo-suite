package module.barter.display;

import com.jfoenix.controls.JFXSpinner;
import common.task.ScheduledTaskRunner;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import module.barter.task.CrowCoinValueTask;
import module.display.ToolView;

import java.util.concurrent.TimeUnit;

public class ValueToolView extends ToolView {

    public ValueToolView() {
        super("Values");

        VBox container = new VBox(10);

        HBox crowCoinValueBox = new HBox(10);
        ImageView imageView = new ImageView(new Image("/module/barter/images/crowcoin.png"));
        imageView.setFitHeight(25);
        imageView.setFitWidth(25);
        Label crowCoinLabel = new Label("Crow Coin Value: ");
        crowCoinLabel.setStyle("-fx-font-size: 16px");
        Label crowCoinValueLabel = new Label();
        crowCoinValueLabel.setStyle("-fx-font-size: 16px");
        crowCoinValueBox.setAlignment(Pos.CENTER_LEFT);
        crowCoinValueBox.getChildren().addAll(imageView, crowCoinLabel, crowCoinValueLabel);
        BooleanProperty loadingCrowCoins = injectLoadingSpinner(crowCoinValueBox, crowCoinValueLabel);

        //schedule the crowcoin update task
        ScheduledTaskRunner.getInstance().scheduleTask(new CrowCoinValueTask(crowCoinValueLabel.textProperty(), loadingCrowCoins), TimeUnit.MINUTES.toMillis(5));

        container.getChildren().addAll(crowCoinValueBox);
        getCard().setDisplayedContent(container);
    }

    public BooleanProperty injectLoadingSpinner(Pane container, Node nodeToHide) {
        JFXSpinner loadingSpinner = new JFXSpinner();
        loadingSpinner.setRadius(5);
        BooleanProperty loadingProperty = new SimpleBooleanProperty(false);
        loadingSpinner.visibleProperty().bind(loadingProperty);
        loadingSpinner.managedProperty().bind(loadingProperty);

        container.getChildren().add(loadingSpinner);

        nodeToHide.visibleProperty().bind(loadingProperty.not());
        nodeToHide.managedProperty().bind(loadingProperty.not());

        return loadingProperty;
    }
}
