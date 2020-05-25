package module.barter.display;

import common.jfx.FXUtil;
import common.task.BackgroundTaskRunner;
import common.task.GenericTask;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import module.barter.model.BarterPlan;
import module.barter.model.PlannedRoute;

import java.util.ArrayList;
import java.util.List;

public class BarterPlanDisplayPane extends StackPane {

    private List<RouteDisplayPane> routeDisplayPanes = new ArrayList<>();
    private final StackPane displayPane;
    private ObjectProperty<RouteDisplayPane> currentlyDisplayed = new SimpleObjectProperty<>();
    private Pane leftArrowButton;
    private Pane rightArrowButton;

    public BarterPlanDisplayPane(ObjectProperty<BarterPlan> barterPlanProperty) {
        setPrefSize(USE_COMPUTED_SIZE, 55);
        setMinSize(USE_COMPUTED_SIZE, USE_PREF_SIZE);
        setMaxSize(USE_COMPUTED_SIZE, USE_PREF_SIZE);
        HBox container = new HBox(10);
        container.setAlignment(Pos.CENTER);

        //left arrow
        leftArrowButton = new Pane();
        leftArrowButton.setPrefSize(24, 24);
        leftArrowButton.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
        leftArrowButton.setMinSize(USE_PREF_SIZE, USE_PREF_SIZE);
        leftArrowButton.setId("leftArrow");
        leftArrowButton.setOnMouseClicked(me -> {
            if (me.getButton().equals(MouseButton.PRIMARY)) {
                displayPrevious();
            }
        });

        //display pane
        displayPane = new StackPane();
        HBox.setHgrow(displayPane, Priority.ALWAYS);


        //right arrow
        rightArrowButton = new Pane();
        rightArrowButton.setPrefSize(24, 24);
        rightArrowButton.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
        rightArrowButton.setMinSize(USE_PREF_SIZE, USE_PREF_SIZE);
        rightArrowButton.setId("rightArrow");
        rightArrowButton.setOnMouseClicked(me -> {
            if (me.getButton().equals(MouseButton.PRIMARY)) {
                displayNext();
            }
        });

        container.getChildren().addAll(leftArrowButton,displayPane,rightArrowButton);
        getChildren().setAll(container);

        currentlyDisplayed.addListener((obs, ov, nv) -> {
            FXUtil.runOnFXThread(() -> displayPane.getChildren().setAll(nv));
            int index = routeDisplayPanes.indexOf(nv);
            leftArrowButton.setDisable(false);
            rightArrowButton.setDisable(false);
            if (index == 0) {
                leftArrowButton.setDisable(true);
            } else if (index == routeDisplayPanes.size()) {
                rightArrowButton.setDisable(true);
            }
        });

        barterPlanProperty.addListener((obs,ov,barterPlan) -> {
            createRouteDisplayPanes(barterPlan);
        });


    }

    private void createRouteDisplayPanes(BarterPlan barterPlan) {
        GenericTask task = new GenericTask(() -> {
            routeDisplayPanes.clear();
            barterPlan.getRoutes().stream().map(this::createRouteDisplayPane).forEach(routeDisplayPanes::add);
            FXUtil.runOnFXThread(() -> {
                displayPane.getChildren().setAll(routeDisplayPanes.get(0));
                currentlyDisplayed.setValue(routeDisplayPanes.get(0));
            });
        });
        BackgroundTaskRunner.getInstance().runTask(task);
    }

    private RouteDisplayPane createRouteDisplayPane(PlannedRoute plannedRoute) {
        return new RouteDisplayPane(plannedRoute);
    }

    private void displayNext() {
        if (currentlyDisplayed.get() == null) {
            return;
        }

        int index = routeDisplayPanes.indexOf(currentlyDisplayed.get()) + 1;

        if (routeDisplayPanes.size() > index) {
            currentlyDisplayed.setValue(routeDisplayPanes.get(index));
        }

    }

    private void displayPrevious() {
        if (currentlyDisplayed == null) {
            return;
        }

        int index = routeDisplayPanes.indexOf(currentlyDisplayed.get()) - 1;

        if (routeDisplayPanes.size() >= index) {
            currentlyDisplayed.setValue(routeDisplayPanes.get(index));
        }

    }

}
