package module.gardening.display;

import common.task.ScheduledTaskRunner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import module.display.ToolView;
import module.gardening.model.CropAnalysis;
import module.gardening.task.UpdateGardenProfitabilityTask;

import java.util.concurrent.TimeUnit;

public class GardenProfitabilityToolView extends ToolView {

    private ObservableList<CropAnalysis> cropAnalyses = FXCollections.observableArrayList();

    public GardenProfitabilityToolView() {
        super("Profitability");

        GardenProfitabilityTable table = new GardenProfitabilityTable();
        table.setItems(cropAnalyses);
        getCard().setDisplayedContent(table);

        // schedule the task
        UpdateGardenProfitabilityTask task = new UpdateGardenProfitabilityTask(cropAnalyses);
        ScheduledTaskRunner.getInstance().scheduleTask(task, TimeUnit.MINUTES.toMillis(5));

    }
}
