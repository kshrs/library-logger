# Library Lab Logger

**Current Status:** Iteration 2 - Interactive Console & Logic Core

### Project Overview
The Library Lab Logger is a Java application designed to solve the "blind search" problem in high-partition computer labs. It replaces the manual ledger system with a real-time tracking engine.

### Changes in Iteration 2
* **Interactive Game Loop:** The application now runs continuously, allowing multiple students to check in and out without restarting.
* **Smart Availability:** The system now automatically tracks and displays a list of available cabin numbers (`[1, 3, 4...]`) before every entry.
* **Duplicate Prevention:** Logic added to detect if a student ID is already logged in, automatically toggling to "Check-out" mode.
* **Input Handling:** robust `Scanner` implementation to handle integer and string inputs within a CLI environment.

### Project Structure
* `Main.java`: Handles the application lifecycle and user input loop.
* `Lab.java`: The "Brain" of the system. Manages the list of cabins, tracks occupied seats, and validates logic (e.g., ensuring a student doesn't sit in an occupied seat).
* `Cabin.java`: Represents a physical seat. Stores the state (`isOccupied`) and the `Student` object.
* `Student.java`: Data model holding student credentials.

### How to Run
1.  **Compile:**
    Navigate to the source folder and compile all classes:
    ```bash
    javac logger/*.java
    ```

2.  **Run:**
    Execute the Main class:
    ```bash
    java logger.Main
    ```

### Usage Guide
1.  **Check-In:** Enter a Name and ID. If the ID is new, the system will ask for a Cabin Number.
2.  **Check-Out:** Enter the Name and ID of a student currently inside. The system will auto-detect the ID and process the exit.
3.  **Exit Program:** Press Enter (submit an empty string) at the Name prompt to shut down the logger.
