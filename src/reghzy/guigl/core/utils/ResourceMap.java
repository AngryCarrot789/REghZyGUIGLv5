package reghzy.guigl.core.utils;

import java.util.HashMap;

public class ResourceMap {
    private final HashMap<String, Object> map;

    public ResourceMap() {
        this.map = new HashMap<String, Object>();
    }

    public Object put(String key, Object object) {
        return this.map.put(key, object);
    }

    public ColourARGB getColour(String name) {
        return (ColourARGB) this.map.get(name);
    }

    public boolean hasKey(String key) {
        return this.map.containsKey(key);
    }

    public ResourceMap getSubMap(String key) {
        return (ResourceMap) this.map.get(key);
    }

    public <T> T get(String key) {
        return (T) this.map.get(key);
    }

    public HashMap<String, Object> getMap() {
        return this.map;
    }

    public void clear() {
        this.map.clear();
    }
}
