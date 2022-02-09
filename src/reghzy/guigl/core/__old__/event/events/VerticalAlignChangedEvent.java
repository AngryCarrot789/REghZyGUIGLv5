package reghzy.guigl.core.__old__.event.events;

import reghzy.guigl.core.__old__.event.RoutedEvent;
import reghzy.guigl.core.utils.VerticalAlignment;

public class VerticalAlignChangedEvent extends RoutedEvent {
    private VerticalAlignment oldValue;
    private VerticalAlignment newValue;

    public VerticalAlignChangedEvent(VerticalAlignment oldValue, VerticalAlignment newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public VerticalAlignment getOldValue() {
        return oldValue;
    }

    public void setOldValue(VerticalAlignment oldValue) {
        this.oldValue = oldValue;
    }

    public VerticalAlignment getNewValue() {
        return newValue;
    }

    public void setNewValue(VerticalAlignment newValue) {
        this.newValue = newValue;
    }
}
