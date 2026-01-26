# Library Lab Logger

**Current Status:** Command Line Interface (CLI) Prototype

### The Problem
In our university computer lab, high partition walls create a quiet environment but block visibility. Students often struggle to see which cabins are empty, forcing them to roam around and cause distractions while searching for a seat.

### The Solution
This project is a Java-based application designed to track real-time cabin occupancy. It replaces the manual ledger system with a digital "Check-in/Check-out" workflow, automatically identifying available seats and preventing double-booking.

### Technology Stack
* **Language:** Java 17+
* **Build Tool:** Apache Maven
* **Architecture:** Standard Maven Directory Layout

### Project Structure
The source code follows the below mentioned package structure:
`src/main/java/com/kishor/logger/`

### How to Run
This project uses Maven for compilation and execution. You do not need to manually compile classes.

1.  **Prerequisite:** Ensure **Maven** (`mvn`) and the **Java JDK** are installed.
2.  **Navigate** to the project root directory (where `pom.xml` is located).
3.  **Run the application** using the following command:

```bash
mvn clean compile exec:java -Dexec.mainClass="com.kishor.logger.Main"
```

### Usage Guide

1. Check-In: Enter a Name and ID. If the ID is new, the system will ask for a Cabin Number from the list of available seats.

2. Check-Out: Enter the Name and ID of a student currently inside. The system will auto-detect the ID and process the exit.

3. Exit: Submit an empty string at the Name prompt to close the application.
