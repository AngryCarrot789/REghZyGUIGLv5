package reghzy.guigl.render.primitive;

import reghzy.guigl.GuiGLEngine;
import reghzy.guigl.core.CollectiveContentControl;
import reghzy.guigl.core.Control;
import reghzy.guigl.maths.Region2d;
import reghzy.guigl.maths.Vector2d;
import reghzy.guigl.maths.Vector3f;
import reghzy.guigl.render.ControlRenderer;
import reghzy.guigl.render.RenderEngine;
import reghzy.guigl.render.RenderManager;
import reghzy.guigl.window.Window;

import java.util.LinkedList;

public class RenderCollectiveContentControl extends ControlRenderer<CollectiveContentControl> {
    @Override
    public void render(CollectiveContentControl control, LinkedList<Region2d> occlusions, GuiGLEngine engine, RenderEngine render, Window window, Vector2d pos, double z, Vector3f rotation) {
        for (Control child : control.getChildren()) {
            RenderManager.renderControl(child, engine, render, window, z, rotation, true);
        }
    }
}
