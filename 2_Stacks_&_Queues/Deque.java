import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private int numberOfElements;
    private Node firstNode;
    private Node lastNode;

    private class Node {
        Item item;
        Node previousNode;
        Node nextNode;
    }

    public Deque() { }

    public boolean isEmpty() {
        return numberOfElements == 0;
    }

    public int size() {
        return numberOfElements;
    }

    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("queueing null item");
        if (isEmpty()) {
            firstNode = new Node();
            lastNode = firstNode;
        } else {
            Node oldFirst = firstNode;
            firstNode = new Node();
            firstNode.nextNode = oldFirst;
            oldFirst.previousNode = firstNode;
        }
        firstNode.item = item;
        numberOfElements++;
    }

    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("queueing null item");
        if (isEmpty()) {
            lastNode = new Node();
            firstNode = lastNode;
        } else {
            Node oldLast = lastNode;
            lastNode = new Node();
            lastNode.previousNode = oldLast;
            oldLast.nextNode = lastNode;
        }
        lastNode.item = item;
        numberOfElements++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (numberOfElements == 0) 
            throw new java.util.NoSuchElementException("dequeueing from an empty dequeue");
        Item item = firstNode.item;
        firstNode = firstNode.nextNode;
        if (firstNode == null) lastNode = null;
        else firstNode.previousNode = null;
        numberOfElements--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (numberOfElements == 0) 
            throw new java.util.NoSuchElementException("dequeueing from an empty dequeue");
        Item item = lastNode.item;
        lastNode = lastNode.previousNode;
        if (lastNode == null) firstNode = null;
        else lastNode.nextNode = null;
        numberOfElements--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node currentNode = firstNode;

        public boolean hasNext() { return currentNode != null; }
        public void remove() {
            throw new UnsupportedOperationException("removing an element within the iterator");
        }
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("no elements to iterate over");
            Item item = currentNode.item;
            currentNode = currentNode.nextNode;
            return item;
        }
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        StdOut.println("Deque is empty: " + deque.isEmpty());
        deque.addFirst(1);
        deque.addLast(2);
        deque.addFirst(3);
        StdOut.println("Elements in the deque: " + deque.numberOfElements);
        StdOut.println("Removed first: " + deque.removeFirst());
        StdOut.println("Removed last: " + deque.removeLast());
    }
}