package module.gardening.task;

import common.task.BackgroundTask;
import common.task.BackgroundTaskRunner;
import common.task.GenericTask;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import module.gardening.model.Crop;
import module.marketapi.MarketDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetCropValuesTask extends BackgroundTask {

    private static final Logger logger = LogManager.getLogger(GetCropValuesTask.class);

    public static final BooleanProperty busy = new SimpleBooleanProperty(false);

    @Override
    public void doTask() {
        busy.unbind();
        busy.setValue(true);


        // Schedule a new task for each crop to grab data from the API.
        Crop.getCrops().forEach(crop -> {
            crop.loadedProperty().addListener((obs, ov, loaded) -> {
                if (loaded) {
                    checkAllForLoaded();
                }
            });
            GenericTask seedTask = new GenericTask(() -> {
                MarketDAO.getInstance().fetchData(crop.getSeedId());
                crop.setSeedLoaded(true);
            });
            BackgroundTaskRunner.getInstance().runTask(seedTask);

            GenericTask cropTask = new GenericTask(() -> {
                MarketDAO.getInstance().fetchData(crop.getCropId());
                crop.setCropLoaded(true);
            });
            BackgroundTaskRunner.getInstance().runTask(cropTask);
        });

    }

    private void checkAllForLoaded() {
        if (busy.get()) {
            busy.setValue(Crop.getCrops().stream().anyMatch(crop -> !crop.loadedProperty().get()));
        }
        if (!busy.get()){
            logger.info("Crop data fetch complete.");
        }
    }
}
