package com.cliff.conch.scene.bp

import android.content.ContentProvider
import android.content.ContentResolver
import android.content.ContentValues
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.os.Bundle
import com.cliff.conch.scene.aidl.IOnNewBookArrivedListener
import com.cliff.conch.scene.aidl.ProviderBookManager
import com.orhanobut.logger.Logger

class BinderProvider : ContentProvider() {
    override fun onCreate(): Boolean = true
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int = 0
    override fun getType(uri: Uri): String? = null
    override fun insert(uri: Uri, values: ContentValues?): Uri? = null
    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int = 0

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor {
        Logger.d("ContentProvider:Server端接到BookManager申请,开始返回BookManager")
        return BinderCursor()
    }

    companion object {
        const val KEY_BINDER_COUNT = "key_provider_binder"
        fun getProviderBookManager(
            contentResolver: ContentResolver,
            listener: IOnNewBookArrivedListener.Stub
        ): ProviderBookManager? {
            Logger.d("ContentProvider:Client端申请获取BookManager")
            val cursor = contentResolver.query(
                Uri.parse("content://com.cliff.binder.provider.auth"),
                null,
                null,
                null
            )
            return try {
                val binder = cursor?.extras?.getBinder(KEY_BINDER_COUNT)
                val manager = binder?.let(ProviderBookManager.Stub::asInterface)
                Logger.d("ContentProvider:Client端接收到BookManager")
                manager?.apply {
                    Logger.d("ContentProvider:Client端开始注册Listener")
                    registerListener(listener)
                }
            } catch (e: Exception) {
                null
            } finally {
                cursor?.close()
            }
        }
    }

    private class BinderCursor(private val mBinderExtra: Bundle = Bundle()) :
        MatrixCursor(emptyArray()) {
        init {
            mBinderExtra.putBinder(KEY_BINDER_COUNT, BookManagerCenter())
        }

        override fun getExtras(): Bundle {
            return mBinderExtra
        }
    }
}