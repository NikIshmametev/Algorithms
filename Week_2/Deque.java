import java.util.Iterator;
import java.util.NoSuchElementException;



public class Deque<Item> implements Iterable<Item> {
    private static final int INIT_CAPACITY = 8;
    private Item[] s;
    private int N = 0;
    private int first; // index of the first item

    // construct an empty deque
    public Deque() {
        first = 0;
        s = (Item[]) new Object[INIT_CAPACITY];
    }

    public boolean isEmpty()  {
        return N == 0;
    }
    public int size() {
        return N;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (N == 0) {
            s[0] = item;
            first = 0;
        }
        else {
            if (first == 0) {
                resize(2 * s.length, false);
                first = s.length/2;
            }
            s[--first] = item;
        }
        N++;
    }

    private void shift() {
        if (s.length - 5*N/4 > 0) {
            int firstNew = (s.length - N)/2;
            for (int i = 0; i < N; i++) {
                s[firstNew + i] = s[first + i];
                s[first + i] = null;
            }
            first = firstNew;
        }
        else {
            resize(2*s.length, true);
        }
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (N == s.length) resize(2*s.length, true);
        if (s[s.length-1] != null) {
            shift();
        }
        s[first + N] = item;
        N++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (N == 0) throw new NoSuchElementException();
        else {
            Item item = s[first];
            s[first++] = null;
            N--;
            if (N == 0) {
                first = 0;
            }
            if (N > 0 && N == s.length/4) resize(s.length/2, true);
            return item;
        }
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (N == 0) throw new NoSuchElementException();
        else {
            Item item = s[first+N-1];
            s[first+N-1] = null;
            N--;
            if (N > 0 && N <= s.length/4) resize(s.length/2, true);
            return item;
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }
    private class ArrayIterator implements Iterator<Item> {
        // Support LIFO iteration.
        private int i = 0;
        public boolean hasNext() {
            return i < N;
        }
        public Item next() {
            if (N == 0 || i > N) throw new NoSuchElementException();
            Item item = s[i+first];
            i++;
            return item;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // Move stack to a new array of size max.
    private void resize(int max, boolean right) {
        Item[] temp = (Item[]) new Object[max];
        int shift = 0;
        if (!right) shift = s.length;
        for (int i = 0; i < N; i++)
            temp[i+shift] = s[i+first];
        first = 0;
        s = temp;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();

        for (int i = 0; i < 5; i++)
            deque.addFirst(i);
        for (int i = 0; i < 5; i++)
            deque.addLast(i);
        for (int a : deque) {
            for (int b : deque)
                System.out.println(a + "-" + b + " ");
            System.out.println();
        }
        int a;
        for (int i = 0; i < 4; i++) {
            a = deque.removeFirst();
            System.out.print(a); // 0123
        }
    }

}