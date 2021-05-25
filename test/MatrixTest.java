import matrix.DenseMatrix;
import matrix.SparseMatrix;
import matrix.exception.MatrixException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.generator.SkylineMatrixGenerator;
import util.generator.exception.GeneratorException;
import util.input.Scanner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MatrixTest {
    private final long SEED = 228;
    private final double LOWER = -10;
    private final double UPPER = 10;
    private final double EPS = 1e-7;

    SkylineMatrixGenerator matrixGenerator = new SkylineMatrixGenerator(SEED, LOWER, UPPER);

    Path generateSparseMatrix(int n) throws IOException, GeneratorException {
        Path output = Files.createTempFile(null, null);
        matrixGenerator.generate(n, output);
        return output;
    }

    void testSample(Path output) throws IOException, MatrixException {
        Scanner denseScanner = new Scanner(output);
        int n = denseScanner.nextInt();
        DenseMatrix expected = new DenseMatrix(denseScanner, n, n);
        denseScanner.close();
        Scanner sparseScanner = new Scanner(output);
        sparseScanner.nextInt();
        SparseMatrix actual = new SparseMatrix(sparseScanner, n);
        sparseScanner.close();
        Assertions.assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void sparseMatrixTest1() throws IOException, MatrixException {
        testSample(Path.of("samples/sample1.txt"));
    }

    @Test
    void sparseMatrixTest2() throws IOException, MatrixException {
        testSample(Path.of("samples/sample2.txt"));
    }

    @Test
    void sparseMatrixRandomTest() throws IOException, GeneratorException, MatrixException {
        for (int i = 3; i < 20; i++) {
            Path output = generateSparseMatrix(i);
            testSample(output);
        }
    }
}
