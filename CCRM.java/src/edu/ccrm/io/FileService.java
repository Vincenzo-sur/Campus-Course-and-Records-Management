package edu.ccrm.io;

import edu.ccrm.domain.Student;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.enums.Semester;
import edu.ccrm.config.AppConfig;

import java.nio.file.*;
import java.io.IOException;
import java.util.*;

public class FileService {
    private AppConfig config;
    
    public FileService() {
        this.config = AppConfig.getInstance();
    }
    
    public void exportStudentsToCSV(List<Student> students, String filename) throws IOException {
        Path filePath = config.getDataDirectory().resolve(filename);
        List<String> lines = new ArrayList<>();
        
        for (Student student : students) {
            String line = String.format("%s,%s,%s,%s,%s", 
                student.getId(), student.getRegNo(), student.getFullName(), 
                student.getEmail(), student.isActive());
            lines.add(line);
        }
        
        Files.write(filePath, lines);
        System.out.println("Exported " + students.size() + " students to " + filePath);
    }
    
    public void exportCoursesToCSV(List<Course> courses, String filename) throws IOException {
        Path filePath = config.getDataDirectory().resolve(filename);
        List<String> lines = new ArrayList<>();
        
        for (Course course : courses) {
            String line = String.format("%s,%s,%d,%s,%s,%s,%s", 
                course.getCode(), course.getTitle(), course.getCredits(), 
                course.getInstructor(), course.getSemester(), course.getDepartment(), course.isActive());
            lines.add(line);
        }
        
        Files.write(filePath, lines);
        System.out.println("Exported " + courses.size() + " courses to " + filePath);
    }
    
    public void backupData() throws IOException {
        Path backupPath = config.getBackupDirectory().resolve("backup_" + 
            java.time.LocalDateTime.now().format(config.getDateFormatter()));
        
        if (!Files.exists(backupPath)) {
            Files.createDirectories(backupPath);
        }
        
        // Copy all files from data directory to backup directory
        if (Files.exists(config.getDataDirectory())) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(config.getDataDirectory())) {
                for (Path source : stream) {
                    if (Files.isRegularFile(source)) {
                        Path destination = backupPath.resolve(source.getFileName());
                        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            }
        }
        
        System.out.println("Backup created at: " + backupPath);
    }
    
    public long calculateBackupSize() throws IOException {
        Path backupDir = config.getBackupDirectory();
        return calculateDirectorySize(backupDir);
    }
    
    private long calculateDirectorySize(Path directory) throws IOException {
        long size = 0;
        
        if (Files.isDirectory(directory)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
                for (Path path : stream) {
                    size += calculateDirectorySize(path);
                }
            }
        } else {
            size = Files.size(directory);
        }
        
        return size;
    }
}