import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int INIT_CAPACITY = 8;
    private Item[] s;
    private int N = 0;
    private int first;

    // construct an empty randomized queue
    public RandomizedQueue() {
        first = 0;
        s = (Item[]) new Object[INIT_CAPACITY];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return N;
    }

    // Move stack to a new array of size max.
    private void resize(int max) {
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < N; i++)
            temp[i] = s[i+first];
        first = 0;
        s = temp;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (N == s.length) resize(2*s.length);
        check();
        s[first+N] = item;
        N++;
    }

    // check that array is not full and have slots on right side
    private void check() {
        if (s[s.length-1] != null) {
            for (int i = 0; i < N; i++) {
                s[i] = s[first+i];
                s[first+i] = null;
            }
            first = 0;
        }
    }

    // remove and return a random item
    public Item dequeue() {
        if (N == 0) throw new NoSuchElementException();
        int idx = StdRandom.uniform(N);
        Item removed = s[first + idx];
        s[first + idx] = null;
        if (idx == 0) first++;
        else if (idx != N-1) {
            shrink(first+idx);
        }
        N--;
        if (N == 0) first = 0;
        return removed;
    }

    // Remove hole between sub arrays
    private void shrink(int hole) {
        int i = hole + 1;
        while (s[i] != null) {
            s[i-1] = s[i];
            s[i] = null;
            i++;
            if (i >= s.length) break;
        }
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (N == 0) throw new NoSuchElementException();
        int idx = StdRandom.uniform(N);
        return s[first + idx];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }
    private class ArrayIterator implements Iterator<Item> {
        // Support LIFO iteration.
        private boolean init = false;
        private int idx = 0;
        private Integer[] index = new Integer[N];

        private boolean initIndex() {
            if (!init) {
                for (int i = 0; i < N; i++) index[i] = i;
                StdRandom.shuffle(index);
            }
            return true;
        }

        public boolean hasNext() {
            init = initIndex();
            return idx < index.length;
        }
        public    Item next() {
            if (idx >= index.length) throw new NoSuchElementException();
            init = initIndex();
            return s[first+index[idx++]];
        }
        public    void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();

        for (int i = 0; i < 9; i++)
            queue.enqueue(i);

        for (int i = 0; i < 9; i++)
            queue.dequeue();
        queue.iterator().next();

        StdOut.println("Removed from Queue" + "-" + queue.dequeue());
        StdOut.println("Size of Queue" + "-" + queue.size());

        for (int a : queue) {
            for (int b : queue)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }
    }
}
