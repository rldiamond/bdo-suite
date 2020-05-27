package common.jfx.components;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;

public class EnumComboBox<T extends Enum> extends JFXComboBox<T> {

    public EnumComboBox(Class<T> enumType) {
        super(FXCollections.observableArrayList(enumType.getEnumConstants()));
    }

}
