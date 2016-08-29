package com.epicodus.mememaker.ui;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.epicodus.mememaker.Constants;
import com.epicodus.mememaker.R;
import com.epicodus.mememaker.adapters.CreatedMemesAdapter;
import com.epicodus.mememaker.extensions.BaseActivity;
import com.epicodus.mememaker.models.MemeUrl;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WelcomeActivity extends BaseActivity {
    public static final String TAG = WelcomeActivity.class.getSimpleName();
    private String mName;

    @Bind(R.id.listView) ListView mListView;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Firebase mFirebaseRef;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        Firebase.setAndroidContext(this);
        Constants.memeList.clear();
        getMemes();
        mListView.setAdapter(new CreatedMemesAdapter(WelcomeActivity.this, Constants.memeList));
        Intent intent = getIntent();
        intent.putExtra("name", mName);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                } else {

                }
            }
        };
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    // Show Recently Created Memes
    public void getMemes() {
        mRef = FirebaseDatabase.getInstance().getReference("Url");
        Query q = mRef.orderByKey();

        q.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    MemeUrl meme = ds.getValue(MemeUrl.class);
                    meme.setUrl(ds.getValue(MemeUrl.class).getUrl());
                    if (Constants.memeList.size() < 10) {
                        Constants.memeList.add(meme.getUrl());
                    } else {
                        Constants.memeList.remove(0);
                        Constants.memeList.add(meme.getUrl());
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}
