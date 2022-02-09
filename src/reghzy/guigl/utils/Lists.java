package reghzy.guigl.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class Lists {
    public static List<String> stringStartsWith(Collection<String> list, String value) {
        return stringStartsWith(list, value, false);
    }

    public static List<String> stringStartsWith(Collection<String> list, String value, boolean toLower) {
        if (toLower) {
            value = value.toLowerCase();
        }

        ArrayList<String> newList = new ArrayList<String>(list.size());
        for (String next : list) {
            if (toLower) {
                if (next.toLowerCase().startsWith(value)) {
                    newList.add(next);
                }
            }
            else if (next.startsWith(value)) {
                newList.add(next);
            }
        }
        
        return newList;
    }

    public static String toString(Iterable<String> list) {
        StringJoiner joiner = new StringJoiner(", ");
        for(String str : list) {
            joiner.append(str);
        }

        return joiner.toString();
    }

    public static <E> List<E> toList(Set<E> set) {
        return new ArrayList<E>(set);
    }

    public static <E> List<E> toList(Iterable<E> iterable, int initialListSize) {
        List<E> list = new ArrayList<E>(initialListSize);
        for(E e : iterable)
            list.add(e);
        return list;
    }

    public static <E> List<E> toList(Iterable<E> iterable) {
        List<E> list = new ArrayList<E>();
        for (E e : iterable)
            list.add(e);
        return list;
    }
}
