package com.cliff.nativelib

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.IBinder.DeathRecipient
import android.view.View
import com.cliff.common.BaseActivity
import com.cliff.nativelib.databinding.ActivityFoodBinding

class FoodActivity : BaseActivity<ActivityFoodBinding>() {
    lateinit var foodService: IFoodManager

    private val serviceConnection by lazy {
        object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder) {
                println("onServiceConnected")
                foodService = IFoodManager.Stub.asInterface(service)
                val deathRecipient = object : DeathRecipient {
                    override fun binderDied() {
                        service.unlinkToDeath(this, 0)
                    }
                }
                service.linkToDeath(deathRecipient, 0)
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                println("onServiceDisconnected")
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Intent(this, FoodService::class.java).apply {
            bindService(this, serviceConnection, BIND_AUTO_CREATE)
        }
    }

    override fun initBinding() {
        binding = ActivityFoodBinding.inflate(layoutInflater)
    }

    override fun mainView(): View {
        return binding.main
    }

    fun addFood(view: View) {
        foodService.addFood(Food().apply {
            foodId = 1
            foodName = "Apple"
        })
    }
}