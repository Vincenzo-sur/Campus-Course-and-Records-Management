package edu.ccrm.domain;

import edu.ccrm.domain.enums.Grade;
import java.time.LocalDateTime;

public class Enrollment {
    private String studentId;
    private String courseCode;
    private LocalDateTime enrollmentDate;
    private Grade grade;
    private Double marks;
    
    public Enrollment(String studentId, String courseCode) {
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.enrollmentDate = LocalDateTime.now();
        this.grade = Grade.NOT_GRADED;
        this.marks = null;
    }
    
    public void recordMarks(double marks) {
        this.marks = marks;
        this.grade = Grade.fromScore(marks);
    }
    
    // Getters
    public String getStudentId() { return studentId; }
    public String getCourseCode() { return courseCode; }
    public LocalDateTime getEnrollmentDate() { return enrollmentDate; }
    public Grade getGrade() { return grade; }
    public Double getMarks() { return marks; }
    
    @Override
    public String toString() {
        return String.format("Enrollment[student=%s, course=%s, grade=%s]", studentId, courseCode, grade);
    }
}