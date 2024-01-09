package com.cliff.conch.scene.provider

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.orhanobut.logger.Logger

class CliffSQLiteOpenHelper(context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    private val sqlCreateBookTable =
        "CREATE TABLE IF NOT EXISTS $BOOK_TABLE_NAME ( _id INTEGER PRIMARY KEY, name TEXT)"
    private val sqlCreateUserTable =
        "CREATE TABLE IF NOT EXISTS $USER_TABLE_NAME ( _id INTEGER PRIMARY KEY, name TEXT , sex INT)"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(sqlCreateBookTable)
        db?.execSQL(sqlCreateUserTable)
        Logger.i("CliffSQLiteOpenHelper onCreate")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }


    companion object {
        const val DB_NAME = "book_provider.db"
        const val BOOK_TABLE_NAME = "book"
        const val USER_TABLE_NAME = "user"
        const val DB_VERSION = 1
    }
}