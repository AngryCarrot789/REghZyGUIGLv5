package reghzy.guigl.core.__old__.event;

public class RoutedEvent {
    private boolean isCancelled;

    public RoutedEvent() {

    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }
}
