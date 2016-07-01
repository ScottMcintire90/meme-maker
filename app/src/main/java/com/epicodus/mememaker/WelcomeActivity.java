package com.epicodus.mememaker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WelcomeActivity extends AppCompatActivity {
    private String mName;
    @Bind(R.id.memeListView) ListView mMemeListView;
    @Bind(R.id.welcomeName) TextView mWelcomeName;

    //temporary custom adapter display for showcase purposes
    private String[] memes = new String[] {"Compile Errors", "iOS vs Android Argument", "Morhpeus on Instagram", "Reading Website on Mobile",
            "I'm a Developer Now", "No Idea What I'm Doing"};

    Meme[] memeArray = {new Meme(R.drawable.compile_errors), new Meme(R.drawable.argument_image),
            new Meme(R.drawable.morpheus), new Meme(R.drawable.website),
            new Meme(R.drawable.developer), new Meme(R.drawable.no_idea)};

    Integer[] imageId = {
            R.drawable.compile_errors,
            R.drawable.argument_image,
            R.drawable.morpheus,
            R.drawable.website,
            R.drawable.developer,
            R.drawable.no_idea
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        intent.putExtra("name", mName);
        mWelcomeName.setText("Welcome Scott!");

        CustomList adapter = new CustomList(WelcomeActivity.this, memes, imageId);
        mMemeListView.setAdapter(adapter);
        mMemeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent newIntent = new Intent(WelcomeActivity.this, MemeActivity.class);
                newIntent.putExtra("memeObject", memeArray[position]);

                startActivity(newIntent);
            }
        });

    }
}
