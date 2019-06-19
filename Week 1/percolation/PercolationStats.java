/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] x;

    public PercolationStats(int n, int trials)     // perform trials ind-t exp-s on an nxn grid
    {
        this.x = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            boolean cond = false;
            int t = 1;
            while (!cond) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                perc.open(row, col);
                if (perc.percolates()) {
                    x[i] = (double) t / (double) n;
                    cond = true;
                }
                else
                    t += 1;
            }
        }

        double m = mean(x);
        double std = stddev(x);
        double low = confidenceLo(m, std, trials);
        double high = confidenceHi(m, std, trials);
        System.out.println(String.format("mean                    = %f", m));
        System.out.println(String.format("stddev                  = %f", std));
        System.out.println(String.format("95%% confidence interval = [%f, %f]", low, high));

    }


    private boolean isvalid(int n, int trials) {
        if (n > 0 && trials > 0)
            return true;
        else {
            throw new IllegalArgumentException(
                    String.format("n:%d trials:%d", n, trials));
        }
    }

    public double mean(double[] arr)    // sample mean of percolation threshold
    {
        return StdStats.mean(arr);
    }

    public double stddev(double[] arr)  // sample standard deviation of percolation threshold
    {
        return StdStats.stddev(arr);
    }

    public double confidenceLo(double m, double std, int n)     // low  endpoint of 95% conf int
    {
        return m - 1.96 * std / Math.sqrt(n);
    }

    public double confidenceHi(double m, double std, int n)     // high endpoint of 95% conf int
    {
        return m + 1.96 * std / Math.sqrt(n);
    }

    public static void main(String[] args) {
        PercolationStats percolationStats1 = new PercolationStats(200, 100);
        PercolationStats percolationStats2 = new PercolationStats(200, 100);
    }
}
