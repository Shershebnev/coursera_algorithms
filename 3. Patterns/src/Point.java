/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Arrays;
import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {
    
    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point
//    public static final Comparator<Point> SLOPE_ORDER = new slopeOrder();
    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }
    
    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
//        double slope;
        if (this.x == that.x && this.y == that.y) {
            return Double.NEGATIVE_INFINITY;
        } else if (this.x == that.x) {
            return Double.POSITIVE_INFINITY;
        } else if (this.y == that.y) {
            return +0.0;
        }
        return (double)(that.y - this.y) / (that.x - this.x);
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        // Equal
        if (this.y == that.y && this.x == that.x) {
            return 0;
        } 
        // this is less than that
        if (this.y < that.y || (this.y == that.y && this.x < that.x)) {
            return -1;
        }
        // this is greater that that
        return 1;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
         /* YOUR CODE HERE */
        return new SlopeOrder();
     }

    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            double slope1 = slopeTo(p1);
            double slope2 = slopeTo(p2);
            return Double.compare(slope1,  slope2);
        }
    }
    
    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */
//        Point a = new Point(29000, 170000);
//        Point b = new Point(31000, 16000);
//        Point c = new Point(1000, 31000);
//        Point[] d = {a, b, c};
//        Arrays.sort(d, a.slopeOrder());
//        Point p = new Point(4, 359);
//        Point q = new Point(4, 327);
//        System.out.println(p.slopeTo(q));
//        Point a = new Point(10000, 0);
//        Point b = new Point(0, 10000);
//        Point c = new Point(3000, 7000);
//        Point d = new Point(7000, 3000);
//        Point e = new Point(20000, 21000);
//        Point f = new Point(3000, 4000);
//        System.out.println(c.slopeTo(b));
//        Point g = new Point(14000, 15000);
//        Point h = new Point(6000, 7000);
//        
//        Point[] i = {a, b, c, d, e, f, g, h};
//        ArrayList<LineSegment> collinears = new ArrayList<LineSegment>();
//        LineSegment lsab = new LineSegment(a, b);
//        LineSegment lsba = new LineSegment(a, b);
//        System.out.println(lsab.equals(lsba));
//        collinears.add(lsab);
//        if (collinears.contains(lsba)) {
//            System.out.println("azaza");
//            collinears.add(lsab);
//        } else {
//            System.out.println("bugaga");
//        }
        
//        for (Point p : i) {
//            System.out.println(p);
//        }
//        System.out.println("Sorted:");
////        Arrays.sort(i, i[0].SLOPE_ORDER);
//        for (Point p : i) {
//            Arrays.sort(i, p.SLOPE_ORDER);
//            System.out.println("Point: " + p);
//            for (Point pSorted : i) {
//                System.out.println(pSorted);
//            }
//        }
        
    }
}
