import edu.princeton.cs.algs4.StdRandom;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Board is an immutable data type to encapsulate a 
 * N-by-N grid of the 8-puzzle board.
 * @author shershebnev
 */
public class Board {
    
    private final int[][] blocks;
    private int dim;
    
    /**
     * Initializes a new board
     * @param blocks array representation of board
     */
    public Board(int[][] blocks) {
        this.blocks = cloneArray(blocks);
        dim = blocks.length;
    }
    
    /**
     * Return the dimension of board
     * @return the dimension of board
     */
    public int dimension() {
        return dim;
    }
    
    /**
     * Return hamming distance - the number of blocks in the wrond position
     * @return hamming distance
     */
    public int hamming() {
        int distance = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (this.blocks[i][j] == 0) continue;
                if (!(this.blocks[i][j] == twoTo1D(i, j))) {
                    distance++;
                }
            }
        }
        return distance;
    }
    
    /**
     * Return manhattan distance - sum of distances from the blocks to their goal position
     * @return manhattan distance
     */
    public int manhattan() {
        int distance = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (this.blocks[i][j] == 0) continue;
                int[] idxFinal = oneTo2D(this.blocks[i][j]);
                distance += Math.abs(i - idxFinal[0]) + Math.abs(j - idxFinal[1]);
            }
        }
        return distance;
    }
    
    /**
     * Compare board to goal board
     * @return true if board is equal to goal board, false otherwise
     */
    public boolean isGoal() {
        return this.equals(finalBoard());
    }
    
    /**
     * Return the board with two adjacent random blocks exchanged
     * @return the board with two adjacent random blocks exchanged
     */
    public Board twin() {
        Board twin = new Board(this.blocks);
        boolean exchange = false;
        while (!exchange) {
            int i = StdRandom.uniform(dim);
            int j = StdRandom.uniform(dim);
            int ej = j;
            if (j == dim - 1) {
                --ej;
            } else {
                ++ej;
            }
            if (blocks[i][j] != 0 && blocks[i][ej] != 0) {
                int temp = twin.blocks[i][j];
                twin.blocks[i][j] = blocks[i][ej];
                twin.blocks[i][ej] = temp;
                exchange = true;
            }
        }
        return twin;
    }
    
    /**
     * Compare Object y to the board
     * @return true if Object y is equal to the board, false otherwise
     */
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.dimension() != that.dimension()) return false;
        if (!Arrays.deepEquals(this.blocks, that.blocks)) return false;
        return true;
    }
    
    /**
     * Return iterable containing all possible neighbors of the board.
     * Neighbor is obtained by exchanging empty block (represented as 0)
     * with adjacent block
     * @return iterable containing all possible neighbors
     */
    public Iterable<Board> neighbors() {
        ArrayList<Board> boards = new ArrayList<Board>();
        int[] zeroIndex = locateZero();
        if (zeroIndex[0] - 1 >= 0) {
            Board newBoard = new Board(cloneArray(this.blocks));
            newBoard.exch(zeroIndex[0], zeroIndex[1], zeroIndex[0] - 1, zeroIndex[1]);
            boards.add(newBoard);
        }
        if (zeroIndex[0] + 1 < dim) {
            Board newBoard = new Board(cloneArray(this.blocks));
            newBoard.exch(zeroIndex[0], zeroIndex[1], zeroIndex[0] + 1, zeroIndex[1]);
            boards.add(newBoard);
        }
        if (zeroIndex[1] - 1 >= 0) {
            Board newBoard = new Board(cloneArray(this.blocks));
            newBoard.exch(zeroIndex[0], zeroIndex[1], zeroIndex[0], zeroIndex[1] - 1);
            boards.add(newBoard);
        }
        if (zeroIndex[1] + 1 < dim) {
            Board newBoard = new Board(cloneArray(this.blocks));
            newBoard.exch(zeroIndex[0], zeroIndex[1], zeroIndex[0], zeroIndex[1] + 1);
            boards.add(newBoard);
        }
        return boards;
    }
    
    /**
     * Convert board to string row by row
     * @return string representation of board
     */
    public String toString() {
        StringBuffer results = new StringBuffer();
        String separator = " ";
        
        results.append(dim).append("\n");
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (j == dim - 1) {
                    results.append(this.blocks[i][j]).append("\n");
                } else {
                    results.append(this.blocks[i][j]).append(separator);
                }
            }
        }
        return results.toString();
    }
    
    private int[] oneTo2D(int idx) {
        // idx - 1 bc idx is not zero-based
        int i = (idx - 1) / dim;
        int j = (idx - 1) % dim;
        int[] coords = {i, j};
        return coords;
    }
    
    private int twoTo1D(int i, int j) {
        return i * dim + j + 1; // +1 - from zero to one-based indexing
    }
    
    private static int[][] cloneArray(int[][] src) {
        int length = src.length;
        int[][] target = new int[length][src[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(src[i], 0, target[i], 0, src[i].length);
        }
        return target;
    }
    
    private void exch(int iFirst, int jFirst, int iSecond, int jSecond) {
        int t = this.blocks[iFirst][jFirst];
        this.blocks[iFirst][jFirst] = this.blocks[iSecond][jSecond];
        this.blocks[iSecond][jSecond] = t;
    }
    
    private int[] locateZero() {
        int[] index = new int[2];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (this.blocks[i][j] != 0) continue;
                index[0] = i;
                index[1] = j;
            }
        }
        return index;
    }
    
    private Board finalBoard() {
        int[][] finalB = new int[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                finalB[i][j] = twoTo1D(i, j);
            }
        }
        finalB[dim - 1][dim - 1] = 0;
        return new Board(finalB);
    }
    public static void main(String[] args) {
        // int[][] a = { {1, 2, 3}, {4, 5, 6}, {7, 0, 8}};
        // Board b = new Board(a);
        // for (int i = 0; i < 20; i++){
        //     Board c = b.twin();
        //     System.out.print(c.hamming());
        // }
//        for (int i = 0; i < 10; i++) {
//            System.out.println(StdRandom.uniform(0, b.dim));
//        }
//        int[][] c = { {1, 2, 3}, {4, 5, 6}, {7, 0, 8}};
//        for (Board c : b.neighbors()) {
//            System.out.println(c.isGoal());
//        }
//        Board d = new Board(c);
//        System.out.println(d.equals(c));
    }
}
