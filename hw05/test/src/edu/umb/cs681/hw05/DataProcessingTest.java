package edu.umb.cs681.hw05;

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
    public void VerifyMeanCalculation() {
        List<NASAData> nasaDataList = Arrays.asList(
                new NASAData("2022", "150", 0.12, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
                new NASAData("2022", "151", 0.10, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
        );
        double mean = DataProcessing.calculateMean(nasaDataList, NASAData::getPrectotcorr);
        assertEquals(0.11, mean, 0.01);
    }

    @Test
    public void VerifyMedianOddCalculation() {
        List<NASAData> nasaDataList = Arrays.asList(
                new NASAData("2022", "150", 1, 0, 0, 0, 0, 0, 0, 0),
                new NASAData("2022", "151", 2, 0, 0, 0, 0, 0, 0, 0),
                new NASAData("2022", "152", 3, 0, 0, 0, 0, 0, 0, 0)
        );
        double median = DataProcessing.calculateMedian(nasaDataList, NASAData::getPrectotcorr);
        assertEquals(2, median, 0.01);
    }

    @Test
    public void VerifyMedianEvenCalculation() {
        List<NASAData> nasaDataList = Arrays.asList(
                new NASAData("2022", "150", 1, 0, 0, 0, 0, 0, 0, 0),
                new NASAData("2022", "151", 2, 0, 0, 0, 0, 0, 0, 0),
                new NASAData("2022", "152", 3, 0, 0, 0, 0, 0, 0, 0),
                new NASAData("2022", "153", 4, 0, 0, 0, 0, 0, 0, 0)
        );
        double median = DataProcessing.calculateMedian(nasaDataList, NASAData::getPrectotcorr);
        assertEquals(2.5, median, 0.01);
    }

    @Test
    public void VerifyMaxCalculation() {
        List<NASAData> nasaDataList = Arrays.asList(
                new NASAData("2022", "150", 1, 0, 0, 0, 0, 0, 0, 0),
                new NASAData("2022", "151", 3, 0, 0, 0, 0, 0, 0, 0),
                new NASAData("2022", "152", 2, 0, 0, 0, 0, 0, 0, 0)
        );
        double max = DataProcessing.calculateMax(nasaDataList, NASAData::getPrectotcorr);
        assertEquals(3, max, 0.01);
    }

    @Test
    public void VerifyMinCalculation() {
        List<NASAData> nasaDataList = Arrays.asList(
                new NASAData("2022", "150", 2, 0, 0, 0, 0, 0, 0, 0),
                new NASAData("2022", "151", 3, 0, 0, 0, 0, 0, 0, 0),
                new NASAData("2022", "152", 1, 0, 0, 0, 0, 0, 0, 0)
        );
        double min = DataProcessing.calculateMin(nasaDataList, NASAData::getPrectotcorr);
        assertEquals(1, min, 0.01);
    }

    @Test
    public void VerifyStandardDeviationCalculation() {
        List<NASAData> nasaDataList = Arrays.asList(
                new NASAData("2022", "150", 1, 0, 0, 0, 0, 0, 0, 0),
                new NASAData("2022", "151", 3, 0, 0, 0, 0, 0, 0, 0),
                new NASAData("2022", "152", 5, 0, 0, 0, 0, 0, 0, 0)
        );
        double stdDev = DataProcessing.calculateStandardDeviation(nasaDataList, NASAData::getPrectotcorr);
        assertEquals(Math.sqrt(8.0 / 3), stdDev, 0.01);
    }
}
