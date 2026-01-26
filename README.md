# Library Lab Logger

> ** NOTE: Experimental GUI Support**
> This project currently defaults to a Command Line Interface (CLI). An early-stage JavaFX GUI implementation exists within `Main.java` but is commented out/inactive by default.
>
> To enable the GUI:
> 1. Uncomment the `launch(args)` method call in `Main.java`.
> 2. The GUI will launch **only after** the CLI session is terminated (by pressing Enter at the prompt).
> 3. Use the specific Maven command listed below to run it.

### Project Overview
The Library Lab Logger addresses the "blind search" problem in high-partition computer labs. It replaces the manual ledger system with a digital tracking engine to manage student check-ins, check-outs, and real-time cabin availability.

### Project Structure (Class Breakdown)
The logic is encapsulated in the package `com.kishor.logger`:

* **`Main.java`**: The entry point. Handles the application lifecycle, the interactive CLI "Game Loop," and the experimental JavaFX `Application` entry.
* **`Lab.java`**: The core logic engine. Manages the list of `Cabin` objects, tracks occupancy, handles input validation, and maintains the list of available seats.
* **`Cabin.java`**: Represents a physical workspace. Stores state (`isOccupied`) and the current `Student` object.
* **`Student.java`**: Data model representing a user (Name and ID).
* **`CabinType.java`**: Enum defining cabin amenities (e.g., `WITH_COMPUTER`, `WITHOUT_COMPUTER`).
* **`LabMode.java`**: Enum defining lab structure (e.g., `STANDARD`, `SPECIAL`).

---

### Installation & Setup

This project uses **Apache Maven** for dependency management. You do **not** need to manually download JAR files (like JavaFX); Maven handles this automatically.

**Prerequisites:**
* Java JDK 17+
* Apache Maven

1. **Install Maven (Arch Linux):**

```bash
sudo pacman -S maven
```

2. **Install Dependencies & Build:**
Run this command in the project root (where pom.xml is located) to download required libraries and compile the code:

```Bash
mvn clean compile
```

### How to Run
**Option 1: CLI Mode (Standard)**

To run the text-based logger interface:

```bash
mvn exec:java -Dexec.mainClass="com.kishor.logger.Main"
```

**Usage:**

1. Check-In: Enter Name and ID. Select a cabin from the available list.

2. Check-Out: Enter the Name and ID of a current student.

3. Exit: Press Enter (empty input) at the ID prompt to close the loop.

**Option 2: GUI Mode (Experimental)**

If you have uncommented the launch(args) line in Main.java, use this command to ensure JavaFX libraries are loaded correctly:

```bash
mvn clean javafx:run
```

**Note:** As per current logic, the CLI loop runs first. The GUI window will open only after you exit the CLI loop.
