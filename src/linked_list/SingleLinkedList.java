package linked_list;

import base.LinkedListContract;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SingleLinkedList<T> extends LinkedListContract<T> {

    private Node<T> head;
    private Node<T> tail;

    private int length = 0;

    @SuppressWarnings("unused")
    public SingleLinkedList() {
    }

    @SuppressWarnings("unused")
    public SingleLinkedList(T element) {
        add(element);
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
        checkIndexBounds(index);
        return getNode(index).value;
    }

    @Override
    public void set(int index, T element) {
        checkIndexBounds(index);

        Node<T> trev = this.head;
        for (int i = 1; i <= index; i++) {
            trev = trev.next;
        }

        trev.value = element;
    }

    @Override
    public void clear() {
        // Clearing all of the links between nodes is "unnecessary", but:
        // - helps a generational GC if the discarded nodes inhabit
        //   more than one generation
        // - is sure to free memory even if there is a reachable Iterator

        Node<T> trev = this.head;
        while (trev != null) {
            trev = unlinkAndReturnNext(trev);
        }
        this.head = this.tail = null;
        length = 0;
    }

    @Override
    public void add(T element) {
        addLast(element);
    }

    @Override
    public T removeAt(int rm_index) {
        checkIndexBounds(rm_index);

        if (rm_index == 0) {
            return removeFirst();
        }

        if (rm_index == size() - 1) {
            return removeLast();
        }

        Node<T> prev = getNode(rm_index - 1);
        T data = prev.next.value;
        prev.next = unlinkAndReturnNext(prev.next);
        length--;

        return data;
    }

    @Override
    public boolean remove(Object object) {
        for (Node<T> x = this.head, prev = x; x != null; prev = x, x = x.next) {
            if (Objects.equals(x.value, object)) {
                if (prev.equals(x)) {
                    this.head = unlinkAndReturnNext(this.head);

                    if (size() == 1) {
                        this.tail = null;
                    }
                } else if (x.equals(this.tail)) {
                    this.tail = prev;
                    prev.next = null;
                } else {
                    prev.next = x.next;
                    x.value = null;
                }

                length--;
                return true;
            }
        }
        return false;
    }

    @Override
    public int indexOf(Object object) {
        Node<T> trev = this.head;
        for (int i = 0; i < size(); i++) {
            if (Objects.equals(trev.value, object)) {
                return i;
            }
            trev = trev.next;
        }
        return -1;
    }

    @Override
    public boolean contains(Object object) {
        Node<T> trev = this.head;
        while (trev != null) {
            if (trev.value.equals(object)) {
                return true;
            }
            trev = trev.next;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size();
            }

            @Override
            public T next() {
                return get(index++);
            }
        };
    }

    @Override
    public void addFirst(T element) {
        if (isEmpty()) {
            this.head = this.tail = new Node<>(element);
        } else {
            this.head = new Node<>(element, this.head);
        }

        length++;
    }

    @Override
    public void addLast(T element) {
        if (isEmpty()) {
            this.head = this.tail = new Node<>(element);
        } else {
            Node<T> node = new Node<>(element);
            this.tail.next = node;
            this.tail = node;
        }

        length++;
    }

    @Override
    public T peekFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        return this.head.value;
    }

    @Override
    public T peekLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        return this.tail.value;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        T data = this.head.value;
        this.head = unlinkAndReturnNext(this.head);
        length--;

        if (isEmpty()) {
            this.tail = null;
        }

        return data;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException();
        }

        if (size() == 1) {
            return removeFirst();
        }

        Node<T> prev = getNode(size() - 2);
        T data = prev.next.value;
        this.tail = prev;

        prev.next.value = null;
        prev.next = null;
        length--;

        return data;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        for (Node<T> node = this.head; node != null; node = node.next) {
            builder.append(node.value).append(((node.next == null) ? "]" : ", "));
        }
        return builder.toString();
    }

    static class Node<T> {
        T value;
        Node<T> next;

        public Node(T value) {
            this.value = value;
        }

        public Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }
    }

    private Node<T> getNode(int index) {
        Node<T> trev = this.head;
        for (int i = 1; i <= index; i++) {
            trev = trev.next;
        }

        return trev;
    }

    private Node<T> unlinkAndReturnNext(Node<T> node) {
        Node<T> next = node.next;
        node.next = null;
        node.value = null;

        return next;
    }

    private void checkIndexBounds(int index) {
        if (index >= size() || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }
}