package utils.generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
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
}
