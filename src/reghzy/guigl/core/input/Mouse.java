package reghzy.guigl.core.input;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import reghzy.guigl.core.event.events.MouseButtonEvent;
import reghzy.guigl.core.event.events.MouseMoveEvent;
import reghzy.guigl.core.event.handlers.RoutedEventStore;
import reghzy.guigl.math.Vector2;
import reghzy.guigl.window.Window;

import java.nio.DoubleBuffer;

public class Mouse {
    private final Window window;
    private double oldX;
    private double oldY;
    private double newX;
    private double newY;
    private final DoubleBuffer mouseBufferX = BufferUtils.createDoubleBuffer(1);
    private final DoubleBuffer mouseBufferY = BufferUtils.createDoubleBuffer(1);

    private final GLFWMouseButtonCallback mouseCallback;
    private final boolean[] buttonsDown;

    public final RoutedEventStore<MouseButtonEvent> eventRawOnButtonDown = new RoutedEventStore<MouseButtonEvent>(null);
    public final RoutedEventStore<MouseButtonEvent> eventRawOnButtonUp = new RoutedEventStore<MouseButtonEvent>(null);
    public final RoutedEventStore<MouseMoveEvent> eventRawOnMouseMove = new RoutedEventStore<MouseMoveEvent>(null);

    public Mouse(Window window) {
        this.window = window;
        this.buttonsDown = new boolean[GLFW.GLFW_KEY_LAST];
        this.mouseCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                boolean isDown = (action != GLFW.GLFW_RELEASE);
                buttonsDown[button] = isDown;
                if (action == GLFW.GLFW_RELEASE) {
                    eventRawOnButtonUp.raise(new MouseButtonEvent(MouseButton.fromCode(button), ButtonState.released, mods));
                }
                else {
                    eventRawOnButtonDown.raise(new MouseButtonEvent(MouseButton.fromCode(button), ButtonState.released, mods));
                }
            }
        };

        setInputMode(false);
    }

    public void setInputMode(boolean hideMouse) {
        GLFW.glfwSetInputMode(this.window.getId(), GLFW.GLFW_CURSOR, hideMouse ? GLFW.GLFW_CURSOR_HIDDEN : GLFW.GLFW_CURSOR_NORMAL);
    }

    public void updateMouse() {
        GLFW.glfwGetCursorPos(this.window.getId(), mouseBufferX, mouseBufferY);
        this.oldX = this.newX;
        this.oldY = this.newY;
        this.newX = mouseBufferX.get(0);
        this.newY = mouseBufferY.get(0);
        mouseBufferX.rewind();
        mouseBufferY.rewind();

        if (doubleEquality(this.oldX, this.newX) && doubleEquality(this.oldY, this.newY))  {
            return;
        }

        this.eventRawOnMouseMove.raise(new MouseMoveEvent(this.oldX, this.oldY, this.newX, this.newY));
    }

    public void registerCallback() {
        GLFW.glfwSetMouseButtonCallback(this.window.getId(), this.mouseCallback);
    }

    public boolean isButtonDown(MouseButton button) {
        return this.buttonsDown[button.code];
    }

    public boolean isButtonUp(MouseButton button) {
        return !this.buttonsDown[button.code];
    }

    public Vector2 getMousePosI() {
        return new Vector2((int) this.newX, (int) this.newY);
    }

    public Vector2 getMousePos() {
        return new Vector2(this.newX, this.newY);
    }

    public Vector2 getOldMousePosI() {
        return new Vector2((int) this.oldX, (int) this.oldY);
    }

    public Vector2 getOldMousePos() {
        return new Vector2(this.oldX, this.oldY);
    }

    public double getNewX() {
        return newX;
    }

    public double getNewY() {
        return newY;
    }

    public double getOldX() {
        return this.oldX;
    }

    public double getOldY() {
        return oldY;
    }

    public void destroyCallback() {
        this.mouseCallback.free();
    }

    private static boolean doubleEquality(double a, double b) {
        return Double.compare(a, b) == 0;
    }

    public Window getWindow() {
        return window;
    }
}
