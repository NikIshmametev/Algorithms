/*****************************************************************************
 *  Compilation:  javac Percolation.java
 *  Execution:    java Percolation n
 *  Dependencies: PercolationVisualizer.java Percolation.java
 *                StdDraw.java StdOut.java
 *
 ******************************************************************************/


import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] grid;
    private int size;
    private int opened = 0;

    private WeightedQuickUnionUF qu;
    // Doesn't connect to bottom, doesn't backwash
    private WeightedQuickUnionUF qu2;

    private int top;
    private int bot;

    public Percolation(int size) {
        this.size = size;
        this.grid = new boolean[size][size];
        int maxIndex;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = false;
            }
        }
        maxIndex = xytoD(size, size - 1);
        top = maxIndex + 1;
        bot = maxIndex + 2;
        qu = new WeightedQuickUnionUF(bot + 1);
        qu2 = new WeightedQuickUnionUF(bot + 1);
    }

    private int xytoD(int x, int y) {
        return x + (y * size);
    }

    public boolean isOpen(int i, int j) {
        validateIndex(i, j);
        return grid[i - 1][j - 1];
    }

    public boolean isFull(int i, int j) {
        validateIndex(i, j);
        return qu2.connected(top, xytoD(i - 1, j - 1));
    }

    public int numberOfOpenSites() {
        return opened;
    }

    public void open(int i, int j) {
        if (!isOpen(i, j)) {
            // Indexes are from 1 to size while array indexes from 0 to size - 1
            grid[i - 1][j - 1] = true;
            opened += 1;

            if (!isLeftEdge(j)) {
                union(i, j, 0, -1); // Connect to left square
            }
            if (!isRightEdge(j)) {
                union(i, j, 0, 1); // Connect to right square
            }
            if (!isTopEdge(i)) {
                union(i, j, -1, 0); // connect to top cell
            }
            else {
                unionVirtual(i, j, top, false); // Connect to top virtual
            }
            if (!isBottomEdge(i)) {
                union(i, j, 1, 0); // connect to bottom cell
            }
            else {
                // Connect to bottom virtual
                unionVirtual(i, j, bot, true);
            }
        }
    }

    public boolean percolates() {
        return qu.connected(bot, top);
    }

    private void union(int i, int j, int rowOffset, int columnOffset) {
        final int currentKey = xytoD(i - 1, j - 1);
        final int column2 = j + columnOffset;
        final int row2 = i + rowOffset;
        if (isOpen(row2, column2)) {
            qu.union(currentKey, xytoD(row2 - 1, column2 - 1));
            qu2.union(currentKey, xytoD(row2 - 1, column2 - 1));
        }
    }

    private void unionVirtual(int i, int j, int virtualKey, boolean bottom) {
        final int currentKey = xytoD(i - 1, j - 1);
        qu.union(currentKey, virtualKey);
        if (!bottom) {
            qu2.union(currentKey, virtualKey);
        }
    }

    private boolean isValid(int x, int y) {
        return x >= 1 && x <= size && y >= 1 && y <= size;
    }

    private void validateIndex(int x, int y) {
        if (!isValid(x, y)) {
            throw new IndexOutOfBoundsException(
                    String.format("N:%d x:%d y:%d", size, x, y));
        }
    }

    private boolean isBottomEdge(int i) {
        return i == size;
    }

    private boolean isTopEdge(int i) {
        return i == 1;
    }

    private boolean isRightEdge(int j) {
        return j == size;
    }

    private boolean isLeftEdge(int j) {
        return j == 1;
    }

    public static void main(String[] args) {
        test();
    }

    private static void test() {
        final Percolation p = new Percolation(3);
        System.out.println("p.isOpen(1, 2) = " + p.isOpen(1, 2));
        p.open(1, 2);
        System.out.println("p.isOpen(1, 2) = " + p.isOpen(1, 2));

        System.out.println("p.isOpen(2,2) = " + p.isOpen(2, 2));
        p.open(2, 2);
        System.out.println("p.isOpen(2,2) = " + p.isOpen(2, 2));
        System.out.println("p.isFull(2, 2) = " + p.isFull(2, 2));

        System.out.println("p.isOpen(3, 1) = " + p.isOpen(3, 2));
        p.open(3, 2);
        System.out.println("p.isOpen(3, 1) = " + p.isOpen(3, 2));
        p.isFull(3, 2);

        System.out.println("p.percolates() = " + p.percolates());
    }

}
