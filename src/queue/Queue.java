package queue;

import base.QueueContract;
import linked_list.DoublyLinkedList;

import java.util.Iterator;

public class Queue<T> extends QueueContract<T> {

    private DoublyLinkedList<T> list;

    public Queue() {
        list = new DoublyLinkedList<>();
    }

    public Queue(T element) {
        list = new DoublyLinkedList<>(element);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Empty queue");
        }
        return list.peekFirst();
    }

    @Override
    public T poll() {
        if (isEmpty()) {
            throw new RuntimeException("Empty queue");
        }
        return list.removeFirst();
    }

    @Override
    public void offer(T elem) {
        list.addLast(elem);
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }
}
