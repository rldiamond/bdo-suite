package module.barter.display;

import common.json.JsonParseException;
import common.task.BackgroundTaskRunner;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import module.barter.BarterBdoModule;
import module.barter.BarterJsonFileReader;
import module.barter.model.Barter;
import module.barter.task.BarterOptimizationTask;
import module.display.ToolView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.util.List;

public class RouteOptimizationToolView extends ToolView {

    private static final Logger logger = LogManager.getLogger(BarterBdoModule.class);
    private final SimpleBooleanProperty busyProperty = new SimpleBooleanProperty(false);
    private TextArea console;
    private BarterRouteInputPane inputPane;
    private BarterRouteControlsPane controlsPane;
    private BarterRouteInputControlsPane inputControlsPane;

    public RouteOptimizationToolView() {
        super("Route Optimization");

        console = new TextArea();
        console.setEditable(false);
        inputPane = new BarterRouteInputPane();
        inputPane.bindDisableProperty(busyProperty);
        controlsPane = new BarterRouteControlsPane();
        inputControlsPane = new BarterRouteInputControlsPane();
        VBox stackEm = new VBox(5);
        stackEm.getChildren().addAll(inputControlsPane, inputPane, console);
        getCard().setDisplayedContent(stackEm);
        getCard().setFooterContent(controlsPane);

        // bind controls
        controlsPane.getResetButton().setOnMouseClicked(me -> {
            if (me.getButton().equals(MouseButton.PRIMARY)) {
                //TODO: Show a warning dialog
                inputPane.reset();
            }
        });
        controlsPane.getResetButton().disableProperty().bind(busyProperty);

        controlsPane.getOptimizeButton().setOnMouseClicked(me -> {
            if (me.getButton().equals(MouseButton.PRIMARY)) {
                doOptimize();
            }
        });
        controlsPane.getOptimizeButton().disableProperty().bind(busyProperty);

        busyProperty.addListener(c -> {
            if (busyProperty.get()) {
                console.setText("Loading... please wait.");
            }
        });

        inputControlsPane.getAddBarterButton().setOnMouseClicked(me -> {
            if (me.getButton().equals(MouseButton.PRIMARY)) {
                Barter newRoute = new Barter();
                inputPane.addRoutes(newRoute);
            }
        });
        inputControlsPane.getAddBarterButton().disableProperty().bind(busyProperty);

        inputControlsPane.getRemoveBarterButton().setOnMouseClicked(me -> {
            if (me.getButton().equals(MouseButton.PRIMARY)) {
                inputPane.getBarters().remove(inputPane.getSelectedRoute());
            }
        });

        inputControlsPane.getRemoveBarterButton().disableProperty().bind(Bindings.or(Bindings.isNull(inputPane.selectedItemProperty()),busyProperty));

        //TODO: TEMPORARY CODE FOR TESTING PURPOSES!!!
        URL barterUrl = RouteOptimizationToolView.class.getClassLoader().getResource("barter.json");
        List<Barter> possibleRoutes;
        try {
            possibleRoutes = BarterJsonFileReader.readBarterRoutesFromFile(new File(barterUrl.getPath()));
        } catch (JsonParseException ex) {
            logger.error("Fatal error! Could not parse the possible barter routes JSON!");
            possibleRoutes = null;
        }
        setBarters(possibleRoutes);

        //TODO: ------------------------------------
    }

    /**
     * Run the optimization process.
     */
    private void doOptimize() {
        busyProperty.set(true);
        // Get barter routes from the table
        final List<Barter> barters = inputPane.getEnteredRoutes();
        // Create the background task
        BarterOptimizationTask task = new BarterOptimizationTask(barters, console, busyProperty);
        BackgroundTaskRunner.getInstance().runTask(task);
    }

    //TODO REMOVE
    public void setBarters(List<Barter> barters) {
        barters.forEach(inputPane::addRoutes);
    }
}
