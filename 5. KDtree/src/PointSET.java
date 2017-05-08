import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
//import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;


public class PointSET {
    
    private SET<Point2D> pointSet;
    public PointSET() {
        pointSet = new SET<Point2D>();
        
    }

    public boolean isEmpty() {
        return pointSet.isEmpty();
    }

    public int size() {
        return pointSet.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException();
        pointSet.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        return pointSet.contains(p);
    }

    public void draw() {
        for (Point2D p : pointSet) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();
        ArrayList<Point2D> pointsList = new ArrayList<Point2D>();
        for (Point2D p : pointSet) {
            if (rect.contains(p)) {
                pointsList.add(p);
            }
        }
        return pointsList;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException();
        if (pointSet.isEmpty()) return null;
        if (pointSet.contains(p)) return p;
        Point2D nearestPoint = p;
        double nearestDistance = Double.POSITIVE_INFINITY;
        for (Point2D pSet : pointSet) {
            if (p.equals(pSet)) continue;
            double distance = p.distanceTo(pSet);
            if (distance < nearestDistance) {
                nearestPoint = pSet;
                nearestDistance = distance;
            }
        }
        return nearestPoint;
    }
    
    public static void main(String[] args) {
//        Point2D a = new Point2D(289.9810, 99.71439);
//        Point2D b = new Point2D(289.5872, 98.35824);
//        System.out.println(a.distanceTo(b));
//        PointSET a = new PointSET();
//        System.out.println(a.isEmpty());
//        System.out.println(a.size());
//        a.insert(new Point2D(10, 10));
//        a.insert(new Point2D(12, 10));
//        a.insert(new Point2D(30, 20));
//        a.insert(new Point2D(40, 14));
//        Point2D nearestP = a.nearest(new Point2D(15, 15));
//        StdDraw.show();
//        StdDraw.setXscale(0, 50);
//        StdDraw.setYscale(0, 50);
//        StdDraw.setPenColor(StdDraw.BLUE);
//        StdDraw.setPenRadius(0.01);
//        a.draw();
//        nearestP.draw();
//        StdDraw.setPenColor(StdDraw.GREEN);
//        StdDraw.setPenRadius(0.005);
//        StdDraw.line(nearestP.x(), nearestP.y(), 15, 15);
//        StdDraw.show();
    }
}
