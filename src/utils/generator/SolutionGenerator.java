package utils.generator;

import utils.generator.exception.GeneratorException;

import java.io.IOException;
import java.nio.file.Path;

public class SolutionGenerator extends AbstractGenerator {
    public SolutionGenerator(long seed, double lower, double upper) {
        super(seed, lower, upper);
    }

    public SolutionGenerator(double lower, double upper) {
        super(lower, upper);
    }

    public void generate(int n, Path output) throws GeneratorException {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            builder.append(nextDouble())
                    .append(" ");
        }
        builder.append('\n');
        try {
            write(output, builder.toString());
        } catch (IOException e) {
            throw new GeneratorException("Cannot generate solution", e);
        }
    }
}
