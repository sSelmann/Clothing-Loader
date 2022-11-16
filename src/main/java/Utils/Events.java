package Utils;

import Operations.JsonOperations;
import Operations.StringOperations;
import Operations.XMLOperations;
import com.jcabi.xml.XML;

import java.io.*;
import java.util.*;

public class Events {

    public static void addFiguremapData() throws IOException {

        String hotelFiguremapXMLPath = "backupFiles/figuremap.xml";
        String hotelFiguremapJSONPath = "backupFiles/FigureMap.json";
        String inputFiguremapXMLPath = "input/figuremap.xml";
        String inputFiguremapJSONPath = "input/FigureMap.json";
        File fileXML = new File(hotelFiguremapXMLPath);
        File fileJSON = new File(hotelFiguremapXMLPath);

        boolean xml=fileXML.exists();
        boolean json=fileJSON.exists();

        if (xml || json) {

            if (xml) {

                String hotelFigureMap = StringOperations.readFile(hotelFiguremapXMLPath);
                List<XML> figuremapDatas = XMLOperations.getFiguremapLibs(inputFiguremapXMLPath);

                System.out.println(XMLOperations.getFiguremapLibs(inputFiguremapXMLPath));
                System.out.println(XMLOperations.getFiguremapLibIDs(inputFiguremapXMLPath));
                System.out.println(XMLOperations.getFiguremapLibPartIDs(inputFiguremapXMLPath));

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

                for(int i = 0; i< JsonOperations.getJsonArrayLength(inputFigureMap); i++) {
                    Map<String,ArrayList<String>> map=JsonOperations.getFigureMapTypeObjectID(inputFigureMap,i);
                    System.out.println(map);
                    String object=JsonOperations.getJsonObject(inputFigureMap, i).toString(4);
                    System.out.println(object);
                    toBeeAddedData = toBeeAddedData.concat(","+object);
                }
                System.out.println(toBeeAddedData);

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
        String inputFiguredataXMLPath = "input/figuredata.xml";
        String inputFiguredataJSONPath = "input/FigureData.json";
        File xmlFile = new File(hotelFiguredataXMLPath);
        File jsonFile = new File(hotelFiguredataJSONPath);

        boolean xml= xmlFile.exists();
        boolean json= jsonFile.exists();

        if (xml || json) {

            if (xml) {

            String hotelFigureData = StringOperations.readFile(hotelFiguredataXMLPath);
            String inputFigureData = StringOperations.readFile(inputFiguredataXMLPath);

            List<String> figuremapDatas = Arrays.asList(inputFigureData.split("<set"));

            for (int i = 1; i < figuremapDatas.size(); i++) {
                String s = figuremapDatas.get(i);
                s = "<set" + s;

                int indexOfPart = s.indexOf("<part");
                int indexOfTypeAtt = s.indexOf("type=\"", indexOfPart);
                int indexOfCloseQuotes = s.indexOf("\"", indexOfTypeAtt + 6);

                String figureType = s.substring(indexOfTypeAtt + 6, indexOfCloseQuotes);
                StringOperations.sendFigureTypes(figureType, hotelFigureData,s,"xml");
            }
        }

        if(json) {

            String hotelFigureData = StringOperations.readFile(hotelFiguredataJSONPath).replaceAll("\\s+", "");;
            String inputFigureData = StringOperations.readFile(inputFiguredataJSONPath).replaceAll("\\s+", "");;


            List<String> figureDatas = Arrays.asList(inputFigureData.split("]},"));

            for (int i = 0; i < figureDatas.size(); i++) {
                String s = figureDatas.get(i);
                s = s.concat("]},").replaceAll("\\s+", "");

                int lastIndexOfTypeAtt = s.indexOf("\",\"colorable\"");
                int lastIndexOfCloseQuotes = s.lastIndexOf("\"", lastIndexOfTypeAtt-1);


                String figureType = s.substring(lastIndexOfCloseQuotes+1, lastIndexOfTypeAtt);
                hotelFigureData= StringOperations.sendFigureTypes(figureType, hotelFigureData,s,"json");

            }
            StringOperations.isJSONValid(hotelFigureData);
            StringOperations.beautifyWriteJson(hotelFigureData,"output/FigureData.json");

        }
        } else {
            System.out.println("figuremap is not found");
        }

    }

    }


