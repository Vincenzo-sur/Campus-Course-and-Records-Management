# Campus Course & Records Manager (CCRM)

## Project Overview
A comprehensive Java console application for managing campus academic records.

## Features
- Student Management (CRUD operations)
- Course Management with Builder pattern  
- Enrollment System with business rules
- Grade Management and GPA calculation
- File I/O with NIO.2
- Backup system with recursion
- Comprehensive reporting

## Technical Stack
- Java SE 17+
- OOP Principles (Encapsulation, Inheritance, Polymorphism, Abstraction)
- Design Patterns (Singleton, Builder)
- Java Streams API
- NIO.2 File Operations
- Date/Time API
- Exception Handling
- Recursion

## How to Run in Eclipse
1. Import project into Eclipse
2. Ensure JDK 17+ is configured
3. Right-click `CCRMMain.java` → Run As → Java Application
4. Follow the menu prompts

## Java Evolution Timeline
- 1996: Java 1.0 (Oak)
- 2004: Java 5 (Generics, Enums, Autoboxing)
- 2014: Java 8 (Lambdas, Streams API)
- 2018: Java 11 (LTS)
- 2021: Java 17 (LTS - Current)

## Java Platforms Comparison
| Platform | Purpose | Use Case |
|----------|---------|----------|
| Java ME | Micro Edition | Embedded systems, IoT |
| Java SE | Standard Edition | Desktop apps, Core Java |
| Java EE | Enterprise Edition | Large-scale systems |

## Architecture
- **JVM**: Bytecode execution engine
- **JRE**: JVM + Runtime libraries  
- **JDK**: JRE + Development tools

## Project Structure
src/edu/ccrm/
├── config/ # Singleton configuration
├── domain/ # Entity classes (OOP)
├── service/ # Business logic
├── io/ # File operations (NIO.2)
├── util/ # Utilities (Recursion)
├── cli/ # User interface
└── exceptions/ # Custom exceptions


## Sample Operations
1. Manage students and courses
2. Enroll students with credit limits
3. Record grades and calculate GPA
4. Export data to CSV
5. Create backups with timestamps
6. Generate transcripts