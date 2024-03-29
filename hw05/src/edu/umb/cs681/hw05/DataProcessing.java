package edu.umb.cs681.hw05;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

public class DataProcessing {
    static List<NASAData> parseCSV(String csvData) {
        List<NASAData> nasaDataList = new ArrayList<>();
        String[] lines = csvData.split("\n");
        boolean dataSectionStarted = false;
        for (String line : lines) {
            if (line.trim().startsWith("YEAR")) {
                continue;
            }
            if (line.trim().equals("-END HEADER-")) {
                dataSectionStarted = true;
                continue;
            }
            if (dataSectionStarted) {
                String[] parts = line.split(",");
                try {
                    NASAData nasaData = new NASAData(
                            parts[0], // Year
                            parts[1], // DOY
                            Double.parseDouble(parts[2]), // PRECTOTCORR
                            Double.parseDouble(parts[3]), // RH2M
                            Double.parseDouble(parts[4]), // GWETPROF
                            Double.parseDouble(parts[5]), // GWETROOT
                            Double.parseDouble(parts[6]), // GWETTOP
                            Double.parseDouble(parts[7]), // T2M
                            Double.parseDouble(parts[8]), // WS10M
                            Double.parseDouble(parts[9])); // ALLSKY_SFC_SW_DWN
                    nasaDataList.add(nasaData);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.err.println("Skipping malformed line: " + line);
                }
            }
        }
        return nasaDataList;
    }
    static double calculateMean(List<NASAData> nasaDataList, ToDoubleFunction<NASAData> mapper) {
        return nasaDataList.stream()
                .mapToDouble(mapper)
                .average()
                .orElse(Double.NaN);
    }

    static double calculateMedian(List<NASAData> nasaDataList, ToDoubleFunction<NASAData> mapper) {
        List<Double> sortedValues = nasaDataList.stream()
                .mapToDouble(mapper)
                .sorted()
                .boxed()
                .collect(Collectors.toList());
        int size = sortedValues.size();
        if (size % 2 == 0) {
            return (sortedValues.get(size / 2 - 1) + sortedValues.get(size / 2)) / 2;
        } else {
            return sortedValues.get(size / 2);
        }
    }

    static double calculateMax(List<NASAData> nasaDataList, ToDoubleFunction<NASAData> mapper) {
        return nasaDataList.stream()
                .mapToDouble(mapper)
                .max()
                .orElse(Double.NaN);
    }

    static double calculateMin(List<NASAData> nasaDataList, ToDoubleFunction<NASAData> mapper) {
        return nasaDataList.stream()
                .mapToDouble(mapper)
                .min()
                .orElse(Double.NaN);
    }

    static double calculateStandardDeviation(List<NASAData> nasaDataList, ToDoubleFunction<NASAData> mapper) {
        double mean = calculateMean(nasaDataList, mapper);
        double sumOfSquaredDifferences = nasaDataList.stream()
                .mapToDouble(data -> Math.pow(mapper.applyAsDouble(data) - mean, 2))
                .sum();
        return Math.sqrt(sumOfSquaredDifferences / nasaDataList.size());
    }

    private static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    private static double[][] createDistanceMatrix(String[] locations) {
        double[][] distanceMatrix = new double[locations.length][locations.length];
        for (int i = 0; i < locations.length; i++) {
            for (int j = 0; j < locations.length; j++) {
                if (i == j) {
                    distanceMatrix[i][j] = 0;
                    continue;
                }
                String[] loc1 = locations[i].split(",");
                String[] loc2 = locations[j].split(",");
                double lat1 = Double.parseDouble(loc1[0]);
                double lon1 = Double.parseDouble(loc1[1]);
                double lat2 = Double.parseDouble(loc2[0]);
                double lon2 = Double.parseDouble(loc2[1]);
                distanceMatrix[i][j] = calculateDistance(lat1, lon1, lat2, lon2);
            }
        }
        return distanceMatrix;
    }

    private static String buildDataUrl(String location, String startDate, String endDate, String[] parameters) {
        String baseUrl = "https://power.larc.nasa.gov/api/temporal/daily/point?";
        String params = Arrays.stream(parameters).collect(Collectors.joining(","));
        String lat = location.split(",")[0].trim();
        String lon = location.split(",")[1].trim();
        return baseUrl + "parameters=" + params + "&community=AG&format=CSV&start=" + startDate + "&end=" + endDate + "&latitude=" + lat + "&longitude=" + lon;
    }

    private static String downloadData(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
            response.append('\n');
        }
        reader.close();

        return response.toString();
    }

    public static void main(String[] args) {
        String[] parameters = {"PRECTOTCORR", "RH2M", "GWETPROF", "GWETROOT", "GWETTOP", "T2M", "WS10M", "ALLSKY_SFC_SW_DWN"};
        String[] locations = {
                "71.0954,42.3851", // Boston
                "40.7128,-74.0060", // New York City
                "34.0522,-118.2437", // Los Angeles
                "41.8781,-87.6298", // Chicago
                "29.7604,-95.3698", // Houston
                "33.4484,-112.0740", // Phoenix
                "39.7392,-104.9903", // Denver
                "47.6062,-122.3321", // Seattle
                "25.7617,-80.1918", // Miami
                "38.9072,-77.0369" // Washington D.C.
        };
        String startDate = "20220601";
        String endDate = "20220901";

        List<NASAData> nasaDataList = new ArrayList<>();

        for (String location : locations) {
            try {
                String dataUrl = buildDataUrl(location, startDate, endDate, parameters);
                String csvData = downloadData(dataUrl);
                List<NASAData> locationData = parseCSV(csvData);
                nasaDataList.addAll(locationData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Mean for PRECTOTCORR: " + calculateMean(nasaDataList, NASAData::getPrectotcorr));
        System.out.println("Mean for GWETPROF: " + calculateMean(nasaDataList, NASAData::getGwetprof));
        System.out.println("Median for PRECTOTCORR: " + calculateMedian(nasaDataList, NASAData::getPrectotcorr));
        System.out.println("Median for GWETTOP: " + calculateMedian(nasaDataList, NASAData::getGwettop));
        System.out.println("Maximum for PRECTOTCORR: " + calculateMax(nasaDataList, NASAData::getPrectotcorr));
        System.out.println("Minimum for T2M: " + calculateMin(nasaDataList, NASAData::getT2m));
        System.out.println("Standard Deviation for PRECTOTCORR: " + calculateStandardDeviation(nasaDataList, NASAData::getPrectotcorr));

        // Calculate the distance matrix
        double[][] distanceMatrix = createDistanceMatrix(locations);
        System.out.println("Distance Matrix:");
        for (double[] row : distanceMatrix) {
            for (double dist : row) {
                System.out.printf("%10.2f", dist);
            }
            System.out.println();
        }
    }
}

