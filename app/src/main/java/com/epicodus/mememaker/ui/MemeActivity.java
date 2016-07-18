package com.epicodus.mememaker.ui;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.epicodus.mememaker.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MemeActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = LoginActivity.class.getSimpleName();

    @Bind(R.id.memeImageView) ImageView mMemeImageView;
    @Bind(R.id.saveBitmap) ImageButton mSaveBitmap;
    @Bind(R.id.downloadButton) ImageButton mDownloadButton;
    @Bind(R.id.shareButton) ImageButton mShareButton;

    private Bitmap borderedBmp;
    private Bitmap bmp;

    StorageReference storageRef;
    FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meme);
        ButterKnife.bind(this);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://meme-maker-3d8b9.appspot.com");

        Intent intent = getIntent();
        String upperText = intent.getStringExtra("upper");
        String lowerText = intent.getStringExtra("lower");
        byte[] byteArray = getIntent().getByteArrayExtra("bitmap");
        Uri imageUri = intent.getData();
        if(imageUri == null) {
            byteArray = getIntent().getByteArrayExtra("bitmap");
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            bmp = drawTextToBitmap(this, bitmap, upperText, lowerText);
        } else {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                bmp = drawTextToBitmap(this, bitmap, upperText, lowerText);
            }
            catch (IOException e) {}
        }
        borderedBmp = addBorder(bmp, 15);
        mMemeImageView.setImageBitmap(borderedBmp);
        mSaveBitmap.setOnClickListener(this);
        mDownloadButton.setOnClickListener(this);
        mShareButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mSaveBitmap) {
            Random r = new Random();
            int random = r.nextInt(1000000 - 0);
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat mdformat = new SimpleDateFormat("MM-dd-yyyy");
            String memeName = "Meme Image" + random + " " + mdformat.format(calendar.getTime());
            StorageReference imagesRef = storageRef.child(memeName);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            borderedBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = imagesRef.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                Log.d("Result:", "Unsuccessful");
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.d("Result:", "successful");
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                }
            });
        }
        if(v == mDownloadButton) {
            Random r = new Random();
            int random = r.nextInt(10000 - 0);
            saveFile(borderedBmp, "IMG" + random);
        }
        if(v == mShareButton) {
            shareIt(borderedBmp);
        }
    }

    public Bitmap drawTextToBitmap(Context mContext, Bitmap bitmap, String mText, String yText) {
            //prepare canvas
            Resources resources = mContext.getResources();
            float scale = resources.getDisplayMetrics().density;

        android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();

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
            paint.setTextSize((int) (11 * scale));
            //set letter spacing - requires api level 21 or above
//            paint.setLetterSpacing(.08f);

            //add stroke for black outline
            TextPaint strokePaint = new TextPaint();
             strokePaint.setTypeface(impactFont);
             strokePaint.setARGB(255, 0, 0, 0);
             strokePaint.setTextSize((int) (11 * scale));
             strokePaint.setStyle(Paint.Style.STROKE);
             strokePaint.setStrokeWidth(7);
//             strokePaint.setLetterSpacing(.08f);

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

    private String saveFile(Bitmap bitmapImage, String fileName) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, fileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (Exception e) {
            }
            return directory.getAbsolutePath();
        }
    }

    private void shareIt(Bitmap bitmap) {
        Random r = new Random();
        int random = r.nextInt(1000000 - 0);
        try {
            File file = new File(this.getCacheDir(), random + ".png");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);

            //sharing implementation here
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);

            sharingIntent.putExtra(android.content.Intent.EXTRA_STREAM, Uri.fromFile(file));
            sharingIntent.setType("image/png");
            startActivity(sharingIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}