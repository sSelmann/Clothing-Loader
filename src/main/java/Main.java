import Utils.Events;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        Events.addFiguremapData();
        Events.addFiguredata();

        System.out.println("Done!");

    }

}
