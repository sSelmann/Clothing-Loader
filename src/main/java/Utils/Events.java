package Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Events {

    public static void getFiguremapData() throws IOException {

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
                s="<lib "+s;
                if (s.contains("map>")) {
                    s=s.replace("<map>", "").replace("</map>", "");
                }
                figuremapExpectedDatas.add(s);
                toBeeAddedData=toBeeAddedData.concat(s);
            }

            figuremapExpectedDatas.remove(0);
            System.out.println(figuremapExpectedDatas);

            FileWriter myWriter = new FileWriter("output/figuremap.xml");
            int indexOfLastMapTag=hotelFigureMap.lastIndexOf("</map>");
            myWriter.write(new StringBuffer(hotelFigureMap).insert(indexOfLastMapTag, toBeeAddedData).toString());
            myWriter.close();

            System.out.println(figuremapDatas);
        } else {
            System.out.println("figuremap is not found");
        }

    }

}
