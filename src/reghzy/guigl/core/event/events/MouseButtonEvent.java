package reghzy.guigl.core.event.events;

import reghzy.guigl.core.event.RoutedEvent;
import reghzy.guigl.core.input.ButtonState;
import reghzy.guigl.core.input.MouseButton;

public class MouseButtonEvent extends RoutedEvent {
    private final MouseButton button;
    private final ButtonState state;
    private final int mods;

    public MouseButtonEvent(MouseButton button, ButtonState state, int mods) {
        this.button = button;
        this.state = state;
        this.mods = mods;
    }

    public MouseButton getButton() {
        return button;
    }

    public ButtonState getState() {
        return state;
    }

    public int getMods() {
        return mods;
    }
}
