package logger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Lab {
    private int cabinCount;
    private Scanner cabinScanner;
    private ArrayList<Cabin> cabins = new ArrayList<Cabin>();

    private ArrayList<String> studentsInside = new ArrayList<String>();
    private ArrayList<Integer> availableCabins = new ArrayList<Integer>();
    private ArrayList<Integer> occupiedCabins = new ArrayList<Integer>();

    public Lab(int cabinCount, Scanner scanner) {
        cabinScanner = scanner;
        this.cabinCount = cabinCount;

        for (int i = 1; i <= cabinCount; ++i) {
            cabins.add(new Cabin(CabinType.WITH_COMPUTER, i));
            availableCabins.add(Integer.valueOf(i));
        }
    }

    public void printDetails() {
        System.out.println("Lab Details");
        System.out.println("Cabins in lab => " + cabinCount + "\n");

        for (int i = 0; i < cabins.size(); ++i) {
            cabins.get(i).printDetails();
        }
    }

    private void studentArrives(String name, String id, int cabinPos) {
        if ((cabinPos <= this.cabinCount) && (cabinPos > 0)) {
            cabins.get(cabinPos-1).studentArrives(name, id);
            studentsInside.add(id);
            occupiedCabins.add(cabinPos);
            availableCabins.remove(Integer.valueOf(cabinPos));
        }
    }

    private void studentLeaves(String name, String id) {
        for (int i = 0; i < cabins.size(); ++i) {
            Cabin tempCabin = cabins.get(i);
            if (tempCabin.getOccupancy()) {
                if (tempCabin.getStudent().getID().equalsIgnoreCase(id)) {
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
    private boolean isStudentInside(String id) {
        for (int i = 0; i < studentsInside.size(); ++i) {
           if (studentsInside.get(i).equals(id))  {
               return true;
           }
        }
        return false;
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

    public void studentArrivesOrLeaves(String name, String id) {
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
}
