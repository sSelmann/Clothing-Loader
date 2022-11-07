package Utils;

import java.io.*;

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

    public static String writeFigureData(String type,String hotelFigureData, String toBeAddedData) {
        try {
        FileWriter myWriter = new FileWriter("output/figuredata.xml");
        int indexOfSetType=hotelFigureData.indexOf("<settype type=\""+type+"\"");
        int indexOfSetTypeCloseArrow=hotelFigureData.indexOf(">", indexOfSetType);
        String figuredata= new StringBuffer(hotelFigureData).insert(indexOfSetTypeCloseArrow+1, "\n"+toBeAddedData).toString();
        myWriter.write(figuredata);
            myWriter.close();
            return figuredata;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
