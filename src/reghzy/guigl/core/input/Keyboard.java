package reghzy.guigl.core.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import reghzy.guigl.core.event.events.KeyEvent;
import reghzy.guigl.core.event.handlers.RoutedEventStore;
import reghzy.guigl.window.Window;

public class Keyboard {
    private final GLFWKeyCallback keyCallback;
    private final boolean[] keysDown;

    public final RoutedEventStore<KeyEvent> eventRawOnKeyDown = new RoutedEventStore<>(null);
    public final RoutedEventStore<KeyEvent> eventRawOnKeyUp = new RoutedEventStore<>(null);

    private final Window window;

    public Keyboard(Window window) {
        this.window = window;
        this.keysDown = new boolean[GLFW.GLFW_KEY_LAST];
        this.keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (key < keysDown.length && key >= 0) {
                    boolean isDown = (action != GLFW.GLFW_RELEASE);
                    boolean isRepeat = keysDown[key] == isDown;
                    keysDown[key] = isDown;
                    if (action == GLFW.GLFW_RELEASE) {
                        eventRawOnKeyUp.raise(new KeyEvent(KeyboardKey.fromCode(key), KeyState.released, isRepeat, scancode, mods));
                    }
                    else {
                        eventRawOnKeyDown.raise(new KeyEvent(KeyboardKey.fromCode(key), KeyState.pressed, isRepeat, scancode, mods));
                    }
                }
            }
        };
    }

    public void registerCallback(long windowId) {
        GLFW.glfwSetKeyCallback(windowId, this.keyCallback);
    }

    public boolean isKeyDown(KeyboardKey key) {
        return this.keysDown[key.code];
    }

    public boolean isKeyUp(KeyboardKey key) {
        return !this.keysDown[key.code];
    }

    public void destroyCallback() {
        this.keyCallback.free();
    }

    public Window getWindow() {
        return window;
    }
}
