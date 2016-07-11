package com.epicodus.mememaker.services;


import com.epicodus.mememaker.models.Meme;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MemeService {

    public static void findMemes(Callback callback) {

        OkHttpClient client = new OkHttpClient.Builder().build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.imgflip.com/get_memes").newBuilder();
        String url = urlBuilder.build().toString();

        Request request= new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Meme> processResults(Response response) {
        ArrayList<Meme> memes = new ArrayList<>();

        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject memeObjectJSON = new JSONObject(jsonData);
                JSONObject dataJSON = memeObjectJSON.getJSONObject("data");
                JSONArray memesJSON = dataJSON.getJSONArray("memes");

                for (int i = 0; i < memesJSON.length(); i++) {
                    JSONObject memeJSON = memesJSON.getJSONObject(i);
                    String name = memeJSON.getString("name");
                    double id = memeJSON.getDouble("id");
                    String url = memeJSON.getString("url");
                    Meme meme = new Meme(name, url, id);
                    memes.add(meme);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return memes;
    }
}
