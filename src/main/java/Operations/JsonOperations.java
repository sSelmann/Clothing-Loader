package Operations;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JsonOperations {

    public static Map<String,ArrayList<String>> getFigureMapTypeObjectID(String jsonString, int index) {
        HashMap <String,ArrayList<String>> map=new HashMap<>();
        ArrayList<String> idList=new ArrayList<>();
        JSONObject data=getJsonObject(jsonString, index, "FigureMap");
        String idName=data.get("id").toString();
        JSONArray arr=data.getJSONArray("parts");
        for (int i = 0; i < arr.length(); i++) {
            idList.add(arr.getJSONObject(i).get("id").toString());
        }
        map.put(idName,idList);
        return map;
    }

    public static int getJsonArrayLength(String jsonString,String arrayName) {
        JSONObject obj = new JSONObject(jsonString);
        JSONArray array= obj.getJSONArray(arrayName);
        return array.length();
    }

    public static JSONObject getJsonObject(String jsonString, int index, String arrayName) {
        JSONObject obj = new JSONObject(jsonString);
        JSONArray pageName= obj.getJSONArray(arrayName);
        return pageName.getJSONObject(index);
    }

    public static String getFigureDataFirstFigureType(String jsonString, int index) {
        JSONObject data=getJsonObject(jsonString, index, "FigureData");
        JSONArray arr=data.getJSONArray("parts");
        return arr.getJSONObject(0).get("type").toString();
    }

    public static void createJsonData(String fileName, int customParams, long randomNumber, String furnitureDataJsonFile) {
        try {
            Writer writeCatalogItems = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(furnitureDataJsonFile, true), "UTF-8"));
            //We can write any JSONArray or JSONObject instance to the file
            writeCatalogItems.write(
                    "{" + "\"id\": " + randomNumber + "," +
                            "\"classname\": " + "\"" + fileName + "\"," +
                            "\"revision\": " + randomNumber / 10 + "," +
                            "\"category\": \"unknow\"," +
                            "\"defaultdir\": " + 0 + "," +
                            "\"xdim\": " + 1 + "," +
                            "\"ydim\": " + 1 + "," +
                            "\"partcolors\": " + "{ \"color\": [] }," +
                            "\"name\": \"" + fileName + "\"," +
                            "\"description\": " + "\"\"," +
                            "\"adurl\": \"\"," +
                            "\"offerid\":" + ((randomNumber * 2) / 10) + "," +
                            "\"buyout\": false," +
                            "\"rentofferid\": " + -1 + "," +
                            "\"rentbuyout\": false," +
                            "\"bc\": false," +
                            "\"excludeddynamic\":  false," +
                            "\"customparams\": \""+customParams+"\"," +
                            "\"specialtype\": " + 1 + "," +
                            "\"canstandon\": false," +
                            "\"cansiton\": false," +
                            "\"canlayon\": false," +
                            "\"furniline\": \"custom\"," +
                            "\"environment\":  \"\"," +
                            "\"rare\": false},"

            );
            writeCatalogItems.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
