package lssolvers;

import matrix.SkylineMatrix;

public class LUSolver {
    private LUSolver() {
        // Only static method solve
    }

    public static double[] solve(SkylineMatrix matrix, double[] b) {
        int n = matrix.getN();

        double[] y = new double[n + 1];
        for (int i = 1; i <= n; i++) {
            double sum = 0;
            for (int p = 1; p <= i - 1; p++) {
                sum += matrix.get(i, p) * y[p];
            }
            y[i] = b[i] - sum;
        }

        double[] x = new double[n + 1];
        for (int i = 0; i < n; i++) {
            double sum = 0;
            for (int p = 0; p <= i - 1; p++) {
                sum += matrix.get(n - i, n - p) * x[n - p];
            }
            x[n - i] = (y[n - i] - sum) / matrix.get(n - i, n - i);
        }

        return x;
    }
}