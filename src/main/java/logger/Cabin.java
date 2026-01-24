package logger;

public class Cabin {
    private CabinType cabinType;
    private boolean isOccupied;
    private int cabinPos;
    private Student student;

    // Constructor
    public Cabin(CabinType cabinType, int cabinPos) {
        isOccupied = false;
        this.cabinType = cabinType;
        this.cabinPos = cabinPos;
        student = null;
    }


    // Getters and Setters for Occupancy
    public void toggleOccupancy() {
        isOccupied = !isOccupied;
    }
    public boolean getOccupancy() {
        return isOccupied;
    }
    public int getCabinPos() {
        return cabinPos;
    }
    public Student getStudent() {
       return student;
    }
    public void studentArrives(String name, String id) {
        System.out.println("\n--> Student [" + name + "] Arrives at cabin " + cabinPos + "\n");
        student = new Student(name, id);
        toggleOccupancy();
    }
    public void studentLeaves() {
        System.out.println("\n<--Student [" + student.getName() + "] Leaves cabin " + cabinPos + "\n");
        student = null;
        toggleOccupancy();
    }

    // Details of the cabin
    public void printDetails() {
        System.out.println("Cabin Details: cabin-" + cabinPos);
        System.out.println("Cabin Type => " + cabinType);
        System.out.println("isOccupied => " + getOccupancy());
        System.out.println();
        System.out.println("=========================");
    }
}
