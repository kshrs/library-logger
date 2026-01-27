# Library Lab Logger

**Current Status:** Hybrid Release
* **CLI:** Stable
* **GUI:** Beta / Testing Phase

![GUI Demo](./assets/gui_demo.png)

### Project Overview
The Library Lab Logger addresses the "blind search" problem in high-partition computer labs. It replaces the manual ledger system with a digital tracking engine to manage student check-ins, check-outs, and real-time cabin availability.

### Architecture Updates
The application now supports two distinct modes of operation, handled dynamically by `Main.java`:
1.  **CLI Mode (Default):** A robust text-based interface for low-resource environments.
2.  **GUI Mode (Experimental):** A visual dashboard built with JavaFX, featuring an interactive grid of cabins and form-based entry.

### Project Structure
The logic is encapsulated in the package `com.kishor.logger`:

* **`Main.java`**: The dual-mode entry point. It checks command-line arguments to decide whether to launch the JavaFX lifecycle (`start()`) or the interactive Console Loop (`mainLoop`).
* **`Lab.java`**: The core logic engine. Manages the list of `Cabin` objects, tracks occupancy, and serves as the single source of truth for both the GUI and CLI.
* **`Cabin.java`**: Represents a physical workspace. Stores state (`isOccupied`) and the current `Student` object.
* **`Student.java`**: Data model representing a user (Name and ID).

---

### How to Run

This project uses **Apache Maven** for building and execution.

**Prerequisites:**
* Java JDK 17+
* Apache Maven

1. **Install Maven (Arch Linux):**

```bash
sudo pacman -S maven
```

2. **Install Dependencies & Build:**
Run this command in the project root (where pom.xml is located) to download required libraries and compile the code:

```bash
mvn clean compile
```

#### 1. Standard CLI Mode (Default)
If no flags are provided, the application defaults to the Command Line Interface.

```bash
mvn clean compile exec:java -Dexec.mainClass="com.kishor.logger.Main" -Dexec.args="--cli"
```

> (**Note:** You can omit -Dexec.args="--cli" as the code defaults to this mode if no flag is found, but being explicit is recommended.)

#### 2. GUI Mode (Beta)
To launch the JavaFX visual dashboard:

```bash
mvn clean compile exec:java -Dexec.mainClass="com.kishor.logger.Main" -Dexec.args="--gui"
```

### Usage Guide
**CLI Controls**

- **Check-In:** Enter Name and ID. Select a cabin from the printed list of available seats.

- **Check-Out:** Enter the Name and ID of a student currently inside.

- **Exit:** Submit an empty string (press Enter) at the Name prompt.

**GUI Controls (Testing)**

- Enter Details: Fill in the "Name" and "ID" text fields on the left.

- Select Cabin: Click any Green (Empty) button in the grid.

- Process Action: The system will attempt to log the student into that cabin.

#### Visual Feedback:

- `Green`: Empty Cabin

- `Red`: Occupied Cabin

- `Yellow`: Currently Selected Target
