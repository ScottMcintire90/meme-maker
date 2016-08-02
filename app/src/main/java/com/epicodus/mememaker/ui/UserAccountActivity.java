package com.epicodus.mememaker.ui;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.epicodus.mememaker.R;
import com.epicodus.mememaker.adapters.FirebaseMemeViewHolder;
import com.epicodus.mememaker.extensions.BaseActivity;
import com.epicodus.mememaker.models.MemeUrl;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserAccountActivity extends BaseActivity {
    private DatabaseReference mMemeReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String uid;
    private String userName;
    ArrayList<MemeUrl> mMemeList = new ArrayList<>();
    @Bind(R.id.userTitle) TextView mUserTitle;
    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
        ButterKnife.bind(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        mMemeReference = FirebaseDatabase
                .getInstance()
                .getReference("Url")
                .child(uid);

        mMemeReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    MemeUrl newItem = ds.getValue(MemeUrl.class);

                    mMemeList.add(newItem);
                }
//                renderItemList(mMemeList);
                for(int i = 0; i<mMemeList.size(); i++){
                    System.out.println(mMemeList.get(i).getUrl());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError);
            }
        });

        mMemeReference = FirebaseDatabase.getInstance().getReference("Url").child(uid);
        setUpFirebaseAdapter();

//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                   userName = user.getDisplayName();
//            }
//        };
        userName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        mUserTitle.setText(userName + "'s Memes");
    }



    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<MemeUrl, FirebaseMemeViewHolder>
                (MemeUrl.class, R.layout.user_meme_list, FirebaseMemeViewHolder.class,
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