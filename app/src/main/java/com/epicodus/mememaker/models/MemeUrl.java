package com.epicodus.mememaker.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MemeUrl {
    private String mUrl;

    public MemeUrl() {}

    public MemeUrl(String url) {
        this.mUrl = url;
    }

    public String getUrl() {
        return mUrl;
    }
    public void setUrl(String url){this.mUrl = url; }
}

