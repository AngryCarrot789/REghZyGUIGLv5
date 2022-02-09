package reghzy.guigl.core.event.handlers;

import reghzy.guigl.core.event.RoutedEvent;

/**
 * Contains an event handler and information about it
 * @param <T> The routed event type
 * @param <J> The routed event handler type
 */
public class RoutedEventHandlerInfo<T extends RoutedEvent, J extends RoutedEventHandler<T>> {
    private final RoutedEventHandler<T> handler;
    private final boolean ignoreCancelled;
    private final boolean handleOtherEventsIfCancelled;

    public RoutedEventHandlerInfo(RoutedEventHandler<T> handler, boolean ignoreCancelled, boolean handleOtherEventsIfCancelled) {
        this.handler = handler;
        this.ignoreCancelled = ignoreCancelled;
        this.handleOtherEventsIfCancelled = handleOtherEventsIfCancelled;
    }

    /**
     * The handler to the event
     */
    public RoutedEventHandler<T> getHandler() {
        return this.handler;
    }

    /**
     * If true, then if the previous event handler sets the event args to cancelled, it will be ignored and this handler will be called anyway
     */
    public boolean isIgnoreCancelled() {
        return this.ignoreCancelled;
    }

    /**
     * This is only used if ignoreCancelled is true. If it is, then once this handler is called, then:
     * <p>
     *     If this is true, the pending events down the stack will be executed even if the event was cancelled (basically all other events will ignore the event being cancelled)
     * </p>
     * <p>
     *     If this is false, then only this event will be handled and the rest wont be (due to the event being cancelled)
     * </p>
     */
    public boolean shouldHandleOtherEventsIfCancelled() {
        return this.handleOtherEventsIfCancelled;
    }
}
