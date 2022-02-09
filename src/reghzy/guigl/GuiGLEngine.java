package reghzy.guigl;

import org.lwjgl.glfw.GLFW;
import reghzy.guigl.core.event.EventTracer;
import reghzy.guigl.core.input.Keyboard;
import reghzy.guigl.core.input.KeyboardKey;
import reghzy.guigl.core.input.Mouse;
import reghzy.guigl.core.log.GuiGLLogger;
import reghzy.guigl.core.primitive.Panel;
import reghzy.guigl.core.primitive.Rectangle;
import reghzy.guigl.core.utils.ColourARGB;
import reghzy.guigl.core.utils.Thickness;
import reghzy.guigl.maths.Vector2d;
import reghzy.guigl.render.RenderEngine;
import reghzy.guigl.render.RenderManager;
import reghzy.guigl.resource.ResourceManager;
import reghzy.guigl.utils.ExceptionHelper;
import reghzy.guigl.utils.RZFormats;
import reghzy.guigl.window.Window;

import java.io.File;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The main class for any GuiGL application
 * <h1>
 *     GuiGL (Graphical user interface Graphics library)
 * </h1>
 * <h2>
 *     This class is what runs a GuiGL application; event loop, render loop, etc
 * </h2>
 */
public class GuiGLEngine implements Runnable {
    private static GuiGLEngine engine;

    private final GuiGLLogger logger = new GuiGLLogger("GuiGL");
    private final ResourceManager manager;
    private RenderEngine renderEngine;
    private boolean isRunning;
    private long initialisationTime;
    private long totalTicks;
    private boolean isShuttingDown;

    private Window mainWindow;
    private final ArrayList<Window> windows;

    public static final long DELTA_TIME = 100;
    public double deltaTime;

    public GuiGLEngine(File launchDirectory) {
        if (engine != null) {
            throw new IllegalStateException("GuiGL engine already exists!");
        }

        engine = this;
        this.manager = new ResourceManager(new File(launchDirectory, "assets"));
        this.windows = new ArrayList<Window>();
    }

    public static GuiGLEngine getInstance() {
        return engine;
    }

    private void start() {
        if (!GLFW.glfwInit()) {
            throw new RuntimeException("Failed to initialise GLFW");
        }

        getLogger().infoFormat("&6GLFW Successfully initialised, version: {0}", GLFW.glfwGetVersionString());
    }

    public void shutdownSafely() {
        this.isShuttingDown = true;
    }

    private void beginShutdown() {
        if (!this.isRunning) {
            throw new IllegalStateException("Engine is not running; it may have already been shutdown");
        }

        getLogger().info("Shutting down application...");
        for(Window window : this.windows) {
            try {
                if (window.isOpen()) {
                    window.close();
                }
                else if (!window.isDestroyed()) {
                    window.close();
                }
            }
            catch (Throwable e) {
                getLogger().warnFormat("Failed to destroy window '{0}'", window.getTitle());
                getLogger().printException(e);
            }
        }

        doShutdown();
    }

    private void doShutdown() {
        for(WeakReference<EventTracer> tracker : EventTracer.APP_TRACERS) {
            EventTracer tracer = tracker.get();
            if (tracer != null && tracer.getStackFrames().size() > 0) {
                getLogger().infoFormat("{0} -> {1}", tracer.event.getClass().getSimpleName(), tracer.getStackFrames().size());
                for(StackTraceElement[] elements : tracer.getStackFrames()) {
                    ExceptionHelper.printStackTraceColour(elements, getLogger());
                }

                getLogger().infoFormat("------------------------------------------------");
            }
        }

        GLFW.glfwTerminate();
    }

