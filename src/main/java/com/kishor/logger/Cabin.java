package com.kishor.logger;

// Cabin class
public class Cabin {
    private CabinType cabinType;
    private boolean isOccupied;
    private int cabinPos;

    private Student student;

    // Constructor to initialize the object with cabin type and cabin position
    public Cabin(CabinType cabinType, int cabinPos) {
        isOccupied = false;
        this.cabinType = cabinType;
        this.cabinPos = cabinPos;
        student = null;
    }


    // Setters
    public void toggleOccupancy() {
        isOccupied = !isOccupied;
    }

    // Getters
    public boolean getOccupancy() {
        return isOccupied;
    }
    public int getCabinPos() {
        return cabinPos;
    }
    public Student getStudent() {
       return student;
    }

    // Method for Student entry
    public void studentArrives(String name, String id) {
        student = new Student(name, id);
        toggleOccupancy();
    }

    // Method for Student exit
    public void studentLeaves() {
        student = null;
        toggleOccupancy();
    }

    // Details of the cabin
    // NOTE: Need to delete/modify this for GUI in the future
    public void printDetails() {
        System.out.println("Cabin Details: cabin-" + cabinPos);
        System.out.println("Cabin Type => " + cabinType);
        System.out.println("isOccupied => " + getOccupancy());
        System.out.println();
        System.out.println("=========================");
    }
}
