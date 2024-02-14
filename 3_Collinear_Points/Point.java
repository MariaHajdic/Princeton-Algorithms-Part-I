import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Compares two points by coordinates. Formally, the invoking point (x0, y0) 
     * is less than the argument point (x1, y1) if and only if either y0 < y1 or 
     * if y0 = y1 and x0 < x1.
     *
     * @return 0 if the points are equal;
     *         -1 if the invoking point is less than the argument point; 
     *         1 if the invoking point is greater than the argument point.
     */
    public int compareTo(Point that) {
        if (this.x == that.x && this.y == that.y) return 0;
        return ((this.y < that.y || this.y == that.y && this.x < that.x) ? -1 : 1);
    }

    /**
     * Returns the slope (y1 - y0) / (x1 - x0) between the invoking point x and
     * the argument point y.
     * The slope is +0.0 if the line segment connecting the two points is horizontal,
     * Double.POSITIVE_INFINITY if it's is vertical and 
     * Double.NEGATIVE_INFINITY if the points are equal.
     */
    public double slopeTo(Point that) {
        if (this.x == that.x) 
            return (this.y == that.y) ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
        if (this.y == that.y) return +0.0;
        return (double) (that.y - this.y) / (that.x - this.x);
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new Comparator<Point>() {
            @Override
            public int compare(Point p, Point q) {
                return Double.compare(slopeTo(p), slopeTo(q));
            }
            
        };
    }
}