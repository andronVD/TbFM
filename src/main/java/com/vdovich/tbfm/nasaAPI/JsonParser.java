package com.vdovich.tbfm.nasaAPI;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {
    public String getPicture(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        String pictureUrl = jsonObject.getString("url");
        return pictureUrl;
    }
}
