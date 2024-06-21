package wrapper.replace;

import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

abstract class PBaseParceledListSlice<T> implements Parcelable {
    private static String TAG = "ParceledListSlice";
    private static boolean DEBUG = false;

    /*
     * TODO get this number from somewhere else. For now set it to a quarter of
     * the 1MB limit.
     */
    private static final int MAX_IPC_SIZE = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R ? IBinder.getSuggestedMaxIpcSizeBytes() : 64 * 1024;

    private List<T> mList;

    private int mInlineCountLimit = Integer.MAX_VALUE;

    private boolean mHasBeenParceled = false;

    public PBaseParceledListSlice(List<T> list) {
        mList = list;
    }

    @SuppressWarnings("unchecked")
    PBaseParceledListSlice(Parcel p, ClassLoader loader) {
        final int N = p.readInt();
        mList = new ArrayList<T>(N);
        if (DEBUG) Log.d(TAG, "Retrieving " + N + " items");
        if (N <= 0) {
            return;
        }

        Parcelable.Creator<?> creator = readParcelableCreator(p, loader);
        Class<?> listElementClass = null;

        int i = 0;
        while (i < N) {
            if (p.readInt() == 0) {
                break;
            }
            listElementClass = readVerifyAndAddElement(creator, p, loader, listElementClass);
            if (DEBUG) Log.d(TAG, "Read inline #" + i + ": " + mList.get(mList.size() - 1));
            i++;
        }
        if (i >= N) {
            return;
        }
        final IBinder retriever = p.readStrongBinder();
        while (i < N) {
            if (DEBUG) Log.d(TAG, "Reading more @" + i + " of " + N + ": retriever=" + retriever);
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            data.writeInt(i);
            try {
                retriever.transact(IBinder.FIRST_CALL_TRANSACTION, data, reply, 0);
            } catch (RemoteException e) {
                Log.w(TAG, "Failure retrieving array; only received " + i + " of " + N, e);
                return;
            }
            while (i < N && reply.readInt() != 0) {
                listElementClass = readVerifyAndAddElement(creator, reply, loader,
                        listElementClass);
                if (DEBUG) Log.d(TAG, "Read extra #" + i + ": " + mList.get(mList.size() - 1));
                i++;
            }
            reply.recycle();
            data.recycle();
        }
    }

    private Class<?> readVerifyAndAddElement(Parcelable.Creator<?> creator, Parcel p,
                                             ClassLoader loader, Class<?> listElementClass) {
        final T parcelable = readCreator(creator, p, loader);
        if (listElementClass == null) {
            listElementClass = parcelable.getClass();
        } else {
            verifySameType(listElementClass, parcelable.getClass());
        }
        mList.add(parcelable);
        return listElementClass;
    }

    private T readCreator(Parcelable.Creator<?> creator, Parcel p, ClassLoader loader) {
        if (creator instanceof Parcelable.ClassLoaderCreator<?>) {
            Parcelable.ClassLoaderCreator<?> classLoaderCreator =
                    (Parcelable.ClassLoaderCreator<?>) creator;
            return (T) classLoaderCreator.createFromParcel(p, loader);
        }
        return (T) creator.createFromParcel(p);
    }

    private static void verifySameType(final Class<?> expected, final Class<?> actual) {
        if (!actual.equals(expected)) {
            throw new IllegalArgumentException("Can't unparcel type "
                    + (actual == null ? null : actual.getName()) + " in list of type "
                    + (expected == null ? null : expected.getName()));
        }
    }

    public List<T> getList() {
        return mList;
    }

    /**
     * Set a limit on the maximum number of entries in the array that will be included
     * inline in the initial parcelling of this object.
     */
    public void setInlineCountLimit(int maxCount) {
        mInlineCountLimit = maxCount;
    }

    /**
     * Write this to another Parcel. Note that this discards the internal Parcel
     * and should not be used anymore. This is so we can pass this to a Binder
     * where we won't have a chance to call recycle on this.
     * <p>
     * This method can only be called once per BaseParceledListSlice to ensure that
     * the referenced list can be cleaned up before the recipient cleans up the
     * Binder reference.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (mHasBeenParceled) {
            throw new IllegalStateException("Can't Parcel a ParceledListSlice more than once");
        }
        mHasBeenParceled = true;
        final int N = mList.size();
        final int callFlags = flags;
        dest.writeInt(N);
        if (DEBUG) Log.d(TAG, "Writing " + N + " items");
        if (N > 0) {
            final Class<?> listElementClass = mList.get(0).getClass();
            writeParcelableCreator(mList.get(0), dest);
            int i = 0;
            while (i < N && i < mInlineCountLimit && dest.dataSize() < MAX_IPC_SIZE) {
                dest.writeInt(1);

                final T parcelable = mList.get(i);
                verifySameType(listElementClass, parcelable.getClass());
                writeElement(parcelable, dest, callFlags);

                if (DEBUG) Log.d(TAG, "Wrote inline #" + i + ": " + mList.get(i));
                i++;
            }
            if (i < N) {
                dest.writeInt(0);
                Binder retriever = new Binder() {
                    @Override
                    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                            throws RemoteException {
                        if (code != FIRST_CALL_TRANSACTION) {
                            return super.onTransact(code, data, reply, flags);
                        } else if (mList == null) {
                            throw new IllegalArgumentException("Attempt to transfer null list, "
                                    + "did transfer finish?");
                        }
                        int i = data.readInt();

                        if (DEBUG) {
                            Log.d(TAG, "Writing more @" + i + " of " + N + " to "
                                    + Binder.getCallingPid() + ", sender=" + this);
                        }

                        while (i < N && reply.dataSize() < MAX_IPC_SIZE) {
                            reply.writeInt(1);

                            final T parcelable = mList.get(i);
                            verifySameType(listElementClass, parcelable.getClass());
                            writeElement(parcelable, reply, callFlags);

                            if (DEBUG) Log.d(TAG, "Wrote extra #" + i + ": " + mList.get(i));
                            i++;
                        }
                        if (i < N) {
                            if (DEBUG) Log.d(TAG, "Breaking @" + i + " of " + N);
                            reply.writeInt(0);
                        } else {
                            if (DEBUG) Log.d(TAG, "Transfer complete, clearing mList reference");
                            mList = null;
                        }
                        return true;
                    }
                };
                if (DEBUG) Log.d(TAG, "Breaking @" + i + " of " + N + ": retriever=" + retriever);
                dest.writeStrongBinder(retriever);
            }
        }
    }

    protected abstract void writeElement(T parcelable, Parcel reply, int callFlags);

    protected abstract void writeParcelableCreator(T parcelable, Parcel dest);

    protected abstract Parcelable.Creator<?> readParcelableCreator(Parcel from, ClassLoader loader);
}
