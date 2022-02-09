package reghzy.guigl.core.event;

/**
 * A form of event, that is also cancellable
 */
public class RoutedEvent {
    private final boolean isCancellable;
    private boolean isCancelled;

    private boolean isHandled;

    protected EventTracer tracer = EventTracer.getTracer(this);

    public RoutedEvent() {
        this.isCancellable = getClass().isAnnotationPresent(Cancellable.class);
    }

    public final boolean isFullyCancelled() {
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

    /**
     * Whether this event is handled, and shouldn't be bubbled/sent to the parent control.
     * <p>
     *     Preview events may chose to ignore this, but regular bubble events will not be called if the event is handled
     * </p>
     */
    public boolean isHandled() {
        return isHandled;
    }

    /**
     * Preview events may chose to ignore if this event is handled. But regular bubble events will not be called if the event is handled
     * @param handled Whether this event is handled, and shouldn't be bubbled/sent to the parent control
     */
    public void setHandled(boolean handled) {
        this.isHandled = handled;
        EventTracer.pushFrameHelper(this);
    }
}
