package com.epicodus.mememaker;

import android.os.Parcel;
import android.os.Parcelable;

public class Meme implements Parcelable {
    int mImageId;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mImageId);
    }
    public Meme (int sImageId) {
        this.mImageId = sImageId;
    }
    public static final Parcelable.Creator<Meme> CREATOR = new Parcelable.Creator<Meme>() {
        public Meme createFromParcel(Parcel in) {
            return new Meme(in);
        }

        public Meme[] newArray(int size) {
            return new Meme[size];
        }
    };

    private Meme(Parcel in) {
        mImageId = in.readInt();
    }
}
