package reghzy.guigl.render.tessellation;

import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GLAllocation {
    private static final Map<Integer, Integer> mapDisplayLists = new HashMap();
    private static final List<Integer> listDummy = new ArrayList();

    /**
     * Generates the specified number of display lists and returns the first index.
     */
    public static synchronized int generateDisplayLists(int p_74526_0_) {
        int var1 = GL11.glGenLists(p_74526_0_);
        mapDisplayLists.put(var1, p_74526_0_);
        return var1;
    }

    public static synchronized void deleteDisplayLists(int list) {
        GL11.glDeleteLists(list, (Integer) mapDisplayLists.remove(list));
    }

    /**
     * Deletes all textures and display lists. Called when Minecraft is shutdown to free up resources.
     */
    public static synchronized void deleteTexturesAndDisplayLists() {
        for (Object o : mapDisplayLists.entrySet()) {
            Map.Entry var1 = (Map.Entry) o;
            GL11.glDeleteLists(((Integer) var1.getKey()).intValue(), ((Integer) var1.getValue()).intValue());
        }

        mapDisplayLists.clear();
    }

    /**
     * Creates and returns a direct byte buffer with the specified capacity. Applies native ordering to speed up access.
     */
    public static synchronized ByteBuffer createDirectByteBuffer(int size) {
        return ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder());
    }

    /**
     * Creates and returns a direct int buffer with the specified capacity. Applies native ordering to speed up access.
     */
    public static IntBuffer createDirectIntBuffer(int size) {
        return createDirectByteBuffer(size << 2).asIntBuffer();
    }

    /**
     * Creates and returns a direct float buffer with the specified capacity. Applies native ordering to speed up
     * access.
     */
    public static FloatBuffer createDirectFloatBuffer(int size) {
        return createDirectByteBuffer(size << 2).asFloatBuffer();
    }
}
