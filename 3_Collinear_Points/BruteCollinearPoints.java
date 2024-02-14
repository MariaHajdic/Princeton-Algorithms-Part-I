import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private LineSegment[] collinearSegments;

    public BruteCollinearPoints(Point[] points) {
        Point[] point = validateInput(points);
        collinearSegments = new LineSegment[point.length/4+1];
        int segmentsIdx = 0;

        for (int p = 0; p < point.length - 3; p++) {
            for (int q = p + 1; q < point.length - 2; q++) {
                for (int r = q + 1; r < point.length - 1; r++) {
                    if (point[p].slopeTo(point[q]) != point[q].slopeTo(point[r])) continue;
                    for (int s = r + 1; s < point.length; s++) {
                        if (point[q].slopeTo(point[r]) == point[r].slopeTo(point[s]))
                            collinearSegments[segmentsIdx++] = new LineSegment(point[p], point[s]);
                    }
                }
            }
        }
        collinearSegments = optimisedSegmentsArray(segmentsIdx);
    }

    private Point[] validateInput(Point[] inputPoints) {
        if (inputPoints == null) throw new IllegalArgumentException("points[] is null");
        Point[] point = new Point[inputPoints.length];
        for (int i = 0; i < inputPoints.length; i++) {
            if (inputPoints[i] == null) throw new IllegalArgumentException("null point");
            point[i] = inputPoints[i];
        }
        Arrays.sort(point);
        for (int i = 0; i < point.length - 1; i++) {
            if (point[i].compareTo(point[i+1]) == 0) throw new IllegalArgumentException("duplicate point");
        }
        return point;
    }

    private LineSegment[] optimisedSegmentsArray(int len) {
        LineSegment[] tmpSegments = new LineSegment[len];
        for (int i = 0; i < len; i++)
            tmpSegments[i] = collinearSegments[i];
        return tmpSegments;
    }

    public int numberOfSegments() {
        return collinearSegments.length;
    }

    public LineSegment[] segments() {
        return collinearSegments.clone();
    }

    public static void main(String[] args) {
        In scanner = new In(args[0]);
        int n = scanner.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = scanner.readInt();
            int y = scanner.readInt();
            points[i] = new Point(x, y);
        }

        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points)
            p.draw();
        StdDraw.show();

        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}