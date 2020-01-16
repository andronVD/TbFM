package com.vdovich.tbfm.service;

import com.vdovich.tbfm.util.JsonProperty;

import java.net.MalformedURLException;
import java.net.URL;

public interface INasaService {
    String sendPicture(URL url, JsonProperty jsonProperty);

    URL getPictureOfTheDay() throws MalformedURLException;

    void savePicture();

    URL getPictureFromMars() throws MalformedURLException;
}
