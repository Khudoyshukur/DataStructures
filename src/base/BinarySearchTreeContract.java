package base;

import binary_search_tree.TreeTraversalOrder;

import java.util.Iterator;

public abstract class BinarySearchTreeContract<T extends Comparable<T>> {

    // Check if this binary tree is empty
    public abstract boolean isEmpty();

    // Get the number of nodes in this binary tree
    public abstract int size();

    // Add an element to this binary tree. Returns true
    // if we successfully perform an insertion
    // if element is already in the tree, ignore and return false
    public abstract boolean add(T elem);

    // Remove a value from this binary tree if it exists, O(n)
    public abstract boolean remove(T elem);

    // returns true is the element exists in the tree
    public abstract boolean contains(T elem);

    // returns height of tree
    public abstract int height();

    // returns iterator according to TreeTraversalOrder
    public abstract Iterator<T> traverse(TreeTraversalOrder order);
}