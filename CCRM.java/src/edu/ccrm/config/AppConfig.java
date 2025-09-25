package edu.ccrm.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

public class AppConfig {
    private static AppConfig instance;
    private Path dataDirectory;
    private Path backupDirectory;
    private DateTimeFormatter dateFormatter;
    
    private AppConfig() {
        this.dataDirectory = Paths.get("data");
        this.backupDirectory = Paths.get("backups");
        this.dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        initializeDirectories();
    }
    
    public static AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }
    
    private void initializeDirectories() {
        try {
            java.nio.file.Files.createDirectories(dataDirectory);
            java.nio.file.Files.createDirectories(backupDirectory);
        } catch (Exception e) {
            System.err.println("Error creating directories: " + e.getMessage());
        }
    }
    
    public Path getDataDirectory() { return dataDirectory; }
    public Path getBackupDirectory() { return backupDirectory; }
    public DateTimeFormatter getDateFormatter() { return dateFormatter; }
}