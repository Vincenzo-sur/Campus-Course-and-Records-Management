package edu.ccrm.cli;

import edu.ccrm.service.StudentService;
import edu.ccrm.service.CourseService;
import edu.ccrm.io.FileService;
import edu.ccrm.domain.*;
import edu.ccrm.domain.enums.Semester;
import edu.ccrm.domain.enums.Grade;
import edu.ccrm.exceptions.DuplicateEnrollmentException;
import edu.ccrm.exceptions.MaxCreditLimitExceededException;
import edu.ccrm.util.ValidationUtil;
import edu.ccrm.util.RecursionUtil;
import edu.ccrm.config.AppConfig;

import java.util.Scanner;

public class CLIMenu {
    private StudentService studentService;
    private CourseService courseService;
    private FileService fileService;
    private Scanner scanner;
    
    public CLIMenu() {
        this.studentService = new StudentService();
        this.courseService = new CourseService();
        this.fileService = new FileService();
        this.scanner = new Scanner(System.in);
        initializeSampleData();
    }
    
    private void initializeSampleData() {
        // Add sample students
        studentService.addStudent(new Student("S001", "2023001", "John Doe", "john.doe@university.edu"));
        studentService.addStudent(new Student("S002", "2023002", "Jane Smith", "jane.smith@university.edu"));
        
        // Add sample courses
        courseService.addCourse(new Course.Builder("CS101")
            .title("Introduction to Programming")
            .credits(3)
            .instructor("Dr. Smith")
            .semester(Semester.SPRING)
            .department("Computer Science")
            .build());
            
        courseService.addCourse(new Course.Builder("MATH101")
            .title("Calculus I")
            .credits(4)
            .instructor("Dr. Johnson")
            .semester(Semester.FALL)
            .department("Mathematics")
            .build());
    }
    
    public void showMainMenu() {
        while (true) {
            System.out.println("\n=== CAMPUS COURSE & RECORDS MANAGER ===");
            System.out.println("1. Student Management");
            System.out.println("2. Course Management");
            System.out.println("3. Enrollment & Grading");
            System.out.println("4. File Operations");
            System.out.println("5. Backup Operations");
            System.out.println("6. Utility Functions");
            System.out.println("7. Exit");
            System.out.print("Choose an option (1-7): ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                
                switch (choice) {
                    case 1: showStudentMenu(); break;
                    case 2: showCourseMenu(); break;
                    case 3: showEnrollmentMenu(); break;
                    case 4: showFileMenu(); break;
                    case 5: showBackupMenu(); break;
                    case 6: showUtilityMenu(); break;
                    case 7: 
                        System.out.println("Thank you for using CCRM. Goodbye!");
                        return;
                    default: System.out.println("Invalid option!");
                }
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine();
            }
        }
    }
    
