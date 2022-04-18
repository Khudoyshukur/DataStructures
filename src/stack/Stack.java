package stack;

import base.StackContract;
import linked_list.DoublyLinkedList;

import java.util.EmptyStackException;
import java.util.Iterator;

public class Stack<T> extends StackContract<T> {

    private final DoublyLinkedList<T> linkedList;

    public Stack() {
        linkedList = new DoublyLinkedList<>();
    }

    public Stack(T element) {
        linkedList = new DoublyLinkedList<>();
        push(element);
    }

    @Override
    public int size() {
        return linkedList.size();
    }

    @Override
    public boolean isEmpty() {
        return linkedList.isEmpty();
    }

    @Override
    public void push(T elem) {
        linkedList.addLast(elem);
    }

    @Override
    public T pop() {
        if(isEmpty()) {
            throw new EmptyStackException();
        }
        return linkedList.removeLast();
    }

    @Override
    public T peek() {
        if(isEmpty()) {
            throw new EmptyStackException();
        }
        return linkedList.peekLast();
    }

    @Override
    public Iterator<T> iterator() {
        return linkedList.iterator();
    }
}
