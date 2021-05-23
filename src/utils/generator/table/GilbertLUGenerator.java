package utils.generator.table;

import lssolvers.GaussSolver;
import lssolvers.LUSolver;
import matrix.DenseMatrix;
import matrix.Matrix;
import matrix.SkylineMatrix;
import matrix.exception.MatrixException;
import utils.input.Scanner;

import java.io.IOException;
import java.nio.file.Path;

public class GilbertLUGenerator extends Table {
    public GilbertLUGenerator(int n) {
        super(n);
    }

    private void buildMatrix(final Path matrixPath) {
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                data[i][j] = ((double)1) / (double)(i + j - 1);
            }
        }


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

    public double[] getResult(final Path matrixPath) throws IOException, MatrixException {
        buildMatrix(matrixPath);
        SkylineMatrix decomposed = SkylineMatrix.LUDecomposition(new Scanner(matrixPath), n);
        double[] LUsolution = LUSolver.solve(decomposed, b);
        double[] ans = new double[2];

        ans[0] = Utils.norm(Utils.subVector(solution, LUsolution));
        ans[1] = ans[0] / Utils.norm(solution);
        return ans;
    }
}
