package display.main;

import com.jfoenix.controls.JFXSpinner;
import common.jfx.FXUtil;
import common.task.BackgroundTaskRunner;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import module.common.BdoModule;

import java.util.ArrayList;
import java.util.List;

/**
 * The root display for the entire software.
 * Alpha stage: Simply displays what is in the loaded module.
 */
public class RootDisplayPane extends BorderPane {

    private ObservableList<BdoModule> modules = FXCollections.observableArrayList();
    private List<ModuleMenuButton> menuButtons = new ArrayList<>();
    private HeaderPane headerPane;
    private VBox moduleMenu;
    private StackPane displayedContentPane;

    /**
     * Default constructor.
     */
    public RootDisplayPane() {
        setPrefSize(800, 500);
        displayedContentPane = new StackPane();
        setCenter(displayedContentPane);

        // Module menu
        moduleMenu = new VBox();
        moduleMenu.setPadding(new Insets(25,0,0,0));
        AnchorPane.setTopAnchor(moduleMenu, 0D);
        AnchorPane.setLeftAnchor(moduleMenu, 0D);
        AnchorPane.setRightAnchor(moduleMenu, 0D);
        AnchorPane leftMenu = new AnchorPane();
        leftMenu.setId("leftMenu");
        leftMenu.getChildren().add(moduleMenu);
        leftMenu.setPrefSize(175, USE_COMPUTED_SIZE);
        JFXSpinner backgroundBusyIndicator = new JFXSpinner();
        backgroundBusyIndicator.setRadius(5);
        backgroundBusyIndicator.managedProperty().bind(BackgroundTaskRunner.getInstance().busyProperty());
        backgroundBusyIndicator.visibleProperty().bind(BackgroundTaskRunner.getInstance().busyProperty());
        AnchorPane.setRightAnchor(backgroundBusyIndicator, 5D);
        AnchorPane.setBottomAnchor(backgroundBusyIndicator, 5D);
        leftMenu.getChildren().add(backgroundBusyIndicator);
        setLeft(leftMenu);

        headerPane = new HeaderPane();
        setTop(headerPane);

        // Listen for newly added modules and add them to the display.
        modules.addListener((ListChangeListener.Change<? extends BdoModule> c) -> {
            c.next();
            c.getAddedSubList().forEach(this::processNewModule);
        });
    }

    /**
     * Adds the provided module to the display.
     * @param module The module to add to the display.
     */
    private void processNewModule(BdoModule module) {
        ModuleMenuButton moduleMenuButton = new ModuleMenuButton(module);
        moduleMenu.getChildren().add(moduleMenuButton);
        menuButtons.add(moduleMenuButton);

        moduleMenuButton.setOnSelectAction(() -> loadModule(module));
        moduleMenuButton.setOnMouseClicked(me -> selectModule(module));
    }

    private void selectModule(BdoModule module) {
        menuButtons.stream().filter(button -> module.getTitle().equalsIgnoreCase(button.getTitle())).findFirst()
                .ifPresent(button -> {
                    menuButtons.forEach(ModuleMenuButton::deselect);
                    button.select();
                });
    }

    private void loadModule(BdoModule module) {
        FXUtil.runOnFXThread(() -> displayedContentPane.getChildren().setAll(module.getModulePane()));
    }

    /**
     * Loads a BdoModule in the main display.
     *
     * @param modules The module(s) to load into the display.
     */
    public void addModule(BdoModule... modules) {
        if (modules != null && modules.length > 0) {
            this.modules.addAll(modules);
        }
    }

}
