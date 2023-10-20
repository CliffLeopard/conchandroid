package com.cliff.nativelib

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.View
import com.cliff.nativelib.databinding.ActivityFoodBinding

class FoodActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFoodBinding
    lateinit var foodService: IFoodManager
    private val serviceConnection by lazy {
        object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder) {
                println("onServiceConnected")
                foodService =  IFoodManager.Stub.asInterface(service)
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                println("onServiceDisconnected")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Intent(this, FoodService::class.java).apply {
            bindService(this, serviceConnection, BIND_AUTO_CREATE)
        }
    }

    fun addFood(view: View) {
        foodService.addFood(Food().apply {
            foodId = 1
            foodName = "Apple"
        })
    }
}