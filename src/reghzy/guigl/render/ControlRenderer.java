package reghzy.guigl.render;

import reghzy.guigl.GuiGLEngine;
import reghzy.guigl.core.Control;
import reghzy.guigl.maths.Region2d;
import reghzy.guigl.maths.Vector2d;
import reghzy.guigl.maths.Vector3f;
import reghzy.guigl.window.Window;

import java.util.LinkedList;

public abstract class ControlRenderer<T extends Control> {
    protected ControlRenderer() {

    }

    /**
     * Renders an element, at the given X, Y and Z coordinates, with the given XYZ rotation
     * <p>
     *     The X and Y parameter will be the world/onscreen render location. They will (usually)
     *     be the exact position of the control, but they can be offset externally, so it is best
     *     to use the X and Y parameters as the actual render location
     * </p>
     * <p>
     *     The Z location will usually always be 0, as depth isn't really used
     * </p>
     * @param control The control that will be rendered
     * @param occlusions A collection of regions that are blocking the given control
     * @param x The X position of the control in world space
     * @param y The Y position of the control in world space
     * @param z The Z position of the control in world space (usually 0.0f)
     * @param rotX The rotation of the control along the X axis (usually 0.0f)
     * @param rotY The rotation of the control along the Y axis (usually 0.0f)
     * @param rotZ The rotation of the control along the Z axis (usually 0.0f)
     */
    public abstract void render(T control, LinkedList<Region2d> occlusions, GuiGLEngine engine, RenderEngine render, Window window, Vector2d pos, double z, Vector3f rotation);
}
