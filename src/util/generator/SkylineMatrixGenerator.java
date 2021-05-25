package util.generator;

import util.generator.exception.GeneratorException;

import java.io.IOException;
import java.nio.file.Path;

public class SkylineMatrixGenerator extends AbstractGenerator {
    public SkylineMatrixGenerator(long seed, double lower, double upper) {
        super(seed, lower, upper);
    }

    public SkylineMatrixGenerator(double lower, double upper) {
        super(lower, upper);
    }

    public void generate(int n, Path output) throws GeneratorException {
        double[][] result = new double[n + 1][n + 1];

        int[] profiles = new int[n + 1];
        profiles[1] = 0;
        for (int i = 2; i <= n; i++) {
            profiles[i] = random.nextInt(i);
        }

        for (int i = 1; i <= n; i++) {
            result[i][i] = nextDouble();
        }

        for (int i = 1; i <= n; i++) {
            int start = i - profiles[i];
            for (int j = start; j < i; j++) {
                result[i][j] = nextDouble();
            }
        }

        for (int j = 1; j <= n; j++) {
            int start = j - profiles[j];
            for (int i = start; i < j; i++) {
                result[i][j] = nextDouble();
            }
        }

        StringBuilder builder = new StringBuilder();
        builder.append(n)
                .append('\n');
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                builder.append(result[i][j])
                        .append(" ");
            }
            builder.append('\n');
        }

        try {
            write(output, builder.toString());
        } catch (IOException e) {
            throw new GeneratorException("Cannot generate skyline matrix", e);
        }
    }
}
