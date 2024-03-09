package edu.umb.cs681.hw02;

import java.util.*;
import java.util.stream.Collectors;

class Course {
    String code;
    String grade;
    List<String> prerequisites; // Prerequisite courses for this course

    Course(String code, String grade, List<String> prerequisites) {
        this.code = code;
        this.grade = grade;
        this.prerequisites = prerequisites == null ? new ArrayList<>() : prerequisites;
    }

    boolean isUndergraduate() {
        return code.startsWith("UG-");
    }

    boolean isGraduate() {
        return !isUndergraduate();
    }

    boolean isMajorCourse(String majorPrefix) {
        return code.startsWith(majorPrefix);
    }

    boolean isPassed() {
        // Assuming 'D' and above are considered as passed
        return !"F".equals(grade);
    }
    double gradeToPoint() {
        switch (this.grade) {
            case "A": return 4.0;
            case "B": return 3.0;
            case "C": return 2.0;
            case "D": return 1.0;
            default: return 0.0;
        }
    }

}

public class Transcript {
    private List<Course> courses = new ArrayList<>();
    private String majorPrefix = "UG-CS"; // Default major prefix for CS courses

    public void setMajorPrefix(String majorPrefix) {
        this.majorPrefix = majorPrefix;
    }

    public void addCourse(String courseCode, String grade, List<String> prerequisites) {
        courses.add(new Course(courseCode, grade, prerequisites));
    }

    private double calculateGPA(List<Course> courseList) {
        return courseList.stream()
                .mapToDouble(course -> {
                    switch (course.grade) {
                        case "A": return 4.0;
                        case "B": return 3.0;
                        case "C": return 2.0;
                        case "D": return 1.0;
                        default:  return 0.0;
                    }
                })
                .average()
                .orElse(0.0);
    }

    private Map<Boolean, List<Course>> partitionCourses() {
        return courses.stream()
                .collect(Collectors.partitioningBy(Course::isUndergraduate));
    }

    public double calculateUndergraduateGPA() {
        Map<Boolean, List<Course>> partitionedCourses = partitionCourses();
        List<Course> undergradCourses = partitionedCourses.get(true);
        return calculateGPA(undergradCourses);
    }

    public double calculateGraduateGPA() {
        Map<Boolean, List<Course>> partitionedCourses = partitionCourses();
        List<Course> gradCourses = partitionedCourses.get(false);
        return calculateGPA(gradCourses);
    }

    // For major-specific GPA, we're focusing within the undergraduate partition only
    public double calculateMajorUndergradGPA() {
        Map<Boolean, List<Course>> partitionedCourses = partitionCourses();
        List<Course> undergradCourses = partitionedCourses.get(true);
        List<Course> majorCourses = undergradCourses.stream()
                .filter(course -> course.isMajorCourse(majorPrefix))
                .collect(Collectors.toList());
        return calculateGPA(majorCourses);
    }

    public Map<String, Double> calculateAllGPAs() {
        Map<Boolean, List<Course>> partitionedCourses = courses.stream()
                .collect(Collectors.partitioningBy(Course::isUndergraduate));

        List<Course> undergradCourses = partitionedCourses.get(true);
        List<Course> gradCourses = partitionedCourses.get(false);
        List<Course> majorCourses = undergradCourses.stream()
                .filter(course -> course.isMajorCourse(majorPrefix))
                .collect(Collectors.toList());

        Map<String, Double> gpas = new HashMap<>();
        gpas.put("MajorUndergradGPA", calculateGPA(majorCourses));
        gpas.put("CumulativeUndergradGPA", calculateGPA(undergradCourses));
        gpas.put("GradGPA", calculateGPA(gradCourses));

        return gpas;
    }

    // Analysis of Pass/Fail Rates
    public Map<String, Double> calculatePassFailRates() {
        long total = courses.size();
        long passed = courses.stream().filter(Course::isPassed).count();
        double passRate = (double) passed / total;
        double failRate = 1.0 - passRate;

        Map<String, Double> rates = new HashMap<>();
        rates.put("Pass Rate", passRate);
        rates.put("Fail Rate", failRate);
        return rates;
    }

    public Map<String, Double> analyzePrerequisiteImpact() {
        Map<String, Double> impacts = new HashMap<>();

        for (Course course : courses) {
            if (!course.prerequisites.isEmpty()) {
                for (String prerequisiteCode : course.prerequisites) {
                    Course prerequisiteCourse = courses.stream()
                            .filter(c -> c.code.equals(prerequisiteCode))
                            .findFirst()
                            .orElse(null);

                    if (prerequisiteCourse != null) {
                        double impact = course.gradeToPoint() - prerequisiteCourse.gradeToPoint();
                        impacts.put(course.code + " influenced by " + prerequisiteCourse.code, impact);
                    }
                }
            }
        }
        return impacts;
    }


    public static void main(String[] args) {
        Transcript transcript = new Transcript();
        // Add courses with some example prerequisites (for simplicity, using course codes directly)
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

        double undergradGPA = transcript.calculateUndergraduateGPA();
        System.out.printf("Undergraduate GPA: %.2f%n", undergradGPA);

        double graduateGPA = transcript.calculateGraduateGPA();
        System.out.printf("Graduate GPA: %.2f%n", graduateGPA);

        double majorUndergradGPA = transcript.calculateMajorUndergradGPA();
        System.out.printf("Major Undergraduate GPA: %.2f%n", majorUndergradGPA);

        // Calculate and display GPAs
        Map<String, Double> gpas = transcript.calculateAllGPAs();
        gpas.forEach((k, v) -> System.out.printf("%s: %.2f%n", k, v));

        // Calculate and display pass/fail rates
        Map<String, Double> passFailRates = transcript.calculatePassFailRates();
        passFailRates.forEach((k, v) -> System.out.printf("%s: %.2f%%%n", k, v * 100));

        // Prerequisite impact analysis
        Map<String, Double> prerequisiteImpacts = transcript.analyzePrerequisiteImpact();
        prerequisiteImpacts.forEach((k, v) -> System.out.println(k + ": " + v));
    }
}