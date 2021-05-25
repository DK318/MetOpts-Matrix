package utils.generator.table;

import lssolvers.LUSolver;
import matrix.Matrix;
import matrix.SkylineMatrix;
import matrix.exception.MatrixException;
import utils.decomposers.LUDecomposer;
import utils.input.Scanner;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.IntStream;

public class DirectMethodLUGenerator extends Table {
    public DirectMethodLUGenerator(int n) {
        super(n);
    }

    private void buildMatrix(final int k, final Path matrixPath) {
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

        // Ax = result

        solution = new double[n + 1];
        for (int i = 1; i <= n; i++) {
            solution[i] = i;
        }
        b = Utils.multiplyMatrixByColumn(data, solution);

        try {
            write(this.toString(), matrixPath);
        } catch (IOException e) {
            System.err.println("Failed to write data in files: " + e.getMessage());
        }
    }


    public double[] getResult(final int k, final Path matrixPath) throws IOException, MatrixException {
        buildMatrix(k, matrixPath);
        Matrix matrix = new SkylineMatrix(new Scanner(matrixPath), n);
        double[] LUsolution = LUSolver.solve(matrix, b);
        double[] ans = new double[2];

        ans[0] = Utils.norm(Utils.subVector(solution, LUsolution));
        ans[1] = ans[0] / Utils.norm(solution);
        return ans;
    }
}
