package com.epicodus.mememaker.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.mememaker.Constants;
import com.epicodus.mememaker.R;
import com.epicodus.mememaker.adapters.CreatedMemesAdapter;
import com.epicodus.mememaker.extensions.BaseActivity;
import com.epicodus.mememaker.models.MemeUrl;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MediaSelectionActivity extends BaseActivity implements View.OnClickListener {
    public static final String TAG = WelcomeActivity.class.getSimpleName();

    @Bind(R.id.galleryButton) ImageButton mGalleryButton;
    @Bind(R.id.cameraButton) ImageButton mCameraButton;
    @Bind(R.id.photoButton) ImageButton mPhotoButton;
    @Bind(R.id.starButton) ImageButton mStarButton;
    @Bind(R.id.apiText) TextView mApiText;
    @Bind(R.id.phoneGalleryText) TextView mPhoneGalleryText;
    @Bind(R.id.phoneCameraText) TextView mPhoneCameraText;
    @Bind(R.id.starText) TextView mStarText;
    private int PICK_IMAGE_REQUEST = 1;
    private byte[] byteArray;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Firebase mFirebaseRef;

    //camera
    public static final int REQUEST_TAKE_PHOTO = 0;
    public static final int REQUEST_PICK_PHOTO = 2;
    public static final int MEDIA_TYPE_IMAGE = 4;
    private DatabaseReference mRef;
    private Uri mMediaUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_selection);
        ButterKnife.bind(this);
        Constants.memeList.clear();

        mPhotoButton.setOnClickListener(this);
        mCameraButton.setOnClickListener(this);
        mGalleryButton.setOnClickListener(this);
        mStarButton.setOnClickListener(this);
        mApiText.setOnClickListener(this);
        mPhoneGalleryText.setOnClickListener(this);
        mPhoneCameraText.setOnClickListener(this);
        mStarText.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

            }
        };
    }

    @Override
    public void onClick(View v) {
        if (v == mPhotoButton || v == mApiText) {
            Intent intent = new Intent(MediaSelectionActivity.this, PhotoAPIActivity.class);
            startActivity(intent);
        }

        if (v == mGalleryButton || v == mPhoneGalleryText) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        }

        if (v == mCameraButton || v == mPhoneCameraText) {
            mMediaUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
            if (mMediaUri == null) {
                Toast.makeText(this, "There was a problem accessing your device's external storage.",
                        Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mMediaUri);
                startActivityForResult(intent, REQUEST_TAKE_PHOTO);
            }
        }

        if(v == mStarButton || v == mStarText) {
            Intent intent = new Intent(MediaSelectionActivity.this, WelcomeActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {
                Intent intent = new Intent(this, EditMemeActivity.class);
                intent.setData(mMediaUri);
                startActivity(intent);
            }
            if (requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null) {
                Uri uri = data.getData();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                    //Convert to byte array
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byteArray = stream.toByteArray();

                    Intent intent = new Intent(this, EditMemeActivity.class);
                    intent.putExtra("galleryImage", byteArray);
                    startActivity(intent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Sorry, there was an error!", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    private Uri getOutputMediaFileUri(int mediaType) {
        //check for external storage
        if (isExternalStorageAvailable()) {
            //1. Get the external storage directory
            File mediaStorageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            //2. Create unique file name
            String fileName = "";
            String fileType = "";
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            if (mediaType == MEDIA_TYPE_IMAGE) {
                fileName = "IMG_" + timeStamp;
                fileType = ".jpg";
            } else {
                return null;
            }
            //3. Create the file
            File mediaFile;
            try {
                mediaFile = File.createTempFile(fileName, fileType, mediaStorageDir);
                Log.i(TAG, "File: " + Uri.fromFile(mediaFile));
                //4. Return the file's URI
                return Uri.fromFile(mediaFile);
            } catch (IOException e) {
                Log.e(TAG, "Error creating file: " +
                        mediaStorageDir.getAbsolutePath() + fileName + fileType);
            }
        }
        //something went wrong
        return null;
    }
    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        } else {
            return false;
        }
    }
}
