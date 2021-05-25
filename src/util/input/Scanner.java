package util.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.StringTokenizer;

public class Scanner implements AutoCloseable {
    private final BufferedReader reader;
    private StringTokenizer tokenizer;

    public Scanner(Path file) throws IOException {
        this.reader = Files.newBufferedReader(file);
        this.tokenizer = null;
    }

    public String next() throws IOException {
        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
            tokenizer = new StringTokenizer(reader.readLine());
        }

        return tokenizer.nextToken();
    }

    public int nextInt() throws IOException {
        return Integer.parseInt(next());
    }

    public double nextDouble() throws IOException {
        return Double.parseDouble(next());
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
