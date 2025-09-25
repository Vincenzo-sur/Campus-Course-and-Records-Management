package edu.ccrm.domain;

import java.util.ArrayList;
import java.util.List;

public class Student extends Person {
    private String regNo;
    private List<Enrollment> enrollments;
    
    public Student(String id, String regNo, String fullName, String email) {
        super(id, fullName, email);
        this.regNo = regNo;
        this.enrollments = new ArrayList<>();
    }
    
    @Override
    public String getDisplayInfo() {
        return String.format("Student: %s (%s) - %s", fullName, regNo, email);
    }
    
    public void addEnrollment(Enrollment enrollment) {
        enrollments.add(enrollment);
    }
    
    public boolean removeEnrollment(String courseCode) {
        return enrollments.removeIf(e -> e.getCourseCode().equals(courseCode));
    }
    
    // Getters
    public String getRegNo() { return regNo; }
    public List<Enrollment> getEnrollments() { return new ArrayList<>(enrollments); }
    
    @Override
    public String toString() {
        return String.format("Student[id=%s, regNo=%s, name=%s]", id, regNo, fullName);
    }
}