/*
 * This file is part of Yari Editor.
 *
 *  Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with Yari Editor. If not, see <http://www.gnu.org/licenses/>.
 */

package common.jfx.components.dialog;

import com.jfoenix.controls.JFXButton;
import common.logging.AppLogger;
import javafx.geometry.Pos;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.stage.Window;

public class ActionableAlertDialog extends AlertDialog {

    private static final AppLogger LOGGER = AppLogger.getLogger();
    private JFXButton actionButton;
    private Runnable action;

    /**
     * Creates an AlertDialog of the supplied type with a DISMISS button as well as an action button.
     *
     * @param alertDialogType the type of Dialog to create.
     */
    public ActionableAlertDialog(AlertDialogType alertDialogType) {
        super(alertDialogType);
        initButtons();
    }

    /**
     * Creates an AlertDialog of the supplied type with a DISMISS button as well as an action button. Sets the owning
     * window to the supplied value.
     *
     * @param alertDialogType the type of Dialog to create.
     */
    public ActionableAlertDialog(AlertDialogType alertDialogType, Window modalOwner) {
        super(alertDialogType, modalOwner);
        initButtons();
    }

    private void initButtons() {

        actionButton = new JFXButton("OKAY");
        Tooltip.install(actionButton, new Tooltip("Confirm and continue"));
        actionButton.setButtonType(JFXButton.ButtonType.RAISED);
        actionButton.getStyleClass().add("button-flat-white");
        actionButton.setOnMouseClicked(me -> {
            if (me.getButton() == MouseButton.PRIMARY) {
                if (action != null) {
                    action.run();
                    getStage().close();
                } else {
                    LOGGER.warn("An action has not been set!");
                }
            }
        });

        JFXButton dismissButton = new JFXButton("DISMISS");
        Tooltip.install(dismissButton, new Tooltip("Dismiss this notification"));
        dismissButton.setButtonType(JFXButton.ButtonType.RAISED);
        dismissButton.getStyleClass().add("button-flat-brightred");
        dismissButton.setOnMouseClicked(me -> {
            if (me.getButton() == MouseButton.PRIMARY) {
                getStage().close();
            }
        });
        HBox buttonContainer = new HBox(actionButton, dismissButton);
        buttonContainer.setSpacing(10);
        buttonContainer.setAlignment(Pos.CENTER_RIGHT);
        getFooter().getChildren().setAll(buttonContainer);
    }

    /**
     * Set the action to run when the action button is selected by the user.
     *
     * @param action the action to run when the action button is selected by the user.
     */
    public void setAction(Runnable action) {
        this.action = action;
    }

    /**
     * Set the text on the action button. By default, this value is "OKAY".
     *
     * @param text the text on the action button.
     */
    public void setActionButtonText(String text) {
        actionButton.setText(text);
    }
}
