package com.epicodus.mememaker.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
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

        Bitmap bmp =drawTextToBitmap(this, bitmap, upperText, lowerText);
        mMemeImageView.setImageBitmap(bmp);
    }

    public Bitmap drawTextToBitmap(Context mContext, Bitmap bitmap, String mText, String yText) {
        try {

            //prepare canvas
            Resources resources = mContext.getResources();
            float scale = resources.getDisplayMetrics().density;

            android.graphics.Bitmap.Config bitmapConfig =   bitmap.getConfig();
            // set default bitmap config if none
            if(bitmapConfig == null) {
                bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
            }
            // resource bitmaps are imutable,
            // so we need to convert it to mutable one
            bitmap = bitmap.copy(bitmapConfig, true);

            Canvas canvas = new Canvas(bitmap);

            // new antialised Paint
            TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            // text color - #3D3D3D
            paint.setColor(Color.rgb(255,255,255));
            // text size in pixels
            paint.setTextSize((int) (13 * scale));
            // text shadow
            paint.setShadowLayer(10, 1, 1, Color.BLACK);

            //set text width to canvas with minus 16dp padding
            int mTextWidth = canvas.getWidth() - (int) (0 * scale);
            int yTextWidth = canvas.getWidth() - (int) (0 * scale);
            //init static layout for text
            StaticLayout mTextLayout = new StaticLayout(mText,
                    paint, mTextWidth, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
            StaticLayout yTextLayout = new StaticLayout(yText,
                    paint, yTextWidth, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
            // get height of multiline text
            int mTextHeight = mTextLayout.getHeight();

            int yTextHeight = yTextLayout.getHeight();
            // get position of text's top left corner
            float x = (bitmap.getWidth() - mTextWidth)/2;
            float y = (bitmap.getHeight() - mTextHeight)/250;

            // get position of text's top left corner
            float q = (bitmap.getWidth() - yTextWidth)/2;
            float z = (bitmap.getHeight() - yTextHeight)/1;

            // draw text to the Canvas center
            canvas.save();
            canvas.translate(x, y);
            mTextLayout.draw(canvas);
            canvas.save();
            canvas.translate(q, z);
            yTextLayout.draw(canvas);
            canvas.restore();

            return bitmap;
        } catch (Exception e) {
            // TODO: handle exception

            return null;
        }
    }

}

//http://www.skoumal.net/en/android-drawing-multiline-text-on-bitmap/

//impact font and all caps