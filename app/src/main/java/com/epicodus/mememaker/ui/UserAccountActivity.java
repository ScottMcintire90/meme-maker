package com.epicodus.mememaker.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.epicodus.mememaker.R;
import com.epicodus.mememaker.adapters.FirebaseMemeViewHolder;
import com.epicodus.mememaker.extensions.BaseActivity;
import com.epicodus.mememaker.models.MemeUrl;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserAccountActivity extends BaseActivity {
    private DatabaseReference mMemeReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
        ButterKnife.bind(this);

        mMemeReference = FirebaseDatabase.getInstance().getReference("Url");
        setUpFirebaseAdapter();
    }

    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<MemeUrl, FirebaseMemeViewHolder>
                (MemeUrl.class, R.layout.meme_list_item, FirebaseMemeViewHolder.class,
                        mMemeReference) {

            @Override
            protected void populateViewHolder(FirebaseMemeViewHolder viewHolder,
                                              MemeUrl model, int position) {
                viewHolder.bindRestaurant(model);
            }
        };
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }

}