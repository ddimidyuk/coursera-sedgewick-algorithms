import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private Percolation percolation;
    private double[] thresholds;
    private double k = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException("This is out of bounds");
        else {
            thresholds = new double[trials];
            for (int i = 0; i < trials; i++) {
                percolation = new Percolation(n);
                int openSites = 0;
                // loop through until Percolation object percolates
                while (!percolation.percolates()) {

                    int randomI = StdRandom.uniform(n) + 1;
                    int randomJ = StdRandom.uniform(n) + 1;

                    if (randomI > n) {
                        randomI = randomI - 1;
                    }
                    if (randomJ > n) {
                        randomJ = randomJ - 1;
                    }
                    if (!percolation.isOpen(randomI, randomJ)) {
                        percolation.open(randomI, randomJ);
                        openSites++;
                    }
                    if (percolation.isFull(randomI, randomJ)) {
                        percolation.percolates();
                    }
                }
                // store percolation thresholds in data array
                thresholds[i] = (openSites / (double) (n * n));
            }
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((k * stddev()) / Math.sqrt(thresholds.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((k * stddev()) / Math.sqrt(thresholds.length));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, t);
        ps.printStats();
    }

    private void printStats() {
        System.out.println("mean  = " + mean());
        System.out.println("stddev = " + stddev());
        System.out.println("95% confidence interval  = " + confidenceLo() + ", " + confidenceHi());
    }
}