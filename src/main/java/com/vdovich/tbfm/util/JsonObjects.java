package com.vdovich.tbfm.util;/* Created by user on 15.01.20 */

public enum JsonObjects {
    URL("url"), IMR_SRC("img_src");
    private String o;

    JsonObjects(String o) {
        this.o = o;
    }

    public String getKey() {
        return o;
    }
}
