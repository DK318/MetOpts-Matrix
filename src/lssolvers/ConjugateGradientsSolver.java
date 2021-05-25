package lssolvers;

import matrix.Matrix;

import java.util.stream.IntStream;

public class ConjugateGradientsSolver {
    private ConjugateGradientsSolver() {

    }

    public static double[] solve(Matrix matrix, double[] f, double eps, int maxIterations) {
        int n = f.length - 1;
        double[] x = new double[n + 1];
        double[] r = subtract(f, multiply(matrix, x));
        double[] z = r.clone();
        int iteration = 0;
        while (!halt(r, f, eps) && iteration < maxIterations) {
            double[] az = multiply(matrix, z);
            double rr = scalarMultiply(r, r);
            double alpha = rr / scalarMultiply(az, z);
            x = add(x, multiplyByScalar(z, alpha));
            r = subtract(r, multiplyByScalar(az, alpha));
            double beta = scalarMultiply(r, r) / rr;
            z = add(r, multiplyByScalar(z, beta));
            iteration++;
        }
        return x;
    }

    private static boolean halt(double[] r, double[] f, double eps) {
        return norm(r) / norm(f) < eps;
    }

    private static double[] multiply(Matrix matrix, double[] x) {
        int n = x.length - 1;
        double[] result = new double[x.length];
        IntStream.range(0, n + 1)
                .forEach(i -> IntStream.range(0, n + 1)
                .forEach(j -> result[i] += matrix.get(i, j) * x[j]));
        return result;
    }

    private static double[] add(double[] lhs, double[] rhs) {
        double[] result = new double[lhs.length];
        IntStream.range(0, lhs.length).forEach(i -> result[i] = lhs[i] + rhs[i]);
        return result;
    }

    private static double[] subtract(double[] lhs, double[] rhs) {
        double[] result = new double[lhs.length];
        IntStream.range(0, lhs.length).forEach(i -> result[i] = lhs[i] - rhs[i]);
        return result;
    }

    private static double[] multiplyByScalar(double[] vec, double num) {
        double[] result = new double[vec.length];
        IntStream.range(0, vec.length).forEach(i -> result[i] = vec[i] * num);
        return result;
    }

    private static double scalarMultiply(double[] lhs, double[] rhs) {
        return IntStream.range(0, lhs.length).mapToDouble(i -> lhs[i] * rhs[i]).sum();
    }

    private static double norm(double[] vec) {
        return Math.sqrt(scalarMultiply(vec, vec));
    }
}
