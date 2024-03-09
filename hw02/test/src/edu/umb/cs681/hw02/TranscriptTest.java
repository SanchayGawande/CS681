package edu.umb.cs681.hw02;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TranscriptTest {
    private Transcript transcript;

    @BeforeEach
    void setUp() {
        transcript = new Transcript();
        // Setting up with predefined courses and grades
        transcript.addCourse("UG-CS101", "C", null);
        transcript.addCourse("UG-CS103", "A", null);
        transcript.addCourse("UG-CS102", "A", Collections.singletonList("UG-CS101"));
        transcript.addCourse("UG-CS201", "F", Collections.singletonList("UG-CS102"));
        transcript.addCourse("UG-CS202", "C", Collections.singletonList("UG-CS103"));
        transcript.addCourse("UG-CS203", "F", Collections.singletonList("UG-CS103"));
        transcript.addCourse("UG-MATH201", "A", null);
        transcript.addCourse("UG-PHYS202", "C", null);
        transcript.addCourse("UG-CHEM202", "D", null);
        transcript.addCourse("G-CS680", "A", null);
        transcript.addCourse("G-CS681", "B", null);
        transcript.addCourse("G-CS682", "A", null);
    }

    @Test
    void testCalculateUndergraduateGPA() {
        double expectedUGGPA = (2.0 + 4.0 + 4.0 + 0.0 + 2.0 + 0.0 + 4.0 + 2.0 + 1.0) / 9;
        assertEquals(expectedUGGPA, transcript.calculateUndergraduateGPA(), "The calculated undergraduate GPA does not match the expected value.");
    }

    @Test
    void testCalculateGraduateGPA() {
        double expectedGradGPA = (4.0 + 3.0 + 4.0) / 3;
        assertEquals(expectedGradGPA, transcript.calculateGraduateGPA(), "The calculated graduate GPA does not match the expected value.");
    }

    @Test
    void testCalculateMajorUndergradGPA() {
        double expectedMajorUGGPA = (2.0 + 4.0 + 4.0 + 0.0 + 2.0 + 0.0) / 6;
        assertEquals(expectedMajorUGGPA, transcript.calculateMajorUndergradGPA(), "The calculated major undergraduate GPA does not match the expected value.");
    }

    @Test
    void testCalculatePassFailRates() {
        Map<String, Double> rates = transcript.calculatePassFailRates();
        assertEquals(10.0 / 12.0, rates.get("Pass Rate"), 0.0001, "The calculated pass rate does not match the expected value.");
        assertEquals(2.0 / 12.0, rates.get("Fail Rate"), 0.0001, "The calculated fail rate does not match the expected value.");
    }

    @Test
    void testCalculateAllGPAs() {
        Map<String, Double> gpas = transcript.calculateAllGPAs();
        double expectedUGGPA = (2.0 + 4.0 + 4.0 + 0.0 + 2.0 + 0.0 + 4.0 + 2.0 + 1.0) / 9;
        double expectedGradGPA = (4.0 + 3.0 + 4.0) / 3;
        double expectedMajorUGGPA = (2.0 + 4.0 + 4.0 + 0.0 + 2.0 + 0.0) / 6;

        assertEquals(expectedUGGPA, gpas.get("CumulativeUndergradGPA"), "The calculated cumulative undergraduate GPA does not match the expected value.");
        assertEquals(expectedGradGPA, gpas.get("GradGPA"), "The calculated graduate GPA does not match the expected value.");
        assertEquals(expectedMajorUGGPA, gpas.get("MajorUndergradGPA"), "The calculated major undergraduate GPA does not match the expected value.");
    }

    @Test
    void testAnalyzePrerequisiteImpact() {
        Map<String, Double> impacts = transcript.analyzePrerequisiteImpact();
        assertEquals(2.0, impacts.get("UG-CS102 influenced by UG-CS101"), 0.01, "The calculated prerequisite impact does not match the expected value.");
        assertNull(impacts.get("UG-CS101 influenced by UG-CS101"), "A course should not influence itself.");
    }
}
