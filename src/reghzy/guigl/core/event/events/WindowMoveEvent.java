package reghzy.guigl.core.event.events;

import reghzy.guigl.core.event.Cancellable;
import reghzy.guigl.core.event.RoutedEvent;

@Cancellable
public class WindowMoveEvent extends RoutedEvent {
    private double oldX;
    private double oldY;
    private double newX;
    private double newY;

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

    public void setOldX(double oldX) {
        this.oldX = oldX;
    }

    public void setOldY(double oldY) {
        this.oldY = oldY;
    }

    public void setNewX(double newX) {
        this.newX = newX;
    }

    public void setNewY(double newY) {
        this.newY = newY;
    }
}
