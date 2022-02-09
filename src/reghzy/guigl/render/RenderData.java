package reghzy.guigl.render;

import reghzy.guigl.core.Control;
import reghzy.guigl.maths.Vector2d;

public class RenderData {
    public final Control control;

    // ----- position baking ------
    public boolean isPosBaked;
    public final Vector2d pos = new Vector2d();
    public boolean isSizeBaked;
    public final Vector2d size = new Vector2d();
    public float renderDepth = 0.0f;
    // ----------------------------

    public boolean needsRender = true;

    public RenderData(Control control) {
        this.control = control;
    }
}
