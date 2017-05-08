import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/*
 * Author - Shershebnev Aleksandr
 * Written - 10 September, 2015
 * Generates statistics of T percolation experiments on N-by-N grid
 */
public class PercolationStats {
    private int dim; // grid dimension
    private int experimentAmount; // number of experiments
    private double[] experimentResults; // results of each experiment
    
    public PercolationStats(int N, int T) {
        if (N <= 0) throw new java.lang.IllegalArgumentException("N should be > 0");
        if (T <= 0) throw new java.lang.IllegalArgumentException("T should be > 0");
        experimentAmount = T;
        experimentResults = new double[T];
        dim = N;
        
        // Monte Carlo simulations
        for (int i = 0; i < experimentAmount; i++) {
            Percolation percol = new Percolation(dim);
            double count = 0.0;
            while (!percol.percolates()) {
                int j = StdRandom.uniform(1, dim + 1);
                int k = StdRandom.uniform(1, dim + 1);
                while (percol.isOpen(j, k)) {
                    j = StdRandom.uniform(1, dim + 1);
                    k = StdRandom.uniform(1, dim + 1);                    
                }
                percol.open(j, k);
                count++;
            }
            // return percentage of open sites
            experimentResults[i] = count / (dim * dim);
        }
    }
    
    // return results
    private double[] getResults() {
        return experimentResults;
    }
    
    // return mean of simulations
    public double mean() {
        return StdStats.mean(experimentResults);
    }
    
    // return standard deviation of simulations
    public double stddev() {
        return StdStats.stddev(experimentResults);
    }
    
    // return low endpoint of 95% confidence interval
    public double confidenceLo() {
        return (mean() - 1.96 * stddev() / Math.sqrt(experimentAmount));
    }
    
    // return high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (mean() + 1.96 * stddev() / Math.sqrt(experimentAmount));
    }
    
    public static void main(String[] args) {
        int N = StdIn.readInt();
        int T = StdIn.readInt();
        PercolationStats a = new PercolationStats(20, 10);
        double m = a.mean();
        double s = a.stddev();
        double lo = a.confidenceLo();
        double hi = a.confidenceHi();
        StdOut.println("mean = " + m);
        StdOut.println("stddev = " + s);
        StdOut.println("95% confidence interval = " + lo + ", " + hi);        
    }
}
