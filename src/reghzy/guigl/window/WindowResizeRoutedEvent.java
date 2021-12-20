package reghzy.guigl.window;

import reghzy.guigl.core.event.RoutedEvent;

public class WindowResizeRoutedEvent extends RoutedEvent {
    public final double oldX;
    public final double oldY;
    public final double newX;
    public final double newY;

    public WindowResizeRoutedEvent(double oldX, double oldY, double newX, double newY) {
        this.oldX = oldX;
        this.oldY = oldY;
        this.newX = newX;
        this.newY = newY;
    }
}
