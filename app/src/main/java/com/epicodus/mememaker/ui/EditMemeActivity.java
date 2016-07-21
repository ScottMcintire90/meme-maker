package com.epicodus.mememaker.ui;

import android.app.ProgressDialog;
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
import android.os.AsyncTask;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.epicodus.mememaker.R;
import com.epicodus.mememaker.extensions.BaseActivity;
import com.epicodus.mememaker.models.Meme;
import com.epicodus.mememaker.ui.views.MemeImageView;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

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
import java.util.concurrent.ExecutionException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EditMemeActivity extends BaseActivity {
    @Bind(R.id.editMemeImage) ImageView mEditMemeImage;
    @Bind(R.id.editUpperText) EditText mEditUpperText;
    @Bind(R.id.editLowerText) EditText mEditLowerText;
    @Bind(R.id.saveMeme) ImageButton mSaveMeme;

    private Uri imageUri;
    private Bitmap memeBitmap;
    private byte[] byteArray;
    private String image;
    private Bitmap asyncBitmap;

    private ProgressDialog simpleWaitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meme);
        ButterKnife.bind(this);

        Intent intent = getIntent();


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
//            memeBitmap = getBitmapFromURL(image);
           new AsyncTaskLoadImage(mEditMemeImage).execute(image);
            try {
                memeBitmap = new AsyncTaskLoadImage(mEditMemeImage).execute(image).get();
            }
            catch (ExecutionException | InterruptedException ei) {
                ei.printStackTrace();
            }
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
//    public static Bitmap getBitmapFromURL(String image) {
//        try {
//            URL url = new URL(image);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoInput(true);
//            connection.connect();
//            InputStream input = connection.getInputStream();
//            Bitmap myBitmap = BitmapFactory.decodeStream(input);
//            return myBitmap;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }


    public class AsyncTaskLoadImage  extends AsyncTask<String, String, Bitmap> {
        private final static String TAG = "AsyncTaskLoadImage";
        private ImageView imageView;

        public AsyncTaskLoadImage(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            asyncBitmap = null;
            try {
                URL url = new URL(params[0]);
                asyncBitmap = BitmapFactory.decodeStream((InputStream)url.getContent());
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
            return asyncBitmap;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }

    public class SendTaskLoadImage  extends AsyncTask<String, String, Bitmap> {
        private final static String TAG = "AsyncTaskLoadImage";
        private ImageView imageView;

        public SendTaskLoadImage(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            asyncBitmap = null;
            try {
                URL url = new URL(params[0]);
                asyncBitmap = BitmapFactory.decodeStream((InputStream)url.getContent());
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
            return asyncBitmap;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }

}
