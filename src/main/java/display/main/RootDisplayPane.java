package display.main;

import com.jfoenix.controls.JFXSpinner;
import common.application.ModuleRegistration;
import common.jfx.FXUtil;
import common.task.BackgroundTaskRunner;
import common.task.GenericTask;
import javafx.animation.FadeTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import module.common.BdoModule;
import module.display.ModuleToolDisplayEvent;
import module.display.ToolView;

import java.util.ArrayList;
import java.util.List;

/**
 * The root display for the entire software.
 * Alpha stage: Simply displays what is in the loaded module.
 */
public class RootDisplayPane extends BorderPane {

    private ObservableList<ModuleRegistration> modules = FXCollections.observableArrayList();
    private List<ModuleMenuButton> menuButtons = new ArrayList<>();
    private BooleanProperty displayLoading = new SimpleBooleanProperty(false);
    private HeaderPane headerPane;
    private VBox moduleMenu;
    private StackPane displayedContentPane;

    /**
     * Default constructor.
     */
    public RootDisplayPane() {
        setLoading(true);
        setPrefSize(1000, 700);
        displayedContentPane = new StackPane();
        setCenter(displayedContentPane);

        // displayed content
        //when loadingContent, display a loading indicator
        JFXSpinner loadingSpinner = new JFXSpinner();
        loadingSpinner.setRadius(30);
        loadingSpinner.visibleProperty().bind(displayLoading);
        loadingSpinner.managedProperty().bind(displayLoading);
        //blank pane to cover up any displayed content
        StackPane loadingPane = new StackPane();
        loadingPane.setStyle("-fx-background-color: white");
        FadeTransition fadePaneOut = FXUtil.installFade(loadingPane, FXUtil.AnimationFadeType.OUT);
        FadeTransition fadePaneIn = FXUtil.installFade(loadingPane, FXUtil.AnimationFadeType.IN);

        displayLoading.addListener((obs, ov, loading) -> {
            if (loading) {
                fadePaneOut.stop();
                loadingPane.setOpacity(0);
                loadingPane.setVisible(true);
                loadingPane.setManaged(true);
                fadePaneIn.play();
            } else {
                fadePaneIn.stop();
                fadePaneOut.play();
            }
        });

        //encapsulate the displayedContent so that the loadingSpinner may always appear on top.
        StackPane mainPanel = new StackPane();
        mainPanel.getChildren().setAll(displayedContentPane, loadingPane, loadingSpinner);
        setCenter(mainPanel);

        // Module menu
        moduleMenu = new VBox();
        moduleMenu.setPadding(new Insets(25, 0, 0, 0));
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

        // header
        headerPane = new HeaderPane();
        setTop(headerPane);

        // Listen for newly added modules and add them to the display.
        modules.addListener((ListChangeListener.Change<? extends ModuleRegistration> c) -> {
            c.next();
            c.getAddedSubList().forEach(this::processNewModule);
        });

        this.addEventHandler(ModuleToolDisplayEvent.CHANGEDISPLAY, e -> loadToolView(e.getView()));

        //initialize welcomecard
        displayedContentPane.getChildren().setAll(new WelcomeCard());
        setLoading(false);
    }

    private void setLoading(boolean loading) {
        this.displayLoading.setValue(loading);
    }

    private void loadModule(ModuleRegistration moduleRegistration) {
        setLoading(true);

        GenericTask task = new GenericTask(() -> {
            BdoModule module = moduleRegistration.getNewModule();
            headerPane.setToolBar(module.getModuleToolbar());
            FXUtil.runOnFXThread(() -> {
                headerPane.selectFirstTool();
                setLoading(false);
            });
        });

        BackgroundTaskRunner.getInstance().runTask(task);
    }

    private void loadToolView(ToolView toolView) {
        setLoading(true);

        GenericTask task = new GenericTask(() -> {
            FXUtil.runOnFXThread(() -> {
                this.displayedContentPane.getChildren().setAll(toolView);
                setLoading(false);
            });
        });

        BackgroundTaskRunner.getInstance().runTask(task);
    }

    /**
     * Adds the provided module to the display.
     *
     * @param moduleRegistration The module to add to the display.
     */
    private void processNewModule(ModuleRegistration moduleRegistration) {
        ModuleMenuButton moduleMenuButton = new ModuleMenuButton(moduleRegistration);
        moduleMenu.getChildren().add(moduleMenuButton);
        menuButtons.add(moduleMenuButton);

        moduleMenuButton.setOnSelectAction(() -> loadModule(moduleRegistration));
        moduleMenuButton.setOnMouseClicked(me -> selectModule(moduleRegistration));
    }

    private void selectModule(ModuleRegistration module) {
        menuButtons.stream().filter(button -> module.getTitle().equalsIgnoreCase(button.getTitle())).findFirst()
                .ifPresent(button -> {
                    menuButtons.forEach(ModuleMenuButton::deselect);
                    button.select();
                });
    }

    /**
     * Loads a BdoModule in the main display.
     *
     * @param modules The module(s) to load into the display.
     */
    public void registerModule(ModuleRegistration... modules) {
        if (modules != null && modules.length > 0) {
            this.modules.addAll(modules);
        }
    }

}
