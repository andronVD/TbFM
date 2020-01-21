package com.vdovich.tbfm.service.impl;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.vdovich.tbfm.service.INasaService;
import com.vdovich.tbfm.util.JsonParser;
import com.vdovich.tbfm.util.JsonProperty;

@Service
public class NasaService implements INasaService {

    private static final Logger logger = LoggerFactory.getLogger(NasaService.class);

    private static final String API_URL = "https://api.nasa.gov%s?api_key=%s";
    private static final String APOD = "/planetary/apod";
    private static final String MARS_ROVER_CURIOSITY = "/mars-photos/api/v1/rovers/curiosity/latest_photos";
    private static final String DESTINATION_FILE = "/home/user/image.jpg";

    @Value("${nasa.api.token}")
    private String nasaApiToken;

    @Override
    public String getPictureOfTheDay() {
        String url = buildNasaUrl(APOD);
        return sendPicture(url, JsonProperty.URL);
    }
    public String getTextOfPictureOfTheDay(){
        String url = buildNasaUrl(APOD);
        return sendPicture(url,JsonProperty.EXPLANATION);
    }

    @Override
    public String getPictureFromMars() {
        String url = buildNasaUrl(MARS_ROVER_CURIOSITY);
        return sendPicture(url, JsonProperty.IMG_SRC);
    }

    @Override
    public void savePicture() {
        try (InputStream is = new URL(getPictureOfTheDay()).openStream();
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

    /*
     *
     *  private section
     *
     */
    private String sendPicture(String url, JsonProperty jsonProperty) {
        String response = "";
        try (InputStream is = new URL(url).openStream()) {
            response = convertStreamToString(is);
            return JsonParser.getPicture(response, jsonProperty);
        } catch (IOException e) {
            logger.error("Couldn't read response from url: " + url);
        }
        return StringUtils.EMPTY;
    }

    private String convertStreamToString(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    private String buildNasaUrl(String pathRequest) {
        return String.format(API_URL, pathRequest, nasaApiToken);
    }

}
