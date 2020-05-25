package common.utilities;

import com.jfoenix.controls.JFXSnackbar;
import display.main.RootDisplayPane;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

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

}
