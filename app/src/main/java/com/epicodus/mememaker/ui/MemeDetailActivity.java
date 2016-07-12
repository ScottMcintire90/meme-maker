package com.epicodus.mememaker.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.epicodus.mememaker.R;
import com.epicodus.mememaker.adapters.MemePagerAdapter;
import com.epicodus.mememaker.models.Meme;

public class MemeDetailActivity extends AppCompatActivity {
    @Bind(R.id.viewPager) ViewPager mViewPager;
    private MemePagerAdapter adapterViewPager;
    ArrayList<Meme> mMemes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meme_detail);
        ButterKnife.bind(this);

        mMemes = Parcels.unwrap(getIntent().getParcelableExtra("memes"));
        int startingPosition = Integer.parseInt(getIntent().getStringExtra("position"));

        adapterViewPager = new MemePagerAdapter(getSupportFragmentManager(), mMemes);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
    }
}
