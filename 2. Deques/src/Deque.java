import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node first, last;
    private int N;
    
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }
    
    public boolean isEmpty() {
        return N == 0;
    }
    
    public int size() {
        return N;
    }
    
    /*
     * if deque is empty, creates two node, first and last,
     * connects first.next to last and last.prev to first, sets first.item to item;
     * else if nodes already exist but first item was never added, checks
     * if first.item == null and then sets first.item to item.
     * If not first node in stack, and previously added to first, just create new node
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        if (isEmpty()) {
            onEmpty();
            first.item = item;   
            last.item = null;
        } else if (first.item == null) {
            first.item = item;
        } else {
            Node oldfirst = first;
            first = new Node();
            first.item = item;
            first.prev = null;
            oldfirst.prev = first;
            first.next = oldfirst;
        }
        N++;
    }
    
    /*
     * if deque is empty, create two nodes, last and first, 
     * connects last.prev to first and first.next to prev, sets last.item to item;
     * else if nodes already exist but last item was never added, checks
     * if last.item == null and then sets last.item to item.
     * If not first node in stack, and previously added to last, just create new node
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        if (isEmpty()) {
            onEmpty();
            last.item = item;
            first.item = null;
        } else if (last.item == null) {
            last.item = item;
        } else {
            Node oldlast = last;
            last = new Node();
            last.item = item;
            last.next = null;
            oldlast.next = last;
            last.prev = oldlast;
        }
        N++;
    }
    
    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        if (first.item == null) {
            first = first.next;
        }
        Item item = first.item;
        if (first == last) { // removed last item
            onDeleteAll();
        } else {
            first = first.next;
            first.prev = null;
        }
        --N;
        return item;
    }
    
    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        if (last.item == null) {
            last = last.prev;
        }
        Item item = last.item;
        if (first == last) { // removed last item
            onDeleteAll();
        } else {
            last = last.prev;
            last.next = null;
        }
        --N;
        return item;
    }
    
    public Iterator<Item> iterator() { return new ListIterator(); }
    
    private class ListIterator implements Iterator<Item> {
        private Node current;
        
        private ListIterator() {
            if (first != null && last != null) {
                selectStartNode();
            }
        }
        
        private void selectStartNode() {
            if (first.item == null && last.item != null) {
                current = first.next;
            } else if ((first.item != null && last.item == null) || 
                    (first.item != null && last.item != null)) {
                current = first;
            } else {
                current = first;
            }
        }
        
        public boolean hasNext() {
            return (current != null && current.item != null);
        }
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    
    private void onEmpty() {
        first = new Node();
        last = new Node();
        last.prev = first;
        last.next = null;
        first.prev = null;
        first.next = last;
    }
    
    private void onDeleteAll() {
        first = null;
        last = null;
    }
    
    public static void main(String[] args) {
//        Deque<Integer> a = new Deque<>();
//        for (int i : a) {
//            int g = i;
//            System.out.println(g);
//        }
//        a.addLast(1);
//        System.out.println(a.removeFirst());
//        for (int i : a) {
//            System.out.println(i);
//        }
//        System.out.println(a.isEmpty());
//        a.addFirst(5);
//        a.addLast(6);
    }
}