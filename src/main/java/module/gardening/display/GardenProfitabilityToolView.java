package module.gardening.display;

import common.task.BackgroundTaskRunner;
import common.task.ScheduledTaskRunner;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import module.display.ToolView;
import module.gardening.model.CropAnalysis;
import module.gardening.task.GetCropValuesTask;
import module.gardening.task.UpdateGardenProfitabilityTask;

public class GardenProfitabilityToolView extends ToolView {

    private BooleanProperty loading = new SimpleBooleanProperty(false);
    private ObservableList<CropAnalysis> cropAnalyses = FXCollections.observableArrayList();
    private boolean taskScheduled = false;

    public GardenProfitabilityToolView() {
        super("Profitability");

        getCard().getHeader().getChildren().clear();

        StackPane headerWrapper = new StackPane();
        AnchorPane header = new AnchorPane();
        StackPane.setAlignment(header, Pos.CENTER_RIGHT);

        //refresh
        Pane refresh = new Pane();
        refresh.setPrefSize(16, 16);
        refresh.setMaxSize(16, 16);
        refresh.setId("refresh");
        Tooltip.install(refresh, new Tooltip("Refresh Data"));
        AnchorPane.setRightAnchor(refresh, 10D);
        header.getChildren().add(refresh);
        refresh.disableProperty().bind(Bindings.or(loading, GetCropValuesTask.busy));
        refresh.setOnMouseClicked(me -> {
            if (me.getButton().equals(MouseButton.PRIMARY)) {
                UpdateGardenProfitabilityTask task = new UpdateGardenProfitabilityTask(cropAnalyses, loading);
                BackgroundTaskRunner.getInstance().runTask(task);
            }
        });


        //title
        Label titleLabel = new Label("Profitability");
        titleLabel.getStyleClass().add("title");
        HBox title = new HBox(titleLabel);
        title.setAlignment(Pos.CENTER);
        headerWrapper.getChildren().add(title);

        headerWrapper.getChildren().addAll(header);

        GardenProfitabilityTable table = new GardenProfitabilityTable();
        table.setItems(cropAnalyses);

        VBox.setVgrow(table, Priority.ALWAYS);
        VBox contentWrapper = new VBox(headerWrapper, table);
        getCard().setDisplayedContent(contentWrapper);

        // schedule the task
        if (!GetCropValuesTask.busy.get()) {
            //already finished, schedule
            scheduleUpdateTask();
        } else {
            ChangeListener<Boolean> changeListener = new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean busy) {
                    if (!busy) {
                        scheduleUpdateTask();
                    }
                }
            };
            GetCropValuesTask.busy.addListener(changeListener);
        }


    }

    private void scheduleUpdateTask() {
        if (!taskScheduled) {
            UpdateGardenProfitabilityTask task = new UpdateGardenProfitabilityTask(cropAnalyses, loading);
            ScheduledTaskRunner.getInstance().scheduleTask(task, 5, 0);
        }
    }
}
