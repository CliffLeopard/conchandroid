package com.cliff.conch.scene.bp

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.os.Bundle
import com.cliff.conch.scene.aidl.ProviderBookManager

class BinderProvider : ContentProvider() {
    companion object {
        const val KEY_BINDER_COUNT = "key_provider_binder"
        fun getProviderBookManager(cursor: Cursor?): ProviderBookManager? {
            val binder = cursor?.extras?.getBinder(KEY_BINDER_COUNT)
            return binder?.let(ProviderBookManager.Stub::asInterface)
        }
    }
    
    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor {
        return BinderCursor()
    }

    override fun onCreate(): Boolean {
        return true
    }
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int = 0
    override fun getType(uri: Uri): String? = null
    override fun insert(uri: Uri, values: ContentValues?): Uri? = null
    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int = 0

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