package com.vdovich.tbfm.util;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;

public class JsonParser {

    public static String getPicture(String response, JsonProperty jsonProperty) {
        com.google.gson.JsonParser parser = new com.google.gson.JsonParser();

        JsonObject o = parser.parse(response).getAsJsonObject();
        switch (jsonProperty) {
            case EXPLANATION:
                String explanation = o.get(JsonProperty.EXPLANATION.getKey()).getAsString();
                for (int i = 0; i < explanation.length(); i++) {
                    if (explanation.charAt(i) == '.' && i < 700) {
                        return explanation = explanation.substring(0, i + 1);
                    } else if (i > 300) {
                        return explanation = "Explanation is too long, sorry!";
                    }
                }
            case URL:
                return o.get(jsonProperty.getKey()).getAsString();
            case IMG_SRC:
                JsonArray arr = o.getAsJsonArray("latest_photos");
                for (int i = 0; i < arr.size(); i++) {
                    return arr.get(i).getAsJsonObject().get(jsonProperty.getKey()).getAsString();
                }
            default:
                return StringUtils.EMPTY;
        }

    }
}