    private void showStudentMenu() {
        while (true) {
            System.out.println("\n--- Student Management ---");
            System.out.println("1. List All Students");
            System.out.println("2. Add New Student");
            System.out.println("3. View Student Details");
            System.out.println("4. Generate Transcript");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1: listAllStudents(); break;
                case 2: addNewStudent(); break;
                case 3: viewStudentDetails(); break;
                case 4: generateTranscript(); break;
                case 5: return;
                default: System.out.println("Invalid option!");
            }
        }
    }
    
    private void listAllStudents() {
        System.out.println("\n--- All Students ---");
        for (Student student : studentService.getAllStudents()) {
            System.out.println(student.getDisplayInfo());
        }
    }
    
    private void addNewStudent() {
        System.out.println("\n--- Add New Student ---");
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        
        System.out.print("Enter Registration Number: ");
        String regNo = scanner.nextLine();
        
        System.out.print("Enter Full Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        
        if (ValidationUtil.isValidEmail(email)) {
            Student student = new Student(id, regNo, name, email);
            studentService.addStudent(student);
            System.out.println("Student added successfully!");
        } else {
            System.out.println("Invalid email format!");
        }
    }
    
    private void viewStudentDetails() {
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        Student student = studentService.getStudent(id);
        if (student != null) {
            System.out.println(student.getDisplayInfo());
        } else {
            System.out.println("Student not found!");
        }
    }
    
    private void showCourseMenu() {
        while (true) {
            System.out.println("\n--- Course Management ---");
            System.out.println("1. List All Courses");
            System.out.println("2. Add New Course");
            System.out.println("3. Search Courses");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1: listAllCourses(); break;
                case 2: addNewCourse(); break;
                case 3: searchCourses(); break;
                case 4: return;
                default: System.out.println("Invalid option!");
            }
        }
    }
    
    private void listAllCourses() {
        System.out.println("\n--- All Courses ---");
        for (Course course : courseService.getAllCourses()) {
            System.out.println(course.toString());
        }
    }
    
    private void addNewCourse() {
        System.out.println("\n--- Add New Course ---");
        System.out.print("Enter Course Code: ");
        String code = scanner.nextLine();
        
        System.out.print("Enter Course Title: ");
        String title = scanner.nextLine();
        
        System.out.print("Enter Credits: ");
        int credits = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Enter Instructor: ");
        String instructor = scanner.nextLine();
        
        System.out.println("Available Semesters: SPRING, SUMMER, FALL");
        System.out.print("Enter Semester: ");
        String semesterStr = scanner.nextLine().toUpperCase();
        
        System.out.print("Enter Department: ");
        String department = scanner.nextLine();
        
        try {
            Semester semester = Semester.valueOf(semesterStr);
            Course course = new Course.Builder(code)
                .title(title)
                .credits(credits)
                .instructor(instructor)
                .semester(semester)
                .department(department)
                .build();
                
            courseService.addCourse(course);
            System.out.println("Course added successfully!");
        } catch (Exception e) {
            System.out.println("Error adding course: " + e.getMessage());
        }
    }
    
    private void searchCourses() {
        System.out.print("Enter search keyword: ");
        String keyword = scanner.nextLine();
        System.out.println("Search results:");
        for (Course course : courseService.searchCourses(keyword)) {
            System.out.println(course.toString());
        }
    }
    
    private void showEnrollmentMenu() {
        while (true) {
            System.out.println("\n--- Enrollment & Grading ---");
            System.out.println("1. Enroll Student in Course");
            System.out.println("2. Record Marks");
            System.out.println("3. View Student Enrollments");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1: enrollStudent(); break;
                case 2: recordMarks(); break;
                case 3: viewStudentEnrollments(); break;
                case 4: return;
                default: System.out.println("Invalid option!");
            }
        }
    }
    
    private void enrollStudent() {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();
        
        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine();
        
        try {
            studentService.enrollStudentInCourse(studentId, courseCode, courseService);
            System.out.println("Enrollment successful!");
        } catch (Exception e) {
            System.out.println("Enrollment error: " + e.getMessage());
        }
    }
    
    private void recordMarks() {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();
        
        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine();
        
        System.out.print("Enter Marks (0-100): ");
        double marks = scanner.nextDouble();
        scanner.nextLine();
        
        Student student = studentService.getStudent(studentId);
        if (student != null) {
            for (Enrollment enrollment : student.getEnrollments()) {
                if (enrollment.getCourseCode().equals(courseCode)) {
                    enrollment.recordMarks(marks);
                    System.out.println("Marks recorded successfully!");
                    return;
                }
            }
            System.out.println("Student is not enrolled in this course!");
        } else {
            System.out.println("Student not found!");
        }
    }
    
    private void viewStudentEnrollments() {
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        Student student = studentService.getStudent(id);
        if (student != null) {
            System.out.println("Enrollments for " + student.getFullName() + ":");
            for (Enrollment enrollment : student.getEnrollments()) {
                System.out.println(enrollment.toString());
            }
        } else {
            System.out.println("Student not found!");
        }
    }
    
    private void generateTranscript() {
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        Student student = studentService.getStudent(id);
        if (student != null) {
            System.out.println(studentService.generateTranscript(student, courseService));
        } else {
            System.out.println("Student not found!");
        }
    }
    
    private void showFileMenu() {
        System.out.println("\n--- File Operations ---");
        try {
            fileService.exportStudentsToCSV(studentService.getAllStudents(), "students.csv");
            fileService.exportCoursesToCSV(courseService.getAllCourses(), "courses.csv");
            System.out.println("Data exported successfully!");
        } catch (Exception e) {
            System.out.println("Error exporting data: " + e.getMessage());
        }
    }
    
    private void showBackupMenu() {
        System.out.println("\n--- Backup Operations ---");
        try {
            fileService.backupData();
            long size = fileService.calculateBackupSize();
            System.out.println("Backup size: " + size + " bytes");
        } catch (Exception e) {
            System.out.println("Error during backup: " + e.getMessage());
        }
    }
    
    private void showUtilityMenu() {
        System.out.println("\n--- Utility Functions ---");
        System.out.print("Enter number for factorial calculation: ");
        int num = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Factorial of " + num + " is: " + RecursionUtil.factorial(num));
        
        System.out.print("Enter length for Fibonacci sequence: ");
        int fib = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Fibonacci sequence: ");
        for (int i = 0; i < fib; i++) {
            System.out.print(RecursionUtil.fibonacci(i) + " ");
        }
        System.out.println();
    }
}