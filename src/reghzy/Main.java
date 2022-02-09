package reghzy;

import reghzy.guigl.GuiGLEngine;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        GuiGLEngine engine = new GuiGLEngine(new File("").getAbsoluteFile());
        engine.run();
    }
}
