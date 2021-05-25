package matrix;

import matrix.exception.MatrixException;
import util.input.Scanner;

import java.io.IOException;

public class DenseMatrix extends AbstractMatrix {
    private final double[][] data;
    private final int n;
    private final int m;

    public DenseMatrix(Scanner scanner, int n, int m) throws MatrixException {
        this.n = n;
        this.m = m;
        this.data = new double[n + 1][m + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                try {
                    this.data[i][j] = scanner.nextDouble();
                } catch (IOException e) {
                    throw new MatrixException("Cannot create matrix", e);
                }
            }
        }
    }

    @Override
    public double get(int i, int j) {
        return data[i][j];
    }

    @Override
    public void set(int i, int j, double val) {
        data[i][j] = val;
    }

    @Override
    public int getN() {
        return n;
    }

    @Override
    public int getM() {
        return m;
    }
}
