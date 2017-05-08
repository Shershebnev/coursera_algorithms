import java.util.ArrayList;
import java.util.Arrays;

//import edu.princeton.cs.algs4.In;
//import edu.princeton.cs.algs4.StdDraw;
//import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    
    private int N = 0;
    private ArrayList<LineSegment> collinears = new ArrayList<LineSegment>();
    
    public BruteCollinearPoints(Point[] points) {
        // exceptions
        if (points == null) { throw new NullPointerException(); }
        Point[] pointsCopy = points.clone();
        Arrays.sort(pointsCopy);
        for (int i = 0; i < pointsCopy.length - 1; i++) {
            if (pointsCopy[i].equals(pointsCopy[i + 1])) {
                throw new IllegalArgumentException();
            }
        }
        
        for (int i = 0; i < pointsCopy.length - 3; i++) {
            if (pointsCopy[i] == null) { throw new java.lang.NullPointerException(); }
            for (int j = i + 1; j < pointsCopy.length - 2; j++) {
                if (pointsCopy[j] == null) { throw new java.lang.NullPointerException(); }
                double slope1 = pointsCopy[i].slopeTo(pointsCopy[j]);
                for (int k = j + 1; k < pointsCopy.length - 1; k++) {
                    if (pointsCopy[k] == null) { throw new java.lang.NullPointerException(); }
                    double slope2 = pointsCopy[j].slopeTo(pointsCopy[k]);
                    for (int l = k + 1; l < pointsCopy.length; l++) {
                        if (pointsCopy[l] == null) { throw new java.lang.NullPointerException(); }
                        double slope3 = pointsCopy[k].slopeTo(pointsCopy[l]);
                        if (slope1 == slope2 && slope2 == slope3) {
                            collinears.add(new LineSegment(pointsCopy[i], pointsCopy[l]));
                            N++;
                        }
                    }
                }
            }
        }
    }
    
    public int numberOfSegments() {
        return N;
    }
    
    public LineSegment[] segments() {
        return collinears.toArray(new LineSegment[collinears.size()]);
    }
    
    public static void main(String[] args) {
     // read the N points from a file
//        In in = new In(args[0]);
//        int N = in.readInt();
//        Point[] points = new Point[N];
//        for (int i = 0; i < N; i++) {
//            int x = in.readInt();
//            int y = in.readInt();
//            points[i] = new Point(x, y);
//        }
//
//        // draw the points
//        StdDraw.show(0);
//        StdDraw.setXscale(0, 32768);
//        StdDraw.setYscale(0, 32768);
//        for (Point p : points) {
//            p.draw();
//        }
//        StdDraw.show();
//
//        // print and draw the line segments
//        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
//        for (LineSegment segment : collinear.segments()) {
//            StdOut.println(segment);
//            segment.draw();
//        }
    }
}
