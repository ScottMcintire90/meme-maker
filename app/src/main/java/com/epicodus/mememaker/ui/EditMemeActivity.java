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
import android.os.Environment;
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

    @Bind(R.id.editMemeImage)
    ImageView mEditMemeImage;
    @Bind(R.id.editUpperText)
    EditText mEditUpperText;
    @Bind(R.id.editLowerText)
    EditText mEditLowerText;
    @Bind(R.id.saveMeme)
    Button mSaveMeme;

    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meme);
        ButterKnife.bind(this);
        file = new File(Environment.getExternalStorageDirectory() + File.separator + "saveimage");
        file.mkdirs();

        Intent intent = getIntent();
        String image = intent.getStringExtra("image");
        final String upper = mEditUpperText.getText().toString();
        final String lower = mEditLowerText.getText().toString();
        final Bitmap memeBitmap = getBitmapFromURL(image);

        final Bitmap bmap = loadBitmapFromView(mEditMemeImage);

        //Convert to byte array
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        final byte[] byteArray = stream.toByteArray();


        Picasso.with(EditMemeActivity.this).load(image).into(mEditMemeImage);

        mSaveMeme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditMemeActivity.this, MemeActivity.class);
                intent.putExtra("bitmap", bmap);
                intent.putExtra("upper", upper);
                intent.putExtra("lower", lower);
                startActivity(intent);
            }
        });
    }

    public static Bitmap loadBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap( v.getLayoutParams().width, v.getLayoutParams().height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        return b;
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

//    public static Bitmap drawText(Bitmap memeMap, String upper) {
//        String text = "Write your text on image";
//        Paint textPaint = new Paint();
//        textPaint.setColor(Color.GREEN);
//        textPaint.setTextSize(25);
//        textPaint.setTextAlign(Paint.Align.RIGHT);
//
//        Canvas canvas = new Canvas(memeMap);
//        canvas.drawText(text, 880, 30, textPaint);
//
//    }
}

//http://stackoverflow.com/questions/2339429/android-view-getdrawingcache-returns-null-only-null

