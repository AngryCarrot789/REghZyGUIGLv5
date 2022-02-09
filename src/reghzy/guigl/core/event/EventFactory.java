package reghzy.guigl.core.event;

import reghzy.guigl.core.Control;
import reghzy.guigl.core.event.events.MouseMoveEvent;
import reghzy.guigl.maths.Vector2d;

public class EventFactory {
    public static boolean onMouseEnter(Control control, Vector2d relative, Vector2d oldMousePos, Vector2d newMousePos) {
        return control.eventOnMouseEnter.raise(new MouseMoveEvent(oldMousePos.copy(), newMousePos.copy()));
    }

    public static boolean onMouseMoved(Control control, Vector2d relative, Vector2d oldMousePos, Vector2d newMousePos) {
        return control.eventOnMouseMove.raise(new MouseMoveEvent(oldMousePos.copy(), newMousePos.copy()));
    }

    public static boolean onMouseLeave(Control control, Vector2d relative, Vector2d oldMousePos, Vector2d newMousePos) {
        return control.eventOnMouseLeave.raise(new MouseMoveEvent(oldMousePos.copy(), newMousePos.copy()));
    }
}
