package reghzy.guigl;

import org.lwjgl.glfw.GLFW;
import reghzy.guigl.core.input.Keyboard;
import reghzy.guigl.core.input.Mouse;
import reghzy.guigl.core.log.GuiGLLogger;
import reghzy.guigl.render.engine.RenderEngine;
import reghzy.guigl.render.engine.RenderEngineGL11;
import reghzy.guigl.window.Window;

import java.util.logging.Logger;

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
    private boolean isRunning;
    private RenderEngineGL11 renderEngine;
    private long initialisationTime;

    private final GuiGLLogger logger = new GuiGLLogger("GuiGL");

    private Window mainWindow;

    private long totalTicks;

    public static final long DELTA_TIME = 20;

    public GuiGLEngine() {

    }

    @Override
    public void run() {
        if (this.isRunning) {
            throw new IllegalStateException("GuiGL Engine is already running");
        }

        long startTime = (this.initialisationTime = System.currentTimeMillis());
        try {
            start();
        }
        catch (Throwable e) {
            getLogger().info("Failed to initialise GLFW");
            e.printStackTrace();
            return;
        }

        this.mainWindow = new Window("Main Window - GuiGL v0.0.1");
        this.mainWindow.makeCurrent();
        this.renderEngine = new RenderEngineGL11(this);
        this.renderEngine.initialise();
        this.mainWindow.show();

        this.isRunning = true;
        getLogger().info("GuiGL successfully initialised in " + (System.currentTimeMillis() - startTime) + " milliseconds");

        // final long targetMillisDelay = 1000 / DELTA_TIME;
        // long tick = System.currentTimeMillis();
        // long lastTick;
        // long interval;
        // long nextTickTime;
        try {
            startTime = System.currentTimeMillis();
            long deltaMillis = 1000 / DELTA_TIME; // 50
            long currentTime = 0;
            long intervalTime = 0;
            long lastDelayTime = 0;
            while(true) {
                currentTime = System.currentTimeMillis();
                tick();
                intervalTime = System.currentTimeMillis() - currentTime;

                if (this.mainWindow.shouldClose()) {
                    shutdown();
                    return;
                }

                if (intervalTime > 2000L && (startTime - this.initialisationTime) >= 1000L) { // 1000L == average startup time
                    getLogger().info("Engine did not tick in the last 2 seconds!");
                    intervalTime = 2000L;
                }
                else if (intervalTime < 0L) {
                    getLogger().warn("Tick interval was negative! Did the system time change backwards?");
                    intervalTime = 0L;
                }

                // assuming the engine wants to run at 20 ticks per second, interval would be 0
                // but that assumes the tick took almost no time
                // if the tick took about 10ms, then the interval should be 10ms
                // therefore you want to delay for 40ms
                lastDelayTime = deltaMillis - intervalTime;
                doTickDelay(lastDelayTime);
            }
            // startTime = System.currentTimeMillis();
            // long tick = 0L;
            // long deltaMillis = 1000L / DELTA_TIME;
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
            //         tick();
            //         if (this.mainWindow.shouldClose()) {
            //             shutdown();
            //             return;
            //         }
            //     }
            //     Thread.sleep(1L);
            // }
        }
        catch (InterruptedException e) {
            throw new RuntimeException("Engine thread was interrupted while sleeping", e);
        }
        catch (Throwable e) {
            throw new RuntimeException("Unexpected exception during tick", e);
        }
    }

    private void doTickDelay(long delay) throws InterruptedException {
        long nextTick = System.currentTimeMillis() + delay;
        if (delay > 15) { // average windows thread-slice time == 15~ millis
            long time = Math.max(10, delay / 3);
            long tempNextTick = nextTick - 20;
            while (System.currentTimeMillis() < tempNextTick) {
                Thread.sleep(time);
            }
        }

        while (System.currentTimeMillis() < nextTick) {

        }
    }

    public void shutdown() {
        if (!this.isRunning) {
            throw new IllegalStateException("Application is not running; it may have already been shutdown");
        }

        beginShutdown();
    }

    private void beginShutdown() {

    }

    private void start() {
        if (!GLFW.glfwInit()) {
            throw new RuntimeException("Failed to initialise GLFW");
        }

        getLogger().info("GLFW Successfully initialised, version: " + GLFW.glfwGetVersionString());
    }

    public void tick() {
        this.totalTicks++;
        if (this.totalTicks % 20 == 0) {
            getLogger().info("Tick interval == 20");
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
}
