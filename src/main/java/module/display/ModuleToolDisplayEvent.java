package module.display;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class ModuleToolDisplayEvent extends Event {

    public static final EventType<ModuleToolDisplayEvent> CHANGEDISPLAY =
            new EventType<>(Event.ANY, "CHANGEDISPLAY");

    private final ToolView view;
    private final String title;

    public ModuleToolDisplayEvent(ToolView view, String title) {
        super(CHANGEDISPLAY);
        this.view = view;
        this.title = title;
    }

    public ModuleToolDisplayEvent(Object source, EventTarget target, ToolView view, String title) {
        super(source, target, CHANGEDISPLAY);
        this.view =view;
        this.title = title;
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

    public String getTitle() {
        return title;
    }
}
