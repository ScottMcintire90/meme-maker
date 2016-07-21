package com.epicodus.mememaker.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;

import com.epicodus.mememaker.R;

import butterknife.Bind;

public class MediaSelectionActivity extends AppCompatActivity {
    @Bind(R.id.galleryButton) ImageButton mGalleryButton;
    @Bind(R.id.cameraButton) ImageButton mCameraButton;
    @Bind(R.id.photoButton) ImageButton mPhotoButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_selection);
    }
}
