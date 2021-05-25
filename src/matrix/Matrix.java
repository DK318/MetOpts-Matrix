package matrix;

public interface Matrix {
    double get(int i, int j);
    void set(int i, int j, double val);
    int getN();
    int getM();
}
