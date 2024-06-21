package wrapper.replace;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class PProfilerInfo implements Parcelable {
    protected PProfilerInfo(Parcel in) {
    }

    public static final Creator<PProfilerInfo> CREATOR = new Creator<PProfilerInfo>() {
        @Override
        public PProfilerInfo createFromParcel(Parcel in) {
            return new PProfilerInfo(in);
        }

        @Override
        public PProfilerInfo[] newArray(int size) {
            return new PProfilerInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
    }
}
