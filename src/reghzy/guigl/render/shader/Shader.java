package reghzy.guigl.render.shader;

import org.lwjgl.opengl.GL20;
import reghzy.guigl.render.shader.param.UniformParameter;
import reghzy.guigl.resource.ResourceManager;
import reghzy.guigl.utils.RZFormats;
import reghzy.guigl.utils.StringHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Use one of the static create functions to crate a shader. Then, register uniform variables, and finally invoke {@link Shader#linkAndFinish()}
 */
public class Shader {
    private static Shader inUse;

    private final HashMap<String, UniformParameter> uniforms;
    private final ArrayList<String> inputs = new ArrayList<String>();

    private int vertexId;
    private int fragmentId;
    private int programId;

    private int attribIndex = 0;

    private Shader() {
        this.uniforms = new HashMap<String, UniformParameter>();
    }

    public static Shader create(ResourceManager manager, String name) throws FileNotFoundException, IOException {
        return createFromFiles(manager.getResource("shaders", name + ".vert"), manager.getResource("shaders", name + ".frag"));
    }

    public static Shader createFromFiles(File vertex, File fragment) throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(vertex));
        StringBuilder vertSrc = new StringBuilder((int) (vertex.length() / 2));
        String line;
        while((line = reader.readLine()) != null) {
            vertSrc.append(line).append('\n');
        }

        reader.close();
        reader = new BufferedReader(new FileReader(fragment));
        StringBuilder fragSrc = new StringBuilder((int) (fragment.length() / 2));
        while ((line = reader.readLine()) != null) {
            fragSrc.append(line).append('\n');
        }

        return createFromStrings(vertSrc.toString(), fragSrc.toString());
    }

    public static Shader createFromStrings(String vertex, String fragment) {
        return new Shader().compileShaders(vertex, fragment).createProgramAndLink();
    }

    public Shader compileShaders(String vertexCode, String fragmentCode) {
        this.vertexId = compile(vertexCode, GL20.GL_VERTEX_SHADER);
        this.fragmentId = compile(fragmentCode, GL20.GL_FRAGMENT_SHADER);
        return this;
    }

    public Shader createProgramAndLink() {
        this.programId = GL20.glCreateProgram();
        GL20.glAttachShader(this.programId, this.vertexId);
        GL20.glAttachShader(this.programId, this.fragmentId);
        return this;
    }

    public Shader registerUniform(String name, UniformParameter parameter) {
        if (name.startsWith("gl_")) {
            throw new RuntimeException("Name cannot start with 'gl_', as it is reserved");
        }
        else if (name.indexOf(' ') != -1) {
            throw new RuntimeException("Name cannot contain whitespaces, as variables cannot have those");
        }

        this.uniforms.put(name, parameter.setShader(this).setName(name));
        return this;
    }

    public Shader bindAttribute(String name) {
        if (name.startsWith("gl_")) {
            throw new RuntimeException("Name cannot start with 'gl_', as it is reserved");
        }
        else if (name.indexOf(' ') != -1) {
            throw new RuntimeException("Name cannot contain whitespaces, as variables cannot have those");
        }

        GL20.glBindAttribLocation(this.programId, this.attribIndex++, name);
        return this;
    }

    public Shader linkAndFinish() {
        for(String input : this.inputs) {
            bindAttribute(input);
        }

        GL20.glLinkProgram(this.programId);

        for(Map.Entry<String, UniformParameter> entry : this.uniforms.entrySet()) {
            int id = GL20.glGetUniformLocation(this.programId, entry.getKey());
            if (id == -1) {
                throw new RuntimeException(RZFormats.format("Attribute {0} could not be found", entry.getKey()));
            }

            entry.getValue().setId(id);
        }

        GL20.glDetachShader(this.programId, this.vertexId);
        GL20.glDetachShader(this.programId, this.fragmentId);
        GL20.glDeleteShader(this.vertexId);
        GL20.glDeleteShader(this.fragmentId);
        return this;
    }

    public void dispose() {
        GL20.glDeleteProgram(this.programId);
    }

    public <T extends UniformParameter> T getAttribute(String name) {
        UniformParameter parameter = this.uniforms.get(name);
        if (parameter == null) {
            throw new NullPointerException("Missing shader parameter: " + name);
        }

        return (T) parameter;
    }

    public void use() {
        if (this == inUse) {
            return;
        }

        GL20.glUseProgram(this.programId);
        inUse = this;
    }

    public boolean isUsing() {
        return inUse == this;
    }

    public static void unUse() {
        inUse = null;
        GL20.glUseProgram(0);
    }

    private int compile(String sourceCode, int type) {
        int id = GL20.glCreateShader(type);
        GL20.glShaderSource(id, sourceCode);
        GL20.glCompileShader(id);

        int[] isCompiled = new int[1];
        GL20.glGetShaderiv(id, GL20.GL_COMPILE_STATUS, isCompiled);
        if (isCompiled[0] < 1) {
            throw new RuntimeException("Failed to compile shader\n" + GL20.glGetShaderInfoLog(id));
        }

        if (type == GL20.GL_VERTEX_SHADER) {
            for(String line : StringHelper.split(sourceCode, '\n', 0)) {
                line = line.trim();
                if (line.isEmpty() || line.charAt(0) == '/') {
                    continue;
                }

                if (!line.startsWith("in")) {
                    continue;
                }

                int endIndex = line.indexOf(';', 3);
                if (endIndex == -1) {
                    throw new RuntimeException("Missing semicolon for input variable, but it compiled????: " + line);
                }

                int spaceIndex = line.indexOf(' ', 3);
                if (spaceIndex == -1) {
                    throw new RuntimeException("Missing whitespace between input variable type and name, but it compiled????: " + line);
                }

                this.inputs.add(line.substring(spaceIndex + 1, endIndex));
            }
        }

        return id;
    }

    public int getProgramId() {
        return this.programId;
    }

    public boolean hasAttribute(String attrib) {
        return this.uniforms.containsKey(attrib);
    }
}
