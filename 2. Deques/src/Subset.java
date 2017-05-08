import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Subset {
    public static void main(String[] args) {
        RandomizedQueue<String> s = new RandomizedQueue<String>();
        int k = Integer.parseInt(args[0]);
        while (StdIn.hasNextLine() && !StdIn.isEmpty()) {
            s.enqueue(StdIn.readString());
        }
        for (int i = 0; i < k; i++) {
            StdOut.println(s.dequeue());
        }
    }
}
