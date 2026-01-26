package com.kishor.logger;

import java.util.Scanner;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

// Main class
public class Main extends Application {

    // Number of Cabins in the lab
    private static int cabinCount = 10;

    // Main event loop for the library app
    private static void mainLoop(Lab lab, Scanner scanner) {
        String name;
        String id;
        // Event loop
        while(true) {
            name = null;
            id = null;

            lab.showAvailableCabins();

            // NOTE: To exit the app purposefully, Input '\n' (aka a blank line (or) press enter key) on input of the id
            System.out.print("Enter the Name: ");
            name = scanner.nextLine();
            System.out.print("Enter the ID: ");
            id = scanner.nextLine();

            // Exit control
            if (id.trim().equals("")) {
                System.out.println("Null String");
                break;
            }

            // Method on Lab class to enter the program loggin procedure
            lab.studentArrivesOrLeaves(name.toLowerCase(), id.toLowerCase());

            // // Simple newline catcher
            // scanner.nextLine();
        }
        scanner.close();
    }

    // NOTE: This is testing version where the gui starts up after the cli version
    // JavaFX Testing
    @Override
        public void start(Stage stage) {
            String javaVersion = System.getProperty("java.version");
            String javaFXVersion = System.getProperty("javafx.version");

            Label l = new Label("Hello, JavaFX " + javaFXVersion + " running on java " + javaVersion + " and it's great.");
            Scene scene = new Scene(new StackPane(l), 640, 480);

            stage.setScene(scene);
            stage.setTitle("JavaFX example");
            stage.show();
        }



    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Lab lab = new Lab(cabinCount, sc);
        mainLoop(lab, sc);
        sc.close();
        // launch(args);
    }
}
