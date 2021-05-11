import lssolvers.LUSolver;
import matrix.SkylineMatrix;
import matrix.exception.MatrixException;
import utils.Scanner;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws MatrixException, IOException {
        Path file = Path.of("matrix.txt");
        Scanner scanner = new Scanner(file);
        int n = scanner.nextInt();
        SkylineMatrix matrix = SkylineMatrix.LUDecomposition(scanner, n);
        double[] b = new double[n + 1];
        for (int i = 1; i <= n; i++) {
            b[i] = scanner.nextDouble();
        }
        System.out.println(Arrays.toString(LUSolver.solve(matrix, b)));
    }
}
