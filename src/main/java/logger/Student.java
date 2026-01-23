package logger;

public class Student {
    private String name;
    private String id;

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
    public void info() {
        System.out.println("Student Info");
        System.out.println("Name: " + name);
        System.out.println("ID: " + id);
    }

}
