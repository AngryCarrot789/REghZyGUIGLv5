package reghzy.guigl.render.utils;

public final class Cartesian {
    private Cartesian() { }

    /*
     *  -1, 1             0, 1               1, 1
     *                      |
     *                      |
     *                      |
     *                      |
     *  -1, 0 --------------|--------------- 1, 0
     *                      |
     *                      |
     *      [-0.5, -0.5]    |
     *                      |
     *                      |
     *  -1,-1             0, -1               1, -1
     */

    //void e(double worldX, double worldY) {
    //    int w = 500;
    //    int h = 200;
    //    double openglX = -0.5f;
    //    double openglY = -0.5f;
    //    double targetX = 250;
    //    double targetY = 100;
    //    double x = 0;
    //    double y = 0;
    //    // worldX = -0.8
    //    if (worldX < 0) {
    //        // newX = 0.8
    //        double newX = -worldX;
    //        double actualX = w * (1 - newX);
    //    }
    //    else {
    //        // worldX = 0.8
    //        double actualX = w * worldX;
    //    }
    //    // worldY = 0.8
    //    if (worldY > 0) {
    //        // 0.2
    //        double newY = 1 - worldY;
    //        double actualY = h * newY;
    //    }
    //    // world = -0.8
    //    else {
    //        // 0.8
    //        double newY = -worldY;
    //        double actualY = h * newY;
    //    }
    //    //vec2 windowSpacePos = ((ndcSpacePos.xy + 1.0) / 2.0) * viewSize + viewOffset;
    //}

    /**
     * NDC is the coordinates system from -1 to 1 (left to right) and 1 to -1 (top to bottom)
     * <p>
     * Converts the X NDC coordinates, into screen coordinates (e.g. ndcToScreenX(-0.8, 1000) == 100),
     * </p>
     *
     * @param ndc   the NCD position, e.g. 0.5, 0.2, -0.7
     * @param width the width of the window
     * @return
     */
    public static double ndcToScreenX(double ndc, double width) {
        if (ndc < 0.0f) {
            return width * (1 - (-ndc));
        }
        else if (ndc > 0.0f) {
            return width * ndc;
        }
        else {
            return width * 0.5f;
        }
    }

    /**
     * NDC is the coordinates system from -1 to 1 (left to right) and 1 to -1 (top to bottom)
     * <p>
     * Converts the Y NDC coordinates, into screen coordinates (e.g. ndcToScreenY(-0.8, 1000) == 900),
     * </p>
     *
     * @param ndc    the NCD position, e.g. 0.5, 0.2, -0.7
     * @param height the height of the window
     * @return
     */
    public static double ndcToScreenY(double ndc, double height) {
        if (ndc < 0.0f) {
            return height * (-ndc);
        }
        else if (ndc > 0.0f) {
            return height * (1 - ndc);
        }
        else {
            return height * 0.5f;
        }
    }

    /**
     * Converts the X screen coordinate (100, 700, never below 0) to NDC coordinates (between -1 and 1.. ish)
     *
     * @param x     the X coordinates
     * @param width the width of the window/viewport
     */
    public static double screenToNdcX(double x, double width) {
        return ((x / width) * 2) - 1;
    }

    /**
     * Converts the Y screen coordinate (100, 700, never below 0) to NDC coordinates (between -1 and 1... ish)
     * <p>
     * screenToNdcY(200, 1000) return 0.6f
     * </p>
     *
     * @param y      the Y coordinates
     * @param height the height of the window/viewport
     */
    public static double screenToNdcY(double y, double height) {
        return 1 - ((y / height) * 2);
    }
}
