package common.task;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class TaskingUtility {

    public static final BooleanProperty busyProperty = new SimpleBooleanProperty();

    static {
        busyProperty.bind(Bindings.or(BackgroundTaskRunner.getInstance().busyProperty(), ScheduledTaskRunner.getInstance().busyProperty()));
    }

}
