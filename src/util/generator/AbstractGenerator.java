package util.generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Random;

public abstract class AbstractGenerator {
    protected final Random random;
    protected final double lower;
    protected final double upper;

    public AbstractGenerator(long seed, double lower, double upper) {
        this.random = new Random(seed);
        this.lower = lower;
        this.upper = upper;
    }

    public AbstractGenerator(double lower, double upper) {
        this.lower = lower;
        this.upper = upper;
        this.random = new Random();
    }

    protected double nextDouble() {
        double rDouble = random.nextDouble();
        double sign = Math.signum(rDouble);
        return (lower + (rDouble * (upper - lower))) * sign;
    }

    protected void write(Path output, String string) throws IOException {
        Files.writeString(output, string, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    protected String convertToString(int n, double[][] data, double[] b, double[] solution) {
        StringBuilder builder = new StringBuilder();
        builder.append(n).append(" ").append(n).append('\n');
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                builder.append(data[i][j])
                        .append(" ");
            }
            builder.append('\n');
        }

        for (int i = 1; i <= n; i++) {
            builder.append(b[i])
                    .append(" ");
        }
        builder.append('\n');

        for (int i = 1; i <= n; i++) {
            builder.append(solution[i])
                    .append(" ");
        }
        builder.append('\n');
        return builder.toString();
    }

    protected double[] generateOneToNVector(int n) {
        double[] solution = new double[n + 1];
        for (int i = 1; i <= n; i++) {
            solution[i] = i;
        }
        return solution;
    }

    protected double[] multiply(int n, double[][] data, double[] solution) {
        double[] b = new double[n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                b[i] += data[i][j] * solution[j];
            }
        }
        return b;
    }

    protected void print(int n, double[][] data, Path path) {
        double[] solution = generateOneToNVector(n);
        double[] b = multiply(n, data, solution);
        try {
            write(path, convertToString(n, data, b, solution));
        } catch (IOException e) {
            System.err.println("Failed to write data in files: " + e.getMessage());
        }
    }
}
