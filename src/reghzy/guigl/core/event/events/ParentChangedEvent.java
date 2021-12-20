package reghzy.guigl.core.event.events;

import reghzy.guigl.core.Control;
import reghzy.guigl.core.event.RoutedEvent;

public class ParentChangedEvent extends RoutedEvent {
    private final Control oldParent;
    private final Control newParent;

    public ParentChangedEvent(Control oldParent, Control newParent) {
        this.oldParent = oldParent;
        this.newParent = newParent;
    }

    public Control getOldParent() {
        return oldParent;
    }

    public Control getNewParent() {
        return newParent;
    }
}
