import java.util.Arrays;
import java.util.ArrayList;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> segments = new ArrayList<>();
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();

        Point[] copy = points.clone();
        Arrays.sort(copy);

        if (hasDuplicate(copy)) throw new IllegalArgumentException();

        for (int first = 0; first < copy.length - 3; first++) {
            for (int second = first + 1; second < copy.length - 2; second++) {
                double slopeFS = copy[first].slopeTo(copy[second]);
                for (int third = second + 1; third < copy.length - 1; third++) {
                    double slopeFT = copy[first].slopeTo(copy[third]);
                    if (slopeFS == slopeFT) {
                        for (int fourth = third + 1; fourth < copy.length; fourth++) {
                            double slopeFF = copy[first].slopeTo(copy[fourth]);
                            if (slopeFS == slopeFF) {
                                segments.add(new LineSegment(copy[first], copy[fourth]));
                            }
                        }
                    }
                }
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

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }
}
