package reghzy.guigl.core.event.events;

import reghzy.guigl.core.event.Cancellable;
import reghzy.guigl.core.event.RoutedEvent;
import reghzy.guigl.core.input.KeyState;
import reghzy.guigl.core.input.KeyboardKey;

@Cancellable
public class KeyEvent extends RoutedEvent {
    private final KeyboardKey key;
    private final KeyState state;
    private final boolean isRepeat;
    private final int scanCode;
    private final int mods;

    public KeyEvent(KeyboardKey key, KeyState state, boolean isRepeat, int scanCode, int mods) {
        this.key = key;
        this.state = state;
        this.isRepeat = isRepeat;
        this.scanCode = scanCode;
        this.mods = mods;
    }

    public KeyboardKey getKey() {
        return key;
    }

    public KeyState getState() {
        return state;
    }

    public final boolean isKeyUp() {
        return this.state == KeyState.released;
    }

    public final boolean isKeyDown() {
        return this.state == KeyState.pressed;
    }

    public final boolean isKeyToggled() {
        return this.state == KeyState.toggled;
    }

    public boolean isRepeat() {
        return isRepeat;
    }

    public int getScanCode() {
        return scanCode;
    }

    public int getMods() {
        return mods;
    }
}
