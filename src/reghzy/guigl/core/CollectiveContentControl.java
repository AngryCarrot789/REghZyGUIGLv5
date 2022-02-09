package reghzy.guigl.core;

import reghzy.guigl.maths.Region2d;
import reghzy.guigl.maths.Vector2d;
import reghzy.guigl.render.ControlRenderer;
import reghzy.guigl.render.primitive.RenderCollectiveContentControl;

import java.util.ArrayList;
import java.util.List;

/**
 * A control that can contain children
 */
public abstract class CollectiveContentControl extends Control {
    protected final List<Control> children;

    public CollectiveContentControl() {
        this.children = new ArrayList<Control>();
    }

    @Override
    public Class<? extends ControlRenderer<?>> getRenderClass() {
        return RenderCollectiveContentControl.class;
    }

    public void addChild(Control control) {
        control.parent = this;
        this.children.add(control);
        for(Control ctrl : this.children) {
            ctrl.markForGlobalUpdate();
        }

        recalculateChildrenDepth();
        markForGlobalUpdate();
    }

    public void removeChild(Control control) {
        control.parent = null;
        this.children.remove(control);
        for (Control ctrl : this.children) {
            ctrl.markForGlobalUpdate();
        }

        recalculateChildrenDepth();
        markForGlobalUpdate();
    }

    /**
     * <h1>
     *     DON'T MODIFY THIS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * </h1>
     */
    public List<Control> getChildren() {
        return this.children;
    }

    public void recalculateChildrenDepth() {
        // for(Control a : this.children) {
        //     for (Control b : this.children) {
        //         if (a == b) {
        //             continue;
        //         }
        //         if (b.getRegion().isEntirelyWithin(a.getRegion())) {
        //             b.render.renderDepth += 0.1f;
        //         }
        //     }
        // }
    }
}
