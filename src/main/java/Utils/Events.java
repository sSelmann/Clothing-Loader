package Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Events {

    public static List<String> getFileNames() {

        List<String> results = new ArrayList<String>();


        File[] files = new File("swfs").listFiles();
        //If this pathname does not denote a directory, then listFiles() returns null.

        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".nitro") || file.getName().endsWith(".swf") ) {
                results.add(file.getName());
            }
        }

        return results;
    }

}
