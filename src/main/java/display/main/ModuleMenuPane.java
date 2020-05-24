package display.main;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import module.common.BdoModule;

public class ModuleMenuPane extends StackPane {

    private ObservableList<BdoModule> loadedModules = FXCollections.observableArrayList();
    private VBox container;

    public ModuleMenuPane() {
        container = new VBox(10);
        getChildren().add(container);

        loadedModules.addListener((ListChangeListener.Change<? extends BdoModule> c) -> {
            c.next();
            c.getAddedSubList().forEach(this::processNewModule);
        });
    }

    private void processNewModule(BdoModule module) {
        ModuleMenuButton menuButton = new ModuleMenuButton(module);
        container.getChildren().add(menuButton);
    }

    public void addModule(BdoModule module) {
        loadedModules.add(module);
    }

}
