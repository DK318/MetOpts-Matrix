package lssolvers;

import matrix.AbstractMatrix;
import matrix.Matrix;

import java.util.Arrays;

public class GaussSolver {
    private GaussSolver() {
        // Only static method solve
    }

    public static int solve(Matrix denseMatrix, double[] b, double[] result, double eps) {
        Matrix matrix = new GaussMatrix(denseMatrix, b);

        int n = matrix.getN();
        int m = matrix.getM() - 1;

        int[] where = new int[m + 1];
        Arrays.fill(where, -1);

        for (int column = 1, row = 1; column <= m && row <= n; column++) {
            int sel = row;
            for (int i = row; i <= n; i++) {
                if (Math.abs(matrix.get(i, column)) > Math.abs(matrix.get(sel, column))) {
                    sel = i;
                }
            }
            if (Math.abs(matrix.get(sel, column)) < eps) {
                continue;
            }
            for (int i = column; i <= m + 1; i++) {
                double tmp = matrix.get(sel, i);
                matrix.set(sel, i, matrix.get(row, i));
                matrix.set(row, i, tmp);
            }
            where[column] = row;
            for (int i = 1; i <= n; i++) {
                if (i != row) {
                    double c = matrix.get(i, column) / matrix.get(row, column);
                    for (int j = column; j <= m + 1; j++) {
                        double sub = matrix.get(i, j) - matrix.get(row, j) * c;
                        matrix.set(i, j, sub);
                    }
                }
            }
            row++;
        }

        for (int i = 1; i <= m; i++) {
            if (where[i] != -1) {
                result[i] = matrix.get(where[i], m + 1) / matrix.get(where[i], i);
            }
        }

        for (int i = 1; i <= n; i++) {
            double sum = 0;
            for (int j = 1; j <= m; j++) {
                sum += result[j] * matrix.get(i, j);
            }
            if (Math.abs(sum - matrix.get(i, m + 1)) > eps) {
                return 0;
            }
        }

        for (int i = 1; i <= m; i++) {
            if (where[i] == -1) {
                return -1;
            }
        }

        return 1;
    }

    private static class GaussMatrix extends AbstractMatrix {
        private final Matrix matrix;
        private final double[] b;

        public GaussMatrix(Matrix matrix, double[] b) {
            this.matrix = matrix;
            this.b = b;
        }

        @Override
        public double get(int i, int j) {
            if (j > matrix.getM()) {
                return b[i];
            } else {
                return matrix.get(i, j);
            }
        }

        @Override
        public void set(int i, int j, double val) {
            if (j > matrix.getM()) {
                b[i] = val;
            } else {
                matrix.set(i, j, val);
            }
        }

        @Override
        public int getN() {
            return matrix.getN();
        }

        @Override
        public int getM() {
            return matrix.getM() + 1;
        }
    }
}
