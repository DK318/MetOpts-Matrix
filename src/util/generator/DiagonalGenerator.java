package util.generator;

import java.nio.file.Path;

public class DiagonalGenerator extends AbstractGenerator {
    public DiagonalGenerator(long seed, double lower, double upper) {
        super(seed, lower, upper);
    }

    public DiagonalGenerator(double lower, double upper) {
        super(lower, upper);
    }

    public void generate(Path path, int n, int k) {
        double[][] data = new double[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n / 2; j++) {
                data[i][j] = -random.nextInt(5);
                data[j][i] = data[i][j];
            }
        }

        for (int i = 1; i <= n; i++) {
            double sum = 0;
            for (int j = 1; j <= n; j++) {
                if (i != j) {
                    sum += data[i][j];
                }
            }

            data[i][i] = -sum;
            if (i == 1) {
                data[i][i] += Math.pow(10, -k);
            }
        }

        print(n, data, path);
    }
}
