package com.vdovich.tbfm.nasaAPI;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;

@RestController
@RequestMapping("/nasa")
public class NasaController {
    //TODO make url enum
    private static final String API_URL = "https://api.nasa.gov/planetary/apod";
    private static final String API_KEY_QUERY_PARAM = "api_key";
    @Value("${nasa.api.token}")
    private String nasaApiToken;

    JsonParser jsonParser = new JsonParser();

    @GetMapping("/pictureOfTheDay")
    public String callNasaApi() throws IOException, JSONException {
        StringBuilder result = new StringBuilder();


        //TODO: use url builder
        InputStream in = new URL(API_URL + "?" + API_KEY_QUERY_PARAM + "=" + nasaApiToken).openStream();
        String response = convertStreamToString(in);
        String picture = jsonParser.getPicture(response);
//        ImageIO.read(new URL(picture)).;
        return picture;

    }

    private String convertStreamToString(InputStream stream) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        stream.close();

        return sb.toString();
    }
}
