package edu.umb.cs681.hw03;
import java.util.List;
import java.util.stream.IntStream;

public class Cosine implements DistanceMetric {
    @Override
    public double distance(List<Double> p1, List<Double> p2) {
        if (p1.size() != p2.size()) {
            throw new IllegalArgumentException("Points must have the same dimensions");
        }

        double dotProduct = IntStream.range(0, p1.size())
                .mapToDouble(i -> p1.get(i) * p2.get(i))
                .sum();

        double normP1 = IntStream.range(0, p1.size())
                .mapToDouble(i -> Math.pow(p1.get(i), 2))
                .sum();

        double normP2 = IntStream.range(0, p2.size())
                .mapToDouble(i -> Math.pow(p2.get(i), 2))
                .sum();

        normP1 = Math.sqrt(normP1);
        normP2 = Math.sqrt(normP2);

        if (normP1 == 0 || normP2 == 0) {
            throw new IllegalArgumentException("Norm cannot be zero");
        }

        double cosineSimilarity = dotProduct / (normP1 * normP2);
        return 1 - cosineSimilarity;
    }
}
