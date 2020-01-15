package com.vdovich.tbfm.util;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JsonParser {
    private static final Logger logger = LoggerFactory.getLogger(JsonParser.class);

    public static String getPicture(String response) {

        com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
        String src = "";
        JsonObject o = parser.parse(response).getAsJsonObject();
        try {
            src = o.get("url").getAsString();
        } catch (NullPointerException e) {
            JsonArray arr = o.getAsJsonArray("latest_photos");
            for (int i = 0; i < arr.size(); i++) {
                src = arr.get(i).getAsJsonObject().get("img_src").getAsString();
            }
        }
        return src;
    }
}

