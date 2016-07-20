package com.epicodus.mememaker.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.epicodus.mememaker.R;
import com.epicodus.mememaker.adapters.MemeListAdapter;
import com.epicodus.mememaker.extensions.BaseActivity;
import com.epicodus.mememaker.models.Meme;
import com.epicodus.mememaker.services.MemeService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PhotoAPIActivity extends BaseActivity {
    public static final String TAG = PhotoAPIActivity.class.getSimpleName();
    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    private MemeListAdapter mAdapter;

    public ArrayList<Meme> mMemes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_api);
        ButterKnife.bind(this);
        Intent intent = getIntent();

        getMemes();
    }

    private void getMemes() {
        final MemeService memeService = new MemeService();
        memeService.findMemes(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                mMemes = memeService.processResults(response);

                PhotoAPIActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        mAdapter = new MemeListAdapter(getApplicationContext(), mMemes);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager =
                                new LinearLayoutManager(PhotoAPIActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                    }
                });
            }

        });
    }
}
