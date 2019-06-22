/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int n;
    private int T;
    private double[] x;
    private double mean;
    private double std;

    public PercolationStats(int n, int trials)     // perform trials ind-t exp-s on an nxn grid
    {
        isvalid(n, trials);
        this.n = n;
        this.T = trials;
        this.x = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            boolean cond = false;
            int t = 1;
            while (!cond) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                if (perc.isOpen(row, col))
                    continue;
                perc.open(row, col);
                if (perc.percolates()) {
                    x[i] = (double) t / (n * n);
                    cond = true;
                }
                else
                    t += 1;
            }
        }

        this.mean = mean();
        this.std = stddev();
        double low = confidenceLo();
        double high = confidenceHi();

    }


    private void isvalid(int grid, int trials) {
        if (grid <= 0 || trials <= 0) {
            throw new IllegalArgumentException(
                    String.format("n:%d trials:%d", n, trials));
        }
    }

    public double mean()    // sample mean of percolation threshold
    {
        return StdStats.mean(x);
    }

    public double stddev()  // sample standard deviation of percolation threshold
    {
        return StdStats.stddev(x);
    }

    public double confidenceLo()     // low  endpoint of 95% conf int
    {
        return mean - 1.96 * std / Math.sqrt(T);
    }

    public double confidenceHi()     // high endpoint of 95% conf int
    {
        return mean + 1.96 * std / Math.sqrt(T);
    }

    public static void main(String[] args) {
        PercolationStats percolationStats1 = new PercolationStats(200, 100);
        System.out.println(String.format("mean                    = %f", percolationStats1.mean));
        System.out.println(String.format("stddev                  = %f", percolationStats1.std));
        System.out.println(String.format("95%% confidence interval = [%f, %f]",
                                         percolationStats1.confidenceLo(),
                                         percolationStats1.confidenceHi()));

        PercolationStats percolationStats2 = new PercolationStats(200, 100);
        System.out.println(String.format("mean                    = %f", percolationStats2.mean));
        System.out.println(String.format("stddev                  = %f", percolationStats2.std));
        System.out.println(String.format("95%% confidence interval = [%f, %f]",
                                         percolationStats2.confidenceLo(),
                                         percolationStats2.confidenceHi()));
    }
}
