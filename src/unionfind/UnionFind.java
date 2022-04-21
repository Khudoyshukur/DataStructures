package unionfind;

import base.UnionFindContract;

public class UnionFind<T> extends UnionFindContract<T> {

    /**
     * The number of elements in this union find
     * */
    private final int size;

    /**
     * Tracks the number of components in the union find
     * */
    private int numberOfComponents;

    /**
     * used to track size of each component
     * */
    private final int[] sz;

    /**
     * id[i] points to the parent of i, if id[i] = i then i is a root node
     * */
    private final int[] id;

    public UnionFind(int size){
        if (size <= 0) throw new IllegalArgumentException("Size <= 0 not allowed");

        this.numberOfComponents = this.size = size;
        this.sz = new int[size];
        this.id = new int[size];

        for (int i = 0; i < size; i++) {
            this.id[i] = i; // Link to itself (self root)
            this.sz[i] = 1; // Each component is originally of size one
        }
    }

    @Override
    public int find(int p) {
        // Find the root of the component/set
        int root = p;
        while (root != id[root]) root = id[root];

        // Compress the path leading back to the root.
        // Doing this operation is called "path compression"
        // and is what gives us amortized time complexity.
        while (p != root) {
            int next = id[p];
            id[p] = root;
            p = next;
        }

        return root;
    }

    @Override
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    @Override
    public int componentSize(int p) {
        return sz[find(p)];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int components() {
        return numberOfComponents;
    }

    @Override
    public void unify(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);

        if (rootP == rootQ) {
            return;
        }

        if (sz[rootP] > sz[rootQ]) {
            sz[rootP] += sz[rootQ];
            id[rootQ] = rootP;
        } else {
            sz[rootQ] += sz[rootP];
            id[rootP] = rootQ;
        }

        numberOfComponents--;
    }
}
