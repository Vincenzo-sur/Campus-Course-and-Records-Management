package edu.ccrm.domain;

import java.time.LocalDateTime;

public abstract class Person {
    protected String id;
    protected String fullName;
    protected String email;
    protected LocalDateTime createdDate;
    protected boolean active;
    
    public Person(String id, String fullName, String email) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.createdDate = LocalDateTime.now();
        this.active = true;
    }
    
    public abstract String getDisplayInfo();
    
    // Getters and setters
    public String getId() { return id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    
    @Override
    public String toString() {
        return String.format("Person[id=%s, name=%s, email=%s]", id, fullName, email);
    }
}