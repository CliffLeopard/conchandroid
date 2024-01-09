package com.cliff.conch.scene.provider

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.cliff.conch.databinding.ActivityProviderBinding
import com.cliff.conch.scene.aidl.Book


class ProviderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProviderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProviderBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun clickInsertBook(view: View) {
        val uri = Uri.parse("content://com.cliff.conch.provider/book")
        val values = ContentValues()
        values.put("_id", 100)
        values.put("name", "Android 开发艺术")
        contentResolver.insert(uri, values)
    }

    fun clickQuery(view: View) {
        val uri = Uri.parse("content://com.cliff.conch.provider/book")
        val bookCursor = contentResolver.query(uri, arrayOf("_id", "name"), null, null, null)
        while (bookCursor!!.moveToNext()) {
            val book = Book(bookCursor.getInt(0), bookCursor.getString(1))
            Log.d("TAG", "bookID $book")
        }
        bookCursor.close()
    }

    fun clickInsertMoreBook(view: View) {
        val uri2 = Uri.parse("content://com.cliff.conch.provider/user")
        val values = ContentValues()
        values.put("_id", 6)
        values.put("name", "Android 开发艺术")
        values.put("sex", "0")
        val values2 = ContentValues()
        values2.put("_id", 3)
        values2.put("name", "reoger")
        contentResolver.insert(uri2, values2)

        val bookCursor2 =
            contentResolver.query(uri2, arrayOf("_id", "name", "sex"), null, null, null)
        while (bookCursor2!!.moveToNext()) {
            val user =
                User(bookCursor2.getInt(0), bookCursor2.getString(1), bookCursor2.getInt(2))
            Log.d("TAG", "bookID $user")
        }
        bookCursor2.close()
    }
}