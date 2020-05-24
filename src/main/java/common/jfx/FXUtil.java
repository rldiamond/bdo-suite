package common.jfx;

import common.application.ApplicationSettings;
import javafx.application.Platform;
import javafx.scene.Scene;

public class FXUtil {

    /**
     * Runs the supplied runnable on the FX thread.
     *
     * @param run The runnable to run.
     */
    public static void runOnFXThread(Runnable run) {
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(run);
        } else {
            run.run();
        }
    }

    /**
     * Convenience method to set the currently active theme on a scene.
     *
     * @param scene themed scene.
     */
    public static void setThemeOnScene(Scene scene) {
        if (scene != null) {
            runOnFXThread(() -> scene.getStylesheets().setAll(ApplicationSettings.THEME.getCss()));
        }
    }

}
