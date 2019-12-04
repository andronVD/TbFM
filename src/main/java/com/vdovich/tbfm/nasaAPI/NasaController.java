package com.vdovich.tbfm.nasaAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nasa")
public class NasaController {
    JsonParser jsonParser = new JsonParser();

    @GetMapping("/pictureOfTheDay")
    public String callNasaApi() throws IOException, JSONException {
        StringBuilder result = new StringBuilder();
        String apiUrl = "https://api.nasa.gov/planetary/apod";
        String apiKey = "api_key=UgZR1Lu9l9l5I5sxKPTpptKah8gtMPhvBl1GkEDe";

        URLConnection connection = new URL(apiUrl + "?" + apiKey).openConnection();

        InputStream in = connection.getInputStream();
        String response = convertStreamToString(in);
        String picture = jsonParser.getPicture(response);
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
