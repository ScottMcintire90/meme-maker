package com.epicodus.mememaker.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    }
}
