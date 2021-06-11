import java.util.Arrays;
import java.util.ArrayList;


public class FastCollinearPoints {
    private ArrayList<LineSegment> segments = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        // finds all line segments containing 4 or more points
        if (points == null) throw new IllegalArgumentException();

        if (hasDuplicate(points)) throw new IllegalArgumentException();

        Point[] copy = points.clone();
        Arrays.sort(copy);

        for (int i = 0; i < copy.length - 3; i++) {
            Arrays.sort(copy);
            Arrays.sort(copy, copy[i].slopeOrder());
            for (int p = 0, first = 1, last = 2; last < copy.length; last++) {
                while (last < copy.length &&
                        Double.compare(copy[p].slopeTo(copy[first]), copy[p].slopeTo(copy[last])) == 0) {
                    last++;
                }
                if (last-first >= 3 && copy[p].compareTo(copy[first]) < 0) {
                    segments.add(new LineSegment(copy[p], copy[last-1]));
                }
                first = last;
            }
        }

    }

    private boolean hasDuplicate(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                return true;
            }
        }
        return false;
    }

    public int numberOfSegments() {
        // the number of line segments
        return segments.size();
    }

    public LineSegment[] segments() {
        // the line segments
        return segments.toArray(new LineSegment[segments.size()]);
    }
}