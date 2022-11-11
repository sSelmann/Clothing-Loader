package Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class JsonOperations {

    public static ArrayList<String> getFigureMapTypeObjectID(String jsonString, int index) throws IOException {
        ArrayList<String> idList=new ArrayList<>();
        JSONObject obj = new JSONObject(jsonString);
        JSONArray pageName= obj.getJSONArray("FigureMap");
        JSONObject data=pageName.getJSONObject(index);
        JSONArray arr=data.getJSONArray("parts");
        for (int i = 0; i < arr.length(); i++) {
            System.out.println(arr.getJSONObject(i).get("id"));
        }
        return idList;
    }

    public static int getJsonArrayLength(String jsonString, String arrayName) {
        JSONObject obj = new JSONObject(jsonString);
        JSONArray array= obj.getJSONArray(arrayName);
        return array.length();
    }

}
