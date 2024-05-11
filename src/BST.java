import java.util.Iterator;
import java.util.Stack;

public class BST<K extends Comparable<K>, V> {
    private Node root;

    private class Node {
        private K key;
        private V value;
        private Node left, right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public void put(K key, V value) {
        root = insert(root, key, value);
    }

    private Node insert(Node node, K key, V value) {
        if (node == null) {
            return new Node(key, value);
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = insert(node.left, key, value);
        } else if (cmp > 0) {
            node.right = insert(node.right, key, value);
        } else {
            node.value = value;
        }

        return node;
    }

    public V get(K key) {
        Node node = search(root, key);
        return node == null ? null : node.value;
    }

    private Node search(Node node, K key) {
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return search(node.left, key);
        } else if (cmp > 0) {
            return search(node.right, key);
        } else {
            return node;
        }
    }

    public void delete(K key) {
        root = remove(root, key);
    }

    private Node remove(Node node, K key) {
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = remove(node.left, key);
        } else if (cmp > 0) {
            node.right = remove(node.right, key);
        } else {
            // Key found. Remove the node.
            if (node.left == null && node.right == null) {
                return null;
            }

            if (node.left != null) {
                Node successor = findMin(node.right);
                node.key = successor.key;
                node.value = successor.value;
                node.right = remove(node.right, successor.key);
            } else {
                node = node.left;
            }
        }

        return node;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }

        return node;
    }

    public Iterable<K> iterator() {
        return (Iterable<K>) new InOrderIterator(root);
    }

    private class InOrderIterator implements Iterator<K> {
        private Stack<Node> stack;

        public InOrderIterator(Node root) {
            this.stack = new Stack<>();
            pushAllLeft(root);
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public K next() {
            Node node = stack.pop();
            pushAllLeft(node.right);
            return node.key;
        }

        private void pushAllLeft(Node node) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }
    }
}
