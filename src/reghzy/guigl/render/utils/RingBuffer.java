package reghzy.guigl.render.utils;

public class RingBuffer<T> {
    private final int size;
    private final T[] cache;

    private int next;

    public RingBuffer(T[] cache) {
        this.cache = cache;
        this.size = cache.length;
    }

    public T get() {
        if (this.next >= this.size) {
            return this.cache[this.next = 0];
        }
        else {
            return this.cache[this.next++];
        }
    }

    public void set(int index, T value) {
        this.cache[index] = value;
    }

    public int size() {
        return this.size;
    }
}
