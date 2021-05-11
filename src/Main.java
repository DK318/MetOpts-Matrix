import matrix.SkylineMatrix;
import matrix.exception.MatrixException;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws MatrixException {
        Path file = Path.of("matrix.txt");
        System.out.println(SkylineMatrix.LUDecomposition(file));
    }
}
