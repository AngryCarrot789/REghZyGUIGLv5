package reghzy.guigl.core.__old__.event.events;

import reghzy.guigl.core.__old__.event.RoutedEvent;
import reghzy.guigl.core.utils.HorizontalAlignment;

public class HorizontalAlignChangedEvent extends RoutedEvent {
    private HorizontalAlignment oldValue;
    private HorizontalAlignment newValue;

    public HorizontalAlignChangedEvent(HorizontalAlignment oldValue, HorizontalAlignment newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public HorizontalAlignment getOldValue() {
        return oldValue;
    }

    public void setOldValue(HorizontalAlignment oldValue) {
        this.oldValue = oldValue;
    }

    public HorizontalAlignment getNewValue() {
        return newValue;
    }

    public void setNewValue(HorizontalAlignment newValue) {
        this.newValue = newValue;
    }
}
