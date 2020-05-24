package display.main;

import com.jfoenix.transitions.JFXFillTransition;
import common.application.ModuleRegistration;
import common.jfx.FXUtil;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import module.common.BdoModule;

//TODO: Style, animations, tooltip, fire event to trigger change of modules.
public class ModuleMenuButton extends StackPane {

    private StringProperty titleProperty = new SimpleStringProperty("");
    private BooleanProperty selectedProperty = new SimpleBooleanProperty(false);
    private Runnable runnable;

    public ModuleMenuButton(ModuleRegistration module) {
        titleProperty.setValue(module.getTitle());
        setPadding(new Insets(0, 0, 0, 10));
        setId("mdTab");
        super.setPrefSize(USE_COMPUTED_SIZE, 50);
        setAlignment(Pos.CENTER_LEFT);

        Tooltip.install(this, new Tooltip(module.getDescription()));

        // Button label
        Label label = new Label();
        label.textProperty().bind(titleProperty);

        // Icon
        Pane icon = new Pane();
        icon.getStyleClass().add("mdTab-icon");
        icon.setId(module.getIconId());
        icon.setPrefSize(12, 12);
        icon.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);

        HBox wrapper = new HBox(5);
        wrapper.setAlignment(Pos.CENTER_LEFT);
        wrapper.getChildren().addAll(icon, label);

        // Custom animations when selected and unselected
        JFXFillTransition selectedTransition = new JFXFillTransition();
        selectedTransition.setDuration(Duration.millis(100));
        selectedTransition.setFromValue(Color.TRANSPARENT);
        selectedTransition.setToValue(Color.GRAY);
        selectedTransition.setRegion(this);

        JFXFillTransition unselectedTransition = new JFXFillTransition();
        unselectedTransition.setDuration(Duration.millis(100));
        unselectedTransition.setFromValue(Color.GRAY);
        unselectedTransition.setToValue(Color.TRANSPARENT);
        unselectedTransition.setRegion(this);

        // Hover animation
        Timeline popOut = FXUtil.installBump(wrapper, FXUtil.AnimationDirection.RIGHT);
        Timeline popBack = FXUtil.installBumpBack(wrapper, FXUtil.AnimationDirection.RIGHT);

        setOnMouseEntered(me -> {
            popBack.stop();
            popOut.play();
        });

        setOnMouseExited(me -> {
            popOut.stop();
            popBack.play();
        });

        // Custom CSS pseudoclass when selected
        PseudoClass selected = PseudoClass.getPseudoClass("selected");

        selectedProperty.addListener((obs, wasSelected, isSelected) -> {
            pseudoClassStateChanged(selected, isSelected); //trigger pseudoclass update
            if (isSelected) {
                selectedTransition.play();
                if (runnable != null) {
                    runnable.run();
                }
            } else if (wasSelected && !isSelected) {
                unselectedTransition.play();
            }
        });

        super.getChildren().setAll(wrapper);
    }

    public void setOnSelectAction(Runnable runnable) {
        this.runnable = runnable;
    }

    public void select() {
        selectedProperty.setValue(true);
    }

    public void deselect() {
        selectedProperty.setValue(false);
    }

    public String getTitle() {
        return titleProperty.get();
    }

}
