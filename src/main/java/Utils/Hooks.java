package Utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Hooks {

    public static String readFile(String path) throws IOException {
        String fileOutput = null;
        BufferedReader br = new BufferedReader(new FileReader(path));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            fileOutput = sb.toString();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            br.close();
        }

        return fileOutput;
    }

}
