import matrix.exception.MatrixException;
import utils.generator.SkylineMatrixGenerator;
import utils.generator.TaskGenerator;
import utils.generator.exception.GeneratorException;

import java.io.IOException;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws MatrixException, IOException, GeneratorException {
        /*Path file = Path.of("matrix.txt");
        Scanner scanner = new Scanner(file);
        int n = scanner.nextInt();
        SkylineMatrix matrix = SkylineMatrix.LUDecomposition(scanner, n);
        double[] b = new double[n + 1];
        for (int i = 1; i <= n; i++) {
            b[i] = scanner.nextDouble();
        }
        System.out.println(Arrays.toString(LUSolver.solve(matrix, b)));*/
        /*Path file = Path.of("matrix.txt");
        Scanner scanner = new Scanner(file);
        int n = scanner.nextInt();
        int m = n;
        DenseMatrix matrix = new DenseMatrix(scanner, n, m);
        double[] b = new double[n + 1];
        for (int i = 1; i <= n; i++) {
            b[i] = scanner.nextDouble();
        }
        double[] result = new double[m + 1];
        GaussSolver.solve(matrix, b, result, 1e-7);
        System.out.println(Arrays.toString(result));*/
        Path output = Path.of("generated.txt");
        TaskGenerator generator = new TaskGenerator(228, -10, 10);
        generator.generate(4, output);
    }
}
