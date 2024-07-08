package com.cliff.conch.box.process

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.Process
import android.util.Log

open class ProcessProvider : ContentProvider() {
    override fun onCreate(): Boolean {
        Log.e("GGL", "onCreate:" + this::class.java.canonicalName +" processName:"+Process.myProcessName())
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return null
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return -1
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return -1
    }

    // 可以同时开启30个进程
    class P1 : ProcessProvider()
    class P2 : ProcessProvider()
    class P3 : ProcessProvider()
    class P4 : ProcessProvider()
    class P5 : ProcessProvider()
    class P6 : ProcessProvider()
    class P7 : ProcessProvider()
    class P8 : ProcessProvider()
    class P9 : ProcessProvider()
    class P10 : ProcessProvider()
    class P11 : ProcessProvider()
    class P12 : ProcessProvider()
    class P13 : ProcessProvider()
    class P14 : ProcessProvider()
    class P15 : ProcessProvider()
    class P16 : ProcessProvider()
    class P17 : ProcessProvider()
    class P18 : ProcessProvider()
    class P19 : ProcessProvider()
    class P20 : ProcessProvider()
    class P21 : ProcessProvider()
    class P22 : ProcessProvider()
    class P23 : ProcessProvider()
    class P24 : ProcessProvider()
    class P25 : ProcessProvider()
    class P26 : ProcessProvider()
    class P27 : ProcessProvider()
    class P28 : ProcessProvider()
    class P29 : ProcessProvider()
    class P30 : ProcessProvider()
    companion object {
        private const val authority = "com.cliff.conch.box.process"
        fun authorityById(id: Int): String {
            return authority + id
        }
    }
}