    @Override
    public void run() {
        if (this.isRunning) {
            throw new IllegalStateException("GuiGL Engine is already running");
        }

        this.initialisationTime = System.currentTimeMillis();
        getLogger().infoFormat("GuiGLEngine start @ {0}", new SimpleDateFormat("dd:MM:yyyy hh:mm:ss").format(new Date(this.initialisationTime)));
        getLogger().infoFormat("Allocating object pools...");

        long start = System.currentTimeMillis();

        try {
            Class.forName("reghzy.guigl.maths.Vector2d");
            Class.forName("reghzy.guigl.maths.Vector2f");
            Class.forName("reghzy.guigl.maths.Vector3f");
            Class.forName("reghzy.guigl.maths.Vector4f");
            Class.forName("reghzy.guigl.maths.Matrix4x4");
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to locate a class");
        }

        long end = System.currentTimeMillis();
        getLogger().infoFormat("Allocated object pools in {0} milliseconds", end - start);

        try {
            start();
        }
        catch (Throwable e) {
            getLogger().error("Failed to start GuiGL engine");
            e.printStackTrace();
            return;
        }

        this.mainWindow = new Window(this, "Main Window - GuiGL v0.0.2");
        this.mainWindow.makeCurrent();
        // this.mainWindow.setCursorCaptured();
        setMainWindow(this.mainWindow);

        Rectangle rect = new Rectangle();
        rect.setMargin(new Thickness(100, 500, 0, 0));
        rect.setSize(Vector2d.get(this.mainWindow.getWidth() / 4, this.mainWindow.getHeight() / 4));
        rect.defaultBackground = ColourARGB.BLACK;
        rect.mouseOverBackground = ColourARGB.RED;

        double sz = 50.0d;
        Rectangle rect1 = Rectangle.newRect(Vector2d.get(0.0d), Vector2d.get(sz, sz));
        Rectangle rect2 = Rectangle.newRect(Vector2d.get(this.mainWindow.getWidth() - sz, 0.0d), Vector2d.get(sz, sz));
        Rectangle rect3 = Rectangle.newRect(Vector2d.get(this.mainWindow.getWidth() - sz, this.mainWindow.getHeight() - sz), Vector2d.get(sz, sz));
        Rectangle rect4 = Rectangle.newRect(Vector2d.get(0.0d, this.mainWindow.getHeight() - sz), Vector2d.get(sz, sz));

        rect1.defaultBackground = ColourARGB.BLUE;
        rect2.defaultBackground = ColourARGB.BLUE;
        rect3.defaultBackground = ColourARGB.BLUE;
        rect4.defaultBackground = ColourARGB.BLUE;
        rect1.mouseOverBackground = ColourARGB.GREEN;
        rect2.mouseOverBackground = ColourARGB.GREEN;
        rect3.mouseOverBackground = ColourARGB.GREEN;
        rect4.mouseOverBackground = ColourARGB.GREEN;

        Panel panel = new Panel();
        panel.setSize(this.mainWindow.getSize());
        panel.addChild(rect);
        panel.addChild(rect1.setDepth(0.1f));
        panel.addChild(rect2.setDepth(0.1f));
        panel.addChild(rect3.setDepth(0.1f));
        panel.addChild(rect4.setDepth(0.1f));

        this.mainWindow.setContent(panel);

        this.renderEngine = new RenderEngine(this);
        this.renderEngine.initialise();
        this.mainWindow.show();

        try {
            this.renderEngine.setupWindow(this.mainWindow);
        }
        catch (Throwable e) {
            throw new RuntimeException("Failed to setup window with render engine", e);
        }

        this.isRunning = true;
        getLogger().infoFormat("&6GuiGL successfully initialised in {0} milliseconds", System.currentTimeMillis() - this.initialisationTime);

        long deltaMillis = 1000 / DELTA_TIME;
        long currentTime = 0;
        long intervalTime = 0;
        long delayTime = 0;
        long ticks = 0;
        // TODO: maybe implement a tick catchup system, in case the app misses a tick for some reason. Should be fine though
        try {
            while (true) {
                currentTime = System.currentTimeMillis();
                tick();
                intervalTime = System.currentTimeMillis() - currentTime;
                if ((++ticks) != this.totalTicks) {
                    getLogger().warn("Warning! External application tick detected during main tick");
                    ticks = this.totalTicks;
                }

                final long delayWarn = 2000L;
                if (intervalTime > delayWarn) {
                    getLogger().warn("Engine did not tick in the last " + intervalTime + "ms!");
                    intervalTime = delayWarn;
                }
                else if (intervalTime < 0L) {
                    getLogger().warn("Tick interval was negative! Did the system time change backwards?");
                    intervalTime = 0L;
                }

                if (this.mainWindow.shouldClose()) {
                    beginShutdown();
                    return;
                }
                else if (this.isShuttingDown) {
                    beginShutdown();
                    return;
                }

                // assuming the engine wants to run at 20 ticks per second, if interval is 0, the tick took almost no time, so delay for 50ms
                // if the tick took about 10ms, then the interval should be 10ms therefore you want to delay for 40ms
                delayTime = deltaMillis - intervalTime;
                this.deltaTime = (deltaMillis / 1000.d);
                Time.delta = this.deltaTime;
                Time.deltaLong = (long) (this.deltaTime + 0.5d);
                doDelay(delayTime);
            }
        }
        catch (InterruptedException e) {
            throw new RuntimeException("Engine thread was interrupted while sleeping", e);
        }
        catch (Throwable e) {
            throw new RuntimeException("Unexpected exception during tick", e);
        }
    }

