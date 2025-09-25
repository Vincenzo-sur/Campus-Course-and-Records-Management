package edu.ccrm.CCRMMain.java;

import edu.ccrm.cli.CLIMenu;
import edu.ccrm.config.AppConfig;

public class CCRMMain {
    public static void main(String[] args) {
        System.out.println("Starting Campus Course & Records Manager (CCRM)...");
        
        // Test singleton pattern
        AppConfig config = AppConfig.getInstance();
        System.out.println("Configuration loaded: " + config.getDataDirectory());
        
        // Start the application
        CLIMenu menu = new CLIMenu();
        menu.showMainMenu();
        
        System.out.println("CCRM application terminated.");
    }
}