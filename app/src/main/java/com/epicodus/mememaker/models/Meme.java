package com.epicodus.mememaker.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

public class Meme {
    private String mName;
    private String mUrl;
    private double mId;

    public Meme(String name, String url, double id) {
        this.mName = name;
        this.mUrl = url;
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public String getUrl() {
        return mUrl;
    }

    public double getId() {
        return mId;
    }

}
