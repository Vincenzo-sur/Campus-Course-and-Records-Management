package edu.ccrm.domain;

import edu.ccrm.domain.enums.Semester;

public class Course {
    private final String code;
    private String title;
    private int credits;
    private String instructor;
    private Semester semester;
    private String department;
    private boolean active;
    
    // Private constructor
    private Course(Builder builder) {
        this.code = builder.code;
        this.title = builder.title;
        this.credits = builder.credits;
        this.instructor = builder.instructor;
        this.semester = builder.semester;
        this.department = builder.department;
        this.active = true;
    }
    
    // Builder class
    public static class Builder {
        private final String code;
        private String title = "Untitled Course";
        private int credits = 3;
        private String instructor = "TBA";
        private Semester semester = Semester.SPRING;
        private String department = "General";
        
        public Builder(String code) {
            this.code = code;
        }
        
        public Builder title(String title) {
            this.title = title;
            return this;
        }
        
        public Builder credits(int credits) {
            this.credits = credits;
            return this;
        }
        
        public Builder instructor(String instructor) {
            this.instructor = instructor;
            return this;
        }
        
        public Builder semester(Semester semester) {
            this.semester = semester;
            return this;
        }
        
        public Builder department(String department) {
            this.department = department;
            return this;
        }
        
        public Course build() {
            return new Course(this);
        }
    }
    
    // Getters and setters
    public String getCode() { return code; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }
    public String getInstructor() { return instructor; }
    public void setInstructor(String instructor) { this.instructor = instructor; }
    public Semester getSemester() { return semester; }
    public void setSemester(Semester semester) { this.semester = semester; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    
    @Override
    public String toString() {
        return String.format("%s - %s (%d credits)", code, title, credits);
    }
}