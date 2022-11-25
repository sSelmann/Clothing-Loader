package Operations;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JsonOperations {

    public static Map<String, ArrayList<String>> getFigureMapTypeObjectID(String jsonString) {
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        for (int i = 0; i < getJsonArrayLength(jsonString, "FigureMap"); i++) {
            ArrayList<String> idList = new ArrayList<>();
            JSONObject data = getJsonObject(jsonString, i, "FigureMap");
            String idName = data.get("id").toString();
            JSONArray arr = data.getJSONArray("parts");
            for (int j = 0; j < arr.length(); j++) {
                idList.add(arr.getJSONObject(j).get("id").toString());
            }
            map.put(idName, idList);
        }
        return map;
    }

    public static int getJsonArrayLength(String jsonString, String arrayName) {
        JSONObject obj = new JSONObject(jsonString);
        JSONArray array = obj.getJSONArray(arrayName);
        return array.length();
    }

    public static JSONObject getJsonObject(String jsonString, int index, String arrayName) {
        JSONObject obj = new JSONObject(jsonString);
        JSONArray pageName = obj.getJSONArray(arrayName);
        return pageName.getJSONObject(index);
    }

    public static String getFigureDataFirstFigureType(String jsonString, int index) {
        JSONObject data = getJsonObject(jsonString, index, "FigureData");
        JSONArray arr = data.getJSONArray("parts");
        return arr.getJSONObject(0).get("type").toString();
    }

    public static HashMap<String, ArrayList<String>> getFigureDataValues(String jsonString) {
        HashMap<String, ArrayList<String>> valueMap = new HashMap<>();
        for (int i = 0; i < getJsonArrayLength(jsonString, "FigureData"); i++) {

            ArrayList<String> partIds = new ArrayList<>();
            JSONObject data = getJsonObject(jsonString, i, "FigureData");
            JSONArray arr = data.getJSONArray("parts");
            for (int j = 0; j < arr.length(); j++) {
                partIds.add(arr.getJSONObject(j).get("id").toString());
            }
            valueMap.put(data.get("id").toString(), partIds);


        }
        return valueMap;
    }

    public static void createJsonData(String fileName, int customParams, long randomNumber, String furnitureDataJsonFile) {
        try {
            Writer writeCatalogItems = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(furnitureDataJsonFile, true), "UTF-8"));
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
                            "\"customparams\": \"" + customParams + "\"," +
                            "\"specialtype\": " + 23 + "," +
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
