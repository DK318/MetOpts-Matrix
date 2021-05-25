package util.generator.table;

import java.util.Arrays;

public class Utils {
    public static double norm(double[] column) {
        return Math.sqrt(Arrays.stream(column).map(x -> x * x).sum());
    }

    public static double[] subVector(double[] vec1, double[] vec2) {
        double[] result = new double[vec1.length];
        for (int i = 0; i < vec1.length; i++) {
            result[i] = vec1[i] - vec2[i];
        }

        return result;
    }

    public static double[] multiplyMatrixByColumn(final double[][] matrix, double[] column) {
        double [] result = new double[column.length];

        for (int i = 1; i < column.length; i++) {
            for (int j = 1; j < column.length; j++) {
                result[i] += matrix[i][j] * column[j];
            }
        }
        return result;
    }
}
