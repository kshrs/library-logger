package com.kishor.logger;

// Student class
public class Student {
    private String name;
    private String id;

    // Constructor to initialize object with a name and id
    public Student(String name, String id) {
        this.name = name;
        this.id = id;
    }
    // Getters
    public String getName() {
        return name;
    }
    public String getID() {
        return id;
    }

    // Pretty print Student details
    public void info() {
        System.out.println("Student Info");
        System.out.println("Name: " + name);
        System.out.println("ID: " + id);
    }

}
