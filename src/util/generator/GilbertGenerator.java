package util.generator;

import java.nio.file.Path;

public class GilbertGenerator extends AbstractGenerator {
    public GilbertGenerator(long seed, double lower, double upper) {
        super(seed, lower, upper);
    }

    public GilbertGenerator(double lower, double upper) {
        super(lower, upper);
    }

    public void generate(Path path, int n) {
        double[][] data = new double[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                data[i][j] = ((double)1) / (double)(i + j - 1);
            }
        }

        print(n, data, path);
    }
}
