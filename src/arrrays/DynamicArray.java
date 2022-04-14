package arrrays;


import java.util.Iterator;

public class DynamicArray {
    public static void main(String[] args) {
        Array<Integer> integers = new Array<Integer>(20);
        integers.add(0);
        integers.add(1);
        integers.add(2);
        integers.add(3);
        integers.add(4);
        integers.add(5);
        integers.add(6);
        System.out.println(integers);

        System.out.println(integers.indexOf(5));

        integers.set(6, 7);
        System.out.println(integers);

        System.out.println(integers.get(5));

        integers.removeAt(5);
        System.out.println(integers);

        integers.remove(0);
        System.out.println(integers);

        System.out.println(integers.contains(4));

        integers.clear();
        System.out.println(integers);
    }
}

class Array<T> implements Iterable<T> {

    private T[] arr;
    private int length;
    private int capacity;

    public Array() {
        this(16);
    }

    public Array(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Illegal capacity: " + capacity);
        }

        this.capacity = capacity;
        arr = (T[]) new Object[capacity];
    }

    public int size() {
        return length;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public T get(int index) {
        return arr[index];
    }

    public void set(int index, T element) {
        arr[index] = element;
    }

    public void clear() {
        for (int index = 0; index < capacity; index++) {
            arr[index] = null;
        }
        length = 0;
    }

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

    public boolean remove(Object object) {
        for (int i = 0; i < length; i++) {
            if (arr[i].equals(object)) {
                removeAt(i);
                return true;
            }
        }

        return false;
    }

    public int indexOf(Object object) {
        for (int i = 0; i < length; i++) {
            if (arr[i].equals(object)) {
                return i;
            }
        }

        return -1;
    }

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
