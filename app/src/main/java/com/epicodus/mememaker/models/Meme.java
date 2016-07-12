package com.epicodus.mememaker.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

    public Bitmap getBitmapFromURL() {
        try {
            URL url = new URL(mUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setAnnotations(ArrayList<MemeAnnotation> annotations) {
        mAnnotations = annotations;
    }

    public ArrayList<MemeAnnotation> getAnnotations() {
        return mAnnotations;
    }
}
