package reghzy.guigl.utils;

import reghzy.guigl.utils.reflect.FieldAccessor;
import reghzy.guigl.utils.reflect.ReflectFieldAccessor;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class Memory {
    private static final FieldAccessor<ArrayList, Object[]> ARRAYLIST_ACCESS = ReflectFieldAccessor.create(ArrayList.class, "elementData");

    public static Object[] getArrayListData(ArrayList<?> arrayList) {
        return ARRAYLIST_ACCESS.get(arrayList);
    }

    private Memory() { }

    // ------------------------------ copy ------------------------------

    public static <E> List<E> asList(E... elements) {
        return Arrays.asList(elements);
    }

    public static <E> E getSetIndex(Set<E> set, int index) {
        if (index >= set.size()) {
            throw new IndexOutOfBoundsException("Index cannot be >= the map size");
        }

        int i = 0;
        for (E e : set) {
            if ((i++) == index) {
                return e;
            }
        }

        throw new RuntimeException("wut... set");
    }

    /**
     * Returns a value at the given index within a map. A map's value arrangement might shift around so this wont be 100% the same every time
     * @param map
     * @param index
     * @param <E>
     * @return
     */
    public static <E> E getMapIndex(Map<?,E> map, int index) {
        if (index >= map.size()) {
            throw new IndexOutOfBoundsException("Index cannot be >= the map size");
        }

        if (map.isEmpty()) {
            return null;
        }

        int i = 0;
        for(E value : map.values()) {
            if ((i++) == index) {
                return value;
            }
        }

        throw new Error();
    }

    /**
     * Copies the array (exact same values, but different array object)
     *
     * @param original The array to copy
     * @return A new array containing all the original values
     */
    public static int[] copy(int[] original) {
        int[] copy = new int[original.length];
        System.arraycopy(original, 0, copy, 0, original.length);
        return copy;
    }

    /**
     * Copies the array (exact same values, but different array object)
     *
     * @param original The array to copy
     * @return A new array containing all the original values
     */
    public static char[] copy(char[] original) {
        char[] copy = new char[original.length];
        System.arraycopy(original, 0, copy, 0, original.length);
        return copy;
    }

    /**
     * Copies the array (exact same values, but different array object)
     *
     * @param original The array to copy
     * @return A new array containing all the original values
     */
    public static byte[] copy(byte[] original) {
        byte[] copy = new byte[original.length];
        System.arraycopy(original, 0, copy, 0, original.length);
        return copy;
    }

    /**
     * Copies the array (exact same values, but different array object)
     *
     * @param original The array to copy
     * @return A new array containing all the original values
     */
    public static long[] copy(long[] original) {
        long[] copy = new long[original.length];
        System.arraycopy(original, 0, copy, 0, original.length);
        return copy;
    }

    /**
     * Copies the array (exact same values, but different array object)
     *
     * @param original The array to copy
     * @return A new array containing all the original values
     */
    public static float[] copy(float[] original) {
        float[] copy = new float[original.length];
        System.arraycopy(original, 0, copy, 0, original.length);
        return copy;
    }

    /**
     * Copies the array (exact same values, but different array object)
     *
     * @param original The array to copy
     * @return A new array containing all the original values
     */
    public static short[] copy(short[] original) {
        short[] copy = new short[original.length];
        System.arraycopy(original, 0, copy, 0, original.length);
        return copy;
    }

    /**
     * Copies the array (exact same values, but different array object)
     *
     * @param original The array to copy
     * @return A new array containing all the original values
     */
    public static boolean[] copy(boolean[] original) {
        boolean[] copy = new boolean[original.length];
        System.arraycopy(original, 0, copy, 0, original.length);
        return copy;
    }

    /**
     * Copies the array (exact same values, but different array object)
     *
     * @param original The array to copy
     * @return A new array containing all the original values
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] copy(T[] original) {
        T[] copy = createArray(original.getClass(), original.length);
        System.arraycopy(original, 0, copy, 0, original.length);
        return copy;
    }

    // ------------------------------ extractAfter ------------------------------

    /**
     * Extracts all of the values at and after the given start index from the given array
     * @param original       The array to extract values from
     * @param startInclusive The index to start extracting at (inclusive)
     * @return An array containing all of the values after the start index (within the given array)
     */
    public static int[] extractAfter(int[] original, int startInclusive) {
        int length = original.length - startInclusive;
        int[] copy = new int[length];
        System.arraycopy(original, startInclusive, copy, 0, length);
        return copy;
    }

    /**
     * Extracts all of the values at and after the given start index from the given array
     *
     * @param original       The array to extract values from
     * @param startInclusive The index to start extracting at (inclusive)
     * @return An array containing all of the values after the start index (within the given array)
     */
    public static char[] extractAfter(char[] original, int startInclusive) {
        int length = original.length - startInclusive;
        char[] copy = new char[length];
        System.arraycopy(original, startInclusive, copy, 0, length);
        return copy;
    }

    public static byte[] extractAfter(byte[] original, int startInclusive) {
        int length = original.length - startInclusive;
        byte[] copy = new byte[length];
        System.arraycopy(original, startInclusive, copy, 0, length);
        return copy;
    }

    /**
     * Extracts all of the values at and after the given start index from the given array
     *
     * @param original       The array to extract values from
     * @param startInclusive The index to start extracting at (inclusive)
     * @return An array containing all of the values after the start index (within the given array)
     */
    public static long[] extractAfter(long[] original, int startInclusive) {
        int length = original.length - startInclusive;
        long[] copy = new long[length];
        System.arraycopy(original, startInclusive, copy, 0, length);
        return copy;
    }

    /**
     * Extracts all of the values at and after the given start index from the given array
     *
     * @param original       The array to extract values from
     * @param startInclusive The index to start extracting at (inclusive)
     * @return An array containing all of the values after the start index (within the given array)
     */
    public static float[] extractAfter(float[] original, int startInclusive) {
        int length = original.length - startInclusive;
        float[] copy = new float[length];
        System.arraycopy(original, startInclusive, copy, 0, length);
        return copy;
    }

    /**
     * Extracts all of the values at and after the given start index from the given array
     *
     * @param original       The array to extract values from
     * @param startInclusive The index to start extracting at (inclusive)
     * @return An array containing all of the values after the start index (within the given array)
     */
    public static short[] extractAfter(short[] original, int startInclusive) {
        int length = original.length - startInclusive;
        short[] copy = new short[length];
        System.arraycopy(original, startInclusive, copy, 0, length);
        return copy;
    }

    /**
     * Extracts all of the values at and after the given start index from the given array
     *
     * @param original       The array to extract values from
     * @param startInclusive The index to start extracting at (inclusive)
     * @return An array containing all of the values after the start index (within the given array)
     */
    public static boolean[] extractAfter(boolean[] original, int startInclusive) {
        int length = original.length - startInclusive;
        boolean[] copy = new boolean[length];
        System.arraycopy(original, startInclusive, copy, 0, length);
        return copy;
    }

    /**
     * Extracts all of the values at and after the given start index from the given array
     *
     * @param original       The array to extract values from
     * @param startInclusive The index to start extracting at (inclusive)
     * @return An array containing all of the values after the start index (within the given array)
     */
    public static <T> T[] extractAfter(T[] original, int startInclusive) {
        int length = original.length - startInclusive;
        T[] copy = createArray(original.getClass(), length);
        System.arraycopy(original, startInclusive, copy, 0, length);
        return copy;
    }

    public static <T> ArrayList<T> extractAfter(List<T> original, int startInclusive) {
        int length = original.size() - startInclusive;
        ArrayList<T> copy = new ArrayList<T>(length);
        for (int i = startInclusive; i < length; i++) {
            copy.add(original.get(i));
        }
        return copy;
    }

    // ------------------------------ extractBefore ------------------------------

    public static int[] extractBefore(int[] original, int index) {
        int length = Math.min(index, original.length);
        int[] array = new int[length];
        System.arraycopy(original, 0, array, 0, length);
        return array;
    }

    public static char[] extractBefore(char[] original, int index) {
        int length = Math.min(index, original.length);
        char[] array = new char[length];
        System.arraycopy(original, 0, array, 0, length);
        return array;
    }

    public static byte[] extractBefore(byte[] original, int index) {
        int length = Math.min(index, original.length);
        byte[] array = new byte[length];
        System.arraycopy(original, 0, array, 0, length);
        return array;
    }

    public static long[] extractBefore(long[] original, int index) {
        int length = Math.min(index, original.length);
        long[] array = new long[length];
        System.arraycopy(original, 0, array, 0, length);
        return array;
    }

    public static float[] extractBefore(float[] original, int index) {
        int length = Math.min(index, original.length);
        float[] array = new float[length];
        System.arraycopy(original, 0, array, 0, length);
        return array;
    }

    public static short[] extractBefore(short[] original, int index) {
        int length = Math.min(index, original.length);
        short[] array = new short[length];
        System.arraycopy(original, 0, array, 0, length);
        return array;
    }

    public static boolean[] extractBefore(boolean[] original, int index) {
        int length = Math.min(index, original.length);
        boolean[] array = new boolean[length];
        System.arraycopy(original, 0, array, 0, length);
        return array;
    }

    public static <T> T[] extractBefore(T[] original, int index) {
        int length = Math.min(index, original.length);
        T[] array = createArray(original.getClass(), length);
        System.arraycopy(original, 0, array, 0, length);
        return array;
    }

    public static <T> ArrayList<T> extractBefore(List<T> original, int index) {
        int length = Math.min(index, original.size());
        ArrayList<T> copy = new ArrayList<T>(length);
        for (int i = 0; i < length; i++) {
            copy.add(original.get(i));
        }
        return copy;
    }

    // ------------------------------ extractRange ------------------------------

    /**
     * Extracts all between the start and the given index (including that value) the given start index from the given array
     *
     * @param original       The array to extract values from
     * @return An array containing all of the values before and at the index (within the given array)
     */
    public static int[] extractRange(int[] original, int startInclusive, int endInclusive) {
        int length = Math.min((endInclusive - startInclusive) + 1, original.length);
        int[] copy = new int[length];
        System.arraycopy(original, startInclusive, copy, 0, length);
        return copy;
    }

    public static char[] extractRange(char[] original, int startInclusive, int endInclusive) {
        int length = Math.min((endInclusive - startInclusive) + 1, original.length);
        char[] copy = new char[length];
        System.arraycopy(original, startInclusive, copy, 0, length);
        return copy;
    }

    public static byte[] extractRange(byte[] original, int startInclusive, int endInclusive) {
        int length = Math.min((endInclusive - startInclusive) + 1, original.length);
        byte[] copy = new byte[length];
        System.arraycopy(original, startInclusive, copy, 0, length);
        return copy;
    }

    public static long[] extractRange(long[] original, int startInclusive, int endInclusive) {
        int length = Math.min((endInclusive - startInclusive) + 1, original.length);
        long[] copy = new long[length];
        System.arraycopy(original, startInclusive, copy, 0, length);
        return copy;
    }

    public static float[] extractRange(float[] original, int startInclusive, int endInclusive) {
        int length = Math.min((endInclusive - startInclusive) + 1, original.length);
        float[] copy = new float[length];
        System.arraycopy(original, startInclusive, copy, 0, length);
        return copy;
    }

    public static short[] extractRange(short[] original, int startInclusive, int endInclusive) {
        int length = Math.min((endInclusive - startInclusive) + 1, original.length);
        short[] copy = new short[length];
        System.arraycopy(original, startInclusive, copy, 0, length);
        return copy;
    }

    public static boolean[] extractRange(boolean[] original, int startInclusive, int endInclusive) {
        int length = Math.min((endInclusive - startInclusive) + 1, original.length);
        boolean[] copy = new boolean[length];
        System.arraycopy(original, startInclusive, copy, 0, length);
        return copy;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] extractRange(T[] original, int startInclusive, int endInclusive) {
        int length = Math.min((endInclusive - startInclusive) + 1, original.length);
        T[] copy = createArray(original.getClass(), original.length);
        System.arraycopy(original, startInclusive, copy, 0, length);
        return copy;
    }

    public static <T> ArrayList<T> extractRange(List<T> original, int startInclusive, int endInclusive) {
        int length = Math.min((endInclusive - startInclusive) + 1, original.size());
        ArrayList<T> copy = new ArrayList<T>(length);
        for(int i = startInclusive; i <= endInclusive; i++) {
            copy.add(original.get(i));
        }
        return copy;
    }

    // ------------------------------ removeRange ------------------------------

    /**
     * Removes the elements between and including the given start index (inclusive) and the given end index (inclusive)
     * @param src   The array to remove elements from
     * @param start The start index to remove at (inclusive)
     * @param end   The end index to remove elements to (inclusive)
     * @return A new shortened array with the removed elements
     */
    public static int[] removeRange(int[] src, int start, int end) {
        end = Math.min(end, src.length - 1);
        int[] out = new int[src.length - (end - start + 1)];
        System.arraycopy(src, 0, out, 0, start);
        System.arraycopy(src, end + 1, out, start, src.length - end - 1);
        return out;
    }

    /**
     * Removes the elements between and including the given start index (inclusive) and the given end index (inclusive)
     *
     * @param src   The array to remove elements from
     * @param start The start index to remove at (inclusive)
     * @param end   The end index to remove elements to (inclusive)
     * @return A new shortened array with the removed elements
     */
    public static char[] removeRange(char[] src, int start, int end) {
        end = Math.min(end, src.length - 1);
        char[] out = new char[src.length - (end - start + 1)];
        System.arraycopy(src, 0, out, 0, start);
        System.arraycopy(src, end + 1, out, start, src.length - end - 1);
        return out;
    }

    /**
     * Removes the elements between and including the given start index (inclusive) and the given end index (inclusive)
     *
     * @param src   The array to remove elements from
     * @param start The start index to remove at (inclusive)
     * @param end   The end index to remove elements to (inclusive)
     * @return A new shortened array with the removed elements
     */
    public static byte[] removeRange(byte[] src, int start, int end) {
        end = Math.min(end, src.length - 1);
        byte[] out = new byte[src.length - (end - start + 1)];
        System.arraycopy(src, 0, out, 0, start);
        System.arraycopy(src, end + 1, out, start, src.length - end - 1);
        return out;
    }

    /**
     * Removes the elements between and including the given start index (inclusive) and the given end index (inclusive)
     *
     * @param src   The array to remove elements from
     * @param start The start index to remove at (inclusive)
     * @param end   The end index to remove elements to (inclusive)
     * @return A new shortened array with the removed elements
     */
    public static long[] removeRange(long[] src, int start, int end) {
        end = Math.min(end, src.length - 1);
        long[] out = new long[src.length - (end - start + 1)];
        System.arraycopy(src, 0, out, 0, start);
        System.arraycopy(src, end + 1, out, start, src.length - end - 1);
        return out;
    }

    /**
     * Removes the elements between and including the given start index (inclusive) and the given end index (inclusive)
     *
     * @param src   The array to remove elements from
     * @param start The start index to remove at (inclusive)
     * @param end   The end index to remove elements to (inclusive)
     * @return A new shortened array with the removed elements
     */
    public static float[] removeRange(float[] src, int start, int end) {
        end = Math.min(end, src.length - 1);
        float[] out = new float[src.length - (end - start + 1)];
        System.arraycopy(src, 0, out, 0, start);
        System.arraycopy(src, end + 1, out, start, src.length - end - 1);
        return out;
    }

    /**
     * Removes the elements between and including the given start index (inclusive) and the given end index (inclusive)
     *
     * @param src   The array to remove elements from
     * @param start The start index to remove at (inclusive)
     * @param end   The end index to remove elements to (inclusive)
     * @return A new shortened array with the removed elements
     */
    public static short[] removeRange(short[] src, int start, int end) {
        end = Math.min(end, src.length - 1);
        short[] out = new short[src.length - (end - start + 1)];
        System.arraycopy(src, 0, out, 0, start);
        System.arraycopy(src, end + 1, out, start, src.length - end - 1);
        return out;
    }

    /**
     * Removes the elements between and including the given start index (inclusive) and the given end index (inclusive)
     *
     * @param src   The array to remove elements from
     * @param start The start index to remove at (inclusive)
     * @param end   The end index to remove elements to (inclusive)
     * @return A new shortened array with the removed elements
     */
    public static boolean[] removeRange(boolean[] src, int start, int end) {
        end = Math.min(end, src.length - 1);
        boolean[] out = new boolean[src.length - (end - start + 1)];
        System.arraycopy(src, 0, out, 0, start);
        System.arraycopy(src, end + 1, out, start, src.length - end - 1);
        return out;
    }

    /**
     * Removes the elements between and including the given start index (inclusive) and the given end index (inclusive)
     *
     * @param src   The array to remove elements from
     * @param start The start index to remove at (inclusive)
     * @param end   The end index to remove elements to (inclusive)
     * @return A new shortened array with the removed elements
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] removeRange(T[] src, int start, int end) {
        end = Math.min(end, src.length - 1);
        T[] out = createArray(src.getClass(), src.length - (end - start + 1));
        System.arraycopy(src, 0, out, 0, start);
        System.arraycopy(src, end + 1, out, start, src.length - end - 1);
        return out;
    }

    public static <T> ArrayList<T> removeRange(List<T> src, int start, int end) {
        end = Math.min(end, src.size() - 1);
        ArrayList<T> out = new ArrayList<T>(src.size() - (end - start + 1));
        for(int i = 0, len = start + 1; i < len; i++) {
            out.add(src.get(i));
        }
        for (int i = end + 1, len = src.size(); i < len; i++) {
            out.add(src.get(i));
        }
        return out;
    }

    // -------------------------------------- remove --------------------------------------

    /**
     * Removes a single element from the given array (resizes the array, making it smaller by 1 element)
     * @param src   The array to remove an element from
     * @param index The index to remove
     * @return The shortened array with an element removed
     */
    public static int[] removeRange(int[] src, int index) {
        return removeRange(src, index, index);
    }

    /**
     * Removes a single element from the given array (resizes the array, making it smaller by 1 element)
     *
     * @param src   The array to remove an element from
     * @param index The index to remove
     * @return The shortened array with an element removed
     */
    public static char[] removeRange(char[] src, int index) {
        return removeRange(src, index, index);
    }

    /**
     * Removes a single element from the given array (resizes the array, making it smaller by 1 element)
     *
     * @param src   The array to remove an element from
     * @param index The index to remove
     * @return The shortened array with an element removed
     */
    public static byte[] removeRange(byte[] src, int index) {
        return removeRange(src, index, index);
    }

    /**
     * Removes a single element from the given array (resizes the array, making it smaller by 1 element)
     *
     * @param src   The array to remove an element from
     * @param index The index to remove
     * @return The shortened array with an element removed
     */
    public static long[] removeRange(long[] src, int index) {
        return removeRange(src, index, index);
    }

    /**
     * Removes a single element from the given array (resizes the array, making it smaller by 1 element)
     *
     * @param src   The array to remove an element from
     * @param index The index to remove
     * @return The shortened array with an element removed
     */
    public static float[] removeRange(float[] src, int index) {
        return removeRange(src, index, index);
    }

    /**
     * Removes a single element from the given array (resizes the array, making it smaller by 1 element)
     *
     * @param src   The array to remove an element from
     * @param index The index to remove
     * @return The shortened array with an element removed
     */
    public static short[] removeRange(short[] src, int index) {
        return removeRange(src, index, index);
    }

    /**
     * Removes a single element from the given array (resizes the array, making it smaller by 1 element)
     *
     * @param src   The array to remove an element from
     * @param index The index to remove
     * @return The shortened array with an element removed
     */
    public static boolean[] removeRange(boolean[] src, int index) {
        return removeRange(src, index, index);
    }

    /**
     * Removes a single element from the given array (resizes the array, making it smaller by 1 element)
     *
     * @param src   The array to remove an element from
     * @param index The index to remove
     * @return The shortened array with an element removed
     */
    public static <T> T[] remove(T[] src, int index) {
        return removeRange(src, index, index);
    }

    // ------------------------------ combineArrays(int[][]) ------------------------------

    /**
     * Combines all of the given arrays into one big array, one after the other (left to right)
     * @param arrays      The arrays to combine
     * @return An array containing all of the given arrays one after the other
     */
    public static int[] combineArrays(int[]... arrays) {
        int outputLength = 0;
        for (int[] chars : arrays) {
            outputLength += chars.length;
        }

        return combineArrays(outputLength, arrays);
    }

    /**
     * Combines all of the given arrays into one big array, one after the other (left to right)
     *
     * @param arrays The arrays to combine
     * @return An array containing all of the given arrays one after the other
     */
    public static char[] combineArrays(char[]... arrays) {
        int outputLength = 0;
        for (char[] chars : arrays) {
            outputLength += chars.length;
        }

        return combineArrays(outputLength, arrays);
    }

    /**
     * Combines all of the given arrays into one big array, one after the other (left to right)
     *
     * @param arrays The arrays to combine
     * @return An array containing all of the given arrays one after the other
     */
    public static byte[] combineArrays(byte[]... arrays) {
        int outputLength = 0;
        for (byte[] chars : arrays) {
            outputLength += chars.length;
        }

        return combineArrays(outputLength, arrays);
    }

    /**
     * Combines all of the given arrays into one big array, one after the other (left to right)
     *
     * @param arrays The arrays to combine
     * @return An array containing all of the given arrays one after the other
     */
    public static long[] combineArrays(long[]... arrays) {
        int outputLength = 0;
        for (long[] chars : arrays) {
            outputLength += chars.length;
        }

        return combineArrays(outputLength, arrays);
    }

    /**
     * Combines all of the given arrays into one big array, one after the other (left to right)
     *
     * @param arrays The arrays to combine
     * @return An array containing all of the given arrays one after the other
     */
    public static float[] combineArrays(float[]... arrays) {
        int outputLength = 0;
        for (float[] chars : arrays) {
            outputLength += chars.length;
        }

        return combineArrays(outputLength, arrays);
    }

    /**
     * Combines all of the given arrays into one big array, one after the other (left to right)
     *
     * @param arrays The arrays to combine
     * @return An array containing all of the given arrays one after the other
     */
    public static short[] combineArrays(short[]... arrays) {
        int outputLength = 0;
        for (short[] chars : arrays) {
            outputLength += chars.length;
        }

        return combineArrays(outputLength, arrays);
    }

    /**
     * Combines all of the given arrays into one big array, one after the other (left to right)
     *
     * @param arrays The arrays to combine
     * @return An array containing all of the given arrays one after the other
     */
    public static boolean[] combineArrays(boolean[]... arrays) {
        int outputLength = 0;
        for (boolean[] chars : arrays) {
            outputLength += chars.length;
        }

        return combineArrays(outputLength, arrays);
    }

    /**
     * Combines all of the given arrays into one big array, one after the other (left to right)
     *
     * @param arrays The arrays to combine
     * @return An array containing all of the given arrays one after the other
     */
    public static <T> T[] combineArrays(T[]... arrays) {
        int outputLength = 0;
        for (T[] chars : arrays) {
            outputLength += chars.length;
        }

        return combineArrays(outputLength, arrays);
    }

    // ------------------------------ combineArrays(int, int[][]) ------------------------------

    /**
     * Combines all of the given arrays into one big array, one after the other (left to right), taking a
     * totalLength value if you've already pre-calculated the length of all the arrays combined
     * @param totalLength The combined length of all of the given arrays
     * @param arrays The arrays to combine
     * @return An array containing all of the given arrays one after the other
     */
    public static int[] combineArrays(int totalLength, int[]... arrays) {
        int[] output = new int[totalLength];
        for (int outIndex = 0, j = 0, len = arrays.length; j < len; j++) {
            int[] array = arrays[j];
            System.arraycopy(array, 0, output, outIndex, array.length);
            outIndex += array.length;
        }
        return output;
    }

    /**
     * Combines all of the given arrays into one big array, one after the other (left to right), taking a
     * totalLength value if you've already pre-calculated the length of all the arrays combined
     *
     * @param totalLength The combined length of all of the given arrays
     * @param arrays      The arrays to combine
     * @return An array containing all of the given arrays one after the other
     */
    public static char[] combineArrays(int totalLength, char[]... arrays) {
        char[] output = new char[totalLength];
        for (int outIndex = 0, j = 0, len = arrays.length; j < len; j++) {
            char[] array = arrays[j];
            System.arraycopy(array, 0, output, outIndex, array.length);
            outIndex += array.length;
        }
        return output;
    }

    /**
     * Combines all of the given arrays into one big array, one after the other (left to right), taking a
     * totalLength value if you've already pre-calculated the length of all the arrays combined
     *
     * @param totalLength The combined length of all of the given arrays
     * @param arrays      The arrays to combine
     * @return An array containing all of the given arrays one after the other
     */
    public static byte[] combineArrays(int totalLength, byte[]... arrays) {
        byte[] output = new byte[totalLength];
        for (int outIndex = 0, j = 0, len = arrays.length; j < len; j++) {
            byte[] array = arrays[j];
            System.arraycopy(array, 0, output, outIndex, array.length);
            outIndex += array.length;
        }
        return output;
    }

    /**
     * Combines all of the given arrays into one big array, one after the other (left to right), taking a
     * totalLength value if you've already pre-calculated the length of all the arrays combined
     *
     * @param totalLength The combined length of all of the given arrays
     * @param arrays      The arrays to combine
     * @return An array containing all of the given arrays one after the other
     */
    public static long[] combineArrays(int totalLength, long[]... arrays) {
        long[] output = new long[totalLength];
        for (int outIndex = 0, j = 0, len = arrays.length; j < len; j++) {
            long[] array = arrays[j];
            System.arraycopy(array, 0, output, outIndex, array.length);
            outIndex += array.length;
        }
        return output;
    }

    /**
     * Combines all of the given arrays into one big array, one after the other (left to right), taking a
     * totalLength value if you've already pre-calculated the length of all the arrays combined
     *
     * @param totalLength The combined length of all of the given arrays
     * @param arrays      The arrays to combine
     * @return An array containing all of the given arrays one after the other
     */
    public static float[] combineArrays(int totalLength, float[]... arrays) {
        float[] output = new float[totalLength];
        for (int outIndex = 0, j = 0, len = arrays.length; j < len; j++) {
            float[] array = arrays[j];
            System.arraycopy(array, 0, output, outIndex, array.length);
            outIndex += array.length;
        }
        return output;
    }

    /**
     * Combines all of the given arrays into one big array, one after the other (left to right), taking a
     * totalLength value if you've already pre-calculated the length of all the arrays combined
     *
     * @param totalLength The combined length of all of the given arrays
     * @param arrays      The arrays to combine
     * @return An array containing all of the given arrays one after the other
     */
    public static short[] combineArrays(int totalLength, short[]... arrays) {
        short[] output = new short[totalLength];
        for (int outIndex = 0, j = 0, len = arrays.length; j < len; j++) {
            short[] array = arrays[j];
            System.arraycopy(array, 0, output, outIndex, array.length);
            outIndex += array.length;
        }
        return output;
    }

    /**
     * Combines all of the given arrays into one big array, one after the other (left to right), taking a
     * totalLength value if you've already pre-calculated the length of all the arrays combined
     *
     * @param totalLength The combined length of all of the given arrays
     * @param arrays      The arrays to combine
     * @return An array containing all of the given arrays one after the other
     */
    public static boolean[] combineArrays(int totalLength, boolean[]... arrays) {
        boolean[] output = new boolean[totalLength];
        for (int outIndex = 0, j = 0, len = arrays.length; j < len; j++) {
            boolean[] array = arrays[j];
            System.arraycopy(array, 0, output, outIndex, array.length);
            outIndex += array.length;
        }
        return output;
    }

    /**
     * Combines all of the given arrays into one big array, one after the other (left to right), taking a
     * totalLength value if you've already pre-calculated the length of all the arrays combined
     *
     * @param totalLength The combined length of all of the given arrays
     * @param arrays      The arrays to combine
     * @return An array containing all of the given arrays one after the other
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] combineArrays(int totalLength, T[]... arrays) {
        if(arrays.length == 0)
            return null;

        T[] output = createArray(arrays[0].getClass(), totalLength);
        for (int outIndex = 0, j = 0, len = arrays.length; j < len; j++) {
            T[] array = arrays[j];
            System.arraycopy(array, 0, output, outIndex, array.length);
            outIndex += array.length;
        }
        return output;
    }

    // ------------------------------ indexOf ------------------------------

    /**
     * Returns the index where all the elements in the array 'value' match the next values in the array 'array'
     * @param array      The array to search through
     * @param value      The values to search for in array
     * @param startIndex The index to start searching at (in array)
     * @return The index, or -1 if value wasn't found in array
     */
    public static int indexOf(int[] array, int[] value, int startIndex) {
        int first = value[0];
        int i = startIndex;
        while (i < array.length) {
            int c = array[i++];
            if (c == first) {
                boolean found = true;
                int j = 1, k = i;
                while (j < value.length && k < array.length) {
                    if (value[j++] != array[k++]) {
                        found = false;
                        break;
                    }
                }
                if (found) {
                    return i - 1;
                }
            }
        }

        return -1;
    }

    /**
     * Returns the index where all the elements in the array 'value' match the next values in the array 'array'
     *
     * @param array      The array to search through
     * @param value      The values to search for in array
     * @param startIndex The index to start searching at (in array)
     * @return The index, or -1 if value wasn't found in array
     */
    public static int indexOf(char[] array, char[] value, int startIndex) {
        char first = value[0];
        int i = startIndex;
        int arrLen = array.length;
        int valLen = value.length;
        // indexOf("derlol", "er")
        while (i < arrLen) {
            char c = array[i++];
            if (c == first) {
                boolean found = true;
                int j = 1, k = i;
                while (j < valLen && k < arrLen) {
                    if (value[j++] != array[k++]) {
                        found = false;
                        break;
                    }
                }
                if (found) {
                    return i - 1;
                }
            }
        }

        return -1;
    }

    /**
     * Returns the index where all the elements in the array 'value' match the next values in the array 'array'
     *
     * @param array      The array to search through
     * @param value      The values to search for in array
     * @param startIndex The index to start searching at (in array)
     * @return The index, or -1 if value wasn't found in array
     */
    public static int indexOf(byte[] array, byte[] value, int startIndex) {
        byte first = value[0];
        int i = startIndex;
        while (i < array.length) {
            byte c = array[i++];
            if (c == first) {
                boolean found = true;
                int j = 1, k = i;
                while (j < value.length && k < array.length) {
                    if (value[j++] != array[k++]) {
                        found = false;
                        break;
                    }
                }
                if (found) {
                    return i - 1;
                }
            }
        }

        return -1;
    }

    /**
     * Returns the index where all the elements in the array 'value' match the next values in the array 'array'
     *
     * @param array      The array to search through
     * @param value      The values to search for in array
     * @param startIndex The index to start searching at (in array)
     * @return The index, or -1 if value wasn't found in array
     */
    public static int indexOf(long[] array, long[] value, int startIndex) {
        long first = value[0];
        int i = startIndex;
        while (i < array.length) {
            long c = array[i++];
            if (c == first) {
                boolean found = true;
                int j = 1, k = i;
                while (j < value.length && k < array.length) {
                    if (value[j++] != array[k++]) {
                        found = false;
                        break;
                    }
                }
                if (found) {
                    return i - 1;
                }
            }
        }

        return -1;
    }

    /**
     * Returns the index where all the elements in the array 'value' match the next values in the array 'array'
     *
     * @param array      The array to search through
     * @param value      The values to search for in array
     * @param startIndex The index to start searching at (in array)
     * @return The index, or -1 if value wasn't found in array
     */
    public static int indexOf(float[] array, float[] value, int startIndex) {
        float first = value[0];
        int i = startIndex;
        while (i < array.length) {
            float c = array[i++];
            if (c == first) {
                boolean found = true;
                int j = 1, k = i;
                while (j < value.length && k < array.length) {
                    if (value[j++] != array[k++]) {
                        found = false;
                        break;
                    }
                }
                if (found) {
                    return i - 1;
                }
            }
        }

        return -1;
    }

    /**
     * Returns the index where all the elements in the array 'value' match the next values in the array 'array'
     *
     * @param array      The array to search through
     * @param value      The values to search for in array
     * @param startIndex The index to start searching at (in array)
     * @return The index, or -1 if value wasn't found in array
     */
    public static int indexOf(short[] array, short[] value, int startIndex) {
        short first = value[0];
        int i = startIndex;
        while (i < array.length) {
            short c = array[i++];
            if (c == first) {
                boolean found = true;
                int j = 1, k = i;
                while (j < value.length && k < array.length) {
                    if (value[j++] != array[k++]) {
                        found = false;
                        break;
                    }
                }
                if (found) {
                    return i - 1;
                }
            }
        }

        return -1;
    }

    /**
     * Returns the index where all the elements in the array 'value' match the next values in the array 'array'
     *
     * @param array      The array to search through
     * @param value      The values to search for in array
     * @param startIndex The index to start searching at (in array)
     * @return The index, or -1 if value wasn't found in array
     */
    public static int indexOf(boolean[] array, boolean[] value, int startIndex) {
        boolean first = value[0];
        int i = startIndex;
        while (i < array.length) {
            boolean c = array[i++];
            if (c == first) {
                boolean found = true;
                int j = 1, k = i;
                while (j < value.length && k < array.length) {
                    if (value[j++] != array[k++]) {
                        found = false;
                        break;
                    }
                }
                if (found) {
                    return i - 1;
                }
            }
        }

        return -1;
    }

    /**
     * Returns the index where all the elements in the array 'value' match the next values in the array 'array'
     *
     * @param array      The array to search through
     * @param value      The values to search for in array
     * @param startIndex The index to start searching at (in array)
     * @return The index, or -1 if value wasn't found in array
     */
    public static <T> int indexOf(T[] array, T[] value, int startIndex) {
        T first = value[0];
        int i = startIndex;
        while (i < array.length) {
            T c = array[i++];
            if (c.equals(first)) {
                boolean found = true;
                int j = 1, k = i;
                while (j < value.length && k < array.length) {
                    if (!value[j++].equals(array[k++])) {
                        found = false;
                        break;
                    }
                }
                if (found) {
                    return i - 1;
                }
            }
        }

        return -1;
    }

    /**
     * Returns the index where all the elements in the array 'value' match the next values in the array 'array'
     *
     * @param array      The array to search through
     * @param value      The values to search for in array
     * @return The index, or -1 if value wasn't found in array
     */
    public static int indexOf(int[] array, int[] value) {
        return indexOf(array, value, 0);
    }

    /**
     * Returns the index where all the elements in the array 'value' match the next values in the array 'array'
     *
     * @param array The array to search through
     * @param value The values to search for in array
     * @return The index, or -1 if value wasn't found in array
     */
    public static int indexOf(char[] array, char[] value) {
        return indexOf(array, value, 0);
    }

    /**
     * Returns the index where all the elements in the array 'value' match the next values in the array 'array'
     *
     * @param array The array to search through
     * @param value The values to search for in array
     * @return The index, or -1 if value wasn't found in array
     */
    public static int indexOf(byte[] array, byte[] value) {
        return indexOf(array, value, 0);
    }

    /**
     * Returns the index where all the elements in the array 'value' match the next values in the array 'array'
     *
     * @param array The array to search through
     * @param value The values to search for in array
     * @return The index, or -1 if value wasn't found in array
     */
    public static int indexOf(long[] array, long[] value) {
        return indexOf(array, value, 0);
    }

    /**
     * Returns the index where all the elements in the array 'value' match the next values in the array 'array'
     *
     * @param array The array to search through
     * @param value The values to search for in array
     * @return The index, or -1 if value wasn't found in array
     */
    public static int indexOf(float[] array, float[] value) {
        return indexOf(array, value, 0);
    }

    /**
     * Returns the index where all the elements in the array 'value' match the next values in the array 'array'
     *
     * @param array The array to search through
     * @param value The values to search for in array
     * @return The index, or -1 if value wasn't found in array
     */
    public static int indexOf(short[] array, short[] value) {
        return indexOf(array, value, 0);
    }

    /**
     * Returns the index where all the elements in the array 'value' match the next values in the array 'array'
     *
     * @param array The array to search through
     * @param value The values to search for in array
     * @return The index, or -1 if value wasn't found in array
     */
    public static int indexOf(boolean[] array, boolean[] value) {
        return indexOf(array, value, 0);
    }

    /**
     * Returns the index where all the elements in the array 'value' match the next values in the array 'array'
     *
     * @param array The array to search through
     * @param value The values to search for in array
     * @return The index, or -1 if value wasn't found in array
     */
    public static <T> int indexOf(T[] array, T[] value) {
        return indexOf(array, value, 0);
    }

    // ------------------------------ contains ------------------------------

    /**
     * Returns true if array contains the exact range of values in the array 'value'
     * @param array The array to search through
     * @param value The array to find in array
     */
    public static boolean contains(int[] array, int[] value) {
        return indexOf(array, value) != -1;
    }

    /**
     * Returns true if array contains the exact range of values in the array 'value'
     *
     * @param array The array to search through
     * @param value The array to find in array
     */
    public static boolean contains(char[] array, char[] value) {
        return indexOf(array, value) != -1;
    }

    /**
     * Returns true if array contains the exact range of values in the array 'value'
     *
     * @param array The array to search through
     * @param value The array to find in array
     */
    public static boolean contains(byte[] array, byte[] value) {
        return indexOf(array, value) != -1;
    }

    /**
     * Returns true if array contains the exact range of values in the array 'value'
     *
     * @param array The array to search through
     * @param value The array to find in array
     */
    public static boolean contains(long[] array, long[] value) {
        return indexOf(array, value) != -1;
    }

    /**
     * Returns true if array contains the exact range of values in the array 'value'
     *
     * @param array The array to search through
     * @param value The array to find in array
     */
    public static boolean contains(float[] array, float[] value) {
        return indexOf(array, value) != -1;
    }

    /**
     * Returns true if array contains the exact range of values in the array 'value'
     *
     * @param array The array to search through
     * @param value The array to find in array
     */
    public static boolean contains(short[] array, short[] value) {
        return indexOf(array, value) != -1;
    }

    /**
     * Returns true if array contains the exact range of values in the array 'value'
     *
     * @param array The array to search through
     * @param value The array to find in array
     */
    public static boolean contains(boolean[] array, boolean[] value) {
        return indexOf(array, value) != -1;
    }

    /**
     * Returns true if array contains the exact range of values in the array 'value'
     *
     * @param array The array to search through
     * @param value The array to find in array
     */
    public static <T> boolean contains(T[] array, T[] value) {
        return indexOf(array, value) != -1;
    }

    /**
     * Creates a generic array with the given length/size
     *
     * @param length The length/size of the array
     * @param <T>    The class type the array contains
     * @return The generic array with the given length/size
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] createArray(Class<?> arrayType, int length) {
        return (arrayType == Object[].class) ? (T[]) new Object[length] : (T[]) Array.newInstance(arrayType.getComponentType(), length);
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] createArray2(Class<T> componentType, int length) {
        return (componentType == Object[].class) ? (T[]) new Object[length] : (T[]) Array.newInstance(componentType, length);
    }

    /**
     * Moves the element at the given index to the end of the array
     * @param array
     * @param index
     * @param <T>
     */
    public static <T> void moveElementToEnd(T[] array, int index) {
        T element = array[index];
        System.arraycopy(array, index + 1, array, index, array.length - index - 1);
        array[array.length - 1] = element;
    }

    /**
     * Shifts the elements
     * @param arr        The array to copy elements within
     * @param startIndex The index at which to start moving (inclusive)
     * @param endIndex   The ending index of where to stop moving (exclusive)
     * @param moveAmount The amount to shift the elements by
     */
    public static void shiftRight0(Object[] arr, int startIndex, int endIndex, int moveAmount) {
        for(int i = endIndex - 1, j = i + moveAmount; i >= startIndex; --i, --j) {
            arr[j] = arr[i];
        }
    }

    /**
     * Shifts the elements
     * @param arr        The array to copy elements within
     * @param index      The index at which to start moving (inclusive)
     * @param count      The amount of elements to move
     * @param moveAmount The amount to shift the elements by
     */
    public static void shiftRight1(Object[] arr, int index, int count, int moveAmount) {
        // - - 1 2 - - -
        // - - - - - 1 2
        for (int i = index + count - 1, j = i + moveAmount; i >= index; --i, --j) {
            arr[j] = arr[i];
        }
    }
}
