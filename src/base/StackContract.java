package base;

public abstract class StackContract<T> implements Iterable<T> {
  public abstract int size();
  public abstract boolean isEmpty();
  public abstract void push(T elem);
  public abstract T pop();
  public abstract T peek();
}