import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] rQueue;
    private int len;
    private int capacity;

    public RandomizedQueue() {
        this.len = 0;
        this.capacity = 0;
        rQueue = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return len == 0;
    }

    public int size() {
        return len;
    }

    private void resizeArray(int newSize) {
        Item[] newRQueue = (Item[]) new Object[newSize];
        for (int i = 0; i < this.size(); i++)
            newRQueue[i] = rQueue[i];
        rQueue = newRQueue;
        capacity = newSize;
    }

    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("queueing null item");
        if (len == capacity) resizeArray((capacity > 0) ? capacity * 2 : 2);
        rQueue[len++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (len == 0) 
            throw new java.util.NoSuchElementException("dequeueing from an empty queue");
        int idx = (int) (Math.random() * len);
        Item item = rQueue[idx];
        rQueue[idx] = rQueue[--len];
        rQueue[len] = null; // otherwise loitering happens
        if (len > 0 && len == capacity / 4) resizeArray(capacity/2);
        return item; 
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (len == 0) 
            throw new java.util.NoSuchElementException("sampling from an empty queue");
        return rQueue[(int) (Math.random() * len)];
    }

    public Iterator<Item> iterator() {
        return new RQueueIterator();
    }

    private class RQueueIterator implements Iterator<Item> {
        private int[] idxs = new int[len];
        private int currentIdx;

        public RQueueIterator() {
            for (int i = 0; i < len; i++)
                idxs[i] = i;
            StdRandom.shuffle(idxs);
        }

        public boolean hasNext() { return currentIdx < idxs.length; }
        public void remove() {
            throw new UnsupportedOperationException("remove within iterator");
        }
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("iterating over an empty queue");
            return rQueue[idxs[currentIdx++]];
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> rQueue = new RandomizedQueue<>();
        StdOut.println("Queue is empty: " + rQueue.isEmpty());
        rQueue.enqueue(1);
        rQueue.enqueue(2);
        rQueue.enqueue(3);
        StdOut.println("Elements in the queue: " + rQueue.len);
        StdOut.println("Capacity of the queue: " + rQueue.capacity);
        StdOut.println("Dequeued: " + rQueue.dequeue());
        StdOut.println("Sampled: " + rQueue.sample());
    }
}