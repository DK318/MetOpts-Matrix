package matrix;

import matrix.exception.MatrixException;
import utils.input.Scanner;

import java.io.IOException;

public class SkylineMatrix implements Matrix {
    private final int n;
    private final double[] di;
    private final double[] al;
    private final double[] au;
    private final int[] ia;

    public SkylineMatrix(Scanner scanner, int n) throws MatrixException {
        try {
            this.n = n;
            this.ia = new int[n + 2];
            this.di = new double[n + 1];

            double[][] lines = new double[n + 1][];
            int[] linesLast = new int[n + 1];
            double[][] columns = new double[n + 1][];
            int[] columnsLast = new int[n + 1];

            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    double el = scanner.nextDouble();
                    if (i == j) {
                        di[i] = el;
                    } else if (i > j) {
                        if (lines[i] == null && el != 0) {
                            lines[i] = new double[i - j];
                        }
                        if (lines[i] != null) {
                            lines[i][linesLast[i]++] = el;
                        }
                    } else {
                        if (columns[j] == null && el != 0) {
                            columns[j] = new double[j - i];
                        }
                        if (columns[j] != null) {
                            columns[j][columnsLast[j]++] = el;
                        }
                    }
                }
            }

            ia[1] = 1;
            ia[2] = 1;
            for (int i = 3; i < ia.length; i++) {
                int add = 0;
                if (lines[i - 1] != null) {
                    add = lines[i - 1].length;
                }
                ia[i] = ia[i - 1] + add;
            }

            this.al = new double[ia[ia.length - 1]];
            this.au = new double[ia[ia.length - 1]];
            int fromAl = 1;
            int fromAu = 1;
            for (int i = 2; i <= n; i++) {
                if (lines[i] != null) {
                    System.arraycopy(lines[i], 0, al, fromAl, lines[i].length);
                    fromAl += lines[i].length;
                }
                if (columns[i] != null) {
                    System.arraycopy(columns[i], 0, au, fromAu, columns[i].length);
                    fromAu += columns[i].length;
                }
            }
        } catch (IOException e) {
            throw new MatrixException("Cannot create matrix", e);
        }
    }

    @Override
    public double get(int i, int j) {
        if (i == j) {
            return di[i];
        } else if (i > j) {
            int profile = ia[i + 1] - ia[i];
            int start = i - profile;
            if (j < start) {
                return 0;
            } else {
                return al[ia[i] + j - start];
            }
        } else {
            int profile = ia[j + 1] - ia[j];
            int start = j - profile;
            if (i < start) {
                return 0;
            } else {
                return au[ia[j] + i - start];
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

    private void set(int i, int j, double val) {
        if (i == j) {
            di[i] = val;
        } else if (i > j) {
            int profile = ia[i + 1] - ia[i];
            int start = i - profile;
            if (j >= start) {
                al[ia[i] + j - start] = val;
            }
        } else {
            int profile = ia[j + 1] - ia[j];
            int start = j - profile;
            if (i >= start) {
                au[ia[j] + i - start] = val;
            }
        }
    }

    public static SkylineMatrix LUDecomposition(Scanner scanner, int n) throws MatrixException {
        SkylineMatrix matrix = new SkylineMatrix(scanner, n);
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
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
        return matrix;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                builder.append(get(i, j))
                        .append(" ");
            }
            builder.append('\n');
        }
        return builder.toString();
    }
}
