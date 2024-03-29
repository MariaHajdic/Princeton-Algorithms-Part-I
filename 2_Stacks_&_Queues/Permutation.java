import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rQueue = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            rQueue.enqueue(StdIn.readString());
        }
        Iterator<String> iterator = rQueue.iterator();
        for (int i = 0; i < k; i++) {
            StdOut.println(iterator.next());
        }
    }
}
