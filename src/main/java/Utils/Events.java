package Utils;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class Events {

    public static void addFiguremapData() throws IOException {

        String hotelFiguremapXMLPath = "backupFiles/figuremap.xml";
        String inputFiguremapXMLPath = "input/figuremap.xml";
        File file = new File(hotelFiguremapXMLPath);

        if (file.exists()) {

            String hotelFigureMap = Hooks.readFile(hotelFiguremapXMLPath);
            String inputFigureMap = Hooks.readFile(inputFiguremapXMLPath);

            List<String> figuremapDatas = Arrays.asList(inputFigureMap.split("<lib"));

            String toBeeAddedData = "";
            for (int i = 1; i < figuremapDatas.size(); i++) {
                String s = figuremapDatas.get(i);
                s = "<lib" + s;
                if (s.contains("map>")) {
                    s = s.replace("<map>", "").replace("</map>", "");
                }
                toBeeAddedData = toBeeAddedData.concat(s + "\n");
            }

            FileWriter myWriter = new FileWriter("output/figuremap.xml");
            int indexOfLastMapTag = hotelFigureMap.lastIndexOf("</map>");
            myWriter.write(new StringBuffer(hotelFigureMap).insert(indexOfLastMapTag, toBeeAddedData).toString());
            myWriter.close();

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

            String hotelFigureData = Hooks.readFile(hotelFiguredataXMLPath);
            String inputFigureData = Hooks.readFile(inputFiguredataXMLPath);

            List<String> figuremapDatas = Arrays.asList(inputFigureData.split("<set"));

            for (int i = 1; i < figuremapDatas.size(); i++) {
                String s = figuremapDatas.get(i);
                s = "<set" + s;

                int indexOfPart = s.indexOf("<part");
                int indexOfTypeAtt = s.indexOf("type=\"", indexOfPart);
                int indexOfCloseQuotes = s.indexOf("\"", indexOfTypeAtt + 6);

                String figureType = s.substring(indexOfTypeAtt + 6, indexOfCloseQuotes);
                Hooks.sendFigureTypes(figureType, hotelFigureData,s,"xml");
            }
        }

        if(json) {

            String hotelFigureData = Hooks.readFile(hotelFiguredataJSONPath).replaceAll("\\s+", "");;
            String inputFigureData = Hooks.readFile(inputFiguredataJSONPath).replaceAll("\\s+", "");;


            List<String> figureDatas = Arrays.asList(inputFigureData.split("]},"));

            for (int i = 0; i < figureDatas.size(); i++) {
                String s = figureDatas.get(i);
                s = s.concat("]},").replaceAll("\\s+", "");

                int lastIndexOfTypeAtt = s.indexOf("\",\"colorable\"");
                int lastIndexOfCloseQuotes = s.lastIndexOf("\"", lastIndexOfTypeAtt-1);


                String figureType = s.substring(lastIndexOfCloseQuotes+1, lastIndexOfTypeAtt);
                hotelFigureData=Hooks.sendFigureTypes(figureType, hotelFigureData,s,"json");

            }
            Hooks.beautifyWriteJson(hotelFigureData,"output/figuredata.json");

        }
        } else {
            System.out.println("figuremap is not found");
        }

    }
    }


