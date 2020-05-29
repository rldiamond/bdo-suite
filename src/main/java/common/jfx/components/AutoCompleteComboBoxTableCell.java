package common.jfx.components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;

import java.util.List;

public class AutoCompleteComboBoxTableCell <T extends Object> extends TableCell<T, String>  {

    private ComboBox<String> comboBox;
    private ObservableList<String> contents = FXCollections.observableArrayList();

    /**
     * Construct the cell with default settings and populate the ComboBox with the supplied contents.
     *
     * @param contents String contents to populate the ComboBox with.
     */
    public AutoCompleteComboBoxTableCell(List<String> contents) {
        this.contents.addAll(contents);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createComboBox();
            setText(null);
            setGraphic(comboBox);
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText(getString());
        setGraphic(null);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                setText(getString());
                setGraphic(comboBox);
            } else {
                setText(getString());
                setGraphic(null);
            }
        }
    }

    /**
     * Creates the ComboBox and applies listeners to it which allow for our custom editing.
     */
    private void createComboBox() {
        comboBox = new ComboBox<>();
        comboBox.setItems(contents);
        comboBox.valueProperty().set(getString());
        comboBox.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        comboBox.setOnAction(e -> commitEdit(comboBox.getSelectionModel().getSelectedItem()));
        comboBox.getSelectionModel().selectedItemProperty().addListener((obs, ov, selected) ->  {
            if (selected != null) {
                commitEdit(selected);
            }
        });
        AutoCompleteComboBox.install(comboBox);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void commitEdit(String newValue) {
        super.commitEdit(newValue);
    }

    /**
     * Convenience method to return a non-null String from the item.
     *
     * @return a non-null String from the item.
     */
    private String getString() {
        return getItem() == null ? "" : getItem();
    }
}
