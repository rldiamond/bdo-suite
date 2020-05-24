package display.main;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import common.application.ApplicationSettings;
import common.jfx.FXUtil;
import common.jfx.components.PopupMenuEntry;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import module.common.ModuleTool;
import module.common.ModuleToolbar;
import module.display.ModuleToolDisplayEvent;

import java.util.List;
import java.util.stream.Collectors;

public class HeaderPane extends StackPane {

    private ObservableList<ToolButton> toolButtons = FXCollections.observableArrayList();
    private HBox toolbar;

    public HeaderPane() {
        HBox container = new HBox();

        HBox branding = new HBox(20);
        branding.setId("branding");
        branding.setPrefWidth(175);
        branding.setMaxWidth(USE_PREF_SIZE);
        branding.setMinWidth(USE_PREF_SIZE);
        branding.setAlignment(Pos.CENTER_LEFT);
        branding.setPadding(new Insets(5, 5, 5, 10));
        Label appName = new Label(ApplicationSettings.APPLICATION_NAME);
        appName.setStyle("-fx-text-fill: ghostwhite; -fx-font-size: 16px");

        //menu
        Pane menu = new Pane();
        menu.setPrefSize(24, 16);
        menu.setMaxSize(24, 16);
        menu.setId("menu");
        StackPane menuWrapper = new StackPane(menu);
        AnchorPane.setLeftAnchor(menuWrapper, 185D);
        AnchorPane.setTopAnchor(menuWrapper, 0D);
        AnchorPane.setBottomAnchor(menuWrapper, 0D);
        Tooltip.install(menu, new Tooltip("Menu"));

        JFXPopup fileMenuPopUp = new JFXPopup();
        fileMenuPopUp.setAutoHide(true);
        JFXListView<HBox> fileMenuList = new JFXListView<>();
        fileMenuPopUp.setPopupContent(fileMenuList);

        menu.setOnMouseClicked(me -> fileMenuPopUp.show(menu, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT));
        PopupMenuEntry fileExitMenuEntry = new PopupMenuEntry("Exit");
        fileExitMenuEntry.setOnMouseClicked(me -> {
            fileMenuPopUp.hide();
            Platform.exit();
        });
        Tooltip.install(fileExitMenuEntry, new Tooltip("Exit the application."));
        fileMenuList.getItems().addAll(fileExitMenuEntry);

        branding.getChildren().addAll(menu, appName);

        container.getChildren().addAll(branding);
        getChildren().setAll(container);

        toolbar = new HBox();
        toolbar.setPadding(new Insets(0, 0, 0, 20));
        container.getChildren().add(toolbar);

        setPrefSize(USE_COMPUTED_SIZE, 50D);
        setId("header");

        toolButtons.addListener((ListChangeListener.Change<? extends ToolButton> c) -> {
            c.next();
            redrawToolbar();
        });

    }

    private void redrawToolbar() {
        FXUtil.runOnFXThread(() -> {
            toolbar.getChildren().setAll(toolButtons);
        });
    }

    public void selectFirstTool() {
        if (!toolButtons.isEmpty()) {
            this.toolButtons.get(0).select();
        }
    }

    public void setToolBar(ModuleToolbar moduleToolbar) {
        List<ToolButton> buttons = moduleToolbar.getTools().stream().map(ToolButton::new).collect(Collectors.toList());
        this.toolButtons.setAll(buttons);
    }

    class ToolButton extends StackPane {

        private final String title;
        private BooleanProperty selectedProperty = new SimpleBooleanProperty(false);

        public ToolButton(ModuleTool moduleTool) {
            setId("toolbutton");
            Pane icon = new Pane();
            icon.setId(moduleTool.getIconId());
            icon.getStyleClass().add("toolbutton"); //todo
            icon.setPrefSize(24, 24);
            icon.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
            getChildren().setAll(icon);
            title = moduleTool.getTitle();

            Tooltip.install(this, new Tooltip(moduleTool.getDescription()));

            this.setOnMouseClicked(me -> {
                if (me.getButton().equals(MouseButton.PRIMARY)) {
                    select();
                }
            });

            selectedProperty.addListener((c, x, selected) -> {
                if (selected) {
                    fireEvent(new ModuleToolDisplayEvent(moduleTool.getToolView()));
                }
            });


        }

        public void select() {
            this.selectedProperty.setValue(true);
        }

        public void deselect() {
            this.selectedProperty.setValue(false);
        }

        public String getTitle() {
            return title;
        }
    }
}
