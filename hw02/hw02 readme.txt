Additional Data Processing in Transcript Analysis System - 


Below is an overview of these added features:


1. Pass/Fail Rate Calculation
This feature calculates the overall pass and fail rates across all courses listed in the transcript. The system considers a grade of 'D' or higher as a pass and anything below as a fail.
* Method: calculatePassFailRates()
* Output: The method returns a map containing two entries: "Pass Rate" and "Fail Rate", each representing the respective rates as percentages.


2. Prerequisite Course Impact Analysis
This advanced analysis feature evaluates the impact of prerequisite course grades on subsequent course grades. The goal is to understand whether and how prior knowledge (as reflected in the grades of prerequisite courses) influences performance in advanced courses.
* Method: analyzePrerequisiteImpact()
* Output: The method returns a map where each entry correlates a course (key) with the grade impact (value) from its prerequisite. The impact is calculated as the grade point difference between the course and its prerequisite.
This feature provides insights that can help educators identify how well the foundational courses prepare students for more advanced coursework. It might also highlight which prerequisite courses are most critical for student success in subsequent courses.