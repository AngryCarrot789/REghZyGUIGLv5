package reghzy.guigl.core.event.handlers;

import reghzy.guigl.core.Control;
import reghzy.guigl.core.event.RoutedEvent;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Used for storing event handlers. Events are added and ordered like a stack, where the newest handlers are placed at
 * the top and will receive the events first, and the first handlers (at the bottom) receive the events last
 * <p>
 *     This behaviour can be reversed by setting reverseHierarchy to true
 * </p>
 */
public class RoutedEventStore<T extends RoutedEvent> {
    private final HashMap<RoutedEventHandler<T>, RoutedEventHandlerInfo<T, RoutedEventHandler<T>>> handlerToHandlerInfoMap;
    private final ArrayList<RoutedEventHandlerInfo<T, RoutedEventHandler<T>>> handlers;

    private final Control element;
    private boolean reverseHierarchy;

    public RoutedEventStore(Control element) {
        this.element = element;
        this.handlers = new ArrayList<>();
        this.handlerToHandlerInfoMap = new HashMap<>();
        this.reverseHierarchy = false;
    }

    public RoutedEventStore(Control element, boolean reverseHierarchy) {
        this.element = element;
        this.handlers = new ArrayList<>();
        this.handlerToHandlerInfoMap = new HashMap<>();
        this.reverseHierarchy = reverseHierarchy;
    }

    public void raise(T event) {
        raise(event, this.reverseHierarchy);
    }

    public void raise(T event, boolean reverseHierarchy) {
        Control element = this.element;
        if (event.isCancelled()) {
            return;
        }

        boolean wasCancelled = false;
        ArrayList<RoutedEventHandlerInfo<T, RoutedEventHandler<T>>> handlers = this.handlers;

        if (reverseHierarchy) {
            for (RoutedEventHandlerInfo<T, RoutedEventHandler<T>> handlerInfo : handlers) {
                if (wasCancelled) {
                    if (handlerInfo.isIgnoreCancelled()) {
                        handlerInfo.getHandler().onHandle(element, event);
                        if (!handlerInfo.shouldHandleOtherEventsIfCancelled()) {
                            return;
                        }

                        wasCancelled = event.isCancelled();
                        continue;
                    }
                }

                handlerInfo.getHandler().onHandle(element, event);
                wasCancelled = event.isCancelled();
            }
        }
        else {
            for (int i = handlers.size() - 1; i >= 0; i--) {
                RoutedEventHandlerInfo<T, RoutedEventHandler<T>> handlerInfo = handlers.get(i);
                if (wasCancelled) {
                    if (handlerInfo.isIgnoreCancelled()) {
                        handlerInfo.getHandler().onHandle(element, event);
                        if (!handlerInfo.shouldHandleOtherEventsIfCancelled()) {
                            return;
                        }

                        wasCancelled = event.isCancelled();
                        continue;
                    }
                }

                handlerInfo.getHandler().onHandle(element, event);
                wasCancelled = event.isCancelled();
            }
        }
    }

    public RoutedEventStore<T> addHandler(RoutedEventHandler<T> handler) {
        RoutedEventHandlerInfo<T, RoutedEventHandler<T>> info = new RoutedEventHandlerInfo<>(handler, false, false);
        this.handlerToHandlerInfoMap.put(handler, info);
        this.handlers.add(info);
        return this;
    }

    public RoutedEventStore<T> addHandler(RoutedEventHandler<T> handler, boolean ignoreCancelled) {
        RoutedEventHandlerInfo<T, RoutedEventHandler<T>> info = new RoutedEventHandlerInfo<>(handler, ignoreCancelled, false);
        this.handlerToHandlerInfoMap.put(handler, info);
        this.handlers.add(info);
        return this;
    }

    public RoutedEventStore<T> addHandler(RoutedEventHandler<T> handler, boolean ignoreCancelled, boolean handleOtherEventsIfCancelled) {
        RoutedEventHandlerInfo<T, RoutedEventHandler<T>> info = new RoutedEventHandlerInfo<>(handler, ignoreCancelled, handleOtherEventsIfCancelled);
        this.handlerToHandlerInfoMap.put(handler, info);
        this.handlers.add(info);
        return this;
    }

    public RoutedEventStore<T> removeHandler(RoutedEventHandler<T> handler) {
        RoutedEventHandlerInfo<T, RoutedEventHandler<T>> info = this.handlerToHandlerInfoMap.get(handler);
        if (info == null) {
            throw new RuntimeException("Missing cached RoutedEventHandlerInfo for RoutedEventHandler");
        }

        this.handlers.remove(info);
        this.handlerToHandlerInfoMap.remove(handler);
        return this;
    }

    /**
     * Whether the order in which the events are called should be reversed
     * <p>
     *     If false (default), then events are delivered to the derived first, and all the way down to the base class
     * </p>
     * <p>
     *     If true, then events are are delivered to the base class and up to the derived classes
     * </p>
     * @return
     */
    public boolean shouldReverseHierarchy() {
        return this.reverseHierarchy;
    }

    public void setReverseHierarchy(boolean reverseHierarchy) {
        this.reverseHierarchy = reverseHierarchy;
    }
}
