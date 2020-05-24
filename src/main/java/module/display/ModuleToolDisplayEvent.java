package module.display;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class ModuleToolDisplayEvent extends Event {

    public static final EventType<ModuleToolDisplayEvent> CHANGEDISPLAY =
            new EventType<>(Event.ANY, "CHANGEDISPLAY");

    private final ToolView view;

    public ModuleToolDisplayEvent(ToolView view) {
        super(CHANGEDISPLAY);
        this.view = view;
    }

    public ModuleToolDisplayEvent(Object source, EventTarget target, ToolView view) {
        super(source, target, CHANGEDISPLAY);
        this.view =view;
    }

    @Override
    public ModuleToolDisplayEvent copyFor(Object newSource, EventTarget newTarget) {
        return (ModuleToolDisplayEvent) super.copyFor(newSource, newTarget);
    }

    @Override
    public EventType<? extends ModuleToolDisplayEvent> getEventType() {
        return (EventType<? extends ModuleToolDisplayEvent>) super.getEventType();
    }

    public ToolView getView() {
        return view;
    }
}
