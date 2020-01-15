package com.vdovich.tbfm.util;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JsonParser {

    public static String getPicture(String response, JsonObjects jsonObject) {
        com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
        String src = "";
        JsonObject o = parser.parse(response).getAsJsonObject();
        switch (jsonObject) {
            case URL:
                src = o.get(jsonObject.getKey()).getAsString();
                break;
            case IMR_SRC:
                JsonArray arr = o.getAsJsonArray("latest_photos");
                for (int i = 0; i < arr.size(); i++) {
                    src = arr.get(i).getAsJsonObject().get(jsonObject.getKey()).getAsString();
                }
        }
        return src;
    }
}

