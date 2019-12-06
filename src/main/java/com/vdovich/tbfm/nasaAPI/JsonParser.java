package com.vdovich.tbfm.nasaAPI;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {
    public static String getPicture(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        return jsonObject.getString("url");
    }
}
