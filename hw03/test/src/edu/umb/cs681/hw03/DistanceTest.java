package edu.umb.cs681.hw03;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import java.util.List;

public class DistanceTest {

    @Test
    public void VerifyGetEuclideanDistance() {
        List<Double> p1 = Arrays.asList(1.0, 2.0, 3.0);
        List<Double> p2 = Arrays.asList(4.0, 5.0, 6.0);
        double expected = Math.sqrt(27.0);
        assertEquals(expected, Distance.get(p1, p2), 0.001);
    }

    @Test
    public void VerifyGetCustomMetricDistance() {
        List<Double> p1 = Arrays.asList(0.0, 0.0);
        List<Double> p2 = Arrays.asList(3.0, 4.0);
        DistanceMetric metric = (point1, point2) -> 5.0;
        assertEquals(5.0, Distance.get(p1, p2, metric), 0.001);
    }

    @Test
    public void VerifyMatrixWithEuclidean() {
        List<List<Double>> points = Arrays.asList(
                Arrays.asList(0.0, 0.0),
                Arrays.asList(3.0, 4.0)
        );
        List<List<Double>> matrix = Distance.matrix(points);
        assertEquals(0.0, matrix.get(0).get(0), 0.001);
        assertEquals(5.0, matrix.get(0).get(1), 0.001);
        assertEquals(5.0, matrix.get(1).get(0), 0.001);
        assertEquals(0.0, matrix.get(1).get(1), 0.001);
    }

    @Test
    public void VerifyMatrixWithCustomMetric() {
        List<List<Double>> points = Arrays.asList(
                Arrays.asList(0.0, 0.0),
                Arrays.asList(1.0, 1.0)
        );
        DistanceMetric metric = (point1, point2) -> 1.0;
        List<List<Double>> matrix = Distance.matrix(points, metric);
        assertEquals(1.0, matrix.get(0).get(1), 0.001);
        assertEquals(1.0, matrix.get(1).get(0), 0.001);
    }
}
