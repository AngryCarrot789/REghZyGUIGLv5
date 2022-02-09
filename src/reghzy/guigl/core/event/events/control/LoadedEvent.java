package reghzy.guigl.core.event.events.control;

import reghzy.guigl.core.Control;
import reghzy.guigl.core.event.RoutedEvent;

public class LoadedEvent extends RoutedEvent {
    private final Control control;

    public LoadedEvent(Control control) {
        this.control = control;
    }

    /**
     * The control that has loaded
     */
    public Control getControl() {
        return control;
    }
}
