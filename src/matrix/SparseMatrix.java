package matrix;

import matrix.exception.MatrixException;
import util.input.Scanner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class SparseMatrix extends AbstractMatrix {
    private final int n;
    private final double[] di;
    private final double[] al;
    private final double[] au;
    private final int[] ja;
    private final int[] ia;

    public SparseMatrix(Scanner scanner, int n) throws MatrixException {
        try {
            this.n = n;
            this.ia = new int[n + 2];
            this.di = new double[n + 1];

            List<List<Double>> lines = new ArrayList<>();
            fill(lines, n + 1);
            List<List<Integer>> indices = new ArrayList<>();
            fill(indices, n + 1);
            List<List<Double>> columns = new ArrayList<>();
            fill(columns, n + 1);

            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    double el = scanner.nextDouble();
                    if (i == j) {
                        di[i] = el;
                    } else if (i > j) {
                        if (el != 0) {
                            indices.get(i).add(j);
                            lines.get(i).add(el);
                        }
                    } else {
                        if (el != 0) {
                            columns.get(j).add(el);
                        }
                    }
                }
            }

            ia[1] = 1;
            ia[2] = 1;
            for (int i = 3; i < ia.length; i++) {
                int add = lines.get(i - 1).size();
                ia[i] = ia[i - 1] + add;
            }

            this.al = new double[ia[n + 1]];
            this.au = new double[ia[n + 1]];
            this.ja = new int[ia[n + 1]];

            int posAl = 1, posAu = 1, posJa = 1;
            for (int i = 1; i <= n; i++) {
                for (double el : lines.get(i)) {
                    al[posAl++] = el;
                }
                for (double el : columns.get(i)) {
                    au[posAu++] = el;
                }
                for (int el : indices.get(i)) {
                    ja[posJa++] = el;
                }
            }
        } catch (IOException e) {
            throw new MatrixException("Cannot create matrix", e);
        }
    }

    private <T> void fill(List<List<T>> list, int k) {
        IntStream.range(0, k).forEach(i -> list.add(new ArrayList<>()));
    }

    @Override
    public double get(int i, int j) {
        if (i == j) {
            return di[i];
        } else if (i > j) {
            int from = ia[i];
            int to = ia[i + 1];
            int pos = Arrays.binarySearch(ja, from, to, j);
            return pos < 0 ? 0 : al[pos];
        } else {
            int from = ia[j];
            int to = ia[j + 1];
            int pos = Arrays.binarySearch(ja, from, to, i);
            return pos < 0 ? 0 : au[pos];
        }
    }

    @Override
    public void set(int i, int j, double val) {
        if (i == j) {
            di[i] = val;
        } else if (i > j) {
            int from = ia[i];
            int to = ia[i + 1];
            int pos = Arrays.binarySearch(ja, from, to, j);
            if (pos > 0) {
                al[pos] = val;
            }
        } else {
            int from = ia[j];
            int to = ia[j + 1];
            int pos = Arrays.binarySearch(ja, from, to, i);
            if (pos > 0) {
                au[pos] = val;
            }
        }
    }

    @Override
    public int getN() {
        return n;
    }

    @Override
    public int getM() {
        return n;
    }
}
