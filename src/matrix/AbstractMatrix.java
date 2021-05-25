package matrix;

public abstract class AbstractMatrix implements Matrix {
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i <= getN(); i++) {
            for (int j = 1; j <= getM(); j++) {
                builder.append(get(i, j))
                        .append(" ");
            }
            builder.append('\n');
        }
        return builder.toString();
    }
}
