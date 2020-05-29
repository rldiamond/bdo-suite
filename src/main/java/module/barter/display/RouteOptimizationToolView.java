package module.barter.display;

import common.jfx.components.dialog.ActionableAlertDialog;
import common.jfx.components.dialog.AlertDialogType;
import common.task.BackgroundTaskRunner;
import common.task.GenericTask;
import common.utilities.FileUtil;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import module.barter.BarterBdoModule;
import module.barter.display.optimizer.BarterPlanDisplayPane;
import module.barter.display.optimizer.BarterRouteControlsPane;
import module.barter.display.optimizer.BarterRouteInputControlsPane;
import module.barter.display.optimizer.BarterRouteInputPane;
import module.barter.model.*;
import module.barter.task.BarterOptimizationTask;
import module.display.ToolView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class RouteOptimizationToolView extends ToolView {

    private static final Logger logger = LogManager.getLogger(BarterBdoModule.class);
    private final SimpleBooleanProperty busyProperty = new SimpleBooleanProperty(false);
    private BarterRouteInputPane inputPane;
    private BarterRouteControlsPane controlsPane;
    private BarterRouteInputControlsPane inputControlsPane;
    private ObjectProperty<BarterPlan> barterPlanProperty = new SimpleObjectProperty<>();
    private BarterRoutesModuleData moduleData;

    public RouteOptimizationToolView() {
        super("Route Optimization");

        moduleData = FileUtil.loadModuleData(BarterRoutesModuleData.class);

        inputPane = new BarterRouteInputPane();
        inputPane.bindDisableProperty(busyProperty);
        if (moduleData != null && moduleData.getBarters() != null) {
            inputPane.setItems(moduleData.getBarters());
        }
        controlsPane = new BarterRouteControlsPane();
        inputControlsPane = new BarterRouteInputControlsPane();
        VBox stackEm = new VBox(5);
        BarterPlanDisplayPane barterPlanDisplayPane = new BarterPlanDisplayPane(barterPlanProperty);
        VBox.setVgrow(inputPane, Priority.ALWAYS);
        VBox.setVgrow(barterPlanDisplayPane, Priority.SOMETIMES);
        stackEm.getChildren().addAll(inputControlsPane, inputPane, barterPlanDisplayPane);
        getCard().setDisplayedContent(stackEm);
        getCard().setFooterContent(controlsPane);

        // bind controls
        controlsPane.getResetButton().setOnMouseClicked(me -> {
            if (me.getButton().equals(MouseButton.PRIMARY)) {
                ActionableAlertDialog alert = new ActionableAlertDialog(AlertDialogType.CONFIRMATION);
                alert.setTitle("Are you sure?");
                alert.setBody("This will clear all entered barters.");
                alert.setActionButtonText("CLEAR");
                alert.setAction(inputPane::reset);
                alert.show();
            }
        });
        controlsPane.getResetButton().disableProperty().bind(busyProperty);

        controlsPane.getOptimizeButton().setOnMouseClicked(me -> {
            if (me.getButton().equals(MouseButton.PRIMARY)) {
                doOptimize();
            }
        });
        controlsPane.getOptimizeButton().disableProperty().bind(Bindings.or(busyProperty, Bindings.equal(0, Bindings.size(inputPane.getBarters()))));

        inputControlsPane.getAddBarterButton().setOnMouseClicked(me -> {
            if (me.getButton().equals(MouseButton.PRIMARY)) {
                Barter newRoute = new Barter();
                // if there's a previous route, auto-complete accept column
                if (BarterSettings.getSettings().isAutofillAcceptGood()) {
                    newRoute.setParley(11911);
                    newRoute.setAcceptAmount(1);
                    List<Barter> barters = inputPane.getEnteredRoutes();
                    if (!barters.isEmpty()) {
                        Barter previous = barters.get(barters.size() - 1);
                        if (previous != null && previous.getExchangeGoodName() != null) {
                            newRoute.setAcceptGoodName(previous.getExchangeGoodName());
                        }
                    }
                }
                inputPane.addRoutes(newRoute);
                fireEvent(new BarterModuleEvent(BarterModuleEvent.ModuleEventType.PLAYERSAVE));
            }
        });
        inputControlsPane.getAddBarterButton().disableProperty().bind(busyProperty);

        inputControlsPane.getRemoveBarterButton().setOnMouseClicked(me -> {
            if (me.getButton().equals(MouseButton.PRIMARY)) {
                inputPane.getBarters().remove(inputPane.getSelectedRoute());
            }
        });

        inputControlsPane.getRemoveBarterButton().disableProperty().bind(Bindings.or(Bindings.isNull(inputPane.selectedItemProperty()), busyProperty));

        // add a save listener
        addEventHandler(BarterModuleEvent.PLAYERBARTERSAVEEVENT, e -> {
            BackgroundTaskRunner.getInstance().runTask(new GenericTask(() -> {
                moduleData.setBarters(inputPane.getEnteredRoutes());
                FileUtil.saveModuleData(moduleData);
            }));
            e.consume();
        });
    }

    /**
     * Run the optimization process.
     */
    private void doOptimize() {
        busyProperty.set(true);
        // Get barter routes from the table
        final List<Barter> barters = inputPane.getEnteredRoutes();
        // Create the background task
        BarterOptimizationTask task = new BarterOptimizationTask(barters, barterPlanProperty, busyProperty);
        BackgroundTaskRunner.getInstance().runTask(task);
    }

    //TODO REMOVE
    public void setBarters(List<Barter> barters) {
        barters.forEach(inputPane::addRoutes);
    }
}
