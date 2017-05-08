import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import java.util.ArrayList;

public class SAP {
    /** 
     * Short ancestral path class
     */
    
    private Digraph G;
    private int graphSize;
    
    public SAP(Digraph G) {
        if (G == null) throw new NullPointerException();
        
        this.G = new Digraph(G);
        graphSize = this.G.V();
    }    
    
    public int length(int v, int w) {
        if ((v < 0 || v > graphSize) || (w < 0 || w > graphSize)) throw new IndexOutOfBoundsException();
        
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(this.G, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(this.G, w);
        
        int ancestor = ancestor(bfsV, bfsW);
        if (ancestor != -1) {
            return (bfsV.distTo(ancestor) + bfsW.distTo(ancestor));
        } else {
            return -1;
        }
    }
    
    private int ancestor(BreadthFirstDirectedPaths bfsV, BreadthFirstDirectedPaths bfsW) {
        ArrayList<Integer> ancestors = new ArrayList<Integer>();
        for (int i = 0; i < graphSize; i++) {
            if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                ancestors.add(i);
            }
        }
        if (ancestors.size() == 0) {
            return -1;
        }
        int shortestPath = Integer.MAX_VALUE;
        int closestAncestor = -1;
        for (Integer i : ancestors) {
            int distance = bfsV.distTo(i) + bfsW.distTo(i);
            if (distance < shortestPath) {
                shortestPath = distance;
                closestAncestor = i;
            }
        }
        return closestAncestor;
    }
    public int ancestor(int v, int w) {
        if ((v < 0 || v > graphSize) || (w < 0 || w > graphSize)) throw new IndexOutOfBoundsException();
        
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(this.G, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(this.G, w);
        
        return ancestor(bfsV, bfsW);
    }
    
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new NullPointerException();
        
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(this.G, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(this.G, w);
        
        int ancestor = ancestor(bfsV, bfsW);
        if (ancestor != -1) {
            return (bfsV.distTo(ancestor) + bfsW.distTo(ancestor));
        } else {
            return -1;
        }
    }
    
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new NullPointerException();
        
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(this.G, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(this.G, w);
        
        return ancestor(bfsV, bfsW);
    }
    
    public static void main(String[] args) {
//        In in = new In("/Users/shershebnev/Documents/Code/Java/WordNet/wordnet/digraph1.txt");
//        Digraph G = new Digraph(in);
//        SAP sap = new SAP(G);
////        while (!StdIn.isEmpty()) {
//        int v = 1;
//        int w = 6;
////            int v = StdIn.readInt();
////            int w = StdIn.readInt();
//        int length   = sap.length(v, w);
//        System.out.println(length);
//        int ancestor = sap.ancestor(v, w);
//        System.out.println(ancestor);
    }
}

