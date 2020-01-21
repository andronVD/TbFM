package com.vdovich.tbfm.util;/* Created by user on 15.01.20 */

public enum JsonProperty {
    URL("url"), IMG_SRC("img_src"), EXPLANATION("explanation"), TITLE("title");
    private String o;

    JsonProperty(String o) {
        this.o = o;
    }

    public String getKey() {
        return o;
    }
}
