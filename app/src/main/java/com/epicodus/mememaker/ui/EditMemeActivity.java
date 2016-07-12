package com.epicodus.mememaker.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.io.IOException;
import java.io.InputStream;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meme);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String image = intent.getStringExtra("image");
        final Bitmap memeBitmap = getBitmapFromURL(image);

        //Convert to byte array
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        memeBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        final byte[] byteArray = stream.toByteArray();


        Picasso.with(EditMemeActivity.this).load(image).into(mEditMemeImage);

        final String upper = mEditUpperText.getText().toString();
        final String lower = mEditLowerText.getText().toString();

        mSaveMeme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditMemeActivity.this, MemeActivity.class);
                intent.putExtra("bitmap", byteArray);
                intent.putExtra("upper", upper);
                intent.putExtra("lower", lower);
                startActivity(intent);
            }
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
//private Bitmap DownloadImage(String URL)
//{
////      System.out.println("image inside="+URL);
//    Bitmap bitmap = null;
//    InputStream in = null;
//    try {
//        in = OpenHttpConnection(URL);
//        bitmap = BitmapFactory.decodeStream(in);
//        in.close();
//    } catch (IOException e1) {
//        // TODO Auto-generated catch block
//        e1.printStackTrace();
//    }
////        System.out.println("image last");
//    return bitmap;
//}
//    private InputStream OpenHttpConnection(String urlString)
//            throws IOException
//    {
//        InputStream in = null;
//        int response = -1;
//
//        URL url = new URL(urlString);
//        URLConnection conn = url.openConnection();
//
//        if (!(conn instanceof HttpURLConnection))
//            throw new IOException("Not an HTTP connection");
//
//        try{
//            HttpURLConnection httpConn = (HttpURLConnection) conn;
//            httpConn.setAllowUserInteraction(false);
//            httpConn.setInstanceFollowRedirects(true);
//            httpConn.setRequestMethod("GET");
//            httpConn.connect();
//
//            response = httpConn.getResponseCode();
//            if (response == HttpURLConnection.HTTP_OK)
//            {
//                in = httpConn.getInputStream();
//            }
//        }
//        catch (Exception ex)
//        {
//            throw new IOException("Error connecting");
//        }
//        return in;
//    }

}