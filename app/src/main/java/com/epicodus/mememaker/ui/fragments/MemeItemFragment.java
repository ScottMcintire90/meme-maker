package com.epicodus.mememaker.ui.fragments;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.parceler.Parcels;

import com.epicodus.mememaker.R;
import com.epicodus.mememaker.models.Meme;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MemeItemFragment extends Fragment {
    @Bind(R.id.editMemeImage) ImageView mEditMemeImage;
    @Bind(R.id.name) TextView mName;

    private Meme mMeme;

    public static MemeItemFragment newInstance(Meme meme) {
        MemeItemFragment memeItemFragment = new MemeItemFragment();
        Bundle args = new Bundle();
        args.putParcelable("meme", Parcels.wrap(meme));
        memeItemFragment.setArguments(args);
        return memeItemFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMeme = Parcels.unwrap(getArguments().getParcelable("meme"));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {View view = inflater.inflate(R.layout.fragment_meme_item, container, false);
        ButterKnife.bind(this, view);
        mEditMemeImage.setImageBitmap(mMeme.getBitmapFromURL(mMeme.getUrl()));
        mName.setText(mMeme.getName());
        return view;
    }

}
