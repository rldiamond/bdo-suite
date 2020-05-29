package module.barter.display.optimizer;

import common.jfx.FXUtil;
import common.task.BackgroundTaskRunner;
import common.task.GenericTask;
import common.utilities.TextUtil;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import module.barter.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BarterPlanDisplayPane extends StackPane {

    private List<RouteDisplayPane> routeDisplayPanes = new ArrayList<>();
    private final StackPane displayPane;
    private ObjectProperty<RouteDisplayPane> currentlyDisplayed = new SimpleObjectProperty<>();
    private SimpleStringProperty profitText = new SimpleStringProperty();
    private SimpleStringProperty parleyText = new SimpleStringProperty();
    private Pane leftArrowButton;
    private Pane rightArrowButton;

    public BarterPlanDisplayPane(ObjectProperty<BarterPlan> barterPlanProperty) {
        setMinSize(USE_COMPUTED_SIZE, 55);
        setPadding(new Insets(10,0,10,0));
        HBox container = new HBox(10);
        container.setPrefHeight(50);
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
            FXUtil.runOnFXThread(() -> {
                if (barterPlan == null) {
                    parleyText.setValue("");
                    profitText.setValue("");
                } else {
                    profitText.setValue(TextUtil.formatAsSilver(barterPlan.getProfit()));
                    parleyText.setValue(TextUtil.formatWithCommas(barterPlan.getParley()));
                }
            });
        });

        HBox profitAndParley = new HBox(10);
        profitAndParley.visibleProperty().bind(Bindings.or(Bindings.equal("",profitText).not(),Bindings.equal("",parleyText).not()));
        profitAndParley.managedProperty().bind(Bindings.or(Bindings.equal("",profitText).not(),Bindings.equal("",parleyText).not()));
        profitAndParley.setAlignment(Pos.CENTER);

        HBox profitContainer = new HBox(5);
        profitContainer.setAlignment(Pos.CENTER_LEFT);
        Label profitLabel = new Label("Profit: ");
        Label profitAmountLabel = new Label();
        profitAmountLabel.textProperty().bind(profitText);
        profitContainer.getChildren().setAll(profitLabel, profitAmountLabel);

        HBox parleyContainer = new HBox(5);
        parleyContainer.setAlignment(Pos.CENTER_LEFT);
        Label parleyLabel = new Label("Parley: ");
        Label parleyAmountLabel = new Label();
        parleyAmountLabel.textProperty().bind(parleyText);
        parleyContainer.getChildren().setAll(parleyLabel, parleyAmountLabel);

        profitAndParley.getChildren().setAll(profitContainer, parleyContainer);
        VBox wrapper = new VBox(5);
        wrapper.getChildren().setAll(container, profitAndParley);
        getChildren().setAll(wrapper);
    }

    private void createRouteDisplayPanes(BarterPlan barterPlan) {
        GenericTask task = new GenericTask(() -> {
            routeDisplayPanes.clear();
            // here we arrange them appropriately
            // for now, we just want all crow coin routes done last..
            List<PlannedRoute> crowCoinRoutes = getCrowCoinRoutes(barterPlan);
            List<PlannedRoute> finalStorageRunRoutes=  getfinalStorageRoutes(barterPlan);
            barterPlan.getRoutes().stream().filter(route -> !crowCoinRoutes.contains(route)).filter(route -> !finalStorageRunRoutes.contains(route))
                    .map(this::createRouteDisplayPane).forEach(routeDisplayPanes::add);
            finalStorageRunRoutes.stream().map(this::createRouteDisplayPane).forEach(routeDisplayPanes::add);
            routeDisplayPanes.add(new CrowCoinRouteDisplayPane(crowCoinRoutes));
            FXUtil.runOnFXThread(() -> {
                displayPane.getChildren().setAll(routeDisplayPanes.get(0));
                currentlyDisplayed.setValue(routeDisplayPanes.get(0));
            });
        });
        BackgroundTaskRunner.getInstance().runTask(task);
    }
    private List<PlannedRoute> getfinalStorageRoutes(BarterPlan barterPlan) {
        return barterPlan.getRoutes().stream().filter(PlannedRoute::hasTurnInGood).filter(route -> route.getReceivedGood().getLevelType().equals(BarterLevelType.FIVE)).collect(Collectors.toList());
    }

    private List<PlannedRoute> getCrowCoinRoutes(BarterPlan barterPlan) {
        BarterGood crowCoin = BarterGood.getBarterGoodByName("Crow Coin").get();
        return barterPlan.getRoutes().stream().filter(route -> route.getReceivedGood().equals(crowCoin)).collect(Collectors.toList());
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
