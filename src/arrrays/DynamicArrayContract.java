package arrrays;

import java.util.Iterator;

public abstract class DynamicArrayContract<T> implements Iterable<T> {
    public abstract int size();
    public abstract boolean isEmpty();
    public abstract T get(int index);
    public abstract void set(int index, T element);
    public abstract void clear();
    public abstract void add(T element);
    public abstract T removeAt(int rm_index);
    public abstract boolean remove(Object object);
    public abstract int indexOf(Object object);
    public abstract boolean contains(Object object);
}
