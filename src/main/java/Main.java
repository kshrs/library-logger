import logger.Lab;
import java.util.Scanner;

public class Main {

    private static int cabinCount = 10;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Lab lab = new Lab(cabinCount, sc);
        mainLoop(lab, sc);


    }
    private static void mainLoop(Lab lab, Scanner scanner) {
        String name;
        String id;
        while(true) {
            name = null;
            id = null;

            lab.showAvailableCabins();

            System.out.print("Enter the Name: ");
            name = scanner.nextLine();
            System.out.print("Enter the ID: ");
            id = scanner.nextLine();

            // Exit control
            if (id.trim().equals("")) {
                System.out.println("Null String");
                break;
            }

            lab.studentArrivesOrLeaves(name.toLowerCase(), id.toLowerCase());

            // // Simple newline catcher
            // scanner.nextLine();

        }
        scanner.close();
    }
}
