package arrrays;


import java.util.Iterator;
import java.util.Objects;

public class DynamicArray<T> extends DynamicArrayContract<T> {

    private T[] arr;
    private int length;
    private int capacity;

    public DynamicArray() {
        this(16);
    }

    public DynamicArray(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Illegal capacity: " + capacity);
        }

        this.capacity = capacity;
        arr = (T[]) new Object[capacity];
    }

    @Override
    public int size() {
        return length;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public T get(int index) {
        return arr[index];
    }

    @Override
    public void set(int index, T element) {
        arr[index] = element;
    }

    @Override
    public void clear() {
        for (int index = 0; index < capacity; index++) {
            arr[index] = null;
        }
        length = 0;
    }

    @Override
    public void add(T element) {
        // Time to resize!
        if (length + 1 > capacity) {
            if (capacity == 0) {
                capacity = 1;
            } else {
                capacity = capacity * 2;
            }

            T[] newArr = (T[]) new Object[capacity];
            if (length >= 0) {
                System.arraycopy(arr, 0, newArr, 0, length);
            }
            arr = newArr;
        }

        arr[length++] = element;
    }

    @Override
    public T removeAt(int rm_index) {
        if (rm_index < 0 || rm_index >= length) {
            throw new IndexOutOfBoundsException();
        }
        T data = arr[rm_index];
        T[] newArray = (T[]) new Object[capacity];
        for (int i = 0, j = 0; i < length; i++, j++) {
            if (i == rm_index) {
                j--;
            } else {
                newArray[j] = arr[i];
            }
        }
        arr = newArray;
        length--;
        return data;
    }

    @Override
    public boolean remove(Object object) {
        for (int i = 0; i < length; i++) {
            if (Objects.equals(arr[i], object)) {
                removeAt(i);
                return true;
            }
        }

        return false;
    }

    @Override
    public int indexOf(Object object) {
        for (int i = 0; i < length; i++) {
            if (Objects.equals(arr[i], object)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public boolean contains(Object object) {
        return indexOf(object) != -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < length;
            }

            @Override
            public T next() {
                return arr[index++];
            }
        };
    }

    @Override
    public String toString() {
        if (length == 0) return "[]";

        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int i = 0; i < length; i++) {
            if (i == length - 1) {
                builder.append(arr[i]).append("]");
                break;
            }
            builder.append(arr[i]).append(", ");
        }

        return builder.toString();
    }
}
