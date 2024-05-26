package com.cliff.conch.scene.bp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import com.cliff.conch.databinding.ActivityBinderProviderBinding
import com.cliff.conch.scene.aidl.Book
import com.cliff.conch.scene.aidl.IOnNewBookArrivedListener
import com.cliff.conch.scene.aidl.ProviderBookManager

class BinderProviderActivity : AppCompatActivity() {
    lateinit var binding: ActivityBinderProviderBinding
    private var manager: ProviderBookManager? = null
    private val listener by lazy {
        object : IOnNewBookArrivedListener.Stub() {
            override fun onNewBookArrived(newBook: Book?) {
                binding.root.post {
                    Toast.makeText(
                        this@BinderProviderActivity,
                        "新书来临:" + newBook?.bookName,
                        LENGTH_SHORT
                    ).show()
                }
            }

            override fun refreshBookCount(cout: Int) {
                binding.root.post {
                    binding.button2.text = "图书保有量:$cout 本"
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBinderProviderBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun bindProvider(view: View) {
        manager = BinderProvider.getProviderBookManager(contentResolver, listener)
    }

    fun bindProvider2(view: View) {
        manager = BinderProvider2.getProviderBookManager(contentResolver, listener)
    }

    fun addBook(view: View) {
        manager?.addBook(Book(122332, "英语"))
    }
}