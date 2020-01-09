package com.vdovich.tbfm.nasaAPI;


import java.io.*;
import java.net.URL;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/nasa")
public class NasaController {
    //TODO make url enum
    private static final String API_URL = "https://api.nasa.gov/planetary/apod";
    private static final String API_KEY_QUERY_PARAM = "api_key";
    private static final String DESTINATION_FILE = "/home/user/image.jpg";
    @Value("${nasa.api.token}")
    private String nasaApiToken;


    @GetMapping("/pictureOfTheDay")
    public String callNasaApi() throws IOException, JSONException {
        //TODO: use url builder
        InputStream inputStream = new URL(API_URL + "?" + API_KEY_QUERY_PARAM + "=" + nasaApiToken).openStream();
        String response = convertStreamToString(inputStream);
        return JsonParser.getPicture(response);
    }

    @GetMapping("/fullpicture")
    public void savePicture() throws IOException, JSONException {
        InputStream is = new URL(callNasaApi()).openStream();
        OutputStream os = new FileOutputStream(DESTINATION_FILE);
        byte[] b = new byte[2048];
        int length;
        while ((length = is.read(b)) != 1) {
            os.write(b, 0, length);
        }
        is.close();
        os.close();
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
