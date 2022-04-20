package base;

public abstract class BinaryHeapContract<T extends Comparable<T>> {
    // Returns true/false depending on if the priority queue is empty
    public abstract boolean isEmpty();

    // Clears everything inside the heap, O(n)
    public abstract void clear();

    // Return the size of the heap
    public abstract int size();

    // Returns the value of the element with the lowest
    // priority in this priority queue. If the priority
    // queue is empty null is returned.
    public abstract T peek();

    // Removes the root of the heap, O(log(n))
    public abstract T poll();

    // Test if an element is in heap, O(n)
    public abstract boolean contains(T elem);

    // Adds an element to the priority queue, the
    // element must not be null, O(log(n))
    public abstract void add(T elem);

    // Removes a particular element in the heap, O(n)
    public abstract boolean remove(T element);

    // Recursively checks if this heap is a min heap
    // This method is just for testing purposes to make
    // sure the heap invariant is still being maintained
    // Called this method with k=0 to start at the root
    public abstract boolean isMinHeap(int k);
}
