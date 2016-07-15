package com.epicodus.mememaker.ui;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.epicodus.mememaker.R;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener{
    private String mName;
    @Bind(R.id.memeListView) ListView mMemeListView;
    @Bind(R.id.welcomeName) TextView mWelcomeName;
    @Bind(R.id.galleryButton) ImageButton mGalleryButton;
    @Bind(R.id.cameraButton) ImageButton mCameraButton;
    @Bind(R.id.photoButton) ImageButton mPhotoButton;

    //camera
    public static final int REQUEST_TAKE_PHOTO = 0;
    public static final int REQUEST_PICK_PHOTO = 2;

    public static final int MEDIA_TYPE_IMAGE = 4;


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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        if(v == mGalleryButton) {
                Intent intent = new Intent(WelcomeActivity.this, GalleryImageActivity.class);
                startActivity(intent);
            }

        if(v == mCameraButton) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_TAKE_PHOTO);
            }
        if(v == mPhotoButton) {
                Intent intent = new Intent(WelcomeActivity.this, PhotoAPIActivity.class);
                startActivity(intent);
            }
    }
}
