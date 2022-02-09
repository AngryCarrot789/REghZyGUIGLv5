package reghzy.guigl.core.input;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import reghzy.guigl.core.event.events.MouseButtonEvent;
import reghzy.guigl.core.event.events.MouseMoveEvent;
import reghzy.guigl.core.event.handlers.RoutedEventStore;
import reghzy.guigl.maths.Region2d;
import reghzy.guigl.maths.Vector2d;
import reghzy.guigl.window.Window;

import java.nio.DoubleBuffer;
import java.util.Arrays;

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
    private final boolean[] buttonsDownFrame;

    public final RoutedEventStore<MouseButtonEvent> eventRawOnButtonDown = new RoutedEventStore<MouseButtonEvent>(null);
    public final RoutedEventStore<MouseButtonEvent> eventRawOnButtonUp = new RoutedEventStore<MouseButtonEvent>(null);
    public final RoutedEventStore<MouseMoveEvent> eventRawOnMouseMove = new RoutedEventStore<MouseMoveEvent>(null);

    public Mouse(Window window) {
        this.window = window;
        this.buttonsDown = new boolean[GLFW.GLFW_KEY_LAST];
        this.buttonsDownFrame = new boolean[GLFW.GLFW_KEY_LAST];
        this.mouseCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                boolean isDown = (action != GLFW.GLFW_RELEASE);
                Mouse.this.buttonsDown[button] = isDown;
                Mouse.this.buttonsDownFrame[button] = isDown;
                if (action == GLFW.GLFW_RELEASE) {
                    eventRawOnButtonUp.raise(new MouseButtonEvent(Mouse.this, MouseButton.fromCode(button), ButtonState.released, mods));
                }
                else {
                    eventRawOnButtonDown.raise(new MouseButtonEvent(Mouse.this, MouseButton.fromCode(button), ButtonState.released, mods));
                }
            }
        };

        setInputMode(false);
    }

    public void setPosition(double x, double y) {
        GLFW.glfwSetCursorPos(this.window.getId(), x, y);
    }

    public void setCursorToCenter() {
        GLFW.glfwSetCursorPos(this.window.getId(), this.window.getWidth() / 2.0d, this.window.getHeight() / 2.0d);
    }

    public void setInputMode(boolean hideMouse) {
        GLFW.glfwSetInputMode(this.window.getId(), GLFW.GLFW_CURSOR, hideMouse ? GLFW.GLFW_CURSOR_HIDDEN : GLFW.GLFW_CURSOR_NORMAL);
    }

    public void update() {
        GLFW.glfwGetCursorPos(this.window.getId(), mouseBufferX, mouseBufferY);
        this.oldX = this.newX;
        this.oldY = this.newY;
        this.newX = mouseBufferX.get(0);
        this.newY = mouseBufferY.get(0);
        mouseBufferX.rewind();
        mouseBufferY.rewind();
        Arrays.fill(this.buttonsDownFrame, false);
        if (doubleEquality(this.oldX, this.newX) && doubleEquality(this.oldY, this.newY))  {
            return;
        }

        this.eventRawOnMouseMove.raise(new MouseMoveEvent(new Vector2d(this.oldX, this.oldY), new Vector2d(this.newX, this.newY)));
    }

    public void registerCallback() {
        GLFW.glfwSetMouseButtonCallback(this.window.getId(), this.mouseCallback);
    }

    public boolean isButtonDown(MouseButton button) {
        return this.buttonsDown[button.code];
    }

    public boolean isButtonDownFrame(MouseButton button) {
        return this.buttonsDownFrame[button.code];
    }

    public boolean isButtonUp(MouseButton button) {
        return !this.buttonsDown[button.code];
    }

    public Vector2d getMousePosI() {
        return Vector2d.get((int) this.newX, (int) this.newY);
    }

    public Vector2d getMousePos() {
        return Vector2d.get(this.newX, this.newY);
    }

    public Vector2d getOldMousePosI() {
        return Vector2d.get((int) this.oldX, (int) this.oldY);
    }

    public Vector2d getOldMousePos() {
        return Vector2d.get(this.oldX, this.oldY);
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

    public boolean isOver(Region2d region) {
        return region.intersects(this.newX, this.newY);
    }
}
