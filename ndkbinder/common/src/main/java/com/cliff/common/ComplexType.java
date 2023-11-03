package com.cliff.common;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ComplexType implements Parcelable {
    public final int mInt;
    public final long mLong;
    public final boolean mBoolean;
    public final float mFloat;
    public final double mDouble;
    public final String mString;


    protected ComplexType(Parcel in) {
        mInt = in.readInt();
        mLong = in.readLong();
        mBoolean = in.readByte() != 0;
        mFloat = in.readFloat();
        mDouble = in.readDouble();
        mString = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mInt);
        dest.writeLong(mLong);
        dest.writeByte((byte) (mBoolean ? 1 : 0));
        dest.writeFloat(mFloat);
        dest.writeDouble(mDouble);
        dest.writeString(mString);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ComplexType> CREATOR = new Creator<ComplexType>() {
        @Override
        public ComplexType createFromParcel(Parcel in) {
            return new ComplexType(in);
        }

        @Override
        public ComplexType[] newArray(int size) {
            return new ComplexType[size];
        }
    };

    @NonNull
    @Override
    public String toString() {
        return "ComplexType{" +
                "mInt=" + mInt +
                ", mLong=" + mLong +
                ", mBoolean=" + mBoolean +
                ", mFloat=" + mFloat +
                ", mDouble=" + mDouble +
                ", mString='" + mString + '\'' +
                '}';
    }
}
