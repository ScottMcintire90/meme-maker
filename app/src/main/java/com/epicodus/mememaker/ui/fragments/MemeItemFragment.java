package com.epicodus.mememaker.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.parceler.Parcels;

import com.epicodus.mememaker.R;
import com.epicodus.mememaker.adapters.MemeListAdapter;
import com.epicodus.mememaker.adapters.MemePagerAdapter;
import com.epicodus.mememaker.models.Meme;
import com.epicodus.mememaker.ui.EditMemeActivity;
import com.epicodus.mememaker.ui.MemeDetailActivity;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MemeItemFragment extends Fragment {
    @Bind(R.id.editMemeImage) ImageView mEditMemeImage;
    @Bind(R.id.name) TextView mName;
    @Bind(R.id.createMeme) Button mCreateMeme;

    private static EditText topTextInput;
    private static EditText bottomTextInput;

    private Meme mMeme;

    public static MemeItemFragment newInstance(Meme meme) {
        MemeItemFragment memeItemFragment = new MemeItemFragment();
        Bundle args = new Bundle();
        args.putParcelable("meme", Parcels.wrap(meme));
        memeItemFragment.setArguments(args);
        return memeItemFragment;
    }

    TopSectionListener activityCommander;
    public interface TopSectionListener{
        public void createMeme(String top, String bottom);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try{
            activityCommander = (TopSectionListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString());
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMeme = Parcels.unwrap(getArguments().getParcelable("meme"));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meme_item, container, false);
        ButterKnife.bind(this, view);
        mEditMemeImage.setImageBitmap(mMeme.getBitmapFromURL(mMeme.getUrl()));
        mName.setText(mMeme.getName());
        topTextInput = (EditText) view.findViewById(R.id.topText);
        bottomTextInput = (EditText) view.findViewById(R.id.bottomText);
        final Button button = (Button) view.findViewById(R.id.createMeme);

        button.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent(getActivity(), EditMemeActivity.class);
                    }
                }
        );

        return view;
    }

//    public void buttonClicked(View view){
//        activityCommander.createMeme(topTextInput.getText().toString(), bottomTextInput.getText().toString());
//    }
}

