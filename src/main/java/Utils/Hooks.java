package Utils;

import com.fasterxml.jackson.databind.ObjectMapper;

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

    public static String writeFigureDataXML(String type,String hotelFigureData, String toBeAddedData) {
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

    public static String writeFigureDataJSON(String type,String hotelFigureData, String toBeAddedData) {
        try {
            FileWriter myWriter = new FileWriter("output/figuredata.json");
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
                    hotelFigureData = (extension.equals("xml") ? Hooks.writeFigureDataXML("hr", hotelFigureData, toBeAddedData) : Hooks.writeFigureDataJSON("hr", hotelFigureData, toBeAddedData));
                    break;
                case "hd":
                    hotelFigureData = (extension.equals("xml") ? Hooks.writeFigureDataXML("hd", hotelFigureData, toBeAddedData) : Hooks.writeFigureDataJSON("hd", hotelFigureData, toBeAddedData));
                    break;
                case "ch":
                    hotelFigureData = (extension.equals("xml") ? Hooks.writeFigureDataXML("ch", hotelFigureData, toBeAddedData) : Hooks.writeFigureDataJSON("ch", hotelFigureData, toBeAddedData));
                    break;
                case "lg":
                    hotelFigureData = (extension.equals("xml") ? Hooks.writeFigureDataXML("lg", hotelFigureData, toBeAddedData) : Hooks.writeFigureDataJSON("lg", hotelFigureData, toBeAddedData));
                    break;
                case "sh":
                    hotelFigureData = (extension.equals("xml") ? Hooks.writeFigureDataXML("sh", hotelFigureData, toBeAddedData) : Hooks.writeFigureDataJSON("sh", hotelFigureData, toBeAddedData));
                    break;
                case "ha":
                    hotelFigureData = (extension.equals("xml") ? Hooks.writeFigureDataXML("ha", hotelFigureData, toBeAddedData) : Hooks.writeFigureDataJSON("ha", hotelFigureData, toBeAddedData));
                    break;
                case "he":
                    hotelFigureData = (extension.equals("xml") ? Hooks.writeFigureDataXML("he", hotelFigureData, toBeAddedData) : Hooks.writeFigureDataJSON("he", hotelFigureData, toBeAddedData));
                    break;
                case "ea":
                    hotelFigureData = (extension.equals("xml") ? Hooks.writeFigureDataXML("ea", hotelFigureData, toBeAddedData) : Hooks.writeFigureDataJSON("ea", hotelFigureData, toBeAddedData));
                    break;
                case "fa":
                    hotelFigureData = (extension.equals("xml") ? Hooks.writeFigureDataXML("fa", hotelFigureData, toBeAddedData) : Hooks.writeFigureDataJSON("fa", hotelFigureData, toBeAddedData));
                    break;
                case "ca":
                    hotelFigureData = (extension.equals("xml") ? Hooks.writeFigureDataXML("ca", hotelFigureData, toBeAddedData) : Hooks.writeFigureDataJSON("ca", hotelFigureData, toBeAddedData));
                    break;
                case "wa":
                    hotelFigureData = (extension.equals("xml") ? Hooks.writeFigureDataXML("wa", hotelFigureData, toBeAddedData) : Hooks.writeFigureDataJSON("wa", hotelFigureData, toBeAddedData));
                    break;
                case "cc":
                    hotelFigureData = (extension.equals("xml") ? Hooks.writeFigureDataXML("cc", hotelFigureData, toBeAddedData) : Hooks.writeFigureDataJSON("cc", hotelFigureData, toBeAddedData));
                    break;
                case "cp":
                    hotelFigureData = (extension.equals("xml") ? Hooks.writeFigureDataXML("cp", hotelFigureData, toBeAddedData) : Hooks.writeFigureDataJSON("cp", hotelFigureData, toBeAddedData));
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

    public static void beautifyWriteJson(String json,String path) {
        try {
        ObjectMapper mapper = new ObjectMapper();
        Object obj = mapper.readValue(json, Object.class);
        FileWriter myWriter = new FileWriter(path);
            myWriter.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj));
        } catch (IOException e) {
        }
    }

}
