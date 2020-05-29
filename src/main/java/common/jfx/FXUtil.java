package common.jfx;

import common.application.ApplicationSettings;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.util.Duration;

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

    public static String toHexCode(Color color )
    {
        return String.format( "#%02X%02X%02X",
                (int)( color.getRed() * 255 ),
                (int)( color.getGreen() * 255 ),
                (int)( color.getBlue() * 255 ) );
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

    /**
     * Install a FadeTransition on the provided Node. The {@link AnimationFadeType} determines whether the
     * transition should be a fade in, or fade out. This method provides a default duration of 200ms.
     * <p>
     * At the end of the fade, we set the Node's managed property to FALSE and the Node's visible property
     * to FALSE.
     *
     * @param node              the Node to install the fade onto.
     * @param animationFadeType whether the fade should fade in or fade out.
     * @return a complete FadeTransition ready to {@link FadeTransition#play()}.
     */
    public static FadeTransition installFade(Node node, AnimationFadeType animationFadeType) {
        return installFade(node, animationFadeType, Duration.millis(200));
    }

    /**
     * Install a FadeTransition on the provided Node. The {@link AnimationFadeType} determines whether the
     * transition should be a fade in, or fade out. This method provides the ability to set a custom Duration.
     * <p>
     * At the end of the fade, we set the Node's managed property to FALSE and the Node's visible property
     * to FALSE.
     *
     * @param node              the Node to install the fade onto.
     * @param animationFadeType whether the fade should fade in or fade out.
     * @param duration          the Duration to run the fade for.
     * @return a complete FadeTransition ready to {@link FadeTransition#play()}.
     */
    public static FadeTransition installFade(Node node, AnimationFadeType animationFadeType, Duration duration) {
        int from = 0;
        int to = 0;

        FadeTransition fade = new FadeTransition();

        switch (animationFadeType) {
            case IN:
                from = 0;
                to = 1;
                break;
            case OUT:
                from = 1;
                to = 0;
                fade.setOnFinished(e -> {
                    node.setManaged(false);
                    node.setVisible(false);
                });
                break;
            default:
                //..
        }

        fade.setDuration(duration);
        fade.setFromValue(from);
        fade.setToValue(to);
        fade.setNode(node);
        return fade;
    }

    public enum AnimationFadeType {
        IN, OUT
    }

    public enum AnimationDirection {
        RIGHT, LEFT, UP, DOWN
    }

    public static Timeline installBump(Node node, AnimationDirection animationDirection) {
        final int distance = 5;
        final double startX = node.getTranslateX();
        final double startY = node.getTranslateY();
        double endX = node.getTranslateX();
        double endY = node.getTranslateY();

        switch (animationDirection) {
            case RIGHT:
                endX += distance;
                break;
            case LEFT:
                endX -= distance;
                break;
            case UP:
                endY += distance;
                break;
            case DOWN:
                endY -= distance;
                break;
            default:
                //..
        }

        Timeline bump = new Timeline();
        bump.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(node.translateXProperty(), startX),
                        new KeyValue(node.translateYProperty(), startY)
                ),
                new KeyFrame(new Duration(150),
                        new KeyValue(node.translateXProperty(), endX),
                        new KeyValue(node.translateYProperty(), endY)
                )
        );
        return bump;
    }

    public static Timeline installBumpBack(Node node, AnimationDirection originatingDirection) {
        final int distance = 5;
        double startX = node.getTranslateX();
        double startY = node.getTranslateY();
        double endX = node.getTranslateX();
        double endY = node.getTranslateY();

        switch (originatingDirection) {
            case RIGHT:
                startX += distance;
                break;
            case LEFT:
                startX -= distance;
                break;
            case DOWN:
                startY += distance;
                break;
            case UP:
                startY -= distance;
                break;
            default:
                //..
        }

        Timeline bump = new Timeline();
        bump.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(node.translateXProperty(), startX),
                        new KeyValue(node.translateYProperty(), startY)
                ),
                new KeyFrame(new Duration(150),
                        new KeyValue(node.translateXProperty(), endX),
                        new KeyValue(node.translateYProperty(), endY)
                )
        );
        return bump;

    }

}
