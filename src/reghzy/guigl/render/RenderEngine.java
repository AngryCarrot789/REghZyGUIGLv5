package reghzy.guigl.render;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLCapabilities;
import reghzy.guigl.GuiGLEngine;
import reghzy.guigl.core.utils.ColourARGB;
import reghzy.guigl.maths.Matrix4x4;
import reghzy.guigl.maths.Vector3f;
import reghzy.guigl.render.shader.Shader;
import reghzy.guigl.render.tessellation.Tessellator;
import reghzy.guigl.render.utils.Cartesian;
import reghzy.guigl.window.Window;

import java.io.IOException;

public class RenderEngine {
    private boolean hasInitialised;
    private final GuiGLEngine engine;
    private GLCapabilities capabilities;
    private Tessellator tessellator;

    private Window window;

    private double vpWidth;
    private double vpHeight;

    public Shader pinkShader;

    public RenderEngine(GuiGLEngine engine) {
        this.engine = engine;
    }

    public void initialise() {
        if (this.hasInitialised) {
            throw new IllegalStateException("GL11 Rendering engine is already initialised");
        }

        this.capabilities = GL.createCapabilities();
        this.engine.getLogger().info("Created GL capabilities");
        this.hasInitialised = true;
        this.tessellator = new Tessellator(524288);

        try {
            this.pinkShader = Shader.create(engine.getManager(), "pink");
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to create shader", e);
        }
    }

    public void shutdown() {
        if (!this.hasInitialised) {
            throw new IllegalStateException("GL11 Rendering engine has not been initialised yet");
        }

        GL.destroy();
        this.hasInitialised = false;
    }

    public void setupWindow(Window window) {
        window.useViewport();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        window.useViewport();
    }

    public static Vector3f CAMERA_POS = Vector3f.ZERO.copy().setZ(500.0f);
    public static Vector3f CAMERA_ROT = Vector3f.ZERO.copy();

    public void beginWindowRender(Window window) {
        this.window = window;
        this.vpWidth = window.getWidth();
        this.vpHeight = window.getHeight();
        window.makeCurrent();
        // CARTESIAN_MATRIX = Matrix4x4.projection((float) this.vpWidth, (float) this.vpHeight, 0.1f, 100000.0f, 75.0f);
        CARTESIAN_MATRIX = Matrix4x4.orthographic(0.0f, 0.0f, (float) this.vpWidth, (float) this.vpHeight, 0.1f, 100000.0f);

        // CARTESIAN_MATRIX = CARTESIAN_MATRIX.multiply(Matrix4x4.worldToLocal(CAMERA_POS, Vector3f.ZERO, Vector3f.ONE));
        // CARTESIAN_MATRIX = CARTESIAN_MATRIX.multiply(Matrix4x4.translation(-(float) this.vpWidth / 2.0f, -(float) this.vpHeight / 2.0f, -750.0f));
        CARTESIAN_MATRIX = CARTESIAN_MATRIX.multiply(Matrix4x4.translation(0.0f, 0.0f, -10.0f));
    }

    public void endWindowRender(Window window) {
        GL11.glFlush();
        window.swapBuffers();
        this.window = null;
    }

    /**
     * Gets the window that is currently in the process of being rendered
     * <p>
     *     May be null after the global render has finished, or between window renders
     * </p>
     */
    public Window getActiveWindow() {
        return this.window;
    }

    public void clearScreen() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void clearColour(float r, float g, float b, float a) {
        GL11.glClearColor(r, g, b, a);
    }

    public void clearColour(ColourARGB colour) {
        clearColour(colour.r, colour.g, colour.b, colour.a);
    }

    public GLCapabilities getCapabilities() {
        return this.capabilities;
    }

    private static Matrix4x4 CARTESIAN_MATRIX;// = Matrix4x4.identity().copy();

    static {

    }

    public void drawRect2d(double minX, double minY, double maxX, double maxY, double z, ColourARGB colour) {
        // minX = Cartesian.screenToNdcX(minX, this.vpWidth);
        // minY = Cartesian.screenToNdcY(minY, this.vpHeight);
        // maxX = Cartesian.screenToNdcX(maxX, this.vpWidth);
        // maxY = Cartesian.screenToNdcY(maxY, this.vpHeight);
        // this.tessellator.startDrawingQuads();
        // this.tessellator.setColourRGBA(colour.r, colour.g, colour.b, colour.a);
        // this.tessellator.addVertex(maxX, maxY, z);
        // this.tessellator.addVertex(minX, maxY, z);
        // this.tessellator.addVertex(minX, minY, z);
        // this.tessellator.addVertex(maxX, minY, z);
        // this.tessellator.draw();

        this.tessellator.startDrawingQuads();
        this.tessellator.setColourRGBA(colour.r, colour.g, colour.b, colour.a);
        this.tessellator.addVertex(CARTESIAN_MATRIX.multiply(Vector3f.get(maxX, maxY, z)));
        this.tessellator.addVertex(CARTESIAN_MATRIX.multiply(Vector3f.get(minX, maxY, z)));
        this.tessellator.addVertex(CARTESIAN_MATRIX.multiply(Vector3f.get(minX, minY, z)));
        this.tessellator.addVertex(CARTESIAN_MATRIX.multiply(Vector3f.get(maxX, minY, z)));
        this.tessellator.draw();
    }

    public void drawRect2dGradient(double minX, double minY, double maxX, double maxY, double z, ColourARGB left, ColourARGB right) {
        minX = Cartesian.screenToNdcX(minX, this.vpWidth);
        minY = Cartesian.screenToNdcY(minY, this.vpHeight);
        maxX = Cartesian.screenToNdcX(maxX, this.vpWidth);
        maxY = Cartesian.screenToNdcY(maxY, this.vpHeight);
        this.tessellator.startDrawingQuads();
        this.tessellator.setColourRGBA(right.r, right.g, right.b, right.a);
        this.tessellator.addVertex(maxX, maxY, 0.0d);
        this.tessellator.setColourRGBA(left.r, left.g, left.b, left.a);
        this.tessellator.addVertex(minX, maxY, 0.0d);
        this.tessellator.addVertex(minX, minY, 0.0d);
        this.tessellator.setColourRGBA(right.r, right.g, right.b, right.a);
        this.tessellator.addVertex(maxX, minY, 0.0d);
        this.tessellator.draw();
    }

    public void putVertex(double x, double y) {
        this.tessellator.addVertex(Cartesian.screenToNdcX(x, this.vpWidth), Cartesian.screenToNdcY(y, this.vpHeight), 0.0d);
    }

    public void putColour(ColourARGB colour) {
        this.tessellator.setColourRGBA(colour.r, colour.g, colour.b, colour.a);
    }

    public GuiGLEngine getEngine() {
        return this.engine;
    }

    public Tessellator getTessellator() {
        return tessellator;
    }
}
