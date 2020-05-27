package common.jfx;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import common.jfx.components.EnumComboBox;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import java.util.List;

public class LayoutBuilder {

    public static JFXTextField createTextField(String label, String tooltip, Pane parent) {
        HBox container = new HBox(10);
        Tooltip.install(container, new Tooltip(tooltip));
        container.setAlignment(Pos.CENTER_LEFT);

        Label fieldLabel = new Label(label);
        JFXTextField textField = new JFXTextField();
        HBox.setHgrow(textField, Priority.ALWAYS);

        container.getChildren().setAll(fieldLabel, textField);
        parent.getChildren().add(container);
        return textField;
    }

    public static <T extends Enum> EnumComboBox<T> createEnumComboBox(String label, String tooltip, Class<T> enumType, Pane parent) {
        HBox container = new HBox(10);
        Tooltip.install(container, new Tooltip(tooltip));
        container.setAlignment(Pos.CENTER_LEFT);

        Label labelField = new Label(label);
        EnumComboBox<T> comboBox = new EnumComboBox<>(enumType);
        HBox.setHgrow(comboBox, Priority.ALWAYS);

        container.getChildren().setAll(labelField, comboBox);
        parent.getChildren().add(container);
        return comboBox;
    }

    public static <T> JFXComboBox<T> createComboBox(String label, String tooltip, List<T> items, Pane parent) {
        HBox container = new HBox(10);
        Tooltip.install(container, new Tooltip(tooltip));
        container.setAlignment(Pos.CENTER_LEFT);

        Label labelField = new Label(label);
        JFXComboBox<T> comboBox = new JFXComboBox<>(FXCollections.observableArrayList(items));
        HBox.setHgrow(comboBox, Priority.ALWAYS);

        container.getChildren().setAll(labelField, comboBox);
        parent.getChildren().add(container);
        return comboBox;

    }

    public static JFXToggleButton createToggleButton(String label, String tooltip, Pane parent) {
        HBox container = new HBox(10);
        Tooltip.install(container, new Tooltip(tooltip));
        container.setAlignment(Pos.CENTER_LEFT);

        Label labelField = new Label(label);
        JFXToggleButton toggle = new JFXToggleButton();

        container.getChildren().setAll(labelField, toggle);
        parent.getChildren().add(container);
        return toggle;
    }

}
