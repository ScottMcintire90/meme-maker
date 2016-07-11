package com.epicodus.mememaker.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.epicodus.mememaker.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener{
    private String mName;
    @Bind(R.id.memeListView) ListView mMemeListView;
    @Bind(R.id.welcomeName) TextView mWelcomeName;
    @Bind(R.id.galleryButton) ImageButton mGalleryButton;
    @Bind(R.id.cameraButton) ImageButton mCameraButton;
    @Bind(R.id.photoButton) ImageButton mPhotoButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        intent.putExtra("name", mName);
        mWelcomeName.setText("Welcome Scott!");

        mCameraButton.setOnClickListener(this);
        mGalleryButton.setOnClickListener(this);
        mPhotoButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mGalleryButton) {
                Intent intent = new Intent(WelcomeActivity.this, GalleryImageActivity.class);
                startActivity(intent);
            }

        if(v == mCameraButton) {
                Intent intent = new Intent(WelcomeActivity.this, CameraImageActivity.class);
                startActivity(intent);
            }
        if(v == mPhotoButton) {
                Intent intent = new Intent(WelcomeActivity.this, PhotoAPIActivity.class);
                startActivity(intent);
            }
    }
}
