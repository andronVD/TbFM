package com.vdovich.tbfm.util;

import com.google.gson.JsonObject;

public class JsonParser {
    public static String getPicture(String response) {
    	com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
    	JsonObject o = parser.parse(response).getAsJsonObject();
        return o.get("url").getAsString();
    }
}
