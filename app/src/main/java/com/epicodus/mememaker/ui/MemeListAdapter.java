package com.epicodus.mememaker.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.mememaker.R;
import com.epicodus.mememaker.models.Meme;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MemeListAdapter extends RecyclerView.Adapter<MemeListAdapter.MemeViewHolder> {
    private ArrayList<Meme> mMemes = new ArrayList<>();
    private Context mContext;

    public MemeListAdapter(Context context, ArrayList<Meme> memes) {
        mContext = context;
        mMemes = memes;
    }

    @Override
    public MemeListAdapter.MemeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meme_list_item, parent, false);
        MemeViewHolder viewHolder = new MemeViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MemeListAdapter.MemeViewHolder holder, int position) {
        holder.bindMeme(mMemes.get(position));
    }

    @Override
    public int getItemCount() {
        return mMemes.size();
    }

    public class MemeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.memeImageView) ImageView mMemeImageView;
        @Bind(R.id.memeNameTextView) TextView mMemeNameTextView;


        private Context mContext;

        public MemeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, EditMemeActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("image", mMemes.get(getAdapterPosition()).getUrl());
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        }

        public void bindMeme(Meme meme) {
            mMemeNameTextView.setText(meme.getName());
            Picasso.with(mContext).load(meme.getUrl()).into(mMemeImageView);
        }
    }
}
