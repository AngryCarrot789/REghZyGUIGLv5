package reghzy.guigl.window;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowFocusCallback;
import org.lwjgl.glfw.GLFWWindowFocusCallbackI;
import org.lwjgl.glfw.GLFWWindowPosCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL11;
import reghzy.guigl.GuiGLEngine;
import reghzy.guigl.Time;
import reghzy.guigl.core.Control;
import reghzy.guigl.core.MouseHandler;
import reghzy.guigl.core.event.events.KeyEvent;
import reghzy.guigl.core.event.events.MouseButtonEvent;
import reghzy.guigl.core.event.events.MouseMoveEvent;
import reghzy.guigl.core.event.events.WindowMoveEvent;
import reghzy.guigl.core.event.events.WindowResizeEvent;
import reghzy.guigl.core.event.handlers.RoutedEventStore;
import reghzy.guigl.core.input.Keyboard;
import reghzy.guigl.core.input.KeyboardKey;
import reghzy.guigl.core.input.Mouse;
import reghzy.guigl.core.utils.ColourARGB;
import reghzy.guigl.maths.Vector2d;
import reghzy.guigl.maths.Vector2i;
import reghzy.guigl.maths.Vector3f;
import reghzy.guigl.render.RenderEngine;
import reghzy.guigl.render.RenderManager;

import java.util.HashMap;

public class Window {
    private static final HashMap<Long, Window> ID_TO_WINDOW = new HashMap<Long, Window>();
    private static Window currentWindow;
    private static Window previousWindow;

    private static Window LATCHED_CURSOR;

    private final GuiGLEngine engine;
    private final long id;
    public ColourARGB backgroundColour = ColourARGB.DARK_GREY;
    private String title;
    private final Keyboard keyboard;
    private final Mouse mouse;
    private int width;
    private int height;
    private int oldWidth;
    private int oldHeight;
    private int oldX;
    private int oldY;
    private int newX;
    private int newY;
    private boolean isOpen;
    private boolean isDisposed;
    private final GLFWWindowSizeCallback resizeCallback;
    private final GLFWWindowPosCallback posCallback;
    private final GLFWWindowFocusCallback focusCallback;
    public final RoutedEventStore<WindowMoveEvent> onWindowMove = new RoutedEventStore<WindowMoveEvent>(null);
    public final RoutedEventStore<WindowResizeEvent> onWindowResized = new RoutedEventStore<WindowResizeEvent>(null);

    private boolean isMovingInEvent;
    private boolean isResizingInEvent;

    private boolean isFocusedFromCallback;

    public boolean clearScreenNextTick = true;

    private Control content;

    public int bufferSwapCount;

    public Window(GuiGLEngine engine) {
        this(engine, "New Window", 1280, 720);
    }

    public Window(GuiGLEngine engine, String title) {
        this(engine, title, 1280, 720);
    }

    public Window(GuiGLEngine engine, String title, int width, int height) {
        this(engine, title, 0, 0, width, height);
    }

