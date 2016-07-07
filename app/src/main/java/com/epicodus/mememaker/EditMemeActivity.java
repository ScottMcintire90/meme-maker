package com.epicodus.mememaker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EditMemeActivity extends AppCompatActivity {
    public static final String TAG = EditMemeActivity.class.getSimpleName();

    @Bind(R.id.editMemeImage)
    ImageView mEditMemeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meme);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String image = intent.getStringExtra("image");
        Log.d(TAG, image);

        Picasso.with(EditMemeActivity.this).load(image).into(mEditMemeImage);

//        getBitmapFromURL(image);
//
//        Bitmap bitmap = getBitmapFromURL(image); // Load your bitmap here
//        Canvas canvas = new Canvas(bitmap);
//        Paint paint = new Paint();
//        paint.setColor(Color.WHITE);
//        paint.setTextSize(20);
//        canvas.drawText("Some Text here", 5, 5, paint);
    }

    public static Bitmap getBitmapFromURL(String image) {
        try {
            URL url = new URL(image);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
}