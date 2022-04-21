package base;

public abstract class UnionFindContract<T> {

  // Find which component/set 'p' belongs to, takes amortized constant time.
  public abstract int find(int p);

  // Return whether the elements 'p' and
  // 'q' are in the same components/set.
  public abstract boolean connected(int p, int q);

  // Return the size of the components/set 'p' belongs to
  public abstract int componentSize(int p);

  // Return the number of elements in this UnionFind/Disjoint set
  public abstract int size();

  // Returns the number of remaining components/sets
  public abstract int components();

  // Unify the components/sets containing elements 'p' and 'q'
  public abstract void unify(int p, int q);
}