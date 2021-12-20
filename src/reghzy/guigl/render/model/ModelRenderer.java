package reghzy.guigl.render.model;

import reghzy.guigl.core.Control;
import reghzy.guigl.math.Region2d;
import reghzy.guigl.render.ControlRenderer;

import java.util.LinkedList;

public class ModelRenderer<T extends Control> extends ControlRenderer<T> {


    @Override
    public void render(T control, LinkedList<Region2d> occlusions, float x, float y, float z, float rotX, float rotY, float rotZ) {

    }
}
