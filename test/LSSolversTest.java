import lssolvers.GaussSolver;
import lssolvers.LUSolver;
import matrix.DenseMatrix;
import matrix.Matrix;
import matrix.SkylineMatrix;
import matrix.SparseMatrix;
import matrix.exception.MatrixException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.generator.SkylineMatrixGenerator;
import util.generator.TaskGenerator;
import util.generator.exception.GeneratorException;
import util.input.Scanner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LSSolversTest {
    private final long SEED = 228;
    private final double LOWER = -10;
    private final double UPPER = 10;
    private final double EPS = 1e-7;

    TaskGenerator generator = new TaskGenerator(SEED, LOWER, UPPER);

    Path createSkylineTask() {
        Path output;
        try {
            output = Files.createTempFile(null, null);
        } catch (IOException e) {
            throw new AssertionError("Cannot create file for task", e);
        }

        try {
            generator.generate(250, output);
        } catch (GeneratorException e) {
            throw new AssertionError(e);
        }

        return output;
    }

    @Test
    void testLU() {
        Path output = createSkylineTask();

        try (Scanner scanner = new Scanner(output)) {
            int n = scanner.nextInt();
            Matrix matrix = new SkylineMatrix(scanner, n);
            double[] solution = new double[n + 1];
            for (int i = 1; i <= n; i++) {
                solution[i] = scanner.nextDouble();
            }
            double[] b = new double[n + 1];
            for (int i = 1; i <= n; i++) {
                b[i] = scanner.nextDouble();
            }
            double[] luSolution = LUSolver.solve(matrix, b);
            for (int i = 1; i <= n; i++) {
                Assertions.assertTrue(Math.abs(solution[i] - luSolution[i]) < EPS);
            }
        } catch (IOException e) {
            throw new AssertionError("Cannot read from test file", e);
        } catch (MatrixException e) {
            throw new AssertionError(e);
        }
    }

    @Test
    void testMultipleLU() {
        for (int i = 0; i < 10; i++) {
            testLU();
        }
    }

    @Test
    void testGauss() {
        Path output = createSkylineTask();

        try (Scanner scanner = new Scanner(output)) {
            int n = scanner.nextInt();
            DenseMatrix matrix = new DenseMatrix(scanner, n, n);
            double[] solution = new double[n + 1];
            for (int i = 1; i <= n; i++) {
                solution[i] = scanner.nextDouble();
            }
            double[] b = new double[n + 1];
            for (int i = 1; i <= n; i++) {
                b[i] = scanner.nextDouble();
            }
            double[] gaussSolution = new double[n + 1];
            GaussSolver.solve(matrix, b, gaussSolution, EPS);
            for (int i = 1; i <= n; i++) {
                Assertions.assertTrue(Math.abs(solution[i] - gaussSolution[i]) < EPS);
            }
        } catch (IOException e) {
            throw new AssertionError("Cannot read from test file", e);
        } catch (MatrixException e) {
            throw new AssertionError(e);
        }
    }

    @Test
    void testMultipleGauss() {
        for (int i = 0; i < 10; i++) {
            testGauss();
        }
    }
}
