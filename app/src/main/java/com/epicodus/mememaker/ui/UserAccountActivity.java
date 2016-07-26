package com.epicodus.mememaker.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.epicodus.mememaker.R;
import com.epicodus.mememaker.extensions.BaseActivity;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import butterknife.Bind;

public class UserAccountActivity extends BaseActivity {
    private UserAccountActivity mAdapter2;
    @Bind(R.id.recyclerView) RecyclerView mRecyclerView2;
    private DatabaseReference mRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
    }


    }
}
