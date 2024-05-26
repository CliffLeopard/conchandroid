package com.cliff.conch.scene.bp

import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import com.cliff.conch.databinding.ActivityBinderProviderBinding
import com.cliff.conch.scene.aidl.Book
import com.cliff.conch.scene.aidl.IOnNewBookArrivedListener
import com.cliff.conch.scene.aidl.ProviderBookManager
import com.orhanobut.logger.Logger

/**
 * 由下面的测试可以知道，通过call的方式更加高效:
 * 因为通过query的方式，需要先通过ContentProvider拿到Binder,在通过Binder注册Listener,这里是两次三次进程通讯；
 * Main --> BindProvider     申请Binder
 * Main <-- BinderProvider   返回Binder
 * Main --> BinderProvider   注册 Listener
 *
 * 通过call的方式，只需要两次进程通讯
 * Main --> BinderProvider2  申请Binder,同时携带着Listener,BinderProvider2收到申请后注册Listener
 * Main <-- BinderProvider2  并返回Manager
 */

class BinderProviderActivity : AppCompatActivity() {
    lateinit var binding: ActivityBinderProviderBinding
    private var manager: ProviderBookManager? = null
    private var nowDeathRecipient: IBinder.DeathRecipient? = null
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

    fun bindProvider(view: View?) {
        if (manager != null && manager!!.asBinder().isBinderAlive) return

        manager = BinderProvider.getProviderBookManager(contentResolver, listener)
        manager?.asBinder()?.let {
            linkBinderDied(it, BINDER_FEATCHE_TYPE.QUERY)
        }
    }

    fun bindProvider2(view: View?) {
        if (manager != null && manager!!.asBinder().isBinderAlive) return

        manager = BinderProvider2.getProviderBookManager(contentResolver, listener)
        manager?.asBinder()?.let {
            linkBinderDied(it, BINDER_FEATCHE_TYPE.CALL)
        }
    }

    fun addBook(view: View) {
        manager?.addBook(Book(122332, "英语"))
    }

    private fun linkBinderDied(binder: IBinder, type: BINDER_FEATCHE_TYPE) {
        val deathRecipient = object : IBinder.DeathRecipient {
            override fun binderDied() {
                Logger.d("Provider:$type binderDied")
                binder.unlinkToDeath(this, 0)
                when (type) {
                    BINDER_FEATCHE_TYPE.QUERY -> bindProvider(null)
                    BINDER_FEATCHE_TYPE.CALL -> bindProvider2(null)
                }
            }
        }
        try {
            binder.linkToDeath(deathRecipient, 0)
            nowDeathRecipient = deathRecipient
        } catch (e: RemoteException) {
            e.printStackTrace();
        }
    }

    enum class BINDER_FEATCHE_TYPE {
        QUERY,
        CALL
    }

    override fun onDestroy() {
        manager?.unregisterListener(listener)
        nowDeathRecipient?.let { died ->
            manager?.asBinder()?.unlinkToDeath(died, 0)
        }
        manager = null
        super.onDestroy()
    }
}