package Utils;

import Operations.JsonOperations;
import Operations.StringOperations;
import Operations.XMLOperations;
import com.jcabi.xml.XML;

import java.io.*;
import java.util.*;

public class Events {

    static String inputFiguredataXMLPath = "input/figuredata.xml";

    public static void addFiguremapData() throws IOException {

        String hotelFiguremapXMLPath = "backupFiles/figuremap.xml";
        String hotelFiguremapJSONPath = "backupFiles/FigureMap.json";
        String inputFiguremapXMLPath = "input/figuremap.xml";
        String inputFiguremapJSONPath = "input/FigureMap.json";
        File fileXML = new File(hotelFiguremapXMLPath);
        File fileJSON = new File(hotelFiguremapJSONPath);

        boolean xml=fileXML.exists();
        boolean json=fileJSON.exists();

        if (xml || json) {

            if (xml) {
                String hotelFigureMap = StringOperations.readFile(hotelFiguremapXMLPath);
                List<XML> figuremapDatas = XMLOperations.getFiguremapLibs(inputFiguremapXMLPath);
                matchDataAndGenerateSql(inputFiguremapXMLPath);

                String toBeeAddedData = "";
                for (XML s: figuremapDatas) {
                    toBeeAddedData = toBeeAddedData.concat(s.toString());
                }

                FileWriter myWriter = new FileWriter("output/figuremap.xml");
                int indexOfLastMapTag = hotelFigureMap.lastIndexOf("</map>");
                String output=new StringBuffer(hotelFigureMap).insert(indexOfLastMapTag, toBeeAddedData).toString();
                myWriter.write(output);
                myWriter.close();

            }

            if (json) {

                String hotelFigureMap = StringOperations.readFile(hotelFiguremapJSONPath);
                String inputFigureMap = StringOperations.readFile(inputFiguremapJSONPath);
                String toBeeAddedData = "";

                for(int i = 0; i< JsonOperations.getJsonArrayLength(inputFigureMap,"FigureMap"); i++) {
                    Map<String,ArrayList<String>> map=JsonOperations.getFigureMapTypeObjectID(inputFigureMap,i);
                    String object=JsonOperations.getJsonObject(inputFigureMap, i,"FigureMap").toString(4);
                    toBeeAddedData = toBeeAddedData.concat(","+object);
                }

                hotelFigureMap=hotelFigureMap.replaceAll("\\s+", "");
                hotelFigureMap=new StringBuffer(hotelFigureMap).insert(hotelFigureMap.length()-2, toBeeAddedData).toString();
                StringOperations.isJSONValid(hotelFigureMap);
                StringOperations.beautifyWriteJson(hotelFigureMap, "output/FigureMap.json");

            }

            } else {
            System.out.println("figuremap is not found");
        }

    }


    public static void addFiguredata() throws IOException {

        String hotelFiguredataXMLPath = "backupfiles/figuredata.xml";
        String hotelFiguredataJSONPath = "backupfiles/FigureData.json";
        String inputFiguredataJSONPath = "input/FigureData.json";
        File xmlFile = new File(hotelFiguredataXMLPath);
        File jsonFile = new File(hotelFiguredataJSONPath);

        boolean xml= xmlFile.exists();
        boolean json= jsonFile.exists();

        if (xml || json) {

            if (xml) {

            String hotelFigureData = StringOperations.readFile(hotelFiguredataXMLPath);
            List<XML>sets=XMLOperations.getFigureDataSets(inputFiguredataXMLPath);

                for (int i = 0; i < sets.size(); i++) {
                    String set=sets.get(i).toString();
                    String figureType=XMLOperations.getFigureDataSetFigureType(inputFiguredataXMLPath,i);
                    hotelFigureData=StringOperations.sendFigureTypes(figureType, hotelFigureData,set,"xml");

                }

        }

        if(json) {

            String hotelFigureData = StringOperations.readFile(hotelFiguredataJSONPath).replaceAll("\\s+", "");;
            String inputFigureData = StringOperations.readFile(inputFiguredataJSONPath).replaceAll("\\s+", "");;

            for (int i = 0; i < JsonOperations.getJsonArrayLength(inputFigureData,"FigureData"); i++) {
                String toBeeAddedData=JsonOperations.getJsonObject(inputFigureData, i,"FigureData").toString();
                String figureType=JsonOperations.getFigureDataFirstFigureType(inputFigureData,i);
                hotelFigureData= StringOperations.sendFigureTypes(figureType, hotelFigureData,toBeeAddedData+",","json");
            }

            StringOperations.isJSONValid(hotelFigureData);
            StringOperations.beautifyWriteJson(hotelFigureData,"output/FigureData.json");

        }
        } else {
            System.out.println("figuremap is not found");
        }

    }

    static void matchDataAndGenerateSql(String figureMapPath) throws FileNotFoundException {
        HashMap<String, HashSet<String>> figureMapDataList=XMLOperations.getFigureMapValues(figureMapPath);
        List<String> itemNameList=StringOperations.getItemFileNames();

        for (String s: itemNameList) {

            if (GetConfig.ini.get("item&figures",s) !=null) {
                String figureItemName= GetConfig.ini.get("item&figures",s);
                if (figureMapDataList.get(figureItemName) != null) {
                    HashSet<String> figurePartIDs=figureMapDataList.get(figureItemName);
                    HashMap<String,HashSet<String>> figureDataSets=XMLOperations.getFigureDataValues(inputFiguredataXMLPath);
                    List<String> keyLists=new ArrayList<>(figureDataSets.keySet());
                    for (int i = 0; i < keyLists.size(); i++) {
                        if (figureDataSets.get(keyLists.get(i)).containsAll(figurePartIDs)) {
                            // TODO added sql Operations
                        }
                    }
                } else {
                    System.out.println("There is no matching figuremap id for "+figureItemName+" please check config.");
                }
            } else {
                System.out.println("There is no matching figure item name for "+s+" please check config.");
            }

        }
    }


}


