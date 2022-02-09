package reghzy.guigl.core.event.strategy;

import reghzy.guigl.core.Control;
import reghzy.guigl.core.event.RoutedEvent;

public interface EventApplicator<T extends Control, E extends RoutedEvent> {
    void apply(T control, E event);
}
