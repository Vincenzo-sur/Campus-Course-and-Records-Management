package edu.ccrm.service;

import edu.ccrm.domain.Student;
import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.enums.Grade;
import edu.ccrm.exceptions.DuplicateEnrollmentException;
import edu.ccrm.exceptions.MaxCreditLimitExceededException;

import java.util.*;

public class StudentService {
    private Map<String, Student> students = new HashMap<>();
    private final int MAX_CREDITS_PER_SEMESTER = 18;
    
    public void addStudent(Student student) {
        students.put(student.getId(), student);
    }
    
    public Student getStudent(String id) {
        return students.get(id);
    }
    
    public List<Student> getAllStudents() {
        return new ArrayList<>(students.values());
    }
    
    public List<Student> getActiveStudents() {
        List<Student> activeStudents = new ArrayList<>();
        for (Student student : students.values()) {
            if (student.isActive()) {
                activeStudents.add(student);
            }
        }
        return activeStudents;
    }
    
    public boolean deactivateStudent(String studentId) {
        Student student = students.get(studentId);
        if (student != null) {
            student.setActive(false);
            return true;
        }
        return false;
    }
    
    public void enrollStudentInCourse(String studentId, String courseCode, CourseService courseService) 
            throws DuplicateEnrollmentException, MaxCreditLimitExceededException {
        
        Student student = students.get(studentId);
        Course course = courseService.getCourse(courseCode);
        
        if (student == null || course == null) {
            throw new IllegalArgumentException("Student or course not found");
        }
        
        // Check for duplicate enrollment
        for (Enrollment enrollment : student.getEnrollments()) {
            if (enrollment.getCourseCode().equals(courseCode)) {
                throw new DuplicateEnrollmentException("Student already enrolled in this course");
            }
        }
        
        // Check credit limit
        int currentCredits = 0;
        for (Enrollment enrollment : student.getEnrollments()) {
            Course c = courseService.getCourse(enrollment.getCourseCode());
            if (c != null) {
                currentCredits += c.getCredits();
            }
        }
        
        if (currentCredits + course.getCredits() > MAX_CREDITS_PER_SEMESTER) {
            throw new MaxCreditLimitExceededException("Credit limit exceeded");
        }
        
        Enrollment enrollment = new Enrollment(studentId, courseCode);
        student.addEnrollment(enrollment);
    }
    
    public double calculateGPA(Student student, CourseService courseService) {
        List<Enrollment> enrollments = student.getEnrollments();
        if (enrollments.isEmpty()) return 0.0;
        
        double totalPoints = 0.0;
        int totalCredits = 0;
        
        for (Enrollment enrollment : enrollments) {
            Course course = courseService.getCourse(enrollment.getCourseCode());
            if (course != null && enrollment.getGrade() != Grade.NOT_GRADED) {
                totalPoints += enrollment.getGrade().getPoints() * course.getCredits();
                totalCredits += course.getCredits();
            }
        }
        
        return totalCredits > 0 ? totalPoints / totalCredits : 0.0;
    }
    
    public String generateTranscript(Student student, CourseService courseService) {
        StringBuilder transcript = new StringBuilder();
        transcript.append("OFFICIAL TRANSCRIPT\n");
        transcript.append("===================\n");
        transcript.append("Student: ").append(student.getFullName()).append("\n");
        transcript.append("ID: ").append(student.getId()).append("\n");
        transcript.append("Registration No: ").append(student.getRegNo()).append("\n");
        transcript.append("GPA: ").append(String.format("%.2f", calculateGPA(student, courseService))).append("\n\n");
        
        transcript.append("COURSES:\n");
        transcript.append("--------\n");
        
        for (Enrollment enrollment : student.getEnrollments()) {
            Course course = courseService.getCourse(enrollment.getCourseCode());
            if (course != null) {
                transcript.append(String.format("%s - %s - Credits: %d - Grade: %s\n",
                    course.getCode(), course.getTitle(), course.getCredits(), enrollment.getGrade()));
            }
        }
        
        return transcript.toString();
    }
}