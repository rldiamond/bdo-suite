package module.barter.task;

import common.task.BackgroundTask;
import common.utilities.TextUtil;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import module.marketapi.MarketDAO;

public class CrowCoinValueTask extends BackgroundTask {

    private final StringProperty field;
    private final BooleanProperty busyProperty;

    public CrowCoinValueTask(StringProperty field, BooleanProperty busyProperty) {
        this.field =  field;
        this.busyProperty = busyProperty;
    }

    @Override
    public void doTask() {
        field.setValue(TextUtil.formatAsSilver(MarketDAO.getInstance().getCrowCoinValue()));
        busyProperty.set(false);
    }
}
