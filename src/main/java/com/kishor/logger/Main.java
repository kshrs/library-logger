package com.kishor.logger;

import java.util.Scanner;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
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
                // Better than break or return Since sometimes it just hangs at the end.
                System.exit(0);
            }

            // Method on Lab class to enter the program loggin procedure
            lab.studentArrivesOrLeaves(name.toLowerCase(), id.toLowerCase(), scanner);

            // // Simple newline catcher
            // scanner.nextLine();
        }
    }

    // NOTE: This is testing version where the gui starts up
    // JavaFX Testing
    private ArrayList<Button> cabinGridButtons = new ArrayList<Button>();
    private static final int COLUMNS = 5;
    @Override
        public void start(Stage stage) {
            Lab lab = new Lab(cabinCount);

            Label title = new Label("library logger");

            /* Input Form */
            GridPane inputForm = new GridPane(10, 10);
            TextField nameTextField = new TextField();
            TextField idTextField = new TextField();
            inputForm.add(new Label("Enter Name: "),0,0);
            inputForm.add(nameTextField,1,0);
            inputForm.add(new Label("Enter ID: "),0,1);
            inputForm.add(idTextField,1,1);

            GridPane cabinGrid = new GridPane(10, 10);
            cabinGrid.setPadding(new Insets(10, 10, 10, 10));

            // Cabin Grid Button Styles
            String defaultStyle = "-fx-background-color: #0dac50; -fx-border-color: #363636; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx_border-width: 15px;";
            String selectStyle = "-fx-background-color: #ffffc5; -fx-border-color: #363636; -fx_border-width: 15px;";
            String occupiedStyle = "-fx-background-color: #ff2c2c; -fx-border-color: #363636; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx_border-width: 15px;";

            int row, col;
            row = col = 0;
            for (int i=0; i<cabinCount; ++i) {
                int cabinID = i + 1;
                Button btn = new Button("Cabin: " + cabinID);
                btn.setPrefSize(100, 60);
                btn.setStyle(defaultStyle);

                col = i % COLUMNS; // cols = 0, 1, 2, 3, 4 if COLUMNS = 5
                row = i / COLUMNS; // rows = 0, 0, 0, 0, 0 then 1,1,... to 5,5,... if COLUMNS = 5
                cabinGrid.add(btn, col, row);

                btn.setOnAction(event -> {
                    // Set default color for all the buttons which are not occupied and selected
                    // NOTE: Selection color is painted on top of this default color
                    for (Button b : cabinGridButtons) {
                        b.setStyle(defaultStyle);
                    }
                    btn.setStyle(selectStyle);

                    if (!idTextField.getText().trim().equals("")) {
                        lab.studentArrivesOrLeaves(nameTextField.getText(), idTextField.getText(), cabinID);
                        nameTextField.setText("");
                        idTextField.setText("");
                    } else {
                        System.out.println("Null ID! Retry.....");
                    }

                    // Color green on selected cabins after each entry
                    for (Integer j : lab.getOccupiedCabins()) {
                        cabinGridButtons.get(j-1).setStyle(occupiedStyle);
                    }
                });

                // Color green on selected cabins on startup
                for (Integer j : lab.getOccupiedCabins()) {
                    cabinGridButtons.get(j-1).setStyle(occupiedStyle);
                }

                // Add the button to the cabinGridButtons arraylist to modify the colors on click
                // NOTE: Used only inside the Button.setOnAction() method
                cabinGridButtons.add(btn);
            }

            BorderPane primaryPane = new BorderPane();
            primaryPane.setPadding(new Insets(10, 10, 10, 10));
            primaryPane.setTop(new StackPane(title));
            primaryPane.setLeft(inputForm);
            primaryPane.setRight(cabinGrid);

            Scene scene = new Scene(primaryPane, 840, 480);


            stage.setScene(scene);
            stage.setTitle("JavaFX example");
            stage.show();
        }



    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("--gui")) {
            launch();
        } else {
            Scanner sc = new Scanner(System.in);
            Lab lab = new Lab(cabinCount);
            mainLoop(lab, sc);
            sc.close();
        }
    }
}
