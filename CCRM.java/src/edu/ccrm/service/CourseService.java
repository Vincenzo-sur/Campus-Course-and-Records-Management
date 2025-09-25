package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.enums.Semester;

import java.util.*;

public class CourseService {
    private Map<String, Course> courses = new HashMap<>();
    
    public void addCourse(Course course) {
        courses.put(course.getCode(), course);
    }
    
    public Course getCourse(String code) {
        return courses.get(code);
    }
    
    public List<Course> getAllCourses() {
        return new ArrayList<>(courses.values());
    }
    
    public List<Course> getActiveCourses() {
        List<Course> activeCourses = new ArrayList<>();
        for (Course course : courses.values()) {
            if (course.isActive()) {
                activeCourses.add(course);
            }
        }
        return activeCourses;
    }
    
    public boolean deactivateCourse(String courseCode) {
        Course course = courses.get(courseCode);
        if (course != null) {
            course.setActive(false);
            return true;
        }
        return false;
    }
    
    public List<Course> getCoursesByInstructor(String instructor) {
        List<Course> result = new ArrayList<>();
        for (Course course : courses.values()) {
            if (course.getInstructor().equalsIgnoreCase(instructor)) {
                result.add(course);
            }
        }
        return result;
    }
    
    public List<Course> getCoursesByDepartment(String department) {
        List<Course> result = new ArrayList<>();
        for (Course course : courses.values()) {
            if (course.getDepartment().equalsIgnoreCase(department)) {
                result.add(course);
            }
        }
        return result;
    }
    
    public List<Course> getCoursesBySemester(Semester semester) {
        List<Course> result = new ArrayList<>();
        for (Course course : courses.values()) {
            if (course.getSemester() == semester) {
                result.add(course);
            }
        }
        return result;
    }
    
    public List<Course> searchCourses(String keyword) {
        List<Course> result = new ArrayList<>();
        for (Course course : courses.values()) {
            if (course.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                course.getCode().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(course);
            }
        }
        return result;
    }
}