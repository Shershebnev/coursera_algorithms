import java.util.Arrays;
import java.util.Hashtable;
import java.util.HashSet;

//import edu.princeton.cs.algs4.In;
//import edu.princeton.cs.algs4.StdDraw;
//import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class FastCollinearPoints {
    private ArrayList<LineSegment> collinears = new ArrayList<LineSegment>();
//    private ArrayList<Line> collinearsLine = new ArrayList<Line>();
    private ArrayList<Point> collinearPoints = new ArrayList<Point>();
    private Hashtable<Double, ArrayList<String>> slopeTable = new Hashtable<Double, ArrayList<String>>();
    private Point[] pointsClone;
    
//    private class Line {
//        private Point firstPoint;
//        private Point lastPoint;
//    }
    
    public FastCollinearPoints(Point[] points) {
        // exceptions
        if (points == null) { throw new NullPointerException(); }
        pointsClone = points.clone();

        HashSet<String> a = new HashSet<String>();
        for (Point p1 : points) {
            String p2 = p1 + "";
            if (!a.add(p2)) {
                throw new IllegalArgumentException();
            }
        }
        
//        Arrays.sort(pointsClone);
//        for (int i = 0; i < pointsClone.length - 1; i++) {
//            if (pointsClone[i].compareTo(pointsClone[i + 1]) == 0) {
//                throw new IllegalArgumentException();
//            }
//        }

        for (Point p : points) {
            collinearPoints.add(p);
            Arrays.sort(pointsClone, p.slopeOrder());
            Double prevSlope = null;
            Point prevPoint = p;
            boolean foundEqual = false;
//            double[] slopes = new double[points.length];
//            for (int i = 0; i < points.length; i++) {
//                slopes[i] = p.slopeTo(points[i]);
//            }
            for (Point x : pointsClone) {
                if (x == p) continue;
                double slope = p.slopeTo(x);
                if (foundEqual && slope != prevSlope) {
                    if (collinearPoints.size() >= 4) {
                        addCollinears(collinearPoints, prevSlope);
                    }
                    foundEqual = false;
                    collinearPoints.clear();
                    collinearPoints.add(p);
                } else if (prevSlope != null && slope == prevSlope) {
                    foundEqual = true;
                    if (!collinearPoints.contains(x)) {
                        collinearPoints.add(x);
                    }
                    if (!collinearPoints.contains(prevPoint)) {
                        collinearPoints.add(prevPoint);
                    }
                }
                prevSlope = slope;
                prevPoint = x;
            }
            if (collinearPoints.size() >= 4) {
                addCollinears(collinearPoints, prevSlope);
            }
            collinearPoints.clear();
        }
    }
    
    private void addCollinears(ArrayList<Point> collinearPointsList, double slope) {
        Point[] candidates = collinearPointsList.toArray(new Point[collinearPointsList.size()]);
        boolean contains = false;
        if (slopeTable.containsKey(slope)) {
            for (Point p1 : candidates) {
                String p1String = p1 + "";
                if (slopeTable.get(slope).contains(p1String)) {
                    contains = true;
                    break;
                } else {
                    slopeTable.get(slope).add(p1String);
                }
            }
        } else {
            slopeTable.put(slope, new ArrayList<String>());
            for (Point p1 : candidates) {
                String p1String = p1 + "";
                slopeTable.get(slope).add(p1String);
            }
        }
//        Arrays.sort(candidates);
//        Line line = new Line();
//        line.lastPoint = candidates[candidates.length - 1];
//        line.firstPoint = candidates[0];
//        for (Line item : collinearsLine) {
//            if (item.firstPoint == line.firstPoint && item.lastPoint == line.lastPoint) {
//                contains = true;
//                break;
//            }
//        }
        if (!contains) {
//            collinearsLine.add(line);
            Arrays.sort(candidates);
            collinears.add(new LineSegment(candidates[0], candidates[candidates.length - 1]));
        }
    }
    
    public int numberOfSegments() {
        return collinears.size();
    }
    
    public LineSegment[] segments() {
        return collinears.toArray(new LineSegment[collinears.size()]);
    }
    
    public static void main(String[] args) {
        Point a = new Point(10000, 0);
        Point b = new Point(0, 10000);
        Point c = new Point(3000, 7000);
        Point d = new Point(7000, 3000);
        Point e = new Point(20000, 21000);
        Point f = new Point(3000, 4000);
        Point g = new Point(14000, 15000);
        Point h = new Point(6000, 7000);
        
        Point[] points = {a, b, c, d, e, f, g, h};
//        Point[] points = {new Point(1000, 17000), new Point(31000, 16000), new Point(1000, 31000), new Point(29000, 17000), new Point(17000, 17000), new Point(13000, 17000)};
//        Point[] points = {new Point(1000, 17000), new Point(14000, 24000), new Point(26000, 8000), new Point(10000, 28000), new Point(18000, 5000), new Point(1000, 27000), new Point(14000, 14000), new Point(11000, 16000), new Point(29000, 17000), new Point(5000, 21000), new Point(19000, 26000), new Point(28000, 21000), new Point(25000, 24000), new Point(30000, 10000), new Point(25000, 14000), new Point(31000, 16000), new Point(5000, 12000), new Point(1000, 31000), new Point(2000, 24000), new Point(13000, 17000), new Point(1000, 28000), new Point(14000, 16000), new Point(26000, 26000), new Point(10000, 31000), new Point(12000, 4000), new Point(9000, 24000), new Point(28000, 29000), new Point(12000, 20000), new Point(13000, 11000), new Point(4000, 26000), new Point(8000, 10000), new Point(15000, 12000), new Point(22000, 29000), new Point(7000, 15000), new Point(10000, 4000), new Point(2000, 29000), new Point(17000, 17000), new Point(3000, 15000), new Point(4000, 29000), new Point(19000, 2000)};
//        Point[] points = {new Point(30000, 23000), new Point(16000, 6000), new Point(3000, 11000), new Point(19000, 28000), new Point(23000, 19000), new Point(27000, 30000), new Point(28000, 14000), new Point(14000, 5000), new Point(24000, 5000), new Point(8000, 8000), new Point(17000, 2000), new Point(8000, 23000), new Point(3000, 22000), new Point(22000, 5000), new Point(19000, 1000), new Point(25000, 8000), new Point(0, 26000), new Point(22000, 14000), new Point(25000, 27000), new Point(25000, 29000), new Point(4000, 17000), new Point(28000, 29000), new Point(27000, 31000), new Point(18000, 18000), new Point(1000, 7000), new Point(2000, 6000), new Point(13000, 29000), new Point(9000, 13000), new Point(14000, 12000), new Point(4000, 27000), new Point(15000, 21000), new Point(26000, 23000), new Point(8000, 0), new Point(5000, 9000), new Point(11000, 17000), new Point(14000, 16000), new Point(17000, 17000), new Point(12000, 10000), new Point(31000, 1000), new Point(29000, 14000), new Point(4000, 7000), new Point(23000, 1000), new Point(10000, 25000), new Point(8000, 4000), new Point(29000, 18000), new Point(19000, 29000), new Point(1000, 21000), new Point(26000, 13000), new Point(0, 23000), new Point(22000, 28000), new Point(20000, 4000), new Point(9000, 8000), new Point(22000, 19000), new Point(17000, 5000), new Point(5000, 24000), new Point(21000, 8000), new Point(1000, 15000), new Point(28000, 3000), new Point(15000, 0), new Point(24000, 22000), new Point(26000, 22000), new Point(16000, 21000), new Point(25000, 12000), new Point(14000, 21000), new Point(30000, 12000), new Point(29000, 31000), new Point(25000, 31000), new Point(15000, 30000), new Point(17000, 21000), new Point(20000, 0), new Point(24000, 27000), new Point(26000, 16000), new Point(12000, 8000), new Point(29000, 24000), new Point(0, 30000), new Point(13000, 1000), new Point(4000, 2000), new Point(25000, 9000), new Point(21000, 23000), new Point(23000, 24000)};
        for (Point p1 : points) {
            System.out.println(p1);
        }
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment l : collinear.segments()) {
            System.out.println(l);
        }
        System.out.println("After");
        for (Point p1 : points) {
            System.out.println(p1);
        }
        
        
        
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
//        FastCollinearPoints collinear = new FastCollinearPoints(points);
//        for (LineSegment segment : collinear.segments()) {
//            StdOut.println(segment);
//            segment.draw();
//        }
    }
}
