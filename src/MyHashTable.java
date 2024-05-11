import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyHashTable<K, V> {

    private class HashNode<K, V> {
        private K key;
        private V value;

        private HashNode<K, V> next;

        public HashNode(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "{" + key + ", " + value + "}";
        }
    }

    private HashNode<K, V>[] chainArray; // or Object[]
    private int M = 11; // default number of chains

    private int size;

    public MyHashTable() {
        chainArray = new HashNode[M];
        size = 0;
    }

    public MyHashTable(int M) {
        if (M < 1) {
            throw new IllegalArgumentException("M must be at least 1");
        }
        chainArray = new HashNode[M];
        size = 0;
    }

    private int hash(K key) {
        int hash = (key.hashCode() & 0x7fffffff) % M;
        return hash;
    }

    public void put(K key, V value) {
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }

        int hash = hash(key);
        HashNode<K, V> node = chainArray[hash];

        if (node == null) {
            chainArray[hash] = new HashNode<>(key, value);
            size++;
            return;
        }

        while (node.next != null && !node.key.equals(key)) {
            node = node.next;
        }

        if (node.key.equals(key)) {
            node.value = value;
        } else {
            node.next = new HashNode<>(key, value);
            size++;
        }
    }

    public V get(K key) {
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }

        int hash = hash(key);
        HashNode<K, V> node = chainArray[hash];

        while (node != null && !node.key.equals(key)) {
            node = node.next;
        }

        return node == null ? null : node.value;
    }

    public V remove(K key) {
        if (key == null) {
            throw new NullPointerException("Key cannot be null");
        }

        int hash = hash(key);
        HashNode<K, V> prev = null;
        HashNode<K, V> node = chainArray[hash];

        while (node != null && !node.key.equals(key)) {
            prev = node;
            node = node.next;
        }

        if (node == null) {
            return null;
        }

        if (prev == null) {
            chainArray[hash] = node.next;
        } else {
            prev.next = node.next;
        }

        size--;
        return node.value;
    }

    public boolean contains(K key) {
        return get(key) != null;
    }

    public List<V> values() {
        List<V> values = new ArrayList<>();

        for (HashNode<K, V> chain : chainArray) {
            HashNode<K, V> node = chain;
            while (node != null) {
                values.add(node.value);
                node = node.next;
            }
        }

        return values;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        for (int i = 0; i < M; i++) {
            HashNode<K, V> node = chainArray[i];
            if (node != null) {
                sb.append(i).append(": ");
                while (node != null) {
                    sb.append(node).append(", ");
                    node = node.next;
                }
            }
        }
        return null;
    }
    public static class MyTestingClass {
        private String name;
        private int id;

        public MyTestingClass(String name, int id) {
            this.name = name;
            this.id = id;
        }

        @Override
        public int hashCode() {
            // Implement your custom hash function here
            // This example uses a simple XOR of name and id
            return (name.hashCode() ^ id);
        }

        @Override
        public String toString() {
            return "Name: " + name + ", ID: " + id;
        }
    }
    public class MyHashTableTest {

        public static void main(String[] args) {
            MyHashTable<MyTestingClass, String> table = new MyHashTable<>();

            // Add 10000 random elements to the hash table
            Random random = new Random();
            for (int i = 0; i < 10000; i++) {
                String name = "Student" + i;
                int id = random.nextInt(10000);
                MyTestingClass student = new MyTestingClass(name, id);
                table.put(student, name);
            }

        }
    }

}