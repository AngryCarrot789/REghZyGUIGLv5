package reghzy.guigl.core.event.events;

import reghzy.guigl.core.event.RoutedEvent;

public class WindowResizeEvent extends RoutedEvent {
    private final double oldWidth;
    private final double oldHeight;
    private final double newWidth;
    private final double newHeight;

    public WindowResizeEvent(double oldWidth, double oldHeight, double newWidth, double newHeight) {
        this.oldWidth = oldWidth;
        this.oldHeight = oldHeight;
        this.newWidth = newWidth;
        this.newHeight = newHeight;
    }

    public double getOldWidth() {
        return oldWidth;
    }

    public double getOldHeight() {
        return oldHeight;
    }

    public double getNewWidth() {
        return newWidth;
    }

    public double getNewHeight() {
        return newHeight;
    }
}
