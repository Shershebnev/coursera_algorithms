import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] s;
    private int N;
    private int index; // random int
    
    public RandomizedQueue() {
        s = (Item[]) new Object[1];
    }
    
    public boolean isEmpty() {
        return N == 0;
    }
    
    public int size() {
        return N;
    }

    public void enqueue(Item item) {
        if (item == null) throw new java.lang.NullPointerException();
        if (N == s.length) resize(2 * s.length);
        s[N++] = item;
    }
    
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            copy[i] = s[i];
        }
        s = copy;
    }
    
    public Item dequeue() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        index = StdRandom.uniform(N);
        Item item = s[index];
        s[index] = s[N - 1];
        s[N - 1] = null;
        --N;
        if (N > 0 && N == s.length / 4) resize(s.length / 2);
        return item;
    }
    
    public Item sample() {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        index = StdRandom.uniform(N);
        Item item = s[index];
        return item;
    }
    
    public Iterator<Item> iterator() { return new ArrayIterator(); }
    
    private class ArrayIterator implements Iterator<Item> {
        private int index;
        private Item[] sequence;
        
        public ArrayIterator() {
            index = N;
            sequence = (Item[]) new Object[size()];
            for (int i = 0; i < N; i++) {
                sequence[i] = get(i);
            }
            StdRandom.shuffle(sequence);
        }
        private Item get(int i) {
            return s[i];
        }
        public boolean hasNext() {
            return index > 0;
        }
        
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
        
        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            Item item = sequence[--index];
            return item;
        }
    }
    public static void main(String[] args) {
//        RandomizedQueue<Integer> a = new RandomizedQueue<>();
//        a.enqueue(0);
//        a.enqueue(1);
//        a.enqueue(2);
//        System.out.println(a.isEmpty());
//        a.enqueue(3);
//        a.dequeue();
//        a.enqueue(4);
//        System.out.println(a.isEmpty());
//        a.enqueue(5);
//        for (int j = 0; j < 6; j++) {
//            a.enqueue(j);
//        }
//        for (int i : a) {
//            System.out.println("First " + i);
//        }
//        for (int j : a) {
//            System.out.println("Second " + j);
//        }
//        System.out.println("Finished");
//        for (int j : a) {
//            System.out.print(j + " ");
//        }
    }
}