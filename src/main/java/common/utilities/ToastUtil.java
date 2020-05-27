package common.utilities;

import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import display.main.RootDisplayPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class ToastUtil {

    /**
     * Private constructor to hide the implicit public constructor.
     */
    private ToastUtil() {

    }

    /**
     * Send a standard toast which automatically dismisses itself after a short period of time.
     *
     * @param message the message to display in the toast.
     */
    public static void sendToast(String message) {
        StackPane wrapper = new StackPane();
        wrapper.setMinHeight(50);
        Label label = new Label(message);
        label.setStyle("-fx-text-fill: ghostwhite; -fx-font-size: 16px");
        wrapper.getChildren().setAll(label);
        RootDisplayPane.getInstance().getToastBar().enqueue(new JFXSnackbar.SnackbarEvent(wrapper));
    }

    public static void sendErrorToast(String message) {
        Pane errorIcon = new Pane();
        errorIcon.setId("errorIcon");
        errorIcon.setMaxSize(20,20);
        errorIcon.setPrefSize(20,20);
        StackPane iconWrapper = new StackPane(errorIcon);
        iconWrapper.setAlignment(Pos.CENTER);
        iconWrapper.setPadding(new Insets(5,5,5,5));

        Label label = new Label(message);
        StackPane labelWrapper = new StackPane(label);
        labelWrapper.setMinHeight(50);
        labelWrapper.setMinWidth(250);
        label.setStyle("-fx-text-fill: ghostwhite; -fx-font-size: 16px");
        JFXSnackbarLayout layout = new JFXSnackbarLayout(message, "DISMISS", a -> RootDisplayPane.getInstance().getToastBar().close());
        layout.setLeft(iconWrapper);
        layout.setCenter(labelWrapper);
        RootDisplayPane.getInstance().getToastBar().enqueue(new JFXSnackbar.SnackbarEvent(layout,
                Duration.INDEFINITE, null));
    }

}
