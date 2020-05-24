package display.main;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.layout.BorderPane;
import module.common.BdoModule;

/**
 * The root display for the entire software.
 * Alpha stage: Simply displays what is in the loaded module.
 */
public class RootDisplayPane extends BorderPane {

    private ObservableList<BdoModule> modules = FXCollections.observableArrayList();
    private ModuleMenuPane moduleMenuPane;
    private HeaderPane headerPane;

    /**
     * Default constructor.
     */
    public RootDisplayPane() {
        moduleMenuPane = new ModuleMenuPane();
        setLeft(moduleMenuPane);
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
        moduleMenuPane.addModule(module);
        loadModule(module);

    }

    private void loadModule(BdoModule module) {
        this.setCenter(module.getModulePane());
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
