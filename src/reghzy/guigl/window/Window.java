package reghzy.guigl.window;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowPosCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import reghzy.guigl.core.event.events.MouseButtonEvent;
import reghzy.guigl.core.event.events.MouseMoveEvent;
import reghzy.guigl.core.event.handlers.RoutedEventStore;
import reghzy.guigl.core.input.Keyboard;
import reghzy.guigl.core.input.Mouse;
import reghzy.guigl.math.Vector2;

public class Window {
    private final long id;

    private String title;

    private final Keyboard keyboard;
    private final Mouse mouse;

    private double width;
    private double height;
    private double oldWidth;
    private double oldHeight;
    private double oldX;
    private double oldY;
    private double newX;
    private double newY;

    private boolean isOpen;
    private boolean isDisposed;

    private final GLFWWindowSizeCallback resizeCallback;
    private final GLFWWindowPosCallback posCallback;

    public final RoutedEventStore<WindowMoveRoutedEvent> onWindowMove = new RoutedEventStore<>(null);
    public final RoutedEventStore<WindowResizeRoutedEvent> onWindowResized = new RoutedEventStore<>(null);

    public Window() {
        this("New Window", 1280, 720);
    }

    public Window(String title) {
        this(title, 1280, 720);
    }

    public Window(String title, int width, int height) {
        this(title, 0, 0, width, height);
    }

    public Window(String title, int x, int y, int width, int height) {
        this.id = GLFW.glfwCreateWindow(width, height, title, x, y);
        if (this.id == 0) {
            throw new RuntimeException("Failed to create a window");
        }

        this.keyboard = new Keyboard(this);
        this.mouse = new Mouse(this);
        this.mouse.registerCallback();

        this.resizeCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                Window.this.oldWidth = Window.this.width;
                Window.this.oldHeight = Window.this.height;
                Window.this.width = width;
                Window.this.height = height;
                Window.this.onWindowResized.raise(new WindowResizeRoutedEvent(Window.this.oldWidth, Window.this.oldHeight, width, height));
            }
        };

        this.posCallback = new GLFWWindowPosCallback() {
            @Override
            public void invoke(long window, int x, int y) {
                Window.this.oldX = Window.this.newX;
                Window.this.oldY = Window.this.newY;
                Window.this.newX = x;
                Window.this.newY = y;
                Window.this.onWindowMove.raise(new WindowMoveRoutedEvent(Window.this.oldX, Window.this.oldY, x, y));
            }
        };

        GLFW.glfwSetWindowSizeCallback(this.id, this.resizeCallback);
        GLFW.glfwSetWindowPosCallback(this.id, this.posCallback);
        this.mouse.eventRawOnMouseMove.addHandler((sender, event) -> this.onMouseMove(event));
        this.mouse.eventRawOnButtonDown.addHandler((sender, event) -> this.onMouseDown(event));
        this.mouse.eventRawOnButtonUp.addHandler((sender, event) -> this.onMouseUp(event));
    }

    public void setSize(double width, double height) {
        GLFW.glfwSetWindowSize(this.id, (int) width, (int) height);
        this.oldWidth = this.width;
        this.oldHeight = this.height;
        this.width = width;
        this.height = height;
    }

    public void setSize(Vector2 size) {
        GLFW.glfwSetWindowSize(this.id, (int) size.x, (int) size.y);
        this.oldWidth = this.width;
        this.oldHeight = this.height;
        this.width = size.x;
        this.height = size.y;
    }

    public void show() {
        checkNotDisposed();
        if (this.isOpen) {
            throw new IllegalStateException("Window is already open");
        }

        this.isOpen = true;
        GLFW.glfwShowWindow(this.id);
    }

    public void hide() {
        checkNotDisposed();
        if (!this.isOpen) {
            throw new IllegalStateException("Window is not open");
        }

        this.isOpen = false;
        GLFW.glfwHideWindow(this.id);
    }

    public void close() {
        checkNotDisposed();
        this.isDisposed = true;
        this.isOpen = false;
        GLFW.glfwSetWindowShouldClose(this.id, true);
    }

    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(this.id);
    }

    public void onMouseUp(MouseButtonEvent event) {

    }

    public void onMouseDown(MouseButtonEvent event) {

    }

    public void onMouseMove(MouseMoveEvent event) {

    }

    public void makeCurrent() {
        GLFW.glfwMakeContextCurrent(this.id);
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        GLFW.glfwSetWindowTitle(this.id, this.title = title);
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public long getId() {
        return this.id;
    }

    private void checkNotDisposed() {
        if (this.isDisposed) {
            throw new IllegalStateException("Window has already fully closed");
        }
    }
}
