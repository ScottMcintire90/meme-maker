package com.epicodus.mememaker.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.epicodus.mememaker.Constants;
import com.epicodus.mememaker.R;
import com.epicodus.mememaker.models.MemeUrl;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CreatedMemesAdapter extends ArrayAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<String> imageUrls;

    public CreatedMemesAdapter(Context context, ArrayList<String> imageUrls) {
        super(context, R.layout.listview_item_image, imageUrls);
        this.context = context;
        this.imageUrls = Constants.memeList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.listview_item_image, parent, false);
        }
        Picasso
                .with(context)
                .load(Constants.memeList.get(position))
                .fit()
                .into((ImageView) convertView);
        return convertView;
    }
}