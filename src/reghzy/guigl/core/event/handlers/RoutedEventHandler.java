package reghzy.guigl.core.event.handlers;

import reghzy.guigl.core.Control;
import reghzy.guigl.core.event.RoutedEvent;

public interface RoutedEventHandler<T extends RoutedEvent> {
    void onHandle(Control sender, T event);
}
