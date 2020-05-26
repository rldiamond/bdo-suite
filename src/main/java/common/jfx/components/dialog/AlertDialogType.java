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

public enum AlertDialogType {

    CONFIRMATION("confirmationIcon"),
    ERROR("errorIcon"),
    INFORMATION("informationIcon"),
    WARNING("warningIcon");

    private final String iconId;

    AlertDialogType(String iconId) {
        this.iconId = iconId;
    }

    /**
     * Return the CSS ID of the icon to display in the AlertDialog.
     *
     * @return the CSS ID of the icon to display in the AlertDialog.
     */
    public String getIconId() {
        return iconId;
    }
}
