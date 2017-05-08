import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Queue;

public class KdTree2 {
    private static final boolean VERTICAL = true;
    private static final boolean HORIZONTAL = false;

    private Node root;
    private int size;

    private static class Node {
        // the point
        private Point2D p;

        // the axis-aligned rectangle corresponding to this node
        private RectHV rect;

        // the left/bottom subtree
        private Node lb;

        // the right/top subtree
        private Node rt;

        public Node(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;
        }

        public String toString() {
            StringBuilder s = new StringBuilder();
            s.append(p.toString());
            s.append(" ");
            s.append(rect.toString());
            if (lb != null) {
                s.append("\nlb: ");
                s.append(lb.toString());
            }
            if (rt != null) {
                s.append("\nrt: ");
                s.append(rt.toString());
            }
            return s.toString();
        }
    }

    // construct an empty set of points
    public KdTree2() {
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        root = insert(root, p, VERTICAL, 0, 0, 1, 1);
    }

    private Node insert(Node x, Point2D p, boolean orientation,
                        double xmin, double ymin, double xmax, double ymax) {
        if (x == null) {
            this.size++;
            return new Node(p, new RectHV(xmin, ymin, xmax, ymax));
        }

        if (x.p.equals(p)) {
            return x;
        }

        if (orientation == VERTICAL) {
            double cmp = p.x() - x.p.x();

            if (cmp < 0) {
                x.lb = insert(x.lb, p, !orientation,
                              x.rect.xmin(), x.rect.ymin(), x.p.x(), x.rect.ymax());
            } else {
                x.rt = insert(x.rt, p, !orientation,
                              x.p.x(), x.rect.ymin(), x.rect.xmax(), x.rect.ymax());
            }
        } else {
            double cmp = p.y() - x.p.y();

            if (cmp < 0) {
                x.lb = insert(x.lb, p, !orientation,
                              x.rect.xmin(), x.rect.ymin(), x.rect.xmax(), x.p.y());
            } else {
                x.rt = insert(x.rt, p, !orientation,
                              x.rect.xmin(), x.p.y(), x.rect.xmax(), x.rect.ymax());
            }
        }

        return x;
    }

    // does the set contain the point p?
    public boolean contains(Point2D p) {
        return contains(root, p, VERTICAL);
    }

    private boolean contains(Node x, Point2D p, boolean orientation) {
        if (x == null) {
            return false;
        }

        if (x.p.equals(p)) {
            return true;
        }

        double cmp;
        if (orientation == VERTICAL) {
            cmp = p.x() - x.p.x();
        } else {
            cmp = p.y() - x.p.y();
        }


        if (cmp < 0) {
            return contains(x.lb, p, !orientation);
        } else {
            return contains(x.rt, p, !orientation);
        }
    }

    // draw all of the points to standard draw
    public void draw() {
        draw(root, VERTICAL);
    }

    private void draw(Node x, boolean orientation) {
        if (orientation == VERTICAL) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
        }

        if (x.lb != null) {
            draw(x.lb, !orientation);
        }

        if (x.rt != null) {
            draw(x.rt, !orientation);
        }

        // draw point last to be on top of line
        StdDraw.setPenColor(StdDraw.BLACK);
        x.p.draw();
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> q = new Queue<Point2D>();
        range(root, rect, q);
        return q;
    }

    private void range(Node x, RectHV rect, Queue<Point2D> q) {
        if (x == null) {
            return;
        }

        if (!x.rect.intersects(rect)) {
            return;
        }

        if (rect.contains(x.p)) {
            q.enqueue(x.p);
        }

        range(x.lb, rect, q);

        range(x.rt, rect, q);
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        return nearest(root, p, Double.POSITIVE_INFINITY);
    }

    // Find the nearest point that is closer than distance
    private Point2D nearest(Node x, Point2D p, double distance) {
        if (x == null) {
            return null;
        }

        if (x.rect.distanceTo(p) >= distance) {
            return null;
        }

        Point2D nearestPoint = null;
        double nearestDistance = distance;
        double d;

        d = p.distanceTo(x.p);
        if (d < nearestDistance) {
            nearestPoint = x.p;
            nearestDistance = d;
        }

        // Choose subtree that is closer to point.

        Node firstNode = x.lb;
        Node secondNode = x.rt;

        if (firstNode != null && secondNode != null) {
            if (firstNode.rect.distanceTo(p) > secondNode.rect.distanceTo(p)) {
                firstNode = x.rt;
                secondNode = x.lb;
            }
        }

        Point2D firstNearestPoint = nearest(firstNode, p, nearestDistance);
        if (firstNearestPoint != null) {
            d = p.distanceTo(firstNearestPoint);
            if (d < nearestDistance) {
                nearestPoint = firstNearestPoint;
                nearestDistance = d;
            }
        }

        Point2D secondNearestPoint = nearest(secondNode, p, nearestDistance);
        if (secondNearestPoint != null) {
            d = p.distanceTo(secondNearestPoint);
            if (d < nearestDistance) {
                nearestPoint = secondNearestPoint;
                nearestDistance = d;
            }
        }

        return nearestPoint;
    }

    /*
    public String toString() {
        return root.toString();
    }
     */

    public static void main(String[] args) {
//        StdOut.println("hello world");
//        KdTree kdtree = new KdTree();
//        assert kdtree.size() == 0;
//        kdtree.insert(new Point2D(.7, .2));
//        assert kdtree.size() == 1;
//        kdtree.insert(new Point2D(.5, .4));
//        kdtree.insert(new Point2D(.2, .3));
//        kdtree.insert(new Point2D(.4, .7));
//        kdtree.insert(new Point2D(.9, .6));
//        assert kdtree.size() == 5;
        //StdOut.println(kdtree);

//        kdtree = new KdTree();
//        kdtree.insert(new Point2D(0.206107, 0.095492));
//        kdtree.insert(new Point2D(0.975528, 0.654508));
//        kdtree.insert(new Point2D(0.024472, 0.345492));
//        kdtree.insert(new Point2D(0.793893, 0.095492));
//        kdtree.insert(new Point2D(0.793893, 0.904508));
//        kdtree.insert(new Point2D(0.975528, 0.345492));
//        assert kdtree.size() == 6;
//        kdtree.insert(new Point2D(0.206107, 0.904508));
//        StdOut.println(kdtree);
//        assert kdtree.size() == 7;
//
//        StdOut.println("size: " + kdtree.size());

        /*
        StdDraw.show(0);
        StdDraw.setPenRadius(.02);
        kdtree.draw();
        StdDraw.show(0);
         */


        /*
        String filename;
        In in;
        // Test 1a: Insert N distinct points and check size() after each insertion
        // 100000 random distinct points in 100000-by-100000 grid
        filename = "kdtree/input100K.txt";
        in = new In(filename);
        kdtree = new KdTree();
        for (int i = 0; i < 100000; i++) {
            double x = in.readDouble();
            double y = in.readDouble();
        }
         */
//        KdTree2 a = new KdTree2();
//        Point2D b = new Point2D(0.1, 0.2);
//        a.insert(b);
//        Point2D c = new Point2D(0.2, 0.3);
//        a.insert(c);
//        Point2D d = new Point2D(0, 0.1);
//        a.insert(d);
//        Point2D e = new Point2D(0.5, 0.5);
//        a.insert(e);
//        Point2D f = new Point2D(0.2, 0.2);
//        a.insert(f);
//        System.out.println(a.size());
    }
}