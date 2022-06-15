package bstmap;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private class Node {
        /* The node contains a pair (K, V). */
        private K key;
        private V value;

        /* Successors of the current node. */
        private Node leftChild;
        private Node rightChild;

        /** The constructor. */
        private Node(K initialKey, V initialValue) {
            key = initialKey;
            value = initialValue;
        }
    }

    /* The root of the tree. */
    private Node root;
    /* The number of nodes in the tree. */
    private int size;

    public BSTMap() {
        clear();
    }

    /** Removes all the mappings from this map. */
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key) {
        return keySet().contains(key);
    }

    /** Returns the value to which the specified key is mapped,
     *  or null if this map contains no mapping for the key. */
    public V get(K key) {
        return getHelper(key, root);
    }

    /** Returns the value to which the specified key is mapped in the
     *  subtree rooted in P, or null if this map contains no mapping for the key. */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        }

        if (p.key.compareTo(key) == 0) {
            return p.value;
        } else if (p.key.compareTo(key) < 0) {
            return getHelper(key, p.rightChild);
        } else {
            return getHelper(key, p.leftChild);
        }
    }

    /** Returns the number of key-value mappings in this map. */
    public int size() {
        return size;
    }

    /** Associates the specified value with the specified key in this map. */
    public void put(K key, V value) {
        root = putHelper(key, value, root);
        size++;
    }

    /** Returns a BSTMap rooted in p with (key, value) added as a key-value mapping.
     *  If p is null, it returns a one node BSTMap containing (key, value). */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            return new Node(key, value);
        }

        if (p.key.compareTo(key) == 0) {
            p.value = value;
        } else if (p.key.compareTo(key) < 0) {
            p.rightChild = putHelper(key, value, p.rightChild);
        } else {
            p.leftChild = putHelper(key, value, p.leftChild);
        }

        return p;
    }

    public void printInOrder() {
        for (Iterator<K> it = iterator(); it.hasNext(); ) {
            K key = it.next();
            System.out.println(get(key));
        }
    }

    // The following methods are optional.

    /** Returns a Set view of the keys contained in this map. Not required for Lab 7.
     *  If you don't implement this, throw an UnsupportedOperationException. */
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        addAll(root, keys);
        return keys;
    }

    /** Returns a Set view of the keys contained in this map. */
    private void addAll(Node p, Set<K> keys) {
        if (p != null) {
            keys.add(p.key);
            addAll(p.leftChild, keys);
            addAll(p.rightChild, keys);
        }
    }

    /** Removes the mapping for the specified key from this map if present.
     *  Not required for Lab 7. If you don't implement this, throw an
     *  UnsupportedOperationException. */
    public V remove(K key) {
        V retValue = get(key);
        if (retValue == null) {
            return null;
        }

        root = remove(key, root);
        size--;
        return retValue;
    }

    /** Removes the entry for the specified key only if it is currently mapped to
     *  the specified value. Not required for Lab 7. If you don't implement this,
     *  throw an UnsupportedOperationException. */
    public V remove(K key, V value) {
        if (containsKey(key) && get(key) == value) {
            return remove(key);
        } else {
            return null;
        }
    }

    /** Returns a new tree with the given key removed.
     *  assume that the key is in the tree. */
    private Node remove(K key, Node p) {
        int cmp = p.key.compareTo(key);
        if (cmp == 0) {
            if (p.leftChild == null) return p.rightChild;
            if (p.rightChild == null) return p.leftChild;
            Node t = p;
            p = min(t.rightChild);
            p.rightChild = removeMin(key, t.rightChild);
            p.leftChild = t.leftChild;
        } else if (cmp < 0) {
            p.rightChild = remove(key, p.rightChild);
        } else {
            p.leftChild = remove(key, p.leftChild);
        }

        return p;
    }

    /** Returns the node with the smallest key in the given tree. */
    private Node min(Node p) {
        if (p.leftChild == null) {
            return p;
        }
        return min(p.leftChild);
    }

    /** Returns a new tree which removes the smallest key
     *  and associated value in the given tree. */
    private Node removeMin(K key, Node p) {
        if (p.leftChild == null) {
            return p.rightChild;
        }
        p.leftChild = removeMin(key, p.leftChild);
        return p;
    }

    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
