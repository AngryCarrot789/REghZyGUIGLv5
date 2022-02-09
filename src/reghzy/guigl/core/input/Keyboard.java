package reghzy.guigl.core.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import reghzy.guigl.core.event.events.KeyEvent;
import reghzy.guigl.core.event.handlers.RoutedEventStore;
import reghzy.guigl.window.Window;

import java.util.Arrays;

public class Keyboard {
    private final GLFWKeyCallback keyCallback;
    private final boolean[] keysDown;
    private final boolean[] keysDownFrame;

    public final RoutedEventStore<KeyEvent> eventRawOnKeyDown;
    public final RoutedEventStore<KeyEvent> eventRawOnKeyUp;

    private final Window window;

    public Keyboard(Window window) {
        this.window = window;
        this.eventRawOnKeyDown = new RoutedEventStore<KeyEvent>(null);
        this.eventRawOnKeyUp = new RoutedEventStore<KeyEvent>(null);
        this.keysDown = new boolean[GLFW.GLFW_KEY_LAST];
        this.keysDownFrame = new boolean[GLFW.GLFW_KEY_LAST];
        this.keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                // action 0 == key down,
                // action 1 == key up,
                // action 2 == key repeat (still down)
                if (key < keysDown.length && key >= 0) {
                    boolean isDown = (action != GLFW.GLFW_RELEASE);
                    keysDown[key] = isDown;
                    if (isDown) {
                        keysDownFrame[key] = true;
                    }

                    if (action == GLFW.GLFW_RELEASE) {
                        eventRawOnKeyUp.raise(new KeyEvent(KeyboardKey.fromCode(key), KeyState.released, false, scancode, mods));
                    }
                    else {
                        eventRawOnKeyDown.raise(new KeyEvent(KeyboardKey.fromCode(key), KeyState.pressed, action == GLFW.GLFW_REPEAT, scancode, mods));
                    }
                }
            }
        };
    }

    public void registerCallback() {
        GLFW.glfwSetKeyCallback(this.window.getId(), this.keyCallback);
    }

    public void update() {
        Arrays.fill(this.keysDownFrame, false);
    }

    // key will be down until the user releases the key
    public boolean isKeyDown(KeyboardKey key) {
        return this.keysDown[key.code];
    }

    // key will only be down for 1 application tick, then next tick it becomes "released"
    public boolean isKeyDownFrame(KeyboardKey key) {
        return this.keysDownFrame[key.code];
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
