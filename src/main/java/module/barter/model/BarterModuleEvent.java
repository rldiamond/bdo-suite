package module.barter.model;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class BarterModuleEvent extends Event {

    public static final EventType<BarterModuleEvent> BARTERSAVEEVENT =
            new EventType<>(Event.ANY, "BARTERSAVEEVENT");

    public static final EventType<BarterModuleEvent> BARTERDELETELOCATIONEVENT =
            new EventType<>(Event.ANY, "BARTERDELETELOCATIONEVENT");

    public static final EventType<BarterModuleEvent> PLAYERBARTERSAVEEVENT =
            new EventType<>(Event.ANY, "PLAYERBARTERSAVEEVENT");


    public enum ModuleEventType {
        PLAYERSAVE, SAVE, DELETE
    }

    public BarterModuleEvent(ModuleEventType eventType) {
        super(eventType.equals(ModuleEventType.SAVE) ? BARTERSAVEEVENT : eventType.equals(ModuleEventType.PLAYERSAVE) ? PLAYERBARTERSAVEEVENT : BARTERDELETELOCATIONEVENT);
    }

    @Override
    public BarterModuleEvent copyFor(Object newSource, EventTarget newTarget) {
        return (BarterModuleEvent) super.copyFor(newSource, newTarget);
    }

    @Override
    public EventType<? extends BarterModuleEvent> getEventType() {
        return (EventType<? extends BarterModuleEvent>) super.getEventType();
    }

}
