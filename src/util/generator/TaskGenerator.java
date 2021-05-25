package util.generator;

import util.generator.exception.GeneratorException;
import util.input.Scanner;

import java.io.IOException;
import java.nio.file.Path;

public class TaskGenerator extends AbstractGenerator {
    private final SkylineMatrixGenerator matrixGenerator;
    private final SolutionGenerator solutionGenerator;

    public TaskGenerator(long seed, double lower, double upper) {
        super(seed, lower, upper);
        this.matrixGenerator = new SkylineMatrixGenerator(seed, lower, upper);
        this.solutionGenerator = new SolutionGenerator(seed, lower, upper);
    }

    public void generate(int n, Path output) throws GeneratorException {
        matrixGenerator.generate(n, output);
        solutionGenerator.generate(n, output);

        double[][] matrix = new double[n + 1][n + 1];
        double[] solution = new double[n + 1];
        double[] result = new double[n + 1];

        try (Scanner scanner = new Scanner(output)) {
            scanner.next();
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    matrix[i][j] = scanner.nextDouble();
                }
            }
            for (int i = 1; i <= n; i++) {
                solution[i] = scanner.nextDouble();
            }
        } catch (IOException e) {
            throw new GeneratorException("Cannot generate task", e);
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                result[i] += matrix[i][j] * solution[j];
            }
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            builder.append(result[i])
                    .append(" ");
        }
        builder.append('\n');

        try {
            write(output, builder.toString());
        } catch (IOException e) {
            throw new GeneratorException("Cannot generate task", e);
        }
    }
}
