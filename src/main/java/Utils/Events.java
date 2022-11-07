package Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Events {

    public static void addFiguremapData() throws IOException {

        String hotelFiguremapPath="backupFiles/figuremap.xml";
        String inputFiguredataPath="input/figuremap.xml";
        File file = new File(hotelFiguremapPath);
        if (file.exists()) {

            String hotelFigureMap=Hooks.readFile(hotelFiguremapPath);
            String inputFigureMap=Hooks.readFile(inputFiguredataPath);

            List<String> figuremapDatas = Arrays.asList(inputFigureMap.split("<lib"));
            List<String> figuremapExpectedDatas= new ArrayList<>();

            String toBeeAddedData="";
            for (int i = 1; i < figuremapDatas.size(); i++) {
                String s=figuremapDatas.get(i);
                s="<lib"+s;
                if (s.contains("map>")) {
                    s=s.replace("<map>", "").replace("</map>", "");
                }
                figuremapExpectedDatas.add(s);
                toBeeAddedData=toBeeAddedData.concat(s+"\n");
            }

            figuremapExpectedDatas.remove(0);

            FileWriter myWriter = new FileWriter("output/figuremap.xml");
            int indexOfLastMapTag=hotelFigureMap.lastIndexOf("</map>");
            myWriter.write(new StringBuffer(hotelFigureMap).insert(indexOfLastMapTag, toBeeAddedData).toString());
            myWriter.close();

        } else {
            System.out.println("figuremap is not found");
        }

    }


    public static void addFiguredata() throws IOException {

        String hotelFiguredataPath="backupFiles/figuredata.xml";
        String inputFiguredataPath="input/figuredata.xml";
        File file = new File(hotelFiguredataPath);
        if (file.exists()) {

            String hotelFigureData=Hooks.readFile(hotelFiguredataPath);
            String inputFigureData=Hooks.readFile(inputFiguredataPath);

            List<String> figuremapDatas = Arrays.asList(inputFigureData.split("<set"));
            List<String> figuremapExpectedDatas= new ArrayList<>();

            for (int i = 1; i < figuremapDatas.size(); i++) {
                String s=figuremapDatas.get(i);
                s="<set"+s;
                figuremapExpectedDatas.add(s);
                int indexOfPart=s.indexOf("<part");
                int indexOfTypeAtt=s.indexOf("type=\"",indexOfPart);
                int indexOfCloseQuotes=s.indexOf("\"",indexOfTypeAtt+6);
                String figureType=s.substring(indexOfTypeAtt+6,indexOfCloseQuotes);
                System.out.println("figureType = " + figureType);
                switch (figureType) {
                    case "hr":
                        hotelFigureData= Hooks.writeFigureData("hr",hotelFigureData, s);break;
                    case "hd":
                        hotelFigureData= Hooks.writeFigureData("hd",hotelFigureData, s);break;
                }
            }
            System.out.println(figuremapExpectedDatas);

        } else {
            System.out.println("figuremap is not found");
        }

    }

}
