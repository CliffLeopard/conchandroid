package com.cliff.conch.scene

import android.os.Bundle
import android.os.SystemClock
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.cliff.conch.databinding.ActivityRxJavaBinding
import com.orhanobut.logger.Logger
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class RxJavaActivity : AppCompatActivity() {
    lateinit var binding: ActivityRxJavaBinding
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRxJavaBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun clickMe(view: View) {
        disposables.add(
            sampleObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<String>() {
                    override fun onComplete() {
                        Logger.d(Thread.currentThread().name + " onComplete()")
                    }

                    override fun onNext(t: String) {
                        Logger.d(
                            Thread.currentThread().name + " onNext(" + t + ")"
                        )
                    }

                    override fun onError(e: Throwable) {
                        Logger.e(Thread.currentThread().name + " onError()", e)
                    }
                })
        )

    }

    private fun sampleObservable(): Observable<String> {
        return Observable.defer {
            SystemClock.sleep(5000)
            Observable.just(
                "one",
                "two",
                "three",
                "four",
                "five"
            )
        }
    }
}