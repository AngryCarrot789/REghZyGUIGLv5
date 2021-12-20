package reghzy.guigl.core.event.events;

import reghzy.guigl.core.event.RoutedEvent;

public class WindowMoveEvent extends RoutedEvent {
    private final double oldX;
    private final double oldY;
    private final double newX;
    private final double newY;

    public WindowMoveEvent(double oldX, double oldY, double newX, double newY) {
        this.oldX = oldX;
        this.oldY = oldY;
        this.newX = newX;
        this.newY = newY;
    }

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    public double getNewX() {
        return newX;
    }

    public double getNewY() {
        return newY;
    }
}
