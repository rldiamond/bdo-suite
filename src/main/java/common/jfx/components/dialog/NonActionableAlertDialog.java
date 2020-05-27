/*
 * This file is part of Yari Editor.
 *
 *  Yari Editor is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *  Yari Editor is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with Yari Editor. If not, see <http://www.gnu.org/licenses/>.
 */

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
import javafx.geometry.Pos;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.stage.Window;

public class NonActionableAlertDialog extends AlertDialog {

    /**
     * Creates an AlertDialog of the supplied type with only a DISMISS button.
     *
     * @param alertDialogType the type of Dialog to create.
     */
    public NonActionableAlertDialog(AlertDialogType alertDialogType) {
        super(alertDialogType);
        initDismissButton();
    }

    /**
     * Creates an AlertDialog of the supplied type with only a DISMISS button. Sets the owning window to the supplied
     * value.
     *
     * @param alertDialogType the type of Dialog to create.
     * @param modalOwner the owning Window.
     */
    public NonActionableAlertDialog(AlertDialogType alertDialogType, Window modalOwner) {
        super(alertDialogType, modalOwner);
        initDismissButton();
    }

    private void initDismissButton() {
        JFXButton dismissButton = new JFXButton("DISMISS");
        Tooltip.install(dismissButton, new Tooltip("Dismiss this notification"));
        dismissButton.setButtonType(JFXButton.ButtonType.RAISED);
        dismissButton.getStyleClass().add("button-flat-brightred");
        dismissButton.setOnMouseClicked(me -> {
            if (me.getButton() == MouseButton.PRIMARY) {
                getStage().close();
            }
        });
        HBox buttonContainer = new HBox(dismissButton);
        buttonContainer.setAlignment(Pos.CENTER_RIGHT);
        getFooter().getChildren().setAll(buttonContainer);
    }


}
