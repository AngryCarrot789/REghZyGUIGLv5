package reghzy.guigl.render.primitive;

import reghzy.guigl.GuiGLEngine;
import reghzy.guigl.core.ContentControl;
import reghzy.guigl.core.Control;
import reghzy.guigl.maths.Region2d;
import reghzy.guigl.maths.Vector2d;
import reghzy.guigl.maths.Vector3f;
import reghzy.guigl.render.ControlRenderer;
import reghzy.guigl.render.RenderEngine;
import reghzy.guigl.render.RenderManager;
import reghzy.guigl.window.Window;

import java.util.LinkedList;

public class RenderContentControl extends ControlRenderer<ContentControl> {
    @Override
    public void render(ContentControl control, LinkedList<Region2d> occlusions, GuiGLEngine engine, RenderEngine render, Window window, Vector2d pos, double z, Vector3f rotation) {
        Control child = control.getContent();
        if (child != null) {
            RenderManager.renderControl(child, null, engine, render, window, z, true);
        }
    }
}
