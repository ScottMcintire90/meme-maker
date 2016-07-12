package com.epicodus.mememaker.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;

import com.epicodus.mememaker.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MemeActivity extends AppCompatActivity {
    @Bind(R.id.memeImageView) ImageView mMemeImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meme);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String upperText = intent.getStringExtra("upper");
        String lowerText = intent.getStringExtra("lower");
        byte[] byteArray = getIntent().getByteArrayExtra("bitmap");
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        mMemeImageView.setImageBitmap(bitmap);
    }
}
