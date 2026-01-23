package logger;
import java.util.ArrayList;

public class Lab {
    private int cabinCount;
    private ArrayList<Cabin> cabins = new ArrayList<Cabin>();

    public Lab(int cabinCount) {
        this.cabinCount = cabinCount;

        for (int i = 1; i <= this.cabinCount; ++i) {
            cabins.add(new Cabin(CabinType.WITH_COMPUTER, i));
        }
    }

    public void printDetails() {
        System.out.println("Lab Details");
        System.out.println("Cabins in lab => " + cabinCount + "\n");

        for (int i = 0; i < cabinCount; ++i) {
            cabins.get(i).printDetails();
        }
    }

    public void studentArrives(String name, String id, int cabinPos) {
        if ((cabinPos <= this.cabinCount) && (cabinPos > 0)) {
            cabins.get(cabinPos-1).studentArrives(name, id);
        }
    }

    public void studentLeaves(String name, String id) {
        for (int i = 0; i < cabinCount; ++i) {
            Cabin tempCabin = cabins.get(i);
            if (tempCabin.getOccupancy()) {
                if (tempCabin.getStudent().getID().equalsIgnoreCase(id)) {
                    cabins.get(i).studentLeaves();
                    break;
                }
            }
        }
    }

}
