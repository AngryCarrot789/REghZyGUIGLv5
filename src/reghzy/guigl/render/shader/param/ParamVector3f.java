package reghzy.guigl.render.shader.param;

import org.lwjgl.opengl.GL20;
import reghzy.guigl.maths.Vector3f;

public class ParamVector3f extends UniformParameter {
    public ParamVector3f set(Vector3f vector) {
        GL20.glUniform3f(this.id, (float) vector.x, (float) vector.y, (float) vector.z);
        return this;
    }

    public ParamVector3f set(float r, float g, float b) {
        GL20.glUniform3f(this.id, r, g, b);
        return this;
    }

    public ParamVector3f set(double r, double g, double b) {
        GL20.glUniform3f(this.id, (float) r, (float) g, (float) b);
        return this;
    }
}
