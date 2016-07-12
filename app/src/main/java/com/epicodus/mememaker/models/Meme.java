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
    private ArrayList<MemeAnnotation> mAnnotations;
    private double mId;

    public Meme(String name, String url, ArrayList<MemeAnnotation> annotations, double id) {
        this.mName = name;
        this.mUrl = url;
        this.mId = id;
        this.mAnnotations = annotations;
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

    public Bitmap getBitmap() {
        File file = new File(mUrl);
        if(!file.exists()) {
            Log.e("FILE IS MISSING", mUrl);
        }
        return BitmapFactory.decodeFile(mUrl);
    }

    public void setAnnotations(ArrayList<MemeAnnotation> annotations) {
        mAnnotations = annotations;
    }

    public ArrayList<MemeAnnotation> getAnnotations() {
        return mAnnotations;
    }
}
