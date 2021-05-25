package util.generator.table;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

public class Table {
    protected final int n;
    protected final Random random;
    protected double[][] data;
    protected double[] b;
    protected double[] solution;

    protected Table(final int n) {
        this.n = n;
        this.random = new Random();
        this.data = new double[n + 1][n + 1];
        this.b = new double[n + 1];
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                builder.append(data[i][j])
                        .append(" ");
            }
            builder.append('\n');
        }
        return builder.toString();
    }

    protected String getFRepresentation() {
        StringBuilder builder = new StringBuilder();

        double []f = Utils.multiplyMatrixByColumn(data, b);
        for (int i = 1; i <= n; i++) {
            builder.append(f[i]).append(' ');
        }

        return builder.toString();
    }

    protected void write(final String string, final Path output) throws IOException {
        Files.writeString(output, string);
    }
}
