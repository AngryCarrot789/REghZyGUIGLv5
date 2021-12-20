package reghzy.guigl.render.engine;

import reghzy.guigl.GuiGLEngine;

public abstract class RenderEngine {
    protected boolean hasInitialised;
    protected final GuiGLEngine engine;

    protected RenderEngine(GuiGLEngine engine) {
        this.engine = engine;
    }

    /**
     * Initialise the rendering engine, and all of the components it needs
     */
    public abstract void initialise();

    /**
     * Shuts down the rendering engine, and releases/unregisters all things it's using
     */
    public abstract void shutdown();

    public abstract void drawRect2d(double minX, double minY, double maxX, double maxY, double z, float red, float green, float blue, float alpha);
}
