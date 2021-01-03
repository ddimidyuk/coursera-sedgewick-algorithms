import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first, last;
    private int size;

    private class Node {
        private Item item;
        private Node next;
        private Node prev;

        public Node(Item item) {
            this.item = item;
        }
    }

    // construct an empty deque
    public Deque() {
        this.size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        validate(item);
        Node oldFirst = first;
        first = new Node(item);
        first.next = oldFirst;
        if (!isEmpty()) {
            oldFirst.prev = first;
        } else {
            last = first;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        validate(item);
        Node oldLast = last;
        last = new Node(item);
        if (!isEmpty()) {
            oldLast.next = last;
            last.prev = oldLast;
        } else {
            first = last;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        checkIfNotEmpty();
        Node nodeToRemove = this.first;
        size--;
        if (!isEmpty()) {
            this.first = nodeToRemove.next;
            this.first.prev = null;
        } else {
            this.first = null;
            this.last = null;
        }
        return nodeToRemove.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        checkIfNotEmpty();
        Node nodeToRemove = this.last;
        size--;
        if (!isEmpty()) {
            this.last = nodeToRemove.prev;
            this.last.next = null;
        } else {
            this.first = null;
            this.last = null;
        }
        return nodeToRemove.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeueIterator();
    }

    private class DequeueIterator implements Iterator<Item> {

        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (hasNext()) {
                Item item = current.item;
                current = current.next;
                return item;
            } else {
                throw new NoSuchElementException("Queue is empty");
            }
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private void validate(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Null is not allowed");
        }
    }

    private void checkIfNotEmpty() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
    }
}