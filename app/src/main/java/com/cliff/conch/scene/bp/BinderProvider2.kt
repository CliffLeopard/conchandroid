package com.cliff.conch.scene.bp

import android.content.ContentProvider
import android.content.ContentResolver
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import com.cliff.conch.scene.aidl.IOnNewBookArrivedListener
import com.cliff.conch.scene.aidl.ProviderBookManager
import com.orhanobut.logger.Logger

class BinderProvider2 : ContentProvider() {
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int = 0
    override fun getType(uri: Uri): String? = null
    override fun insert(uri: Uri, values: ContentValues?): Uri? = null
    override fun onCreate(): Boolean = true
    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? = null

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int = 0

    override fun call(method: String, arg: String?, extras: Bundle?): Bundle {
        val managerCenter = BookManagerCenter()
        extras?.getBinder(KEY_BINDER_LISTENER)?.also {
            val listener = IOnNewBookArrivedListener.Stub.asInterface(it)
            managerCenter.registerListener(listener)
        }
        Logger.d("ContentProvider2:Server端接到BookManager申请,注册Listener,并开始返回BookManager")
        return Bundle().apply {
            putBinder(KEY_BINDER_COUNT2, managerCenter)
        }
    }

    companion object {
        private const val KEY_BINDER_COUNT2 = "key_provider_binder2"
        private const val KEY_BINDER_LISTENER = "key_provider_binder_listener"
        fun getProviderBookManager(
            contentResolver: ContentResolver,
            listener: IOnNewBookArrivedListener.Stub
        ): ProviderBookManager? {
            Logger.d("ContentProvider2:Client端申请获取BookManager")
            val paramBundle = Bundle()
            paramBundle.putBinder(KEY_BINDER_LISTENER, listener)
            val contentProviderClient =
                contentResolver.acquireContentProviderClient(Uri.parse("content://com.cliff.binder.provider.auth2"))
            val bundle = contentProviderClient?.call("", "", paramBundle)
            contentProviderClient?.close()
            val binder = bundle?.getBinder(KEY_BINDER_COUNT2)
            Logger.d("ContentProvider2:Client端接收到BookManager")
            return binder?.let(ProviderBookManager.Stub::asInterface)
        }
    }
}