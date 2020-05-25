package module.barter.task;

import common.jfx.FXUtil;
import common.task.BackgroundTask;
import common.utilities.TextUtil;
import common.utilities.ToastUtil;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import module.marketapi.MarketDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CrowCoinValueTask extends BackgroundTask {

    private static final Logger logger = LogManager.getLogger(CrowCoinValueTask.class);

    private final StringProperty field;
    private final BooleanProperty busyProperty;

    public CrowCoinValueTask(StringProperty field, BooleanProperty busyProperty) {
        this.field =  field;
        this.busyProperty = busyProperty;
    }

    @Override
    public void doTask() {
        busyProperty.setValue(true);
        logger.info("Updating Crow Coin value..");
        double value = MarketDAO.getInstance().getCrowCoinValue();
        FXUtil.runOnFXThread(() -> {
            field.setValue(TextUtil.formatAsSilver(value));
            busyProperty.setValue(false);
            logger.info("Crow Coin value update complete.");
            ToastUtil.sendToast("Crow Coin value has been updated.");
        });

    }
}
