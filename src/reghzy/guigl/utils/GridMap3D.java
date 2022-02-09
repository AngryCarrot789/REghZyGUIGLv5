package reghzy.guigl.utils;

import reghzy.guigl.utils.reflect.Reflect;

import java.lang.reflect.Constructor;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A height-layer based coordinate map, mappings sets of X and Z coordinates (in every layer) to an object
 * @param <T> The objects to store at the coordinates
 */
public class GridMap3D<T> {
    private final HeightLayer<T>[] layers;
    private final int minY;
    private final int maxY;

    /**
     * Creates a new grid map, using a hash based height layer
     * @param minY The minimum possible Y coordinate (inclusive; can be indexed to)
     * @param maxY The end of the height (exclusive; cannot be indexed to, but maxY - 1 can be indexed to)
     */
    public GridMap3D(int minY, int maxY) {
        this(HashMapBasedHeightLayer.class, minY, maxY);
    }

    /**
     * The height of this coordinate map (aka the size of the height layer array)
     * @param layerClass The class that should be used to create the layers
     * @param minY The minimum coordinate (inclusive). Must be 0 or above
     * @param maxY The maximum Y layer (exclusive, aka height if min == 0)
     */
    public GridMap3D(Class<? extends HeightLayer> layerClass, int minY, int maxY) {
        if (maxY <= minY) {
            throw new RuntimeException(RZFormats.format("MaxY ({0}) must be above MinY ({1})", minY, maxY));
        }

        this.minY = minY;
        this.maxY = maxY;
        this.layers = new HeightLayer[maxY - minY];
        Constructor<? extends HeightLayer> constructor;
        try {
            constructor = layerClass.getConstructor(int.class);
        }
        catch (NoSuchMethodException e) {
            throw new RuntimeException("Layer class does not have a constructor that takes a primitive int as the Y coordinate", e);
        }

        for(int i = minY, j = 0; i < maxY; i++) {
            try {
                this.layers[j++] = Reflect.createInstance1P(constructor, i);
            }
            catch (RuntimeException e) {
                throw new RuntimeException("Failed to create layer " + i, e);
            }
        }
    }

    private GridMap3D(int minY, int maxY, HeightLayer<T>[] array) {
        this.minY = minY;
        this.maxY = maxY;
        this.layers = array;
    }

    public T getAt(int x, int y, int z) {
        return this.layers[y - this.minY].getAt(x, z);
    }

    public T putAt(int x, int y, int z, T value) {
        return this.layers[y - this.minY].putAt(x, z, value);
    }

    public T removeAt(int x, int y, int z) {
        return this.layers[y - this.minY].removeAt(x, z);
    }

    public boolean hasAt(int x, int y, int z) {
        return this.layers[y - this.minY].hasAt(x, z);
    }

    public boolean hasAtSafe(int x, int y, int z) {
        if (y < 0 || y >= this.layers.length) {
            return false;
        }

        return this.layers[y - this.minY].hasAt(x, z);
    }

    public void clearLayer(int y) {
        this.layers[y - this.minY].clear();
    }

    public void clear() {
        for (HeightLayer<T> layer : this.layers) {
            layer.clear();
        }
    }

    /**
     * Checks if this particular layer is indexable. This does not check if anything actually exists in the layer though
     * @param y The layer, that may or may not be within range
     * @return Whether the Y coord is within range
     */
    public boolean isWithinRange(int y) {
        return y >= this.minY && y < this.maxY;
    }

    /**
     * Ensures that calls to {@link GridMap3D#getAt(int, int, int)} and similar methods do not fail
     * due to an {@link ArrayIndexOutOfBoundsException} exception. This checks if the layer is indexable, and exists
     * @param y The layer, that may or may not be within range and may or may not exist (may be null)
     * @return Whether the layer at the given Y coord exists
     */
    public boolean doesLayerExist(int y) {
        return y >= this.minY && y < this.maxY && this.layers[y - this.minY] != null;
    }

    public int getMaxY() {
        return this.maxY;
    }

    public int getMinY() {
        return this.minY;
    }

    public int getHeight() {
        return this.maxY - this.minY;
    }

    public Collection<T> getAllValues() {
        ArrayList<T> list = new ArrayList<T>(this.layers.length * 4);
        for(HeightLayer<T> layer : this.layers) {
            if (layer != null) {
                if (layer.isEmpty()) {
                    continue;
                }

                list.addAll(layer.getValues());
            }
        }

        return list;
    }

    /**
     * The total number of elements in the entire grid map (in all layers)
     */
    public int size() {
        int i = 0;
        HeightLayer<T>[] layers = this.layers;
        for (int j = this.minY, len = this.maxY; j < len; j++) {
            i += layers[j].size();
        }

        return i;
    }

