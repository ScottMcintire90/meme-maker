package com.epicodus.mememaker.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.epicodus.mememaker.R;
import com.epicodus.mememaker.extensions.BaseActivity;

import java.util.ArrayList;

public class UserAccountActivity extends BaseActivity {
    private UserAccountActivity mAdapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
    }

    public void renderMemeList(ArrayList array) {
        UserAccountActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                mAdapter2 = new ItemListAdapter(getApplicationContext(), mSortedArray);
//                mRecyclerView2.setAdapter(mAdapter2);
//                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(UserAccountActivity.this);
//                mRecyclerView2.setLayoutManager(layoutManager);
//                mRecyclerView2.setHasFixedSize(true);
//            }
//        });
            }

        });
    }
}
