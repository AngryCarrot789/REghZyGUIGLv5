package reghzy.guigl.render;

import reghzy.guigl.GuiGLEngine;
import reghzy.guigl.core.Control;
import reghzy.guigl.maths.Region2d;
import reghzy.guigl.maths.Vector2d;
import reghzy.guigl.maths.Vector3f;
import reghzy.guigl.render.primitive.RenderCollectiveContentControl;
import reghzy.guigl.render.primitive.RenderRectangle;
import reghzy.guigl.window.Window;

import java.util.HashMap;
import java.util.LinkedList;

public class RenderManager {
    private static final HashMap<Class<? extends ControlRenderer<? extends Control>>, ControlRenderer<? extends Control>> RENDER_INSTANCES = new HashMap<>();

    public static boolean FORCE_RENDER_ALWAYS = true;

    static  {
        // RENDERERS.put(Rectangle.class, new RenderRectangle());
        // RENDERERS.put(ContentControl.class, new RenderContentControl());

        RENDER_INSTANCES.put(RenderRectangle.class, new RenderRectangle());
        RENDER_INSTANCES.put(RenderCollectiveContentControl.class, new RenderCollectiveContentControl());
    }

    private static ControlRenderer getRenderer(Control control) {
        ControlRenderer renderer = RENDER_INSTANCES.get(control.getRenderClass());
        if (renderer == null) {
            throw new RuntimeException("Missing model for control class: " + control.getClass().getName());
        }

        return renderer;
    }

    public static void renderControl(Control control, LinkedList<Region2d> occlusions, GuiGLEngine engine, RenderEngine render, Window window, Vector2d pos, double z, Vector3f rotation, boolean force) {
        if (!FORCE_RENDER_ALWAYS) {
            if (control.render.needsRender) {
                control.render.needsRender = false;
            }
            else if (!force) {
                return;
            }
        }

        getRenderer(control).render(control, occlusions, engine, render, window, pos, z, rotation);
    }

    public static void renderControl(Control control, LinkedList<Region2d> occlusions, GuiGLEngine engine, RenderEngine render, Window window, Vector2d pos, double z, boolean force) {
        renderControl(control, occlusions, engine, render, window, pos, z, Vector3f.get(0.0d), force);
    }

    public static void renderControl(Control control, LinkedList<Region2d> occlusions, GuiGLEngine engine, RenderEngine render, Window window, double z, boolean force) {
        renderControl(control, occlusions, engine, render, window, control.getAbsolutePos(), z, Vector3f.get(0.0d), force);
    }

    public static void renderControl(Control control, GuiGLEngine engine, RenderEngine render, Window window, Vector2d pos, double z, boolean force) {
        renderControl(control, null, engine, render, window, pos, z, Vector3f.get(0.0d), force);
    }

    public static void renderControl(Control control, GuiGLEngine engine, RenderEngine render, Window window, double z, boolean force) {
        renderControl(control, null, engine, render, window, control.getAbsolutePos(), z, Vector3f.get(0.0d), force);
    }

    public static void renderControl(Control control, GuiGLEngine engine, RenderEngine render, Window window, double z, Vector3f rotation, boolean force) {
        renderControl(control, null, engine, render, window, control.getAbsolutePos(), z, rotation, force);
    }

    public static void renderControl(Control control, GuiGLEngine engine, RenderEngine render, Vector2d pos, double z, boolean force) {
        renderControl(control, null, engine, render, engine.getMainWindow(), pos, z, Vector3f.get(0.0d), force);
    }

    public static void renderControl(Control control, RenderEngine render, Window window, double z, boolean force) {
        renderControl(control, null, render.getEngine(), render, window, control.getAbsolutePos(), z, Vector3f.get(0.0d), force);
    }

    public static void renderControl(Control control, RenderEngine render, Vector2d pos, double z, boolean force) {
        renderControl(control, null, render.getEngine(), render, render.getEngine().getMainWindow(), pos, z, Vector3f.get(0.0d), force);
    }

    public static void renderControl(Control control, GuiGLEngine engine, Vector2d pos, double z, boolean force) {
        renderControl(control, null, engine, engine.getRenderEngine(), engine.getMainWindow(), pos, z, Vector3f.get(0.0d), force);
    }

    public static void renderControl(Control control, GuiGLEngine engine, Vector2d pos, boolean force) {
        renderControl(control, null, engine, engine.getRenderEngine(), engine.getMainWindow(), pos, 0.0d, Vector3f.get(0.0d), force);
    }

    public static void renderControl(Control control, GuiGLEngine engine, boolean force) {
        renderControl(control, null, engine, engine.getRenderEngine(), engine.getMainWindow(), control.getAbsolutePos(), 0.0d, Vector3f.get(0.0d), force);
    }
}
