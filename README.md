# Library Lab Logger

**Current Status:** 
* **CLI:** Stable
* **GUI:** Beta / Testing Phase

![GUI Demo](./assets/gui_demo.png)

### Project Overview
The Library Lab Logger addresses the "blind search" problem in high-partition computer labs. It replaces the manual ledger system with a digital tracking engine to manage student check-ins, check-outs, and real-time cabin availability.

**New in v0.0.10:** Automatic Data Persistence. All entries are saved to daily CSV logs for administrative auditing.

### Architecture Updates
The application now supports two distinct modes of operation, handled dynamically by `Main.java`:
1.  **CLI Mode (Default):** A text-based interface for low-resource environments.
2.  **GUI Mode (Experimental):** A visual dashboard built with JavaFX, featuring an interactive grid of cabins and form-based entry.

### Project Structure
The logic is encapsulated in the package `com.kishor.logger`:

* **`Main.java`**: It is the entry point of the application. It checks command-line arguments to decide whether to launch the JavaFX lifecycle (`start()`) or the interactive Console Loop (`mainLoop`).
* **`Lab.java`**: The core logic engine. Manages the list of `Cabin` objects, tracks occupancy, and serves as the single source of truth for both the GUI and CLI.
* **`Cabin.java`**: Represents a physical workspace. Stores state (`isOccupied`) and the current `Student` object.
* **`Student.java`**: Data model representing a user (Name and ID).
* **`LogManager.java`**: Handles file I/O operations. Writes session data to `db/YYYY-MM-DD.csv`.

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
To launch the JavaFX gui application:

```bash
mvn clean compile exec:java -Dexec.mainClass="com.kishor.logger.Main" -Dexec.args="--gui"
```

### Usage Guide
**Data Persistence (Logs):**
The application automatically generates a daily log file in the `db/` folder located in the project root.

- `Format:` YYYY-MM-DD.csv

- `Content:` Time-stamped records of every Check-In and Check-Out event.

**CLI Controls**

- `Check-In:` Enter Name and ID. Select a cabin from the printed list of available seats.

- `Check-Out:` Enter the Name and ID of a student currently inside.

- `Exit:` Submit an empty string (press Enter) at the ID prompt.

**GUI Controls (Testing)**

- `Enter Details:` Fill in the "Name" and "ID" text fields on the left.

- `Select Cabin:` Click any Green (Empty) button in the grid.

- `Process Action:` The system will attempt to log the student into that cabin.

#### Visual Feedback:

- `Green:` Empty Cabin

- `Red:` Occupied Cabin

- `Yellow:` Currently Selected Target
