import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private ArrayList<LineSegment> collinearSegments;

    public FastCollinearPoints(Point[] points) {
        Point[] point = validateInput(points);
        collinearSegments = new ArrayList<LineSegment>();

        for (int p = 0; p < point.length - 3; p++) { // looking for only 4+ collinear points
            Point[] pointsBySlope = point.clone();
            Arrays.sort(pointsBySlope, pointsBySlope[p].slopeOrder());

            double currentSlopeType = pointsBySlope[0].slopeTo(pointsBySlope[1]);
            int consecutivePoints = 2; // points[i] and points[i+1]
            Point line_start = pointsBySlope[0];
            Point line_end = pointsBySlope[1];
            if (line_start.compareTo(line_end) > 0) {
                Point tmp = line_start;
                line_start = line_end;
                line_end = tmp;
            }
            for (int q = 2; q < pointsBySlope.length; q++) {
                if (Double.compare(currentSlopeType, pointsBySlope[0].slopeTo(pointsBySlope[q])) == 0) {
                    consecutivePoints++;

                    if (pointsBySlope[q].compareTo(line_end) >= 0) line_end = pointsBySlope[q];
                    if (pointsBySlope[q].compareTo(line_start) <= 0) line_start = pointsBySlope[q];

                    if (q == pointsBySlope.length - 1 && consecutivePoints >= 4 &&
                        pointsBySlope[0].compareTo(line_start) == 0)
                        collinearSegments.add(new LineSegment(line_start, line_end));

                    continue;
                }
                if (consecutivePoints >= 4 && pointsBySlope[0].compareTo(line_start) == 0)
                    collinearSegments.add(new LineSegment(line_start, line_end));

                currentSlopeType = pointsBySlope[0].slopeTo(pointsBySlope[q]);
                consecutivePoints = 2;
                line_start = pointsBySlope[0];
                line_end = pointsBySlope[q];

                if (line_start.compareTo(line_end) > 0) {
                    Point tmp = line_start;
                    line_start = line_end;
                    line_end = tmp;
                }
            }
        }
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

    public int numberOfSegments() {
        return collinearSegments.size();
    }

    public LineSegment[] segments() {
        LineSegment[] res = new LineSegment[collinearSegments.size()];
        collinearSegments.toArray(res);
        return res;
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

        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}