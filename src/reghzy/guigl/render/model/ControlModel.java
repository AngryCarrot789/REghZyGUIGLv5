package reghzy.guigl.render.model;

import reghzy.guigl.core.Control;
import reghzy.guigl.math.Region2d;
import reghzy.guigl.render.engine.RenderEngine;

import java.util.LinkedList;

public abstract class ControlModel<T extends Control> {
    public abstract void render(T control, LinkedList<Region2d> occlusions, RenderEngine engine, float x, float y, float z, float rotX, float rotY, float rotZ);
}
