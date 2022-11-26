package Operations;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class StringOperations {

    public static String readFile(String path) {
        String fileOutput = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));

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
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return fileOutput;
    }

    public static String writeFigureDataXML(String type,String hotelFigureData, String toBeAddedData) {
        try {
        FileWriter myWriter = new FileWriter("output/xml/figuredata.xml");
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

    public static String writeFigureDataJSON(String type,String hotelFigureData, String toBeAddedData) {
        try {
            FileWriter myWriter = new FileWriter("output/json/FigureData.json");
            int indexOfSetType=hotelFigureData.indexOf("\""+type+"\",\"paletteId\":");
            int indexOfSetTypeCloseArrow=hotelFigureData.indexOf("[", indexOfSetType);
            String figuredata= new StringBuffer(hotelFigureData).insert(indexOfSetTypeCloseArrow+1, "\n"+toBeAddedData).toString();
            myWriter.write(figuredata);
            myWriter.close();
            return figuredata;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String sendFigureTypes(String figureType, String hotelFigureData, String toBeAddedData,String extension) {
            switch (figureType) {
                case "hr":
                    hotelFigureData = (extension.equals("xml") ? StringOperations.writeFigureDataXML("hr", hotelFigureData, toBeAddedData) : StringOperations.writeFigureDataJSON("hr", hotelFigureData, toBeAddedData));
                    break;
                case "hd":
                    hotelFigureData = (extension.equals("xml") ? StringOperations.writeFigureDataXML("hd", hotelFigureData, toBeAddedData) : StringOperations.writeFigureDataJSON("hd", hotelFigureData, toBeAddedData));
                    break;
                case "ch":
                    hotelFigureData = (extension.equals("xml") ? StringOperations.writeFigureDataXML("ch", hotelFigureData, toBeAddedData) : StringOperations.writeFigureDataJSON("ch", hotelFigureData, toBeAddedData));
                    break;
                case "lg":
                    hotelFigureData = (extension.equals("xml") ? StringOperations.writeFigureDataXML("lg", hotelFigureData, toBeAddedData) : StringOperations.writeFigureDataJSON("lg", hotelFigureData, toBeAddedData));
                    break;
                case "sh":
                    hotelFigureData = (extension.equals("xml") ? StringOperations.writeFigureDataXML("sh", hotelFigureData, toBeAddedData) : StringOperations.writeFigureDataJSON("sh", hotelFigureData, toBeAddedData));
                    break;
                case "ha":
                    hotelFigureData = (extension.equals("xml") ? StringOperations.writeFigureDataXML("ha", hotelFigureData, toBeAddedData) : StringOperations.writeFigureDataJSON("ha", hotelFigureData, toBeAddedData));
                    break;
                case "he":
                    hotelFigureData = (extension.equals("xml") ? StringOperations.writeFigureDataXML("he", hotelFigureData, toBeAddedData) : StringOperations.writeFigureDataJSON("he", hotelFigureData, toBeAddedData));
                    break;
                case "ea":
                    hotelFigureData = (extension.equals("xml") ? StringOperations.writeFigureDataXML("ea", hotelFigureData, toBeAddedData) : StringOperations.writeFigureDataJSON("ea", hotelFigureData, toBeAddedData));
                    break;
                case "fa":
                    hotelFigureData = (extension.equals("xml") ? StringOperations.writeFigureDataXML("fa", hotelFigureData, toBeAddedData) : StringOperations.writeFigureDataJSON("fa", hotelFigureData, toBeAddedData));
                    break;
                case "ca":
                    hotelFigureData = (extension.equals("xml") ? StringOperations.writeFigureDataXML("ca", hotelFigureData, toBeAddedData) : StringOperations.writeFigureDataJSON("ca", hotelFigureData, toBeAddedData));
                    break;
                case "wa":
                    hotelFigureData = (extension.equals("xml") ? StringOperations.writeFigureDataXML("wa", hotelFigureData, toBeAddedData) : StringOperations.writeFigureDataJSON("wa", hotelFigureData, toBeAddedData));
                    break;
                case "cc":
                    hotelFigureData = (extension.equals("xml") ? StringOperations.writeFigureDataXML("cc", hotelFigureData, toBeAddedData) : StringOperations.writeFigureDataJSON("cc", hotelFigureData, toBeAddedData));
                    break;
                case "cp":
                    hotelFigureData = (extension.equals("xml") ? StringOperations.writeFigureDataXML("cp", hotelFigureData, toBeAddedData) : StringOperations.writeFigureDataJSON("cp", hotelFigureData, toBeAddedData));
                    break;
                default:
                    System.out.println(figureType + " type is not found");
                    break;

            }
            return hotelFigureData;
    }

    public static boolean isJSONValid(String jsonInString ) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(jsonInString);
            return true;
        } catch (IOException e) {
            System.out.println("everything is fine, there are some errors in the json codes you send only with input. Find the error in json by inspecting the error below."+"\n"+e);
            return false;
        }
    }

    public static void beautifyWriteJson(String json,String path) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        Object obj = mapper.readValue(json, Object.class);
        FileWriter myWriter = new FileWriter(path);
            myWriter.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj));
            myWriter.close();

    }

    public static List<String> getItemFileNames() {

        List<String> results = new ArrayList<String>();

        File[] files = new File("resource/itemfiles").listFiles();
        //If this pathname does not denote a directory, then listFiles() returns null.

        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".nitro") || file.getName().endsWith(".swf") ) {
                results.add(file.getName().substring(0, file.getName().indexOf(".")));
            }
        }

        return results;
    }

    public static long createRandomNumber() {
        java.sql.Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return (int) (Math.random() * (timestamp.getTime() / 100000));
    }

    public static void createDirectory(String path) {
        File dir=new File(path);
        dir.mkdirs();
    }

}
