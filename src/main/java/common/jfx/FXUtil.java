package common.jfx;

import javafx.application.Platform;

public class FXUtil {

    public static void runOnFXThread(Runnable run) {
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(run);
        } else {
            run.run();
        }
    }

}
