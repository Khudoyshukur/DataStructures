package heap;

import base.BinaryHeapContract;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class BinaryHeap<T extends Comparable<T>> extends BinaryHeapContract<T> {

    private final List<T> heap;

    public BinaryHeap() {
        this(1);
    }

    public BinaryHeap(int capacity) {
        heap = new ArrayList<>(capacity);
    }

    public BinaryHeap(T element) {
        this();
        add(element);
    }

    public BinaryHeap(Collection<T> elements) {
        this(elements.size());
        for (T element : elements) {
            add(element);
        }
    }

    public BinaryHeap(T[] elements) {
        this(elements.length);
        for (T element : elements) {
            add(element);
        }
    }

    @Override
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    @Override
    public void clear() {
        heap.clear();
    }

    @Override
    public int size() {
        return heap.size();
    }

    @Override
    public T peek() {
        if (isEmpty()) return null;

        return heap.get(0);
    }

    @Override
    public T poll() {
        if (isEmpty()) return null;

        if (size() == 1) {
            return heap.remove(0);
        }

        T data = heap.get(0);
        swap(0, size() - 1);
        heap.remove(size() - 1);
        sink(0);

        return data;
    }

    @Override
    public boolean contains(T elem) {
        return heap.contains(elem);
    }

    @Override
    public void add(T elem) {
        if (elem == null) throw new IllegalArgumentException("Null element is not allowed");

        heap.add(elem);
        swimUp(heap.size() - 1);
    }

    private void sink(int index) {
        T element = heap.get(index);

        int leftChild = 2 * index + 1;
        int rightChild = leftChild + 1;

        if (rightChild < size()) {
            T left = heap.get(leftChild);
            T right = heap.get(rightChild);
            if (right.compareTo(left) < 0) {
                if (element.compareTo(right) > 0) {
                    swap(index, rightChild);
                    sink(rightChild);
                }
            } else {
                if (element.compareTo(left) > 0) {
                    swap(index, leftChild);
                    sink(leftChild);
                }
            }

        } else if (leftChild < size()) {
            T left = heap.get(leftChild);
            if (element.compareTo(left) > 0) {
                swap(index, leftChild);
                sink(leftChild);
            }
        }
    }

    private void swimUp(int index) {
        T element = heap.get(index);

        int parentIndex;
        if (index % 2 == 0) {
            parentIndex = (index - 2) / 2;
        } else {
            parentIndex = (index - 1) / 2;
        }

        if (parentIndex >= 0) {
            T parent = heap.get(parentIndex);
            if (parent.compareTo(element) > 0) {
                swap(parentIndex, index);
                swimUp(parentIndex);
            }
        }
    }

    private void swap(int j, int i) {
        if (i == j) return;

        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    @Override
    public boolean remove(T element) {
        int index = heap.indexOf(element);
        if (index < 0) return false;

        swap(index, size() - 1);
        heap.remove(size() - 1);

        if (index < size()) {
            swimUp(index);
            sink(index);
        }

        return true;
    }

    @Override
    public boolean isMinHeap(int k) {
        int leftChild = 2 * k + 1;
        int rightChild = leftChild + 1;

        if (rightChild < size()) {
            if (heap.get(rightChild).compareTo(heap.get(k)) < 0) {
                return false;
            }

            if (heap.get(leftChild).compareTo(heap.get(k)) < 0) {
                return false;
            }

            return isMinHeap(leftChild) && isMinHeap(rightChild);
        } else if (leftChild < size()) {
            if (heap.get(leftChild).compareTo(heap.get(k)) < 0) {
                return false;
            }

            return isMinHeap(leftChild);
        }

        // no child
        return true;
    }

    @Override
    public String toString() {
        return heap.toString();
    }
}
