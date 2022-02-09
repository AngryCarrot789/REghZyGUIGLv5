package reghzy.guigl.core.event;

import java.lang.ref.WeakReference;
import java.util.LinkedList;

/**
 * A class for tracking when event do certain things, used for debugging
 */
public class EventTracer {
    public static final LinkedList<WeakReference<EventTracer>> APP_TRACERS = new LinkedList<WeakReference<EventTracer>>();

    private final LinkedList<StackTraceElement[]> stackFrames;

    public static boolean USE_TRACER = false;
    public final RoutedEvent event;

    public EventTracer(RoutedEvent event) {
        this.event = event;
        this.stackFrames = new LinkedList<StackTraceElement[]>();
        APP_TRACERS.add(new WeakReference<EventTracer>(this));
    }

    public static void pushFrameHelper(RoutedEvent event) {
        if (USE_TRACER) {
            if (event.tracer == null) {
                // throw new RuntimeException("Event tracer was re-enabled after an event was created");
                event.tracer = new EventTracer(event);
            }

            event.tracer.pushCurrentFrame(2);
        }
    }

    public static EventTracer getTracer(RoutedEvent event) {
        return USE_TRACER ? new EventTracer(event) : null;
    }

    public void pushCurrentFrame(int skipFrames) {
        StackTraceElement[] frame = Thread.currentThread().getStackTrace();
        StackTraceElement[] copy = new StackTraceElement[frame.length - skipFrames];
        System.arraycopy(frame, skipFrames, copy, 0, copy.length);
        this.stackFrames.add(copy);
    }

    public LinkedList<StackTraceElement[]> getStackFrames() {
        return stackFrames;
    }
}
