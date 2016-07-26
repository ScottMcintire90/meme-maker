package com.epicodus.mememaker.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.epicodus.mememaker.R;
import com.epicodus.mememaker.models.MemeUrl;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class FirebaseMemeViewHolder extends RecyclerView.ViewHolder {

    View mView;
    Context mContext;

    public FirebaseMemeViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
    }

    public void bindRestaurant(MemeUrl memeUrl) {
        ImageView memeImageView = (ImageView) mView.findViewById(R.id.memeImageView);

        Picasso.with(mContext)
                .load(memeUrl.getUrl())
                .fit()
                .into(memeImageView);
    }
}

