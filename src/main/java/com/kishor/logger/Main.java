package com.kishor.logger;

import java.util.Scanner;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.stage.Stage;

import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.input.KeyCode;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

// Main class
public class Main extends Application {

    // Number of Cabins in the lab
    private static int cabinCount = 28;

    // ArrayList to hold the cabin buttons to display in the GUI
    private ArrayList<Button> cabinGridButtons = new ArrayList<Button>();

    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss"); // Pattern 23:54:01

    // Main event loop for the library app --cli version
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

    // All of the main window content
    private BorderPane createMainWindowContent(Lab lab) {


        TableView<Student> table = new TableView<>();
        table.setItems(Lab.studentList);

        TableColumn<Student, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Student, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Student, Integer> cabinIDCol = new TableColumn<>("Cabin ID");
        cabinIDCol.setCellValueFactory(new PropertyValueFactory<>("cabinID"));
        TableColumn<Student, String> checkStatusCol = new TableColumn<>("Check IN/OUT Status");
        checkStatusCol.setCellValueFactory(new PropertyValueFactory<>("checkStatus"));
        TableColumn<Student, String> checkTimeCol = new TableColumn<>("Check IN/OUT Time");
        checkTimeCol.setCellValueFactory(new PropertyValueFactory<>("checkTime"));

        table.getColumns().addAll(nameCol, idCol, cabinIDCol, checkStatusCol, checkTimeCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // NOTE: This variable is used only to show hints to the user
        Label hint = new Label();
        hint.setWrapText(true);

        Label title = new Label("library logger");
        title.setStyle("-fx-font-style: bold; -fx-font-size: 20px; -fx-border-width: 0 0 2px 0; -fx-border-style: solid; -fx-border-color: black;");

        Label timeField = new Label();

        // Time
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(1), event -> {
                timeField.setText(LocalTime.now().format(timeFormatter).toString());
            })
        );

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        /* Input Form | Left Pane */
        GridPane inputForm = new GridPane(10, 10);
        Label nameLabelField = new Label("");
        TextField idTextField = new TextField();
        idTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                hint.setText("Choose a cabin to make a log entry or exit");
            } else {
                hint.setText("");
            }
        });

        Label checkIn = new Label("IN");
        Label checkOut = new Label("OUT");
        inputForm.add(new Label("Enter ID: "), 0, 0);
        inputForm.add(idTextField, 1, 0);
        inputForm.add(new Label("Name: "), 0, 1);
        inputForm.add(nameLabelField, 1, 1);
        inputForm.add(new Label("Action Status: "), 0, 2);
        inputForm.add(checkIn, 0, 3);
        inputForm.add(new StackPane(timeField), 1, 3);
        inputForm.add(checkOut, 2, 3);

        GridPane cabinGrid = new GridPane(10, 10);
        cabinGrid.setPadding(new Insets(10, 10, 10, 10));

        String activeStatus = "-fx-background-color: #ff2c2c; -fx-border: none; -fx-padding: 10px;";
        String inactiveStatus = "-fx-background-color: #ffffff; -fx-border: none; -fx-padding: 10px;";

        // Default check in-out status to be inactive
        checkIn.setStyle(inactiveStatus);
        checkOut.setStyle(inactiveStatus);
        // Cabin Grid Button Styles
        String defaultStyle = "-fx-background-color: #0dac50; -fx-border-color: #363636; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx_border-width: 15px;";
        String selectStyle = "-fx-background-color: #ffffc5; -fx-border-color: #363636; -fx_border-width: 15px;";
        String occupiedStyle = "-fx-background-color: #ff2c2c; -fx-border-color: #363636; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx_border-width: 15px;";

        int row, col;
        row = col = 0;
        int columns = 4;
        for (int i=0; i<cabinCount; ++i) {
            int cabinID = i + 1;
            Button btn = new Button("Cabin: " + cabinID);
            btn.setPrefSize(100, 60);
            btn.setStyle(defaultStyle);

            col = i % columns; // cols = 0, 1, 2, 3, 4 if columns = 5
            row = i / columns; // rows = 0, 0, 0, 0, 0 then 1,1,... to 5,5,... if columns = 5
            cabinGrid.add(btn, col, row);

            // Add the button to the cabinGridButtons arraylist to modify the colors on click
            // NOTE: Used only inside the Button.setOnAction() method
            cabinGridButtons.add(btn);
        }

        for (Button btn : cabinGridButtons) {
            int cabinID = cabinGridButtons.indexOf(btn) + 1;


            btn.setOnAction(event -> {
                // Set default color for all the buttons which are not occupied and selected
                // NOTE: Selection color is painted on top of this default color
                for (Button b : cabinGridButtons) {
                    b.setStyle(defaultStyle);
                }


                btn.setStyle(selectStyle);

                // If the cabin already occupied color it RED and leave the loop for *entry*
                for (Integer j : lab.getOccupiedCabins()) {
                    if (cabinID == j) {
                        // Color all the occupied cabins RED
                        for (Integer k : lab.getOccupiedCabins()) {
                            cabinGridButtons.get(k-1).setStyle(occupiedStyle);
                        }
                        hint.setText("Cabin already occupied! Try choosing any other cabin");
                        System.out.println("Cabin already occupied! Try choosing any other cabin");
                        return;
                    }
                }


                if (!idTextField.getText().trim().equals("")) {

                    String name = LogManager.getStudentNameByID(idTextField.getText());
                    nameLabelField.setText(name.toUpperCase());

                    // actionStatus will be "IN" or "OUT"
                    String actionStatus = lab.studentArrivesOrLeaves(name, idTextField.getText(), cabinID);
                    if (actionStatus.equals("IN")) {
                        checkOut.setStyle(inactiveStatus);
                        checkIn.setStyle(activeStatus);
                    } else {
                        checkOut.setStyle(activeStatus);
                        checkIn.setStyle(inactiveStatus);
                    }

                    idTextField.setText("");
                    hint.setText("");
                } else {
                    hint.setText("Do put an ID First before choosing the cabin!");
                    System.out.println("Null ID! Retry.....");
                }

                // Color all the occupied cabins RED
                for (Integer j : lab.getOccupiedCabins()) {
                    cabinGridButtons.get(j-1).setStyle(occupiedStyle);
                }
            });
        }

        BorderPane mainContent = new BorderPane();
        mainContent.setPadding(new Insets(10, 10, 10, 10));
        mainContent.setTop(new StackPane(title));
        mainContent.setLeft(inputForm);
        mainContent.setRight(cabinGrid);
        mainContent.setCenter(new StackPane(hint));
        mainContent.setBottom(table);

        return mainContent;

    }

    // NOTE: This is testing version where the gui starts up
    // JavaFX Beta Testing
    @Override
        public void start(Stage stage) {
            Lab lab = new Lab(cabinCount);

            BorderPane primaryPane = createMainWindowContent(lab);
            Scene scene = new Scene(primaryPane, 840, 480);

            scene.setOnKeyPressed(event -> {
                // Press Shift+F when no text field is selected to toggle full screen
                if ((event.getCode() == KeyCode.F) && (event.isControlDown() == true)) {
                    if (stage.isFullScreen()) {
                        stage.setFullScreen(false);
                    } else {
                        stage.setFullScreen(true);
                    }
                }

            });

            stage.setScene(scene);
            stage.setTitle("JavaFX - Library Logger Version");
            stage.show();
        }



    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("--cli")) {
            Scanner sc = new Scanner(System.in);
            Lab lab = new Lab(cabinCount);
            mainLoop(lab, sc);
            sc.close();
        } else {
            // Launch the javafx Application.launch() method which fires up Application.start() method defined by the user
            launch();
        }
    }
}
