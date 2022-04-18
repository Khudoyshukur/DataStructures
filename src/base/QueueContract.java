package base;

public abstract class QueueContract<T> implements Iterable<T>{
    public abstract int size();
    public abstract boolean isEmpty();
    public abstract T peek();
    public abstract T poll();
    public abstract void offer(T elem);
}