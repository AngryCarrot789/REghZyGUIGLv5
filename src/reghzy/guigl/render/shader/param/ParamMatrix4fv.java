package reghzy.guigl.render.shader.param;

import org.lwjgl.opengl.GL20;
import reghzy.guigl.maths.Matrix4x4;

public class ParamMatrix4fv extends UniformParameter {
    private boolean transposed;

    public ParamMatrix4fv() {
        this.transposed = true;
    }

    public ParamMatrix4fv setUniformM4fv(Matrix4x4 matrix) {
        if (matrix != null) {
            GL20.glUniformMatrix4fv(this.id, this.transposed, matrix.m);
        }

        return this;
    }

    public ParamMatrix4fv getRaw(float[] reference) {
        GL20.glGetUniformfv(this.shader.getProgramId(), this.id, reference);
        return this;
    }

    public boolean isTransposed() {
        return this.transposed;
    }

    public ParamMatrix4fv setTransposed(boolean transposed) {
        this.transposed = transposed;
        return this;
    }
}
