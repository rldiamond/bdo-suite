package module.barter.display.optimizer;

import common.jfx.components.AutoCompleteComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import module.barter.model.Barter;
import module.barter.model.BarterGood;
import module.barter.model.BarterLevelType;

public class BarterGoodAutoCompleteTableCell<T extends Object> extends TableCell<T, String> {


    private ComboBox<String> comboBox;

    /**
     * Construct the cell with default settings and populate the ComboBox with the supplied contents.
     *
     * @param contents String contents to populate the ComboBox with.
     */
    public BarterGoodAutoCompleteTableCell() {
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
        ObservableList<String> contents = FXCollections.observableArrayList();

        //auto helper..
        if (getTableRow().getItem() != null && getTableRow().getItem() instanceof Barter) {
            Barter barter = (Barter) getTableRow().getItem();

            if (barter.getAcceptGoodName() != null && !barter.getAcceptGoodName().equals("")) {
                if (BarterGood.getBarterGoodByName(barter.getAcceptGoodName()).isPresent()) {
                   BarterLevelType acceptGoodLevel = BarterGood.getBarterGoodByName(barter.getAcceptGoodName()).get().getLevel();
                    BarterLevelType filterLevel = null;
                    switch (acceptGoodLevel) {
                        case ZERO:
                            filterLevel = BarterLevelType.ONE;
                            break;
                        case ONE:
                            filterLevel = BarterLevelType.TWO;
                            break;
                        case TWO:
                            filterLevel = BarterLevelType.THREE;
                            break;
                        case THREE:
                            filterLevel = BarterLevelType.FOUR;
                            break;
                        case FOUR:
                            filterLevel = BarterLevelType.FIVE;
                            break;
                        case FIVE:
                            filterLevel = BarterLevelType.CROW_COIN;
                            break;
                    }

                    if (filterLevel != null) {
                        final BarterLevelType lambda = filterLevel;
                        contents.add(BarterGood.getBarterGoodByName("Crow Coin").get().getName());
                        BarterGood.getBarterGoods().stream().filter(barterGood -> barterGood.getLevel().equals(lambda)).map(BarterGood::getName).forEach(contents::add);
                    }
                }
            }

        }

        if (contents.isEmpty()) {
            BarterGood.getBarterGoods().stream().map(BarterGood::getName).forEach(contents::add);
        }


        comboBox = new ComboBox<>();
        comboBox.setItems(contents);
        comboBox.valueProperty().set(getString());
        comboBox.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        comboBox.setOnAction(e -> commitEdit(comboBox.getSelectionModel().getSelectedItem()));
        comboBox.getSelectionModel().selectedItemProperty().addListener((obs, ov, selected) -> commitEdit(selected));
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
