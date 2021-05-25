package util.generator;

import util.generator.exception.GeneratorException;

import java.io.IOException;
import java.nio.file.Path;

public class GradientTaskGenerator extends AbstractGenerator {
    public GradientTaskGenerator(long seed, double lower, double upper) {
        super(seed, lower, upper);
    }

    public GradientTaskGenerator(double lower, double upper) {
        super(lower, upper);
    }

    public void generate(Path output, int n) throws GeneratorException {
        double[][] matrix = new double[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                double el = nextDouble();
                matrix[i][j] = el;
                matrix[j][i] = el;
            }
        }

        double[] solution = new double[n + 1];
        for (int i = 1; i <= n; i++) {
            solution[i] = nextDouble();
        }

        double[] b = multiply(n, matrix, solution);

        StringBuilder builder = new StringBuilder();
        builder.append(n)
                .append("\n");
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                builder.append(matrix[i][j])
                        .append(" ");
            }
            builder.append("\n");
        }
        for (int i = 1; i <= n; i++) {
            builder.append(solution[i])
                    .append(" ");
        }
        builder.append("\n");
        for (int i = 1; i <= n; i++) {
            builder.append(b[i])
                    .append(" ");
        }

        try {
            write(output, builder.toString());
        } catch (IOException e) {
            throw new GeneratorException("Cannot generate task", e);
        }
    }
}
