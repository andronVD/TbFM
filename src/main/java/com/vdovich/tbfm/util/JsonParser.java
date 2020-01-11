package com.vdovich.tbfm.util;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


public class JsonParser {
    public static String getPicture(String response) {
        com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
        String src = "";
        JsonObject o = parser.parse(response).getAsJsonObject();
        JsonArray arr = o.getAsJsonArray("latest_photos");
        for (int i = 0; i < arr.size(); i++) {
            src = arr.get(i).getAsJsonObject().get("img_src").getAsString();
        }
        return src;
    }
}
