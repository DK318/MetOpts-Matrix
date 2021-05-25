package utils.decomposers;

import matrix.Matrix;

public class LUDecomposer {
    private LUDecomposer() {
    }

    public static void decompose(Matrix matrix) {
        for (int i = 1; i <= matrix.getN(); i++) {
            for (int j = 1; j <= matrix.getN(); j++) {
                double sum = 0;
                if (i <= j) {
                    for (int k = 1; k <= i - 1; k++) {
                        sum += matrix.get(i, k) * matrix.get(k, j);
                    }
                    double aij = matrix.get(i, j);
                    matrix.set(i, j, aij - sum);
                } else {
                    for (int k = 1; k <= j - 1; k++) {
                        sum += matrix.get(i, k) * matrix.get(k, j);
                    }
                    double aij = matrix.get(i, j);
                    double ujj = matrix.get(j, j);
                    matrix.set(i, j, (aij - sum) / ujj);
                }
            }
        }
    }
}
