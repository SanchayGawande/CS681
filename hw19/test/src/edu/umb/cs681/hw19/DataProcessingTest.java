package edu.umb.cs681.hw19;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Arrays;

public class DataProcessingTest {

    @Test
    public void testParseCSV() {
        String csvData = "YEAR,DOY,PRECTOTCORR,RH2M,GWETPROF,GWETROOT,GWETTOP,T2M,WS10M,ALLSKY_SFC_SW_DWN\n"
                + "-END HEADER-\n"
                + "2022,150,0.12,50,0.3,0.4,0.2,15.5,5.1,25.0\n"
                + "2022,151,0.10,55,0.2,0.3,0.1,16.0,5.2,24.0\n";
        List<NASAData> result = DataProcessing.parseCSV(csvData);
        assertEquals(2, result.size());
        assertEquals(0.12, result.get(0).getPrectotcorr());
    }

    @Test
    public void verifyMeanCalculationUsingParallelStreams() {
        List<NASAData> nasaDataList = Arrays.asList(
                new NASAData("2022", "150", 0.12, 50, 0.3, 0.4, 0.2, 15.5, 5.1, 25.0),
                new NASAData("2022", "151", 0.10, 55, 0.2, 0.3, 0.1, 16.0, 5.2, 24.0)
        );
        double mean = DataProcessing.calculateMean(nasaDataList, NASAData::getPrectotcorr);
        assertEquals(0.11, mean, 0.01);
    }

    @Test
    public void verifyMedianCalculationWithOddNumberOfElementsUsingParallelStreams() {
        List<NASAData> nasaDataList = Arrays.asList(
                new NASAData("2022", "150", 1, 50, 0.3, 0.4, 0.2, 15.5, 5.1, 25.0),
                new NASAData("2022", "151", 2, 55, 0.2, 0.3, 0.1, 16.0, 5.2, 24.0),
                new NASAData("2022", "152", 3, 60, 0.1, 0.2, 0.0, 17.0, 5.3, 23.0)
        );
        double median = DataProcessing.calculateMedian(nasaDataList, NASAData::getPrectotcorr);
        assertEquals(2, median, 0.01);
    }

    @Test
    public void verifyMedianCalculationWithEvenNumberOfElementsUsingParallelStreams() {
        List<NASAData> nasaDataList = Arrays.asList(
                new NASAData("2022", "150", 1, 50, 0.3, 0.4, 0.2, 15.5, 5.1, 25.0),
                new NASAData("2022", "151", 2, 55, 0.2, 0.3, 0.1, 16.0, 5.2, 24.0),
                new NASAData("2022", "152", 3, 60, 0.1, 0.2, 0.0, 17.0, 5.3, 23.0),
                new NASAData("2022", "153", 4, 65, 0.0, 0.1, -0.1, 18.0, 5.4, 22.0)
        );
        double median = DataProcessing.calculateMedian(nasaDataList, NASAData::getPrectotcorr);
        assertEquals(2.5, median, 0.01);
    }

    @Test
    public void verifyMaxCalculationUsingParallelStreams() {
        List<NASAData> nasaDataList = Arrays.asList(
                new NASAData("2022", "150", 1, 50, 0.3, 0.4, 0.2, 15.5, 5.1, 25.0),
                new NASAData("2022", "151", 3, 55, 0.2, 0.3, 0.1, 16.0, 5.2, 24.0),
                new NASAData("2022", "152", 2, 60, 0.1, 0.2, 0.0, 17.0, 5.3, 23.0)
        );
        double max = DataProcessing.calculateMax(nasaDataList, NASAData::getPrectotcorr);
        assertEquals(3, max, 0.01);
    }

    @Test
    public void verifyMinCalculationUsingParallelStreams() {
        List<NASAData> nasaDataList = Arrays.asList(
                new NASAData("2022", "150", 2, 50, 0.3, 0.4, 0.2, 15.5, 5.1, 25.0),
                new NASAData("2022", "151", 3, 55, 0.2, 0.3, 0.1, 16.0, 5.2, 24.0),
                new NASAData("2022", "152", 1, 60, 0.1, 0.2, 0.0, 17.0, 5.3, 23.0)
        );
        double min = DataProcessing.calculateMin(nasaDataList, NASAData::getPrectotcorr);
        assertEquals(1, min, 0.01);
    }

    @Test
    public void verifyStandardDeviationCalculationUsingParallelStreams() {
        List<NASAData> nasaDataList = Arrays.asList(
                new NASAData("2022", "150", 1, 50, 0.3, 0.4, 0.2, 15.5, 5.1, 25.0),
                new NASAData("2022", "151", 3, 55, 0.2, 0.3, 0.1, 16.0, 5.2, 24.0),
                new NASAData("2022", "152", 5, 60, 0.1, 0.2, 0.0, 17.0, 5.3, 23.0)
        );
        double stdDev = DataProcessing.calculateStandardDeviation(nasaDataList, NASAData::getPrectotcorr);
        assertEquals(Math.sqrt(8.0 / 3), stdDev, 0.01);
    }
}
