package com.epicodus.mememaker.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.epicodus.mememaker.models.Meme;
import com.epicodus.mememaker.ui.MemeDetailActivity;
import com.epicodus.mememaker.ui.fragments.MemeItemFragment;

import java.util.ArrayList;

public class MemePagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Meme> mMemes;

    public MemePagerAdapter(FragmentManager fm, ArrayList<Meme> memes) {
        super(fm);
        mMemes = memes;
    }

    @Override
    public Fragment getItem(int position) {
        return MemeItemFragment.newInstance(mMemes.get(position));
    }

    @Override
    public int getCount() {
        return mMemes.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mMemes.get(position).getName();
    }
}