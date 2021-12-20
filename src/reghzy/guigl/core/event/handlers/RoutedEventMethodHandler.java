package reghzy.guigl.core.event.handlers;

import reghzy.guigl.core.Control;
import reghzy.guigl.core.event.RoutedEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * An event handler that invokes a specific method
 * <p>
 *     The method can does not need to contain a Control as the first parameter, it can simply contain the event
 * </p>
 * @param <T> The event type
 */
public class RoutedEventMethodHandler<T extends RoutedEvent> implements RoutedEventHandler<T> {
    private final Object instance;
    private final Method method;
    private final boolean passElementToMethod;

    public RoutedEventMethodHandler(Object instance, Method method) {
        this(instance, method, false);
    }

    public RoutedEventMethodHandler(Object instance, Method method, boolean passElementToMethod) {
        this.instance = instance;
        this.method = method;
        this.passElementToMethod = passElementToMethod;
    }

    public RoutedEventMethodHandler(Object instance, String handlerMethodName, Class<? extends RoutedEvent> eventType) {
        boolean passElementToMethod;
        this.instance = instance;

        Method method;
        try {
            method = instance.getClass().getDeclaredMethod(handlerMethodName, Control.class, eventType);
            passElementToMethod = true;
        }
        catch (NoSuchMethodException e) {
            try {
                method = instance.getClass().getDeclaredMethod(handlerMethodName, eventType);
                passElementToMethod = false;
            }
            catch (NoSuchMethodException ex) {
                throw new RuntimeException(e);
            }
        }

        if (!method.isAccessible()) {
            method.setAccessible(true);
        }

        this.passElementToMethod = passElementToMethod;
        this.method = method;
    }

    public Object getInstance() {
        return this.instance;
    }

    public Method getMethod() {
        return this.method;
    }

    public boolean shouldPassElementToMethod() {
        return this.passElementToMethod;
    }

    @Override
    public void onHandle(Control sender, T event) {
        try {
            if (passElementToMethod) {
                this.method.invoke(this.instance, sender, event);
            }
            else {
                this.method.invoke(this.instance, event);
            }
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
