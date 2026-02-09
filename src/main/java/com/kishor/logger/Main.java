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
import javafx.scene.layout.VBox;
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
        table.setEditable(false);
        table.setPrefHeight(200);
        table.setMaxHeight(200);

        // NOTE: This variable is used only to show hints to the user
        Label hint = new Label();
        hint.setWrapText(true);
        hint.getStyleClass().add("hint-label"); // CSS

        Label title = new Label("Library Logger");
        title.getStyleClass().add("label-title"); // CSS

        Label timeField = new Label();
        timeField.getStyleClass().add("time-label"); // CSS

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
        inputForm.getStyleClass().add("sidebar"); // CSS

        Label nameLabelField = new Label("");
        TextField idTextField = new TextField();
        idTextField.getStyleClass().add("text-field"); // CSS

        idTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                hint.setText("Choose a cabin to make a log entry or exit");
            } else {
                hint.setText("");
            }
        });

        Label checkIn = new Label("IN");
        checkIn.getStyleClass().add("status-box"); // CSS Base class

        Label checkOut = new Label("OUT");
        checkOut.getStyleClass().add("status-box"); // CSS Base class

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
        cabinGrid.getStyleClass().add("cabin-grid"); // CSS

        // REMOVED: String activeStatus, inactiveStatus, defaultStyle...
        // We now rely on CSS classes defined in style.css

        int row, col;
        row = col = 0;
        int columns = 4;
        for (int i=0; i<cabinCount; ++i) {
            int cabinID = i + 1;
            Button btn = new Button("Cabin: " + cabinID);
            btn.setPrefSize(150, 80);

            // Set Base Styles
            btn.getStyleClass().addAll("cabin-btn", "cabin-free");

            col = i % columns;
            row = i / columns;
            cabinGrid.add(btn, col, row);

            // Add the button to the cabinGridButtons arraylist
            cabinGridButtons.add(btn);
        }

        for (Button btn : cabinGridButtons) {
            int cabinID = cabinGridButtons.indexOf(btn) + 1;

            btn.setOnAction(event -> {
                // RESET LOOP: Set all buttons to default (Free)
                for (Button b : cabinGridButtons) {
                    b.getStyleClass().removeAll("cabin-selected", "cabin-occupied");
                    if (!b.getStyleClass().contains("cabin-free")) {
                        b.getStyleClass().add("cabin-free");
                    }
                }

                // Apply SELECTED style to current button
                btn.getStyleClass().remove("cabin-free");
                btn.getStyleClass().add("cabin-selected");

                // OCCUPIED CHECK: If this cabin is actually occupied
                for (Integer j : lab.getOccupiedCabins()) {
                    if (cabinID == j) {
                        // Re-color all occupied cabins
                        for (Integer k : lab.getOccupiedCabins()) {
                            Button occupiedBtn = cabinGridButtons.get(k-1);
                            occupiedBtn.getStyleClass().removeAll("cabin-free", "cabin-selected");
                            if (!occupiedBtn.getStyleClass().contains("cabin-occupied")) {
                                occupiedBtn.getStyleClass().add("cabin-occupied");
                            }
                        }
                        hint.setText("Cabin already occupied! Try choosing any other cabin");
                        System.out.println("Cabin already occupied!");
                        return;
                    }
                }

                if (!idTextField.getText().trim().equals("")) {
                    String name = LogManager.getStudentNameByID(idTextField.getText());
                    if (name == null) {
                       hint.setText("Cannot find ID-Name pair in db...");
                       return;
                    } else {
                        nameLabelField.setText(name.toUpperCase());
                    }

                    String actionStatus = lab.studentArrivesOrLeaves(name, idTextField.getText(), cabinID);

                    // TOGGLE STATUS LABELS (IN/OUT)
                    checkIn.getStyleClass().remove("status-active");
                    checkOut.getStyleClass().remove("status-active");

                    if (actionStatus.equals("IN")) {
                        checkIn.getStyleClass().add("status-active");
                    } else {
                        checkOut.getStyleClass().add("status-active");
                    }

                    idTextField.setText("");
                    hint.setText("");
                } else {
                    hint.setText("Do put an ID First before choosing the cabin!");
                    System.out.println("Null ID! Retry.....");
                }

                // FINAL RE-COLOR: Ensure all occupied cabins stay Red
                for (Integer j : lab.getOccupiedCabins()) {
                    Button occupiedBtn = cabinGridButtons.get(j-1);
                    occupiedBtn.getStyleClass().removeAll("cabin-free", "cabin-selected");
                    if (!occupiedBtn.getStyleClass().contains("cabin-occupied")) {
                        occupiedBtn.getStyleClass().add("cabin-occupied");
                    }
                }
            });
        }

        BorderPane mainContent = new BorderPane();
        mainContent.setPadding(new Insets(10, 10, 10, 10));
        mainContent.setTop(new StackPane(title));
        mainContent.setLeft(inputForm);
        // mainContent.setRight(cabinGrid);
        // mainContent.setCenter(new StackPane(hint));
        mainContent.setCenter(cabinGrid);
        mainContent.setRight(new StackPane(hint));
        mainContent.setBottom(table);

        return mainContent;
    }

    // NOTE: This is testing version where the gui starts up
    // JavaFX Beta Testing
    @Override
        public void start(Stage stage) {
            Lab lab = new Lab(cabinCount);

            BorderPane primaryPane = createMainWindowContent(lab);
            Scene scene = new Scene(primaryPane, 1080, 720);
            String css = this.getClass().getResource("style.css").toExternalForm();
            scene.getStylesheets().add(css);

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
            stage.setTitle("JavaFX - Library Logger");
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
