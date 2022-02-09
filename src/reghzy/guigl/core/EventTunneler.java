package reghzy.guigl.core;

import reghzy.guigl.GuiGLEngine;
import reghzy.guigl.core.event.EventFactory;
import reghzy.guigl.core.event.events.MouseMoveEvent;
import reghzy.guigl.core.router.TunnelHitPair;
import reghzy.guigl.maths.Region2d;
import reghzy.guigl.maths.Vector2d;
import sun.misc.FDBigInteger;

import java.util.ArrayList;
import java.util.List;

public class EventTunneler {
    private final List<Control> controls;

    // Bubble event = Button -> Panel -> Grid -> Window
    // Tunnel event = Window -> Grid -> Panel -> Button
    // Window ->            |
    //   Grid ->            |
    //     Panel ->         |
    //       Button         |

    public EventTunneler() {
        this.controls = new ArrayList<Control>(32);
    }

    public EventTunneler add(Control control) {
        this.controls.add(control);
        return this;
    }

    public static void doHandleMouseMovement(Control topControl, Vector2d oldPos, Vector2d newPos) {
        List<TunnelHitPair> tunnelList = new ArrayList<TunnelHitPair>();
        TunnelHitPair top = new TunnelHitPair(topControl);
        top.hitNewPos = topControl.getScreenRegion().intersects(newPos);
        top.hitOldPos = topControl.getScreenRegion().intersects(oldPos);
        tunnelList.add(top);
        doDoubleMouseHitScan(topControl, oldPos, newPos, tunnelList);
        MouseMoveEvent event = new MouseMoveEvent(oldPos.copy(), newPos.copy());
        for(int i = 0, len = tunnelList.size(); i < len; i++) {
            if (event.isCancelled()) {
                GuiGLEngine.getInstance().getMouse().setPosition(oldPos.x, oldPos.y);
                return;
            }

            TunnelHitPair hit = tunnelList.get(i);
            Control control = hit.control;
            if (hit.hitNewPos && hit.hitOldPos) {
                if (control.isMouseOver) {
                    control.onPreviewMouseMove(event);
                    control.eventPreviewOnMouseMove.raise(event);
                }
                else {
                    control.isPreviewMouseOver = true;
                    control.onPreviewMouseEnter(event);
                    control.eventPreviewOnMouseEnter.raise(event);
                }
            }
            else if (hit.hitNewPos) {
                control.isPreviewMouseOver = true;
                control.onPreviewMouseEnter(event);
                control.eventPreviewOnMouseEnter.raise(event);
            }
            else if (hit.hitOldPos) {
                control.isPreviewMouseOver = false;
                control.onPreviewMouseLeave(event);
                control.eventPreviewOnMouseLeave.raise(event);
            }
        }

        for (int i = tunnelList.size() - 1; i >= 0; i--) {
            if (event.isHandled()) {
                break;
            }

            TunnelHitPair hit = tunnelList.get(i);
            Control control = hit.control;
            control.isPreviewMouseOver = false;
            if (hit.hitNewPos && hit.hitOldPos) {
                if (control.isMouseOver) {
                    control.onMouseMove(event);
                    control.eventOnMouseMove.raise(event);
                }
                else {
                    control.isMouseOver = true;
                    control.onMouseEnter(event);
                    control.eventOnMouseEnter.raise(event);
                }

                if (control.isHittable(event.getNewRelativeTo(control), newPos)) {
                    event.setHandled(true);
                }
            }
            else if (hit.hitNewPos) {
                control.isMouseOver = true;
                control.onMouseEnter(event);
                control.eventOnMouseEnter.raise(event);
                if (control.isHittable(event.getNewRelativeTo(control), newPos)) {
                    event.setHandled(true);
                }
            }
            else if (hit.hitOldPos) {
                control.isMouseOver = false;
                control.onMouseLeave(event);
                control.eventOnMouseLeave.raise(event);
                if (control.isHittable(event.getNewRelativeTo(control), newPos)) {
                    event.setHandled(true);
                }
            }
        }
    }

    /**
     * Does a hit scan from the given top-level control, to the lowest possible level, putting all hits in the given list
     * @param hitResults A list of hit controls
     */
    public static void doMouseHitScan(Control control, Vector2d newPos, List<Control> hitResults) {
        if (control instanceof ContentControl) {
            Control child = ((ContentControl) control).getContent();
            if (child != null) {
                Region2d region = child.getScreenRegion();
                if (region.intersects(newPos)) {
                    if (!control.isHittable(newPos.getCopy().subtract(region.getMin()), newPos)) {
                        return;
                    }

                    hitResults.add(control);
                    doMouseHitScan(child, newPos, hitResults);
                }
            }
        }
        else if (control instanceof CollectiveContentControl) {
            CollectiveContentControl collective = (CollectiveContentControl) control;
            for (Control child : collective.getChildren()) {
                Region2d region = child.getScreenRegion();
                if (region.intersects(newPos)) {
                    if (!control.isHittable(newPos.getCopy().subtract(region.getMin()), newPos)) {
                        continue;
                    }

                    hitResults.add(control);
                    doMouseHitScan(child, newPos, hitResults);
                    return;
                }
            }
        }
    }

    public static void doDoubleMouseHitScan(Control control, Vector2d oldPos, Vector2d newPos, List<TunnelHitPair> hitResults) {
        if (control instanceof ContentControl) {
            Control child = ((ContentControl) control).getContent();
            if (child != null) {
                TunnelHitPair hitPair = new TunnelHitPair(child);
                Region2d region = child.getScreenRegion();
                if (region.intersects(newPos)) {
                    if (control.isHittable(newPos.getCopy().subtract(region.getMin()), newPos)) {
                        hitPair.hitNewPos = true;
                    }
                    else {
                        // return?
                    }
                }

                if (region.intersects(oldPos)) {
                    if (control.isHittable(newPos.getCopy().subtract(region.getMin()), newPos)) {
                        hitPair.hitOldPos = true;
                    }
                    else {
                        // return?
                    }
                }

                hitResults.add(hitPair);
                doDoubleMouseHitScan(child, oldPos, newPos, hitResults);
            }
        }
        else if (control instanceof CollectiveContentControl) {
            CollectiveContentControl collective = (CollectiveContentControl) control;
            for (Control child : collective.getChildren()) {
                Region2d region = child.getScreenRegion();
                boolean hitNew = region.intersects(newPos);
                boolean hitOld = region.intersects(oldPos);
                if (hitNew || hitOld) {
                    TunnelHitPair hitPair = new TunnelHitPair(child);
                    if (hitNew) {
                        if (control.isHittable(newPos.getCopy().subtract(region.getMin()), newPos)) {
                            hitPair.hitNewPos = true;
                        }
                        else {
                            // return?
                        }
                    }

                    if (hitOld) {
                        if (control.isHittable(newPos.getCopy().subtract(region.getMin()), newPos)) {
                            hitPair.hitOldPos = true;
                        }
                        else {
                            // return?
                        }
                    }

                    hitResults.add(hitPair);
                    doDoubleMouseHitScan(child, oldPos, newPos, hitResults);
                    return;
                }
            }
        }
    }
}
