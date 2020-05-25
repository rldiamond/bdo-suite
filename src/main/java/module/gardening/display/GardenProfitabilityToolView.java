package module.gardening.display;

import common.task.ScheduledTaskRunner;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import module.display.ToolView;
import module.gardening.model.CropAnalysis;
import module.gardening.task.GetCropValuesTask;
import module.gardening.task.UpdateGardenProfitabilityTask;

public class GardenProfitabilityToolView extends ToolView {

    private ObservableList<CropAnalysis> cropAnalyses = FXCollections.observableArrayList();
    private boolean taskScheduled = false;

    public GardenProfitabilityToolView() {
        super("Profitability");

        GardenProfitabilityTable table = new GardenProfitabilityTable();
        table.setItems(cropAnalyses);
        getCard().setDisplayedContent(table);

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
            UpdateGardenProfitabilityTask task = new UpdateGardenProfitabilityTask(cropAnalyses);
            ScheduledTaskRunner.getInstance().scheduleTask(task, 5, 0);
        }
    }
}
