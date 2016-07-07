package com.epicodus.mememaker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EditMemeActivity extends AppCompatActivity {
    public static final String TAG = EditMemeActivity.class.getSimpleName();

    @Bind(R.id.editMemeImage) ImageView mEditMemeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meme);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String image = intent.getStringExtra("image");
        Log.d(TAG, image);

        Picasso.with(EditMemeActivity.this).load(image).into(mEditMemeImage);
    }
}
