
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] array;
    private int pointer;

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.array = (Item[]) new Object[5];
        this.pointer = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return pointer == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return pointer;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Null is not allowed");
        if (pointer == array.length)
            resize(pointer * 2);
        array[pointer++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        checkIfEmpty();
        if (pointer > 1) {
            int random = StdRandom.uniform(pointer - 1);
            if (random != pointer - 1) {
                swap(random);
            }
        }
        Item item = array[pointer - 1];
        array[(pointer--) - 1] = null;
        if (size() < array.length / 4) resize(array.length / 2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        checkIfEmpty();
        int random = StdRandom.uniform(pointer - 1);
        return array[random];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int current;
        private Item[] shuffled;

        public RandomizedQueueIterator() {
            current = 0;
            shuffled = (Item[]) new Object[pointer];
            for (int i = 0; i < pointer; i++) {
                shuffled[i] = array[i];
            }
            StdRandom.shuffle(shuffled);
        }

        public boolean hasNext() {
            return current < pointer;
        }

        public Item next() {
            if (hasNext()) return shuffled[current++];
            else throw new NoSuchElementException("Queue is empty");
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private void resize(int capacity) {
        Item[] newArray = (Item[]) new Object[capacity];
        for (int i = 0; i < pointer; i++) {
            newArray[i] = array[i];
        }
        array = newArray;
    }

    private void swap(int n) {
        Item itemToSwap = array[n];
        array[n] = array[pointer - 1];
        array[pointer - 1] = itemToSwap;
    }

    private void checkIfEmpty() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
    }
}