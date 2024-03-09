package edu.umb.cs681.hw03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Distance {
    public static double get(List<Double> p1, List<Double> p2) {
        return Distance.get(p1, p2, new Euclidean());
    }

    public static double get(List<Double> p1, List<Double> p2, DistanceMetric metric) {
        return metric.distance(p1, p2);
    }

    public static List<List<Double>> matrix(List<List<Double>> points) {
        return Distance.matrix(points, new Euclidean());
    };
    private static List<List<Double>> initDistanceMatrix(int numOfPoints){
        List<List<Double>> distanceMatrix = new ArrayList<>(numOfPoints);
        for(int i=0; i < numOfPoints; i++) {
            Double[] vector = new Double[numOfPoints];
            Arrays.fill(vector, 0.0);
            distanceMatrix.add( Arrays.asList(vector) );
        }
        return distanceMatrix;
    }
    public static List<List<Double>> matrix(List<List<Double>> points, DistanceMetric metric) {
        return IntStream.range(0, points.size()).parallel()
                .mapToObj(i ->
                        IntStream.range(0, points.size()).parallel()
                                .mapToObj(j -> get(points.get(i), points.get(j), metric))
                                .collect(Collectors.toList())
                ).collect(Collectors.toList());
    }

    public static void main(String[] args) {

        List<List<Double>> points = IntStream.range(0, 1000).parallel()
                .mapToObj(i -> new Random().doubles(100, 0, 1).boxed()
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
        // Calculate the distance matrix using the Euclidean distance metric
        List<List<Double>> distanceMatrix = Distance.matrix(points, new Euclidean());
        // Output the size of the distance matrix to verify
        System.out.println("Distance matrix size: " + distanceMatrix.size() + "x" + distanceMatrix.get(0).size());
    }
}
