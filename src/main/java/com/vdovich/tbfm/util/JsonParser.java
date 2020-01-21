package com.vdovich.tbfm.util;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class JsonParser {

    public static Map<JsonProperty, String> getPicture(String response, JsonProperty jsonProperty) {
        com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
        Map<JsonProperty, String> stringsEntity = new HashMap<>();

        JsonObject o = parser.parse(response).getAsJsonObject();
        switch (jsonProperty) {
            case URL:
                String url = o.get(jsonProperty.getKey()).getAsString();
                stringsEntity.put(JsonProperty.URL, url);
                String explanation = o.get(JsonProperty.EXPLANATION.getKey()).getAsString();
                for (int i = 0; i < explanation.length(); i++) {
                    if (explanation.charAt(i) == '.' && i < 700) {
                        stringsEntity.put(JsonProperty.EXPLANATION, explanation.substring(0, i + 1));
                        break;
                    } else if (i > 300) {
                        stringsEntity.put(JsonProperty.EXPLANATION, "Explanation is too long, sorry!");
                        break;
                    }
                }
                return stringsEntity;
            case IMG_SRC:
                JsonArray arr = o.getAsJsonArray("latest_photos");
                for (int i = 0; i < arr.size(); i++) {
                    stringsEntity.put(JsonProperty.IMG_SRC, arr.get(i).getAsJsonObject().get(jsonProperty.getKey()).getAsString());
                }
            default:
                return null;
        }

    }
}

