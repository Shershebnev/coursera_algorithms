import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Comparator;

public class Solver {
    
    private Board initial;
    private SearchNode root;
    private MinPQ<SearchNode> boardPQ;
    private boolean solvable;
    private boolean isSolved = false;
    private SearchNode lastNode;
    private HashSet<String> boardSet = new HashSet<String>();
    
    /**
     * Find a solution to the initial board
     * @param initial initial board
     * @throws NullPointerException if initial is null
     */
    public Solver(Board initial) {
        boardPQ = new MinPQ<SearchNode>();
        this.initial = initial;
        root = new SearchNode();
        root.board = this.initial;
        root.previousNode = null;
        root.numberOfMoves = 0;
        boardPQ.insert(root);
        
        while (!isSolved) {
            SearchNode minBoard = boardPQ.delMin();
            if (minBoard.board.isGoal()) {
                isSolved = true;
                lastNode = minBoard;
                solvable = true;
                break;
            }
            boardSet.add(minBoard.board.toString());
            for (Board b : minBoard.board.neighbors()) {
                SearchNode c = new SearchNode();
                c.board = b;
                c.numberOfMoves = minBoard.numberOfMoves + 1;
                c.previousNode = minBoard;
                if (b.isGoal()) {
                    isSolved = true;
                    lastNode = c;
                    solvable = true;
                    break;
                }
                if (boardSet.add(c.board.toString())) {
                    boardPQ.insert(c);
                }
            }
            
            if (isSolved) continue;
            
            Board twinBoard = minBoard.board.twin();
            if (twinBoard.isGoal()) {
                isSolved = true;
                solvable = false;
                lastNode = null;
                break;
            }
            if (isSolved) continue;
            for (Board b : twinBoard.neighbors()) {
                if (b.isGoal()) {
                    isSolved = true;
                    lastNode = null;
                    solvable = false;
                    break;
                }
            }
        }
    }

    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int numberOfMoves;
        private SearchNode previousNode;
        
        public Comparator<SearchNode> priority() {
            return new Priority();
        }
        
        public int compareTo(SearchNode that) {
            int thisManh = this.board.manhattan() + this.numberOfMoves;
            int thatManh = that.board.manhattan() + that.numberOfMoves;
            if (thisManh == thatManh) {
                return 0;
            } else if (thisManh < thatManh) {
                return -1;
            }
            return 1;
        }
        
        private class Priority implements Comparator<SearchNode> {
            public int compare(SearchNode sn1, SearchNode sn2) {
                int sn1Manhattan = sn1.board.manhattan() + sn1.numberOfMoves;
                int sn2Manhattan = sn2.board.manhattan() + sn2.numberOfMoves;
                return Integer.compare(sn1Manhattan, sn2Manhattan);
            }
        }
    }
    
    /**
     * Check if board solvable
     * @return true if solvable
     */
    public boolean isSolvable() {
        return solvable;
    }
    
    /**
     * Return min number of moves to solve initial board.
     * Return -1 if unsolvable
     * @return min number of moves to solve initial board or -1 if unsolvable
     */
    public int moves() {
        if (!isSolvable()) return -1;
        if (isSolved) return lastNode.numberOfMoves;
        return boardPQ.min().numberOfMoves;
    }
    
    /**
     * Return shortest solution
     * @return sequence of boards in shortest solution
     */
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        ArrayList<Board> solutions = new ArrayList<Board>();
        solutions.add(lastNode.board);        
        SearchNode prev = lastNode.previousNode;
        while (prev != null) {
            solutions.add(prev.board);
            prev = prev.previousNode;
        }
        ArrayList<Board> reverseSolutions = new ArrayList<Board>();
        int i = solutions.size() - 1;
        while (i >= 0) {
            reverseSolutions.add(solutions.get(i));
            i--;
        }
        return reverseSolutions;
    }
    
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
//     public static void main(String[] args) {
////         int[][] a = { {1, 2, 3}, {4, 5, 6}, {7, 8, 0} };
// //        int[][] a = { {1, 2, 3}, {4, 5, 6}, {8, 7, 0} }; //unsolvable
// //        int[][] a = { {5, 6, 8}, {3, 2, 1}, {0, 4, 7} };
// //        int[][] a = { {0, 1, 3}, {4, 2, 5}, {7, 8, 6} };
// //        int[][] a = { {1, 2, 3}, {0, 7, 6}, {5, 4, 8} };
//         int[][] a = { {2, 3, 4}, {1, 8, 0}, {6, 7, 5} };
//         Board b = new Board(a);
//         Solver c = new Solver(b);
//         System.out.println(b.isGoal());
//         System.out.println(c.isSolvable());
//         System.out.println(c.moves());
//         System.out.println(c.solution());
//     }
}
