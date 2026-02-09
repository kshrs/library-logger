package com.kishor.logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

// Student class
public class Student {
    private final SimpleStringProperty name;
    private final SimpleStringProperty id;
    private final SimpleIntegerProperty cabinID;
    public SimpleStringProperty checkStatus;
    public SimpleStringProperty checkTime;

    // Constructor to initialize object with a name and id
    public Student(String name, String id, int cabinID, String checkStatus) {
        this.name = new SimpleStringProperty(name.toUpperCase());
        this.id = new SimpleStringProperty(id.toUpperCase());
        this.cabinID = new SimpleIntegerProperty(cabinID);
        this.checkStatus = new SimpleStringProperty(checkStatus);
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }
    public SimpleStringProperty idProperty() {
        return id;
    }
    public SimpleIntegerProperty cabinIDProperty() {
        return cabinID;
    }
    public SimpleStringProperty checkStatusProperty() {
        return checkStatus;
    }
    public SimpleStringProperty checkTimeProperty() {
        return checkTime;
    }

    public void checkStatusIN() {
        checkStatus = new SimpleStringProperty("IN");
    }
    public void checkStatusOUT() {
        checkStatus = new SimpleStringProperty("OUT");
    }
    public void setCheckTime(String time) {
        checkTime = new SimpleStringProperty(time);
    }

    // Getters
    public String getName() {
        return name.get();
    }
    public String getID() {
        return id.get();
    }
    public int getCabinID() {
        return cabinID.get();
    }

    // Pretty print Student details
    public void info() {
        System.out.println("Student Info");
        System.out.println("Name: " + name);
        System.out.println("ID: " + id);
    }

}
