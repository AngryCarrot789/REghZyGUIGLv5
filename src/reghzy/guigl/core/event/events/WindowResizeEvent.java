package reghzy.guigl.core.event.events;

import reghzy.guigl.core.event.Cancellable;
import reghzy.guigl.core.event.RoutedEvent;

@Cancellable
public class WindowResizeEvent extends RoutedEvent {
    private final int oldWidth;
    private final int oldHeight;
    private int newWidth;
    private int newHeight;

    public WindowResizeEvent(int oldWidth, int oldHeight, int newWidth, int newHeight) {
        this.oldWidth = oldWidth;
        this.oldHeight = oldHeight;
        this.newWidth = newWidth;
        this.newHeight = newHeight;
    }

    public void setNewWidth(int newWidth) {
        this.newWidth = newWidth;
    }

    public void setNewHeight(int newHeight) {
        this.newHeight = newHeight;
    }

    public int getOldWidth() {
        return oldWidth;
    }

    public int getOldHeight() {
        return oldHeight;
    }

    public int getNewWidth() {
        return newWidth;
    }

    public int getNewHeight() {
        return newHeight;
    }

    public int getChangeX() {
        return this.newWidth - this.oldWidth;
    }

    public int getChangeY() {
        return this.newHeight - this.oldHeight;
    }
}
