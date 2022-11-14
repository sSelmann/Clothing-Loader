package Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class JsonOperations {

    public static Map<String,ArrayList<String>> getFigureMapTypeObjectID(String jsonString, int index) throws IOException {
        HashMap <String,ArrayList<String>> map=new HashMap<>();
        ArrayList<String> idList=new ArrayList<>();
        JSONObject data=getJsonObject(jsonString, "FigureMap", index);
        String idName=data.get("id").toString();
        JSONArray arr=data.getJSONArray("parts");
        for (int i = 0; i < arr.length(); i++) {
            idList.add(arr.getJSONObject(i).get("id").toString());
        }
        map.put(idName,idList);
        return map;
    }

    public static int getJsonArrayLength(String jsonString, String arrayName) {
        JSONObject obj = new JSONObject(jsonString);
        JSONArray array= obj.getJSONArray(arrayName);
        return array.length();
    }

    public static JSONObject getJsonObject(String jsonString, String arrayName,int index) {
        JSONObject obj = new JSONObject(jsonString);
        JSONArray pageName= obj.getJSONArray(arrayName);
        return pageName.getJSONObject(index);
    }

}
