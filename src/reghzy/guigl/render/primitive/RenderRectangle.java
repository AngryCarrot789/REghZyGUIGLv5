package reghzy.guigl.render.primitive;

import reghzy.guigl.GuiGLEngine;
import reghzy.guigl.core.primitive.Rectangle;
import reghzy.guigl.maths.Region2d;
import reghzy.guigl.maths.Vector2d;
import reghzy.guigl.maths.Vector3f;
import reghzy.guigl.render.ControlRenderer;
import reghzy.guigl.render.RenderEngine;
import reghzy.guigl.window.Window;

import java.util.LinkedList;

public class RenderRectangle extends ControlRenderer<Rectangle> {
    @Override
    public void render(Rectangle control, LinkedList<Region2d> occlusions, GuiGLEngine engine, RenderEngine render, Window window, Vector2d pos, double z, Vector3f rotation) {
        if (control.background != null) {
            render.drawRect2d(pos.x, pos.y, pos.x + control.getActualWidth(), pos.y + control.getActualHeight(), z, control.background);
        }
    }
}
