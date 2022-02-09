package reghzy.guigl.core;

import reghzy.guigl.core.event.EventFactory;
import reghzy.guigl.maths.Region2d;
import reghzy.guigl.maths.Vector2d;
import reghzy.guigl.window.Window;

public class MouseHandler {
    public static void calculateMouseMovement(Window window, Vector2d oldPos, Vector2d newPos) {
        if (window.hasContent()) {
            calculateMouseMovement(window.getContent(), oldPos, newPos);
        }
    }

    public static void calculateMouseMovement(Control control, Vector2d oldPos, Vector2d newPos) {
        EventTunneler.doHandleMouseMovement(control, oldPos, newPos);
        // Region2d region = control.getRelativeRegion();
        // Vector2d relative = newPos.getCopy().subtract(control.getAbsolutePos());
        // if (region.intersects(relative)) {
        //     if (control.isMouseOver) {
        //         doOnMouseMove(control, relative, oldPos, newPos);
        //     }
        //     else {
        //         control.isMouseOver = true;
        //         doOnMouseEnter(control, relative, oldPos, newPos);
        //     }
        // }
        // else if (control.isMouseOver) {
        //     control.isMouseOver = false;
        //     doOnMouseLeave(control, relative, oldPos, newPos);
        // }
    }
}
