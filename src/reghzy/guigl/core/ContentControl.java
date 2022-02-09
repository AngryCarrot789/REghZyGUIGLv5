package reghzy.guigl.core;

import reghzy.guigl.render.ControlRenderer;
import reghzy.guigl.render.primitive.RenderContentControl;

public abstract class ContentControl extends Control {
    protected Control content;

    public Control getContent() {
        return this.content;
    }

    public void setContent(Control content) {
        this.content = content;
        markForGlobalUpdate();
    }

    @Override
    public Class<? extends ControlRenderer<?>> getRenderClass() {
        return RenderContentControl.class;
    }
}
