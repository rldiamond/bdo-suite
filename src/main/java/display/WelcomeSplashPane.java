package display;

import common.application.ApplicationSettings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * A welcome splash screen with a logo, some essential application information, and loading status.
 */
public class WelcomeSplashPane extends StackPane {

    private SimpleStringProperty textProperty = new SimpleStringProperty();

    public WelcomeSplashPane() {
        ImageView logo = new ImageView();
        //logo.setImage(ApplicationSettings.LOGO);
        Label statusTextLabel = new Label();
        statusTextLabel.textProperty().bind(textProperty);

        Label titleLabel = new Label("BDO Suite");
        Label versionLabel = new Label(ApplicationSettings.VERSION);

        VBox container = new VBox(10);
        container.getChildren().addAll(logo, titleLabel, versionLabel, statusTextLabel);

    }

    public StringProperty textProperty() {
        return textProperty;
    }

}
