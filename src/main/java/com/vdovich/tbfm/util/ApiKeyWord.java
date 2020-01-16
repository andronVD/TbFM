package com.vdovich.tbfm.util;/* Created by user on 15.01.20 */

public enum ApiKeyWord {
    PICTURE_OF_THE_DAT("apod"), PHOTO_FROM_MARS_ROVER("Mars rover");
    private String apiKeyword;

    ApiKeyWord(String apiKeyword) {
        this.apiKeyword = apiKeyword;
    }

    public String getKeyWord() {
        return apiKeyword;
    }
}

