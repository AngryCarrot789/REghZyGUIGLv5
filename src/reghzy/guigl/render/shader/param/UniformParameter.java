package reghzy.guigl.render.shader.param;

import reghzy.guigl.render.shader.Shader;

public abstract class UniformParameter {
    protected String name;
    protected Shader shader;
    protected int id;

    public Shader getShader() {
        return shader;
    }

    public UniformParameter setShader(Shader shader) {
        this.shader = shader;
        return this;
    }

    public UniformParameter setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public UniformParameter setId(int id) {
        this.id = id;
        return this;
    }
}
