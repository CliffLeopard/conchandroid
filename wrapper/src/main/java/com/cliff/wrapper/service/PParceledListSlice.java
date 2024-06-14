package com.cliff.wrapper.service;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.util.Collections;
import java.util.List;

public class PParceledListSlice<T extends Parcelable> extends PBaseParceledListSlice<T> {
    public PParceledListSlice(List<T> list) {
        super(list);
    }

    private PParceledListSlice(Parcel in, ClassLoader loader) {
        super(in, loader);
    }

    public static <T extends Parcelable> PParceledListSlice<T> emptyList() {
        return new PParceledListSlice<T>(Collections.<T>emptyList());
    }

    @Override
    public int describeContents() {
        int contents = 0;
        final List<T> list = getList();
        for (int i = 0; i < list.size(); i++) {
            contents |= list.get(i).describeContents();
        }
        return contents;
    }

    @Override
    protected void writeElement(T parcelable, Parcel dest, int callFlags) {
        parcelable.writeToParcel(dest, callFlags);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void writeParcelableCreator(T parcelable, Parcel dest) {
        dest.writeParcelableCreator((Parcelable) parcelable);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected Parcelable.Creator<?> readParcelableCreator(Parcel from, ClassLoader loader) {
        return from.readParcelableCreator(loader);
    }

    public static final Parcelable.ClassLoaderCreator<PParceledListSlice> CREATOR =
            new Parcelable.ClassLoaderCreator<PParceledListSlice>() {
                public PParceledListSlice createFromParcel(Parcel in) {
                    return new PParceledListSlice(in, null);
                }

                @Override
                public PParceledListSlice createFromParcel(Parcel in, ClassLoader loader) {
                    return new PParceledListSlice(in, loader);
                }

                @Override
                public PParceledListSlice[] newArray(int size) {
                    return new PParceledListSlice[size];
                }
            };
}
