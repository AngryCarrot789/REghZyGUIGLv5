package reghzy.guigl.core.__old__.event.handlers;

import reghzy.guigl.core.__old__.Control;
import reghzy.guigl.core.__old__.event.RoutedEvent;

public interface RoutedEventHandler<T extends RoutedEvent> {
    void onHandle(Control sender, T event);
}
