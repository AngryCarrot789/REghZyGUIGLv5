package reghzy.guigl.core.__old__.event.events;

import reghzy.guigl.core.__old__.event.RoutedEvent;

public class WindowResizeEvent extends RoutedEvent {
    private double oldWidth;
    private double oldHeight;
    private double newWidth;
    private double newHeight;
    private double changeX;
    private double changeY;

    public WindowResizeEvent(double oldWidth, double oldHeight, double newWidth, double newHeight) {
        this.oldWidth = oldWidth;
        this.oldHeight = oldHeight;
        this.newWidth = newWidth;
        this.newHeight = newHeight;
        this.changeX = newWidth - oldWidth;
        this.changeY = newHeight - oldHeight;
    }

    public void setOldWidth(double oldWidth) {
        this.oldWidth = oldWidth;
    }

    public void setOldHeight(double oldHeight) {
        this.oldHeight = oldHeight;
    }

    public void setNewWidth(double newWidth) {
        this.newWidth = newWidth;
    }

    public void setNewHeight(double newHeight) {
        this.newHeight = newHeight;
    }

    public void setChangeX(double changeX) {
        this.changeX = changeX;
    }

    public void setChangeY(double changeY) {
        this.changeY = changeY;
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

    public double getChangeX() {
        return changeX;
    }

    public double getChangeY() {
        return changeY;
    }
}
