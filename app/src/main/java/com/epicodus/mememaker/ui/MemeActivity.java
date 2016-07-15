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
import android.graphics.Typeface;
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
        Bitmap borderedBmp = addBorder(bmp, 15);
        mMemeImageView.setImageBitmap(borderedBmp);

    }

    public Bitmap drawTextToBitmap(Context mContext, Bitmap bitmap, String mText, String yText) {
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

            //apply IMPACT font
            Typeface impactFont = Typeface.createFromAsset(getAssets(), "fonts/impact.ttf");

            paint.setTypeface(impactFont);
            // text color - #3D3D3D
            paint.setColor(Color.rgb(255,255,255));
            // text size in pixels
            paint.setTextSize((int) (12 * scale));
            //set letter spacing - requires api level 21 or above
            paint.setLetterSpacing(.08f);

            //add stroke for black outline
            TextPaint strokePaint = new TextPaint();
             strokePaint.setTypeface(impactFont);
             strokePaint.setARGB(255, 0, 0, 0);
             strokePaint.setTextSize((int) (12 * scale));
             strokePaint.setStyle(Paint.Style.STROKE);
             strokePaint.setStrokeWidth(7);
             strokePaint.setLetterSpacing(.08f);

            //stroke paint
             int mStrokeTextWidth = canvas.getWidth() - (int) (0 * scale);
             int yStrokeTextWidth = canvas.getWidth() - (int) (0 * scale);

            StaticLayout strokeMTextLayout = new StaticLayout(mText,
                strokePaint, mStrokeTextWidth, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
            StaticLayout strokeYTextLayout = new StaticLayout(yText,
                strokePaint, yStrokeTextWidth, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);

            //text paint
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
            float z = (bitmap.getHeight() - yTextHeight);

            // draw text to the Canvas
            canvas.save();
            canvas.translate(x, y);
            strokeMTextLayout.draw(canvas);
            mTextLayout.draw(canvas);
            canvas.save();
            canvas.translate(q, z);
            strokeYTextLayout.draw(canvas);
            yTextLayout.draw(canvas);
            canvas.save();

            return bitmap;
    }

    public Bitmap addBorder(Bitmap bmp, int borderSize) {
        Bitmap bmpWithBorder = Bitmap.createBitmap(bmp.getWidth() + borderSize * 2, bmp.getHeight() + borderSize * 2, bmp.getConfig());
        Canvas canvas = new Canvas(bmpWithBorder);
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(bmp, borderSize, borderSize, null);
        return bmpWithBorder;
    }
}