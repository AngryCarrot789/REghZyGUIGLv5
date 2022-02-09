package reghzy.guigl.core.event.events;

import reghzy.guigl.core.event.Cancellable;
import reghzy.guigl.core.event.RoutedEvent;

@Cancellable
public class MouseMoveEvent extends RoutedEvent {
    private final double oldX;
    private final double oldY;
    private final double newX;
    private final double newY;

    public MouseMoveEvent(double oldX, double oldY, double newX, double newY) {
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

    public double getChangeX() {
        return this.newX - this.oldX;
    }

    public double getChangeY() {
        return this.newY - this.oldY;
    }
}