    /**
     * Copies the entire map and all layers, ensuring there's no reference between this map and the returned map (the only references being the actual GridMap values in the layers)
     */
    public GridMap3D<T> copy() {
        HeightLayer<T>[] newLayers = new HeightLayer[this.layers.length];
        HeightLayer<T>[] heightLayers = this.layers;
        for (int i = 0, len = heightLayers.length; i < len; i++) {
            HeightLayer<T> layer = heightLayers[i];
            if (layer != null) {
                newLayers[i] = layer.copy();
            }
        }

        return new GridMap3D<T>(this.minY, this.maxY, newLayers);
    }

    public static class HashMapBasedHeightLayer<T> implements HeightLayer<T> {
        private final int y;
        private final HashMap<Integer, T> map;

        public HashMapBasedHeightLayer(int y) {
            this.y = y;
            this.map = new HashMap<Integer, T>();
        }

        private HashMapBasedHeightLayer(int y, HashMap<Integer, T> map) {
            this.y = y;
            this.map = map;
        }

        @Override
        public T getAt(int x, int z) {
            return this.map.get(x + (z << 15));
        }

        @Override
        public T putAt(int x, int z, T value) {
            return this.map.put(x + (z << 15), value);
        }

        @Override
        public boolean hasAt(int x, int z) {
            return this.map.containsKey(x + (z << 15));
        }

        @Override
        public T removeAt(int x, int z) {
            return this.map.remove(x + (z << 15));
        }

        @Override
        public int getY() {
            return this.y;
        }

        @Override
        public boolean isEmpty() {
            return this.map.isEmpty();
        }

        @Override
        public void clear() {
            this.map.clear();
        }

        @Override
        public Collection<T> getValues() {
            return this.map.values();
        }

        @Override
        public int size() {
            return this.map.size();
        }

        @Override
        public HeightLayer<T> copy() {
            return new HashMapBasedHeightLayer<T>(this.y, new HashMap<Integer, T>(this.map));
        }
    }

    public static class ListBasedHeightLayer<T> implements HeightLayer<T> {
        private final int y;
        private final ArrayList<ObjXZ<T>> list;

        public ListBasedHeightLayer(int y) {
            this.y = y;
            this.list = new ArrayList<ObjXZ<T>>();
        }

        private ListBasedHeightLayer(int y, ArrayList<ObjXZ<T>> list) {
            this.y = y;
            this.list = list;
        }

        @Override
        public T getAt(int x, int z) {
            for(ObjXZ<T> obj : this.list) {
                if (obj.x == x && obj.z == z) {
                    return obj.value;
                }
            }

            return null;
        }

        @Override
        public T putAt(int x, int z, T value) {
            ArrayList<ObjXZ<T>> list = this.list;
            if (!list.isEmpty()) {
                for (int i = 0, size = list.size(); i < size; i++) {
                    ObjXZ<T> obj = list.get(i);
                    if (obj.x == x && obj.z == z) {
                        list.set(i, new ObjXZ<T>(value, x, z));
                        return obj.value;
                    }
                }
            }

            list.add(new ObjXZ<T>(value, x, z));
            return null;
        }

        @Override
        public boolean hasAt(int x, int z) {
            ArrayList<ObjXZ<T>> list = this.list;
            if (list.isEmpty()) {
                return false;
            }
            else {
                for (ObjXZ<T> obj : list) {
                    if (obj.x == x && obj.z == z) {
                        return true;
                    }
                }
            }

            return false;
        }

        @Override
        public T removeAt(int x, int z) {
            ArrayList<ObjXZ<T>> list = this.list;
            for (int i = 0, size = list.size(); i < size; i++) {
                ObjXZ<T> obj = list.get(i);
                if (obj.x == x && obj.z == z) {
                    list.remove(i);
                    return obj.value;
                }
            }

            return null;
        }

        @Override
        public int getY() {
            return this.y;
        }

        @Override
        public boolean isEmpty() {
            return this.list.isEmpty();
        }

        @Override
        public void clear() {
            this.list.clear();
        }

        @Override
        public Collection<T> getValues() {
            ArrayList<T> list = new ArrayList<T>(this.list.size());
            for(ObjXZ<T> item : this.list) {
                list.add(item.value);
            }

            return list;
        }

        @Override
        public int size() {
            return this.list.size();
        }

        @Override
        public HeightLayer<T> copy() {
            return new ListBasedHeightLayer<T>(this.y, new ArrayList<ObjXZ<T>>(this.list));
        }

        private static class ObjXZ<V> {
            public final V value;
            public final int x;
            public final int z;

            public ObjXZ(V value, int x, int z) {
                this.value = value;
                this.x = x;
                this.z = z;
            }
        }
    }

    public interface HeightLayer<T> {
        T getAt(int x, int z);
        T putAt(int x, int z, T value);
        boolean hasAt(int x, int z);
        T removeAt(int x, int z);
        int getY();

        boolean isEmpty();

        void clear();

        Collection<T> getValues();

        int size();

        HeightLayer<T> copy();
    }
}