    public Window(GuiGLEngine engine, String title, int x, int y, int width, int height) {
        this.engine = engine;
        this.id = GLFW.glfwCreateWindow(width, height, title, x, y);
        if (this.id == 0) {
            throw new RuntimeException("Failed to create a window");
        }

        ID_TO_WINDOW.put(this.id, this);
        this.width = width;
        this.height = height;
        this.keyboard = new Keyboard(this);
        this.keyboard.registerCallback();
        this.mouse = new Mouse(this);
        this.mouse.registerCallback();

        this.resizeCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                if (window != Window.this.id || Window.this.isResizingInEvent) {
                    return;
                }

                Window.this.oldWidth = Window.this.width;
                Window.this.oldHeight = Window.this.height;
                Window.this.width = width;
                Window.this.height = height;
                if (isCurrent()) {
                    useViewport();
                }

                WindowResizeEvent event = new WindowResizeEvent(Window.this.oldWidth, Window.this.oldHeight, width, height);
                if (Window.this.onWindowResized.raise(event)) {
                    // stop the user from moving the window
                    Window.this.isResizingInEvent = true;
                    GLFW.glfwSetWindowSize(window, Window.this.oldWidth, Window.this.oldHeight);
                    Window.this.isResizingInEvent = false;
                }
                else {
                    if (event.getNewWidth() != width || event.getNewHeight() != height) {
                        Window.this.isResizingInEvent = true;
                        GLFW.glfwSetWindowSize(window, event.getNewWidth(), event.getNewHeight());
                        Window.this.isResizingInEvent = false;
                    }

                    Window.this.onResized();
                }
            }
        };

        this.posCallback = new GLFWWindowPosCallback() {
            @Override
            public void invoke(long window, int x, int y) {
                if (window != Window.this.id) {
                    return;
                }

                Window.this.oldX = Window.this.newX;
                Window.this.oldY = Window.this.newY;
                Window.this.newX = x;
                Window.this.newY = y;

                if (Window.this.isMovingInEvent) {
                    return;
                }

                WindowMoveEvent event = new WindowMoveEvent(Window.this.oldX, Window.this.oldY, x, y);
                if (Window.this.onWindowMove.raise(event)) {
                    Window.this.isMovingInEvent = true;
                    GLFW.glfwSetCursorPos(window, event.getOldX(), event.getOldY());
                    Window.this.isMovingInEvent = false;
                }
                else if (event.getNewX() != x || event.getNewY() != y) {
                    Window.this.isMovingInEvent = true;
                    GLFW.glfwSetCursorPos(window, event.getNewX(), event.getNewY());
                    Window.this.isMovingInEvent = false;
                }
            }
        };

        this.focusCallback = new GLFWWindowFocusCallback() {
            @Override
            public void invoke(long window, boolean focused) {
                if (window == Window.this.id) {
                    Window.this.isFocusedFromCallback = focused;
                }
            }
        };

        GLFW.glfwSetWindowSizeCallback(this.id, this.resizeCallback);
        GLFW.glfwSetWindowPosCallback(this.id, this.posCallback);
        GLFW.glfwSetWindowFocusCallback(this.id, this.focusCallback);
        this.mouse.eventRawOnButtonUp.addHandler((sender, event) -> this.onMouseUp(event));
        this.mouse.eventRawOnButtonDown.addHandler((sender, event) -> this.onMouseDown(event));
        this.mouse.eventRawOnMouseMove.addHandler((sender, event) -> this.onMouseMove(event));
        this.keyboard.eventRawOnKeyDown.addHandler(((sender, event) -> this.onKeyDown(event)));
        this.keyboard.eventRawOnKeyUp.addHandler(((sender, event) -> this.onKeyUp(event)));
    }

    public boolean isFocused() {
        return isFocusedFromCallback;
    }

    public boolean isMouseOverWindow() {
        Vector2d pos = this.mouse.getMousePos();
        if (pos.x >= 0 && pos.y >= 0) {
            return true;
            // return this.isFocusedFromCallback;
        }

        return false;
    }

    /**
     * Called by the application; this is used to mainly update the child control, mouse and keyboard
     */
    public void doTickUpdate() {
        this.mouse.update();
        this.keyboard.update();

        final float moveSpeed = 1200.0f;
        if (keyboard.isKeyDown(KeyboardKey.keyW)) {
            RenderEngine.CAMERA_POS.add(Vector3f.get(0.0f, 0.0f, -moveSpeed * Time.delta));
        }

        if (keyboard.isKeyDown(KeyboardKey.keyS)) {
            RenderEngine.CAMERA_POS.add(Vector3f.get(0.0f, 0.0f, moveSpeed * Time.delta));
        }

        if (keyboard.isKeyDown(KeyboardKey.keyA)) {
            RenderEngine.CAMERA_POS.add(Vector3f.get(-moveSpeed * Time.delta, 0.0f, 0.0f));
        }

        if (keyboard.isKeyDown(KeyboardKey.keyD)) {
            RenderEngine.CAMERA_POS.add(Vector3f.get(moveSpeed * Time.delta, 0.0f, 0.0f));
        }
    }

    /**
     * Called by the application; this is used to render the hierarchy of children
     * @param force Forces the entire window to re-render
     */
    public void doTickRender(RenderEngine render, boolean force) {
        if (hasContent()) {
            RenderManager.renderControl(this.content, null, this.engine, render, this, 0.0d, force);
        }
    }

    public boolean hasContent() {
        return this.content != null;
    }

    public Control getContent() {
        return this.content;
    }

    public void setContent(Control content) {
        this.content = content;
    }

    public void onResized() {
        markViewPortModified();
    }

    public void onMouseUp(MouseButtonEvent event) {

    }

    public void onMouseDown(MouseButtonEvent event) {

    }

    public void onMouseMove(MouseMoveEvent event) {
        // final float sensitivity = 0.05f;
        // RenderEngine.CAMERA_ROT.add(
        //         -(event.getChangeY() * Time.delta * sensitivity),
        //         -(event.getChangeX() * Time.delta * sensitivity),
        //         0.0f);
        MouseHandler.calculateMouseMovement(this, event.getOld(), event.getNew());
    }

    public void onKeyDown(KeyEvent event) {
        // final float moveSpeed = 1200.0f;
        // if (event.getKey() == KeyboardKey.keyW) {
        //     RenderEngine.CAMERA_POS.add(Vector3f.get(0.0f, 0.0f, -moveSpeed * Time.delta));
        // }
        // else if (event.getKey() == KeyboardKey.keyS) {
        //     RenderEngine.CAMERA_POS.add(Vector3f.get(0.0f, 0.0f, moveSpeed * Time.delta));
        // }
        // else if (event.getKey() == KeyboardKey.keyA) {
        //     RenderEngine.CAMERA_POS.add(Vector3f.get(-moveSpeed * Time.delta, 0.0f, 0.0f));
        // }
        // else if (event.getKey() == KeyboardKey.keyD) {
        //     RenderEngine.CAMERA_POS.add(Vector3f.get(moveSpeed * Time.delta, 0.0f, 0.0f));
        // }
    }

    public void onKeyUp(KeyEvent event) {

    }

    public void setCursorCaptured() {
        if (LATCHED_CURSOR == null) {
            LATCHED_CURSOR = this;
        }
        else if (LATCHED_CURSOR != this) {
            LATCHED_CURSOR.setCursorUnCaptured();
            LATCHED_CURSOR = this;
        }

        GLFW.glfwSetInputMode(this.id, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
    }

    public void setCursorUnCaptured() {
        GLFW.glfwSetInputMode(this.id, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
    }

    /**
     * Gets a window from its GLFW window ID/handle
     */
    public static Window byId(long id) {
        return ID_TO_WINDOW.get(id);
    }

    /**
     * Gets the window that is currently being used for rendering
     */
    public static Window getCurrentWindow() {
        return currentWindow;
    }

    /**
     * Gets the window that was previously used for rendering
     */
    public static Window getPreviousWindow() {
        return previousWindow;
    }

    /**
     * Makes this window the current window for rendering
     */
    public void makeCurrent() {
        if (currentWindow == this) {
            return;
        }

        previousWindow = currentWindow;
        currentWindow = this;
        GLFW.glfwMakeContextCurrent(this.id);
    }

    /**
     * Whether this window is the active window being used for rendering
     */
    public boolean isCurrent() {
        return GLFW.glfwGetCurrentContext() == this.id;
    }

    public void setSize(int width, int height) {
        GLFW.glfwSetWindowSize(this.id, width, height);
        this.oldWidth = this.width;
        this.oldHeight = this.height;
        this.width = width;
        this.height = height;
        markViewPortModified();
    }

    public void setSize(Vector2i size) {
        GLFW.glfwSetWindowSize(this.id, size.x, size.y);
        this.oldWidth = this.width;
        this.oldHeight = this.height;
        this.width = size.x;
        this.height = size.y;
        markViewPortModified();
    }

    public void setSize(Vector2d size) {
        GLFW.glfwSetWindowSize(this.id, (int) size.x, (int) size.y);
        this.oldWidth = this.width;
        this.oldHeight = this.height;
        this.width = (int) size.x;
        this.height = (int) size.y;
        markViewPortModified();
    }

    public void show() {
        checkNotDisposed();
        if (this.isOpen) {
            throw new IllegalStateException("Window is already open");
        }

        this.isOpen = true;
        GLFW.glfwShowWindow(this.id);
        markViewPortModified();
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
        setCursorUnCaptured();
        checkNotDisposed();
        this.isDisposed = true;
        this.isOpen = false;
        GLFW.glfwSetWindowShouldClose(this.id, true);
        destroy();
    }

    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(this.id);
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        GLFW.glfwSetWindowTitle(this.id, this.title = title);
    }

    /**
     * Whether this window is open or not
     * <p>
     *     If it is not open, it may be re-openable (it might be hidden, not "closed closed")
     * </p>
     */
    public boolean isOpen() {
        return this.isOpen;
    }

    /**
     * Whether this window has been fully destroyed
     * <p>
     *     Once it has been destroyed, it cannot be re-opened
     * </p>
     */
    public boolean isDestroyed() {
        return this.isDisposed;
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

    /**
     * Swaps the render buffer and the screen buffer, allowing rendered data to be shown on screen
     */
    public void swapBuffers() {
        GLFW.glfwSwapBuffers(this.id);
        this.bufferSwapCount++;
    }

    public int getBufferNumber() {
        return this.bufferSwapCount & 1;
    }

    public void useViewport() {
        GL11.glViewport(0, 0, this.width, this.height);
    }

    private void destroy() {
        this.mouse.destroyCallback();
        this.keyboard.destroyCallback();
        this.resizeCallback.free();
        this.posCallback.free();
        this.focusCallback.free();
        GLFW.glfwDestroyWindow(this.id);
        if (ID_TO_WINDOW.remove(this.id) == null) {
            throw new RuntimeException("Window id " + this.id + " was not cached");
        }
    }

    private void checkNotDisposed() {
        if (this.isDisposed) {
            throw new IllegalStateException("Window has already fully closed");
        }
    }

    @Override
    public int hashCode() {
        if (this.id > Integer.MAX_VALUE) {
            return (int) (this.id >> 32);
        }
        else {
            return (int) id;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        else if (obj instanceof Window) {
            return ((Window) obj).id == this.id;
        }
        else {
            return false;
        }
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Window{id=").append(this.id).append(",w*h={").append(this.width).append(",").append(this.height).append("}}").toString();
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

    public GuiGLEngine getEngine() {
        return engine;
    }

    public void markViewPortModified() {
        this.clearScreenNextTick = true;
    }

    public Vector2d getSize() {
        return Vector2d.get(this.width, this.height);
    }
}
