package com.kishor.logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


// Class Lab
public class Lab {
    private int cabinCount;
    private ArrayList<Cabin> cabins = new ArrayList<Cabin>();

    private ArrayList<String> studentsInside = new ArrayList<String>();
    private ArrayList<Integer> availableCabins = new ArrayList<Integer>();
    private ArrayList<Integer> occupiedCabins = new ArrayList<Integer>();

    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss"); // Pattern 23:54:01

    // Observable List for table entries for the GUI in Main.java
    public static ObservableList<Student> studentList = FXCollections.observableArrayList();

    public void addEntryInTable(Student student) {
        studentList.add(student);
    }

    // Constructor
    public Lab(int cabinCount) {
        this.cabinCount = cabinCount;

        for (int i = 1; i <= cabinCount; ++i) {
            cabins.add(new Cabin(CabinType.WITH_COMPUTER, i));
            availableCabins.add(Integer.valueOf(i));
        }
        LogManager.ensureFileExists();
    }

    // Pretty print info of lab
    public void printDetails() {
        System.out.println("Lab Details");
        System.out.println("Cabins in lab => " + cabinCount + "\n");

        for (int i = 0; i < cabins.size(); ++i) {
            cabins.get(i).printDetails();
        }
    }

    // Getters
    private boolean isStudentInside(String id) {
        for (int i = 0; i < studentsInside.size(); ++i) {
           if (studentsInside.get(i).equalsIgnoreCase(id))  {
               return true;
           }
        }
        return false;
    }

    public ArrayList<Integer> getOccupiedCabins() {
        return occupiedCabins;
    }

    public void showAvailableCabins() {
        System.out.print("Available Cabins: [");
        for (Integer i : availableCabins) {
            System.out.print(i + " ");
        }
        System.out.println("]");
    }

    private boolean isCabinOccupied(int cabinPos) {
        for (int i = 0; i < occupiedCabins.size(); ++i) {
           if (occupiedCabins.get(i).equals(cabinPos))  {
               return true;
           }
        }
        return false;
    }

    // Method for Student entry which calls the studentArrives from Cabin.java class
    private void studentArrives(String name, String id, int cabinPos) {
        if ((cabinPos <= this.cabinCount) && (cabinPos > 0)) {
            cabins.get(cabinPos-1).studentArrives(name, id);
            studentsInside.add(id);
            occupiedCabins.add(cabinPos);
            availableCabins.remove(Integer.valueOf(cabinPos));
        } // the else condition (OutOfCabins) is checked by method Lab.studentArrivesOrLeaves()

        LogManager.checkInUser(cabins.get(cabinPos-1).getStudent(), cabinPos);
        cabins.get(cabinPos-1).getStudent().setCheckTime(LocalTime.now().format(timeFormatter).toString());
        addEntryInTable(cabins.get(cabinPos-1).getStudent());
    }

    // Method for Student entry which calls the studentLeaves from Cabin.java class
    private void studentLeaves(String name, String id) {
        for (int i = 0; i < cabins.size(); ++i) {
            Cabin tempCabin = cabins.get(i);
            if (tempCabin.getOccupancy()) {
                if (tempCabin.getStudent().getID().equalsIgnoreCase(id)) {

                    cabins.get(i).getStudent().checkStatusOUT();
                    cabins.get(i).getStudent().setCheckTime(LocalTime.now().format(timeFormatter).toString());
                    LogManager.checkOutUser(cabins.get(i).getStudent(), i+1);
                    addEntryInTable(cabins.get(i).getStudent());

                    cabins.get(i).studentLeaves();
                    studentsInside.remove(id);
                    availableCabins.add(i+1);
                    occupiedCabins.remove(Integer.valueOf(i+1));

                    Collections.sort(availableCabins);
                    break;
                }
            }
        }
    }

    // Method to make decision on whether a student is already inside or a new entry
    // Note: This method will be the main entry from outside the package `logger`
    public void studentArrivesOrLeaves(String name, String id, Scanner cabinScanner) {
        if (isStudentInside(id)) {
            studentLeaves(name, id);
        } else {

            int cabinPos = 0;
            System.out.print("Enter the Cabin pos: ");
            cabinPos = cabinScanner.nextInt();
            cabinScanner.nextLine();

            if ((cabinPos > cabinCount) || (cabinPos <= 0)) {
                System.out.println("Invalid Cabin Position. Try Again;");
                return;
            }
            if (!(isCabinOccupied(cabinPos))) {
                    studentArrives(name, id, cabinPos);
            } else {
                System.out.println("Cabin is already occupied. Try Again");
            }
        }
    }
    // Overloaded function for the gui version
    public String studentArrivesOrLeaves(String name, String id, int cabinID) {
        if (isStudentInside(id)) {
            studentLeaves(name, id);
            return "OUT";
        } else {
            if (!(isCabinOccupied(cabinID))) {
                    studentArrives(name, id, cabinID);
                    return "IN";
            } else {
                System.out.println("Cabin is already occupied. Try Again");
            }
        }
        return null;
    }
}
