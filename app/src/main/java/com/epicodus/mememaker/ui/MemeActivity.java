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
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            // text color - #3D3D3D
            paint.setColor(Color.rgb(255,255,255));
            // text size in pixels
            paint.setTextSize((int) (13 * scale));
            // text shadow
            paint.setShadowLayer(1f, 0f, 1f, Color.BLACK);

//            //set text width to canvas with minus 16dp padding
//            int mTextWidth = canvas.getWidth() - (int) (16 * scale);
//
//            //init static layout for text
//            StaticLayout mTextLayout = new StaticLayout(mText,
//                    paint, mTextWidth, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);)

            // draw text to the Canvas center
            Rect bounds = new Rect();
            paint.getTextBounds(mText, 0, mText.length(), bounds);
            int x = (bitmap.getWidth() - bounds.width())/7;
            int y = (bitmap.getHeight() + bounds.height())/23;

            canvas.drawText(mText, x * scale, y * scale, paint);

            paint.getTextBounds(yText, 0, yText.length(), bounds);
            int z = (bitmap.getWidth() - bounds.width())/7;
            int q = (bitmap.getHeight() + bounds.height())/4;

            canvas.drawText(yText, z * scale, q * scale, paint);

            return bitmap;
        } catch (Exception e) {
            // TODO: handle exception

            return null;
        }
    }

}

//http://www.skoumal.net/en/android-drawing-multiline-text-on-bitmap/