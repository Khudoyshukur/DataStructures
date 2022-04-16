package linked_list;

import base.LinkedListContract;

import java.util.Iterator;
import java.util.Objects;

public class DoublyLinkedList<T> extends LinkedListContract<T> {

    private Node<T> head;
    private Node<T> tail;
    private int length;

    public DoublyLinkedList() {
    }

    @SuppressWarnings("unused")
    public DoublyLinkedList(T element) {
        this.head = this.tail = new Node<>(element);
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
        assertIndex(index);
        return getNode(index).value;
    }

    @Override
    public void set(int index, T element) {
        assertIndex(index);

        Node<T> node = getNode(index);
        node.value = element;
    }

    @Override
    public void clear() {
        Node<T> trev = this.head;
        while (trev != null) {
            Node<T> temp = trev;
            trev = trev.next;

            temp.prev = null;
            temp.next = null;
            temp.value = null;
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
        assertIndex(rm_index);

        if (rm_index == 0) {
            return removeFirst();
        }

        if (rm_index == size() - 1) {
            return removeLast();
        }

        Node<T> rm_node = getNode(rm_index);
        T data = rm_node.value;
        unlinkNode(rm_node);
        length--;
        return data;
    }

    @Override
    public boolean remove(Object object) {
        for (Node<T> node = this.head; node != null; node = node.next) {
            if (Objects.equals(node.value, object)) {
                if(size() == 1) {
                    unlinkNode(this.head);
                    this.head = this.tail = null;
                } else if (node == this.head) {
                    this.head = this.head.next;
                    this.head.prev.value = null;
                    this.head.prev.next = null;
                    this.head.prev = null;
                } else if (node == this.tail) {
                    this.tail = this.tail.prev;
                    this.tail.next.value = null;
                    this.tail.next.prev = null;
                    this.tail.next = null;
                } else {
                    unlinkNode(node);
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
            if (Objects.equals(trev.value, object)) {
                return true;
            }
            trev = trev.next;
        }
        return false;
    }

    @Override
    public void addFirst(T element) {
        if (isEmpty()) {
            this.head = this.tail = new Node<>(element);
        } else {
            Node<T> newNode = new Node<>(element);
            newNode.next = this.head;
            this.head.prev = newNode;
            this.head = this.head.prev;
        }
        length++;
    }

    @Override
    public void addLast(T element) {
        if (isEmpty()) {
            this.head = this.tail = new Node<>(element);
        } else {
            Node<T> newNode = new Node<>(element);
            newNode.prev = this.tail;
            this.tail.next = newNode;
            this.tail = this.tail.next;
        }
        length++;
    }

    @Override
    public T peekFirst() {
        if (isEmpty()) {
            throw new RuntimeException("Empty list");
        }
        return this.head.value;
    }

    @Override
    public T peekLast() {
        if (isEmpty()) {
            throw new RuntimeException("Empty list");
        }
        return this.tail.value;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            throw new RuntimeException("Empty list");
        }
        Node<T> temp = this.head;
        this.head = this.head.next;
        length--;

        T data = temp.value;
        unlinkNode(temp);

        if (isEmpty()) {
            this.tail = null;
        }

        return data;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            throw new RuntimeException("Empty list");
        }
        Node<T> temp = this.tail;
        this.tail = this.tail.prev;
        length--;

        T data = temp.value;
        unlinkNode(temp);

        if (isEmpty()) {
            this.head = null;
        }

        return data;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int index;

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

    private void unlinkNode(Node<T> node) {
        if (node.prev != null && node.next != null) {
            Node<T> prev = node.prev;
            Node<T> next = node.next;
            prev.next = next;
            next.prev = prev;
        }

        node.prev = null;
        node.next = null;
        node.value = null;
    }

    private Node<T> getNode(int index) {
        if (index > (size() - 1) / 2) {
            Node<T> trev = this.tail;
            for (int i = size() - 2; i >= index; i--) {
                trev = trev.prev;
            }
            return trev;
        } else {
            Node<T> trev = this.head;
            for (int i = 1; i <= index; i++) {
                trev = trev.next;
            }
            return trev;
        }
    }

    private void assertIndex(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
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
        Node<T> prev;

        public Node(T value) {
            this.value = value;
        }

        @SuppressWarnings("unused")
        public Node(T value, Node<T> next, Node<T> prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }
}
