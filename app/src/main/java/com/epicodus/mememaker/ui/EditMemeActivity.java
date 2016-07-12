package com.epicodus.mememaker.ui;

import android.content.Intent;
import android.view.ContextThemeWrapper;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.epicodus.mememaker.R;
import com.epicodus.mememaker.models.Meme;
import com.epicodus.mememaker.ui.views.MemeImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EditMemeActivity extends AppCompatActivity {
    public static final String TAG = EditMemeActivity.class.getSimpleName();
    private ArrayList<EditText> mMemeTexts;
    private FrameLayout mMemeContainer;
    private MemeImageView mMemeBitmapHolder;
    private Meme mCurrentMeme;
    private String mCurrentColor;

    @Bind(R.id.editMemeImage) ImageView mEditMemeImage;
    @Bind(R.id.editUpperText) EditText mEditUpperText;
    @Bind(R.id.editLowerText) EditText mEditLowerText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meme);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String image = intent.getStringExtra("image");
        Log.d(TAG, image);

        Picasso.with(EditMemeActivity.this).load(image).into(mEditMemeImage);

//        mMemeTexts = new ArrayList<EditText>();
//        mMemeContainer = (FrameLayout) findViewById(R.id.meme_container);
//        mMemeBitmapHolder = (MemeImageView) findViewById(R.id.meme_bitmap_container);

//        if(this.getIntent().hasExtra(EXTRA_IMAGE_FILE_PATH)) {
//            mImageFilePath = this.getIntent().getStringExtra(EXTRA_IMAGE_FILE_PATH);
//            mCurrentMeme = new Meme(-1, mImageFilePath, "", null);
//        } else {
//            mCurrentMeme = (Meme)this.getIntent().getSerializableExtra(EXTRA_MEME_OBJECT);
//            mImageFilePath = mCurrentMeme.getAssetLocation();
//
//            for(MemeAnnotation annotation : mCurrentMeme.getAnnotations()) {
//                addEditTextOverImage(
//                        annotation.getTitle(),
//                        annotation.getLocationX(),
//                        annotation.getLocationY(),
//                        annotation.getColor());
//            }
//        }

//        mMemeBitmapHolder.setImageBitmap(mCurrentMeme.getBitmap());
//        mMemeBitmapHolder.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (MotionEvent.ACTION_UP == motionEvent.getAction()) {
//                    int touchX = (int) motionEvent.getX();
//                    int touchY = (int) motionEvent.getY();
//
//                    addAnnotation(touchX, touchY, mCurrentColor);
//                    addEditTextOverImage("Title", touchX, touchY, mCurrentColor);
//                    return false;
//                } else {
//                    return true;
//                }
//            }
//        });
//    }
//
//    private void addAnnotation(int touchX, int touchY, String mCurrentColor) {
//        MemeAnnotation annotation = new MemeAnnotation();
//        annotation.setColor(mCurrentColor);
//        annotation.setLocationX(touchX);
//        annotation.setLocationY(touchY);
//
//        if(mCurrentMeme.getAnnotations() == null) {
//            mCurrentMeme.setAnnotations(new ArrayList<MemeAnnotation>());
//        }
//
//        mCurrentMeme.getAnnotations().add(annotation);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.save_action) {
//            final EditText input = new EditText(this);
//            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.MATCH_PARENT);
//            input.setLayoutParams(lp);
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder
//                    .setTitle("Name of meme?")
//                    .setMessage("Please give this meme a name.")
//                    .setView(input)
//                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            String memeName = input.getText().toString();
//                            mCurrentMeme.setName(memeName);
//                            saveMeme();
//                            finish();
//                        }
//                    });
//            builder.show();
//            return true;
//        } else if(id == android.R.id.home) {
//            finish();
//            return true;
//        } else if(id == R.id.choose_font_action) {
//
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void addEditTextOverImage(String title, int x, int y, String color) {
//        ContextThemeWrapper newContext = new ContextThemeWrapper(this, R.style.holoLightLess);
//        EditText editText = new EditText(this);
//        editText.setText(title);
//        editText.setBackground(null);
//        editText.setTextColor(Color.parseColor(color));
//
//        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layoutParams.setMargins(x, y, 0, 0);
//        editText.setLayoutParams(layoutParams);
//        mMemeContainer.addView(editText);
//        editText.requestFocus();
//        mMemeTexts.add(editText);
//    }
//
//
    }
}