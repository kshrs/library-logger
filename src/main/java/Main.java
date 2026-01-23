import logger.Lab;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World");
        Lab lab = new Lab(10);
        lab.printDetails();

        lab.studentArrives("Kishor S", "24BIT051", 10);
        lab.printDetails();
        lab.studentLeaves("Kishor S", "24BIT051");
        lab.printDetails();
    }
}
