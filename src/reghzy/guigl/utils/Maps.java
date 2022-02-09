package reghzy.guigl.utils;

import java.util.HashMap;
import java.util.Iterator;

public class Maps {
    public static <K, V> HashMap<K, V> asMapRaw(Object... elements) {
        if (elements.length % 2 != 0) {
            throw new RuntimeException("Must have an equal number of elements");
        }

        HashMap map = new HashMap();
        for(int i = 0, len = elements.length; i < len;) {
            map.put(elements[i], elements[i++]);
        }

        return map;
    }

    public static <K, V> HashMap<K, V> asMap(Pair<K, V>... elements) {
        if (elements.length % 2 != 0) {
            throw new RuntimeException("Must have an equal number of elements");
        }

        HashMap<K, V> map = new HashMap<K, V>();
        for (Pair<K, V> pair : elements) {
            map.put(pair.a, pair.b);
        }

        return map;
    }

    public static <K, V> HashMap<K, V> asMap(K[] keys, V[] values) {
        HashMap<K, V> map = new HashMap<K, V>();
        for(int i = 0, end = Math.min(keys.length, values.length); i < end; i++) {
            map.put(keys[i], values[i]);
        }

        return map;
    }

    public static <K, V> HashMap<K, V> asMap(Iterable<K> keys, Iterable<V> values) {
        Iterator<K> kI = keys.iterator();
        Iterator<V> vI = values.iterator();
        HashMap<K, V> map = new HashMap<K, V>();
        while(kI.hasNext() && vI.hasNext()) {
            map.put(kI.next(), vI.next());
        }

        return map;
    }

    public static <K, V> HashMap<K, V> asMap(Iterable<K> keys, V... values) {
        int i = 0;
        int j = values.length;
        Iterator<K> kI = keys.iterator();
        HashMap<K, V> map = new HashMap<K, V>();
        while (kI.hasNext()) {
            if (i == j) {
                throw new IndexOutOfBoundsException("Not enough values for the number of keys supplied");
            }

            map.put(kI.next(), values[i++]);
        }

        return map;
    }
}
