package reghzy.guigl.render.engine;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLCapabilities;
import reghzy.guigl.GuiGLEngine;

public class RenderEngineGL11 extends RenderEngine {
    private GLCapabilities capabilities;

    public RenderEngineGL11(GuiGLEngine engine) {
        super(engine);
    }

    @Override
    public void initialise() {
        if (this.hasInitialised) {
            throw new IllegalStateException("GL11 Rendering engine is already initialised");
        }

        this.capabilities = GL.createCapabilities();
        this.engine.getLogger().info("Created GL capabilities");
        this.hasInitialised = true;
    }

    @Override
    public void shutdown() {
        if (!this.hasInitialised) {
            throw new IllegalStateException("GL11 Rendering engine has not been initialised yet");
        }

        GL.destroy();
        this.hasInitialised = false;
    }

    public GLCapabilities getCapabilities() {
        return this.capabilities;
    }

    @Override
    public void drawRect2d(double minX, double minY, double maxX, double maxY, double z, float red, float green, float blue, float alpha) {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glBegin(GL11.GL_TRIANGLES);
        GL11.glVertex3d(maxX, maxY, z);
        GL11.glVertex3d(minX, maxY, z);
        GL11.glVertex3d(minX, minY, z);
        GL11.glEnd();
        GL11.glBegin(GL11.GL_TRIANGLES);
        GL11.glVertex3d(maxX, maxY, z);
        GL11.glVertex3d(minX, minY, z);
        GL11.glVertex3d(maxX, minY, z);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }
}
