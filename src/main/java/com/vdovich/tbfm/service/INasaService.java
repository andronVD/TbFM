package com.vdovich.tbfm.service;

import com.vdovich.tbfm.util.JsonProperty;

import java.util.Map;

public interface INasaService {

    Map<JsonProperty, String> getPictureOfTheDay();

    void savePicture();

    Map<JsonProperty, String> getPictureFromMars();

    Map<JsonProperty, String> getTextOfPictureOfTheDay();

}