    private static void doDelay(long delay) throws InterruptedException {
        long nextTick = System.currentTimeMillis() + delay;
        if (delay > 20) { // average windows thread-slice time == 15~ millis
            Thread.sleep(delay - 20);
        }

        // do this for the rest of the duration, for precise timing
        while (System.currentTimeMillis() < nextTick) { }
    }

    /**
     * Main application tick; this is what actually updates the application and causes it to do something
     * <p>
     *     This is a very time-sensitive method, it should only really be called by the engine. External ticks are not supported yet
     * </p>
     */
    public void tick() {
        this.totalTicks++;
        GLFW.glfwPollEvents();

        if (this.mainWindow.getKeyboard().isKeyDownFrame(KeyboardKey.escape)) {
            shutdownSafely();
            return;
        }

        try {
            doGlobalUpdate();
        }
        catch (Throwable e) {
            throw new RuntimeException("Failed to do window update", e);
        }

        try {
            doGlobalRender();
        }
        catch (Throwable e) {
            throw new RuntimeException("Failed to do global render", e);
        }
    }

    /**
     * Update the entire application, including all windows
     */
    public void doGlobalUpdate() {
        List<Window> windows = this.windows;
        for(int i = 0, len = windows.size(); i < len; i++) {
            try {
                windows.get(i).doTickUpdate();
            }
            catch (Throwable e) {
                throw new RuntimeException(RZFormats.format("Failed to update window {0} ({1})", i, windows.get(i).getTitle()), e);
            }
        }
    }

    /**
     * Render the entire application, including all windows
     */
    public void doGlobalRender() {
        List<Window> windows = this.windows;
        boolean clearScreen;
        for (int i = 0, len = windows.size(); i < len; i++) {
            Window window = windows.get(i);
            clearScreen = window.clearScreenNextTick;

            try {
                this.renderEngine.beginWindowRender(window);
                if (clearScreen) {
                    window.clearScreenNextTick = false;
                    this.renderEngine.clearColour(window.backgroundColour);
                    this.renderEngine.clearScreen();
                    window.doTickRender(this.renderEngine, true);
                    window.swapBuffers();
                    this.renderEngine.clearColour(window.backgroundColour);
                    this.renderEngine.clearScreen();
                }
                else if (RenderManager.FORCE_RENDER_ALWAYS) {
                    this.renderEngine.clearColour(window.backgroundColour);
                    this.renderEngine.clearScreen();
                }

                window.doTickRender(this.renderEngine, clearScreen);
                this.renderEngine.endWindowRender(window);
            }
            catch (Throwable e) {
                throw new RuntimeException(RZFormats.format("Failed to render window {0} ({1})", i, windows.get(i).getTitle()), e);
            }
        }
    }

    public RenderEngine getRenderEngine() {
        return this.renderEngine;
    }

    public GuiGLLogger getLogger() {
        return this.logger;
    }

    public Keyboard getKeyboard() {
        return this.mainWindow.getKeyboard();
    }

    public Mouse getMouse() {
        return this.mainWindow.getMouse();
    }

    public Window getMainWindow() {
        return this.mainWindow;
    }

    public ResourceManager getManager() {
        return manager;
    }

    public long getTotalTicks() {
        return this.totalTicks;
    }

    public void setMainWindow(Window window) {
        if (!this.windows.contains(window)) {
            this.windows.add(window);
        }

        this.mainWindow = window;
    }

    // This is how minecraft does its game loop
    // long startTime = System.currentTimeMillis();
    // long deltaMillis = 1000L / 20; // 20 ticks per second
    // long tick = 0L;
    // while (true) {
    //     long currentTime = System.currentTimeMillis();
    //     long interval = currentTime - startTime;
    //     if (interval > 2000L && (startTime - this.initialisationTime) >= 1000L) { // 1000L == average startup time
    //         getLogger().info("Engine did not tick in the last 2 seconds!");
    //         interval = 2000L;
    //     }
    //     if (interval < 0L) {
    //         getLogger().warn("Tick interval was negative! Did the system time change backwards?");
    //         interval = 0L;
    //     }
    //     tick += interval;
    //     startTime = currentTime;
    //     while (tick > deltaMillis) {
    //         tick -= deltaMillis;
    //         tickEngine();
    //     }
    //
    //     // this is where the inaccuracy comes from;
    //     // there is every chance the thread will be sleeping while a tick
    //     // is ready, due to how sleep works; thread time-slicing and stuff
    //     // Thread.sleep() is usually never accurate below 15~ millis,
    //     // unless this is the only thread on the CPU core
    //     Thread.sleep(1L);
    // }
}
