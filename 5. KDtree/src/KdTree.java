import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
//import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import java.util.ArrayList;

public class KdTree {
    
    private Node root;
    private int size;
    
    private class Node {
        private Node left, right;
        private Point2D point;
        private boolean vertical;
        private RectHV rect;
        
        public Node(Point2D point, boolean vertical, RectHV rect) {
            this.point = point;
            this.vertical = vertical;
            this.rect = rect;
        }
    }
    
    public KdTree() {
        root = null;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public int size() {
        return size;
    }
    
    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException();
        if (isEmpty()) {
            root = insertRoot(p);
        } else { 
            root = insert(root, p, true, root.rect, root.point, true);
        }
    }
    
    private Node insertRoot(Point2D p) {
        size++;
        return new Node(p, true, new RectHV(0, 0, 1, 1));
    }
    
    private Node insert(Node node, Point2D p, boolean compChoice, RectHV parentRect, Point2D parentP, boolean left) {
        if (node == null) {
            size++;
            if (compChoice) {
                if (left) {
                    return new Node(p, compChoice, new RectHV(parentRect.xmin(), parentRect.ymin(), 
                            parentRect.xmax(), parentP.y())); //LV
                } else {
                    return new Node(p, compChoice, new RectHV(parentRect.xmin(), parentP.y(), 
                            parentRect.xmax(), parentRect.ymax())); //RV
                }
            } else {
                if (left) {
                    return new Node(p, compChoice, new RectHV(parentRect.xmin(), parentRect.ymin(), 
                            parentP.x(), parentRect.ymax())); //LH
                } else {
                    return new Node(p, compChoice, new RectHV(parentP.x(), parentRect.ymin(), 
                            parentRect.xmax(), parentRect.ymax())); //RH
                }
            }            
        }
        int compare = compare(p, node.point, compChoice);
        if (compare < 0) {
            node.left = insert(node.left, p, !compChoice, node.rect, node.point, true);
        }
        else {
//        else if (compare > 0) {
            node.right = insert(node.right, p, !compChoice, node.rect, node.point, false);
        }
//        else {
//            node.point = p;
//        }
        return node;
    }
    
    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        return contains(root, p, true);
    }
    
    private boolean contains(Node node, Point2D p, boolean compChoice) {
        if (node == null) return false;
        int cmp = compare(p, node.point, compChoice);
        if (cmp < 0) return contains(node.left, p, !compChoice);
        else if (cmp > 0) return contains(node.right, p, !compChoice);
        else return true;
    }
    
    private int compare(Point2D p1, Point2D p2, boolean vertical) {
        int compX = Double.compare(p1.x(), p2.x());
        int compY = Double.compare(p1.y(), p2.y());
        if (vertical) {
            if (compX < 0) return -1;
            else return 1;
        } else {
            if (compY < 0) return -1;
            else return 1;
        }
//        if (vertical) {
//            if (compX < 0 || (compX == 0 && compY < 0)) return -1;
//            else if (compX == 0 && compY == 0) return 0;
//            else if (compX > 0 || (compX == 0 && compY > 0)) return 1;
//        } else {
//            if (compY < 0 || (compY == 0 && compX < 0)) return -1;
//            else if (compX == 0 && compY == 0) return 0;
//            else if (compY > 0 || (compY == 0 && compX > 0)) return 1;
//        }
//        return 0;
    }
    public void draw() {
        draw(root);
    }
    
    private void draw(Node node) {
        if (node == null) return;
        if (node.vertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.006);
            StdDraw.line(node.point.x(), node.rect.ymin(), node.point.x(), node.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius(0.006);
            StdDraw.line(node.rect.xmin(), node.point.y(), node.rect.xmax(), node.point.y());
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.point.draw();
        draw(node.left);
        draw(node.right);
    }
    
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();
        ArrayList<Point2D> pointsList = new ArrayList<Point2D>();
        range(rect, root, pointsList);
        return pointsList;
    }
    
    private void range(RectHV rect, Node node, ArrayList<Point2D> pointsList) {
        if (node == null) return;
        if (rect.contains(node.point)) pointsList.add(node.point);
        if (rect.intersects(node.rect)) {
            range(rect, node.left, pointsList);
        }
        if (rect.intersects(node.rect)) {
            range(rect, node.right, pointsList);
        }
    }
    
    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException();
        return nearest(root, p, Double.POSITIVE_INFINITY);
    }
    
    private Point2D nearest(Node node, Point2D p, double distance) {
        if (node == null) return null;
        if (node.rect.distanceSquaredTo(p) > distance) return null;
        
        Point2D nearestPoint = null;
        double nearestDistance = distance;
        double d;
        
        d = p.distanceSquaredTo(node.point);
        if (d < nearestDistance) {
            nearestPoint = node.point;
            nearestDistance = d;
        }
        
        Node firstNode = node.left;
        Node secondNode = node.right;
        
        if (firstNode != null && secondNode != null) {
            if (firstNode.rect.distanceTo(p) > secondNode.rect.distanceTo(p)) {
                firstNode = node.right;
                secondNode = node.left;
            }
        }
        
        Point2D firstNearestPoint = nearest(firstNode, p, nearestDistance);
        if (firstNearestPoint != null) {
            d = p.distanceSquaredTo(firstNearestPoint);
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
//        double distP = p.distanceSquaredTo(node.point);

//        if (distP < distance) {
//            distance = distP;
////            rectDistance = distR;
//            nearestPoint = node.point;
//        }
//        Node firstNode = node.left;
//        Node secondNode = node.right;
//        if (firstNode != null && secondNode != null) {
//            if (firstNode.rect.distanceSquaredTo(p) > secondNode.rect.distanceSquaredTo(p)) {
//                firstNode = node.right;
//                secondNode = node.left;
//            }
//        }
//        
//        Point2D firstNearestPoint = nearest(firstNode, p);
//        if (firstNearestPoint != null) {
//            distP = p.distanceSquaredTo(firstNearestPoint);
//            if (distP < distance) {
//                nearestPoint = firstNearestPoint;
//                distance = distP;
//            }
//        }
//        
//        Point2D secondNearestPoint = nearest(secondNode, p);
//        if (secondNearestPoint != null) {
//            distP = p.distanceSquaredTo(secondNearestPoint);
//            if (distP < distance) {
//                nearestPoint = secondNearestPoint;
//                distance = distP;
//            }
//        }
//        return nearestPoint;
//        double dist = p.distanceTo(node.point);
//        RectHV leftRectangle = leftRect(node);
//        RectHV rightRectangle = rightRect(node);
//        double distLeft = leftRectangle.distanceTo(p);
//        double distRight = rightRectangle.distanceTo(p);
//        int cmp = Double.compare(distLeft, distRight);
//        if (cmp < 0) {
//            if (dist < distance && distLeft < rectDistance) {
//                distance = dist;
//                rectDistance = distLeft;
//                nearestPoint = node.point;
//                nearest(node.left, p);
//                nearest(node.right, p);
//            } else return;
//        } else {
//            if (dist < distance && distRight < rectDistance) {
//                distance = dist;
//                rectDistance = distRight;
//                nearestPoint = node.point;
//                nearest(node.right, p);
//                nearest(node.left, p);
//            } else return;
//        }
    }
    
    public static void main(String[] args) {
//        String filename = args[0];
//        In in = new In(filename);
//
//        StdDraw.show(0);
//
//        // initialize the two data structures with point from standard input
//        KdTree kdtree = new KdTree();
//        while (!in.isEmpty()) {
//            double x = in.readDouble();
//            double y = in.readDouble();
//            Point2D p = new Point2D(x, y);
//            kdtree.insert(p);
//        }
//        kdtree.draw();
//        StdDraw.show();
//        KdTree a = new KdTree();
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
        
//        KdTree a = new KdTree();
//        a.insert(new Point2D(0.206107, 0.095492));
//        a.insert(new Point2D(0.975528, 0.654508));
//        a.insert(new Point2D(0.024472, 0.345492));
//        a.insert(new Point2D(0.793893, 0.095492));
//        a.insert(new Point2D(0.793893, 0.904508));
//        a.insert(new Point2D(0.975528, 0.345492));
//        a.insert(new Point2D(0.206107, 0.904508));
//        a.insert(new Point2D(0.500000, 0.000000));
//        a.insert(new Point2D(0.024472, 0.654508));
//        a.insert(new Point2D(0.500000, 1.000000));
//        a.draw();
//        Point2D g = new Point2D(0.81, 0.30);
//        Point2D h = a.nearest(g);
//        System.out.println(h);
        
//        Point2D f = new Point2D(0.975528, 0.345492);
//        Point2D d = new Point2D(0.793893, 0.095492);
//        Point2D g = new Point2D(0.81, 0.30);
        
//        System.out.println("Distance f - g is " + g.distanceTo(f));
//        System.out.println("Distance d - g is " + g.distanceTo(d));
//        System.out.println(a.isEmpty());
//        System.out.println(a.size());
//        a.insert(new Point2D(1, 1));
//        System.out.println(a.size());
//        a.insert(new Point2D(0, 3));
//        System.out.println(a.size());
//        a.insert(new Point2D(11, 2));
//        System.out.println(a.size());
//        Point2D x = new Point2D(9, 9);
//        a.insert(x);
//        System.out.println(a.size());
//        a.insert(new Point2D(4, 5));
//        System.out.println(a.size());
//        a.insert(new Point2D(3, 1));
//        System.out.println(a.size());
//        a.insert(new Point2D(11, 11));
//        System.out.println(a.size());
//        a.insert(new Point2D(0, 0));
//        System.out.println(a.size());
//        a.insert(new Point2D(11, 10));
//        System.out.println(a.isEmpty());
//        System.out.println(a.size());
//        Point2D h = new Point2D(0.1, 0.2);
//        boolean g = a.contains(h);
//        System.out.println(g);
//        StdDraw.show();
//        StdDraw.setXscale(0, 50);
//        StdDraw.setYscale(0, 50);
//        StdDraw.setPenColor(StdDraw.BLUE);
//        StdDraw.setPenRadius(0.01);
//        a.draw();
//        StdDraw.show();
//        Point2D g = new Point2D(0.81, 0.30);
//        Point2D h = a.nearest(g);
//        System.out.println(h);
//        System.out.println(a.nearestCount);
//        System.out.println(c);
//        Point2D b = new Point2D(1, 2);
//        a.insert(b);
//        Point2D c = new Point2D(2, 3);
//        a.insert(c);
//        Point2D d = new Point2D(0, 1);
//        a.insert(d);
//        Point2D e = new Point2D(10, 10);
//        a.insert(e);
//        Point2D f = new Point2D(1, 3);
//        a.insert(f);
//        RectHV rect = new RectHV(0.1, 0.1, 0.5, 0.5);
//        [0.8571099999, 0.8571100001] x [4.99999E-5, 5.00001E-5]
//        RectHV rect = new RectHV(0.199999999999, 0.199999999999, 0.2000000000001, 0.2000000000001);
//        System.out.println(a.size());
//        System.out.println(a.contains(new Point2D(100, 100)));
//        for (Point2D p : a.range(rect)) {
//            System.out.println(p);
//        }
    }
}