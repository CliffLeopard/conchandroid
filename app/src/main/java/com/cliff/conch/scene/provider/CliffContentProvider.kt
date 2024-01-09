package com.cliff.conch.scene.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import com.orhanobut.logger.Logger


class CliffContentProvider : ContentProvider() {
    private var mContext: Context? = null
    private val db: SQLiteDatabase by lazy {
        CliffSQLiteOpenHelper(mContext).writableDatabase
    }

    private fun getTableName(uri: Uri): String? {
        var tableName: String? = null
        when (sUriMatcher.match(uri)) {
            BOOK_URI_CODE -> tableName = CliffSQLiteOpenHelper.BOOK_TABLE_NAME
            USER_URI_CODE -> tableName = CliffSQLiteOpenHelper.USER_TABLE_NAME
            else -> {}
        }
        return tableName
    }

    override fun onCreate(): Boolean {
        mContext = context
        initProvider()
        Logger.i("CliffContentProvider onCreate")
        return true
    }

    private fun initProvider() {
        db.execSQL("delete from ${CliffSQLiteOpenHelper.BOOK_TABLE_NAME}")
        db.execSQL("delete from ${CliffSQLiteOpenHelper.USER_TABLE_NAME}")
        db.execSQL("insert into book values(3,'android');")
        db.execSQL("insert into book values(4,'ios');")
        db.execSQL("insert into book values(5,'html5');")
        db.execSQL("insert into user values(1,'jack',1);")
        db.execSQL("insert into user values(2,'jany',0);")
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        val table = getTableName(uri) ?: throw IllegalArgumentException(" UnSupported URL: $uri")
        return db.query(
            table,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder,
            null
        )
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri {
        val table = getTableName(uri) ?: throw IllegalArgumentException("UnSupported URI :$uri")
        db.insert(table, null, values)
        context?.contentResolver?.notifyChange(uri, null)
        return uri
    }

    override fun delete(
        uri: Uri,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        val table = getTableName(uri) ?: throw IllegalArgumentException("UnSupported URI :$uri")
        val count = db.delete(table, selection, selectionArgs)
        if (count > 0) {
            context?.contentResolver?.notifyChange(uri, null)
        }
        return count
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        val table = getTableName(uri) ?: throw IllegalArgumentException("UnSupported URI :$uri")
        val row = db.update(table, values, selection, selectionArgs)
        if (row > 0) {
            context!!.contentResolver.notifyChange(uri, null)
        }
        return row
    }

    companion object {
        private const val AUTHORITY = "com.cliff.conch.provider"

//        val BOOK_CONTENT_URI = Uri.parse("content://$AUTHORITY/book")
//        val USER_CONTENT_URI = Uri.parse("content://$AUTHORITY/user")
        const val BOOK_URI_CODE = 0
        const val USER_URI_CODE = 1
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            sUriMatcher.addURI(AUTHORITY, "book", BOOK_URI_CODE)
            sUriMatcher.addURI(AUTHORITY, "user", USER_URI_CODE)
        }
    }
}

