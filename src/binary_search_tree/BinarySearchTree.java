package binary_search_tree;

import base.BinarySearchTreeContract;

import java.util.*;

public class BinarySearchTree<T extends Comparable<T>> extends BinarySearchTreeContract<T> {

    private int nodeCount;
    private Node<T> root;

    static class Node<T> {
        T value;
        Node<T> left, right;

        public Node(T value) {
            this.value = value;
        }

        public Node(T value, Node<T> left, Node<T> right) {
            this.value = value;
            this.right = right;
            this.left = left;
        }
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return this.nodeCount;
    }

    @Override
    public boolean add(T elem) {
        if (contains(elem)) {
            return false;
        }

        this.root = add(this.root, elem);
        nodeCount++;
        return true;
    }

    private Node<T> add(Node<T> root, T element) {
        // checks whether the tree has elements
        if (root == null) {
            root = new Node<>(element);
        } else {
            if (root.value.compareTo(element) < 0) {
                root.right = add(root.right, element);
            } else {
                root.left = add(root.left, element);
            }
        }
        return root;
    }

    @Override
    public boolean remove(T elem) {
        if (contains(elem)) {
            this.root = remove(this.root, elem);
            nodeCount--;
            return true;
        }
        return false;
    }

    private Node<T> remove(Node<T> node, T element) {
        if (node == null) return null;

        if (node.value.compareTo(element) == 0) {
            if (node.left == null && node.right == null) { // left and right null
                node.value = null;
                node = null;
            } else if (node.left != null && node.right == null) { // right null, left not null
                node.value = null;
                Node<T> temp = node.left;
                node.left = null;
                node = temp;
            } else if (node.left == null) { // left null, right not null
                node.value = null;
                Node<T> temp = node.right;
                node.right = null;
                node = temp;
            } else { // left not null, right not null
                Node<T> minRight = node.right;
                while (minRight.left != null) {
                    minRight = minRight.left;
                }

                node.value = minRight.value;
                node.right = remove(node.right, minRight.value);
            }
        } else if (node.value.compareTo(element) > 0) {
            node.left = remove(node.left, element);
        } else {
            node.right = remove(node.right, element);
        }

        return node;
    }

    @Override
    public boolean contains(T elem) {
        return contains(this.root, elem);
    }

    private boolean contains(Node<T> root, T element) {
        if (root == null) {
            return false;
        }

        int compare = root.value.compareTo(element);

        if (compare == 0) return true;

        if (compare > 0) {
            return contains(root.left, element);
        } else {
            return contains(root.right, element);
        }
    }

    @Override
    public int height() {
        return height(this.root);
    }

    private int height(Node<T> node) {
        if (node == null) return 0;

        return Math.max(height(node.left), height(node.right)) + 1;
    }

    @Override
    public Iterator<T> traverse(TreeTraversalOrder order) {
        switch (order) {
            case IN_ORDER: {
                return inOrderTraversal();
            }
            case PRE_ORDER: {
                return preOrderTraversal();
            }
            case POST_ORDER: {
                return postOrderTraversal();
            }
            case LEVEL_ORDER: {
                return levelOrderTraversal();
            }
        }

        return null;
    }

    private Iterator<T> preOrderTraversal() {
        final int expectedCount = nodeCount;

        Stack<Node<T>> stackToIterate = new Stack<>();
        stackToIterate.push(this.root);

        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                if (expectedCount != nodeCount) throw new ConcurrentModificationException();

                return root != null && !stackToIterate.empty();
            }

            @Override
            public T next() {
                if (expectedCount != nodeCount) throw new ConcurrentModificationException();
                Node<T> data = stackToIterate.pop();

                return data.value;
            }
        };
    }

    private void preOrder(Node<T> node, Stack<T> stack) {
        if (node == null) return;
        stack.push(node.value);
        preOrder(node.left, stack);
        preOrder(node.right, stack);
    }

    private Iterator<T> inOrderTraversal() {
        Stack<T> stackToIterate = new Stack<>();
        inOrder(this.root, stackToIterate);

        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return !stackToIterate.isEmpty();
            }

            @Override
            public T next() {
                return stackToIterate.pop();
            }
        };
    }

    private void inOrder(Node<T> node, Stack<T> stack) {
        if (node == null) return;
        preOrder(node.left, stack);
        stack.push(node.value);
        preOrder(node.right, stack);
    }

    private Iterator<T> postOrderTraversal() {
        Stack<T> stackToIterate = new Stack<>();
        postOrder(this.root, stackToIterate);

        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return !stackToIterate.isEmpty();
            }

            @Override
            public T next() {
                return stackToIterate.pop();
            }
        };
    }

    private void postOrder(Node<T> node, Stack<T> stack) {
        if (node == null) return;
        preOrder(node.left, stack);
        preOrder(node.right, stack);
        stack.push(node.value);
    }

    private Iterator<T> levelOrderTraversal() {
        Queue<T> queue = new LinkedList<>();
        levelOrder(this.root, queue);

        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return !queue.isEmpty();
            }

            @Override
            public T next() {
                return queue.poll();
            }
        };
    }

    private void levelOrder(Node<T> node, Queue<T> queue) {
        if (node == null) return;
        queue.add(node.value);

        if (node.left != null) {
            queue.add(node.left.value);
        }

        if (node.right != null) {
            queue.add(node.right.value);
        }

        levelOrder(node.left, queue);
        levelOrder(node.right, queue);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");

        Iterator<T> iterator = inOrderTraversal();
        while (iterator.hasNext()) {
            builder.append(iterator.next());
            if (!iterator.hasNext()) {
                builder.append("]");
            }
        }

        return builder.toString();
    }
}
