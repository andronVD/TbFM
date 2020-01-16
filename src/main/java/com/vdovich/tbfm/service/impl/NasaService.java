package com.vdovich.tbfm.service.impl;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.vdovich.tbfm.util.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.vdovich.tbfm.service.INasaService;
import com.vdovich.tbfm.util.JsonParser;
@Service
public class NasaService implements INasaService {

    private static final Logger logger = LoggerFactory.getLogger(NasaService.class);

    private static final String API_URL = "https://api.nasa.gov";
    private static final String APOD = "/planetary/apod";
    private static final String MARS_ROVER_CURIOSITY = "/mars-photos/api/v1/rovers/curiosity/latest_photos";
    private static final String API_KEY_QUERY_PARAM = "api_key";
    private static final String DESTINATION_FILE = "/home/user/image.jpg";


    @Value("${nasa.api.token}")
    private String nasaApiToken;

    @Override
    public String sendPicture(URL url, JsonProperty jsonProperty) {
        InputStream inputStream;
        String response = "";
        try {
            inputStream = url.openStream();
            response = convertStreamToString(inputStream);
        } catch (IOException e) {
            return "not found";
        }
        return JsonParser.getPicture(response, jsonProperty);
    }


    @Override
    public URL getPictureOfTheDay() throws MalformedURLException {
        return new URL(API_URL + APOD + "?" + API_KEY_QUERY_PARAM + "=" + nasaApiToken);
    }

    @Override
    public URL getPictureFromMars() throws MalformedURLException {
        return new URL(API_URL + MARS_ROVER_CURIOSITY + "?" + API_KEY_QUERY_PARAM + "=" + nasaApiToken);
    }

    @Override
    public void savePicture() {
        try (InputStream is = getPictureOfTheDay().openStream();
             OutputStream os = new FileOutputStream(DESTINATION_FILE)) {
            byte[] b = new byte[2048];
            int length;
            while ((length = is.read(b)) != 1) {
                os.write(b, 0, length);
            }
        } catch (IOException e) {
            logger.error("Something went wrong..");
        }
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
