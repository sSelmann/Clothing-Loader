package Operations;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JsonOperations {

    public static Map<String,ArrayList<String>> getFigureMapTypeObjectID(String jsonString, int index) {
        HashMap <String,ArrayList<String>> map=new HashMap<>();
        ArrayList<String> idList=new ArrayList<>();
        JSONObject data=getJsonObject(jsonString, index);
        String idName=data.get("id").toString();
        JSONArray arr=data.getJSONArray("parts");
        for (int i = 0; i < arr.length(); i++) {
            idList.add(arr.getJSONObject(i).get("id").toString());
        }
        map.put(idName,idList);
        return map;
    }

    public static int getJsonArrayLength(String jsonString) {
        JSONObject obj = new JSONObject(jsonString);
        JSONArray array= obj.getJSONArray("FigureMap");
        return array.length();
    }

    public static JSONObject getJsonObject(String jsonString, int index) {
        JSONObject obj = new JSONObject(jsonString);
        JSONArray pageName= obj.getJSONArray("FigureMap");
        return pageName.getJSONObject(index);
    }

}
