package reghzy.guigl.core.event;

/**
 * A form of event, that is also cancellable
 */
public class RoutedEvent {
    private final boolean isCancellable;
    private boolean isCancelled;

    public RoutedEvent() {
        this.isCancellable = getClass().isAnnotationPresent(Cancellable.class);
    }

    public boolean isFullyCancelled() {
        return this.isCancellable && this.isCancelled();
    }

    /**
     * Returns whether this specific class instance has been cancelled
     */
    public boolean isCancelled() {
        return this.isCancelled;
    }

    /**
     * Sets whether this specific class instance has been cancelled
     */
    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
    }

    /**
     * Gets whether this class type is cancellable
     */
    public final boolean isCancellable() {
        return this.isCancellable;
    }
}
