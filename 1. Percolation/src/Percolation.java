import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/*
 * Author - Shershebnev Aleksandr
 * Written - 10 September, 2015
 * Simulates a grid N-by-N, open cells and checks whether the system is percolating
 */
public class Percolation {
    private int dim; // grid dimension
    private int gridSize; // grid size
    private int virtualTop; // index of virtual top
    private int virtualBottom; // index of virtual bottom
    private boolean[] grid; // grid N-by-N
    private WeightedQuickUnionUF fullGrid;
    private WeightedQuickUnionUF unions; // array of unions
    
    public Percolation(int N) {
        dim = N;
        gridSize = N * N;
        if (N <= 0) throw new java.lang.IllegalArgumentException();
        
        grid = new boolean[gridSize]; // create N-by-N grid
        
        // array of unions, two extra elements are for virtual top and bottom
        unions = new WeightedQuickUnionUF(gridSize + 2);
        fullGrid = new WeightedQuickUnionUF(gridSize + 1);
        
        // virtual top and bottom indexes
        virtualTop = gridSize;
        virtualBottom = gridSize + 1;
    }
    
    // check validness of indexes, should be in range [1, dim]
    private void isValid(int i, int j) {
        if (i <= 0 || i > dim) throw new IndexOutOfBoundsException("row index " + i + " out of bounds");
        if (j <= 0 || j > dim) throw new IndexOutOfBoundsException("col index " + j + " out of bounds");
    }
    
    /*
     * check if cell is open based on its indexes in N-by-N array
     * 
     * @param (int) i row index 1-based
     * @param (int) j col index 1-based
     * 
     * @return (boolean) whether cell is open
     */
    public boolean isOpen(int i, int j) {
        isValid(i, j);
        return grid[xyto1D(i - 1, j - 1)];
    }
    
    /* 
     * check if cell is open based on its index in neighbors array
     * @param (int) index cell's index
     * 
     * @return (boolean) whether cell is open
     */
    private boolean isOpen(int index) {
        return grid[index];
    }
    
    // check if cell is filled in. Cell is filled in if it is connected to virtual top
    public boolean isFull(int i, int j) {
        // one-based
        isValid(i, j);
        return unions.connected(virtualTop, xyto1D(i - 1, j - 1)) && 
                fullGrid.connected(virtualTop, xyto1D(i - 1, j - 1));
    }
    
    // convert 2-dimensional indexes two one-dimensional
    private int xyto1D(int i, int j) {
        // zero-based
        return i * dim + j;
    }
    
    // open a cell, make union with neighboring cells, if they were open before
    public void open(int iOne, int jOne) {
        isValid(iOne, jOne);
        
        // for zero-based
        int i = iOne - 1;
        int j = jOne - 1;

        int index = xyto1D(i, j);
        
        if (!isOpen(iOne, jOne)) {
            grid[index] = true;
            
            // get indexes of neighboring cells, connects them, if open
            for (int d = 0; d < 4; d++) {
                int neighborIndex = getNeighborIndex(i, j, d);
                if (neighborIndex != -1 && isOpen(neighborIndex)) {
                    unions.union(index, neighborIndex);
                    fullGrid.union(index, neighborIndex);
                }
            }
            
            // connect to virtual top if in the first row
            if (i == 0) {
                unions.union(virtualTop, index);
                fullGrid.union(virtualTop, index);
            }
            
            // connect to virtual bottom if in the last row
            if (iOne == dim) {
                unions.union(virtualBottom, index);
            }
            
            // for 1 by 1 grid
            if (dim == 1) {
                unions.union(virtualBottom, index);
            }
        }
    }
    
    /*
     * return index of neighbor in given direction. Zero-based indexing
     * 
     * @param (int) i row index
     * @param (int) j col index
     * @param (int) direction direction of neighbor:
     *          0 = UP, 1 = DOWN, 2 = RIGHT, 3 = LEFT
     * 
     * @return (int) neighbor's index or -1 if out of bound                 
     */
    private int getNeighborIndex(int i, int j, int direction) {
        if (direction < 0 || direction > 3) {
            throw new IndexOutOfBoundsException("direction must be in range [0, 3], but given " + direction);
        }
        switch (direction) {
            case 0: // UP
                if (i == 0) {
                    return -1;
                }
                return xyto1D(i - 1, j);
            case 1: // DOWN
                if (i + 1 == dim) {
                    return -1;
                }
                return xyto1D(i + 1, j);
            case 2: // RIGHT
                if (j + 1 == dim) {
                    return -1;
                }
                return xyto1D(i, j + 1);
            case 3: //LEFT
                if (j == 0) {
                    return -1;
                }
            return xyto1D(i, j - 1);
        }
        return -1;
    }
    
    // check if system percolates, i.e. virtual top and bottom are connected
    public boolean percolates() {
        return unions.connected(virtualTop, virtualBottom);
    }
    
    private void main(String[] args) {
//        Percolation a = new Percolation(5);
//        a.open(3, 1);
//        a.open(4, 1);
//        a.open(5, 1);
//        a.open(5, 2);
//        a.open(3, 3);
//        a.open(3, 4);
//        a.open(2, 4);
//        a.open(1, 4);
//        a.open(3, 2);
//        System.out.println(a.percolates());
//        System.out.println(a.isFull(4, 4));
//        a.open(4, 4);
//        System.out.println(a.percolates());
//        System.out.println(a.isFull(2, 1));
       
    }
}