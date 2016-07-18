package com.epicodus.mememaker.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.ContextThemeWrapper;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.epicodus.mememaker.R;
import com.epicodus.mememaker.models.Meme;
import com.epicodus.mememaker.ui.views.MemeImageView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EditMemeActivity extends AppCompatActivity {

    @Bind(R.id.editMemeImage) ImageView mEditMemeImage;
    @Bind(R.id.editUpperText) EditText mEditUpperText;
    @Bind(R.id.editLowerText) EditText mEditLowerText;
    @Bind(R.id.saveMeme) Button mSaveMeme;
    private Uri imageUri;
    private Bitmap memeBitmap;
    private byte[] byteArray;
    private String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meme);
        ButterKnife.bind(this);

        Intent intent = getIntent();

//        Log.d("result", );

        //get camera image
        if (intent.getData() != null) {
            imageUri = intent.getData();
            Picasso.with(EditMemeActivity.this).load(imageUri).into(mEditMemeImage);
        }

        //get phone gallery image
        if (getIntent().getByteArrayExtra("galleryImage") != null) {
            byteArray = getIntent().getByteArrayExtra("galleryImage");
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            mEditMemeImage.setImageBitmap(bitmap);
        }

        //get API photo gallery image
        if (intent.getStringExtra("image") != null) {
            // get bitmap from api gallery
            image = intent.getStringExtra("image");
            memeBitmap = getBitmapFromURL(image);

            //Convert to byte array
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            memeBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();
            Picasso.with(EditMemeActivity.this).load(image).into(mEditMemeImage);
        }

        mSaveMeme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageUri == null) {
                    Intent intent = new Intent(EditMemeActivity.this, MemeActivity.class);
                    intent.putExtra("bitmap", byteArray);
                    String upper = mEditUpperText.getText().toString().toUpperCase();
                    intent.putExtra("upper", upper);
                    String lower = mEditLowerText.getText().toString().toUpperCase();
                    intent.putExtra("lower", lower);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(EditMemeActivity.this, MemeActivity.class);
                    intent.setData(imageUri);
                    String upper = mEditUpperText.getText().toString().toUpperCase();
                    intent.putExtra("upper", upper);
                    String lower = mEditLowerText.getText().toString().toUpperCase();
                    intent.putExtra("lower", lower);
                    startActivity(intent);
                }

            }

            ;
        });
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
            e.printStackTrace();
            return null;
        }
    }


}
