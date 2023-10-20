package com.cliff.nativelib

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.util.concurrent.CopyOnWriteArrayList

class FoodService : Service() {
    val foodList = CopyOnWriteArrayList<Food>()
    private val foodService by lazy {
        object : IFoodManager.Stub() {
            override fun addFood(food: Food) {
                println("addFood${food.foodName}")
                foodList.add(food)

            }
            override fun removeFood(food: Food) {
                println("removeFood")
                foodList.remove(food)
            }
        }
    }

    override fun onBind(intent: Intent): IBinder {
        println("onBind")
        return foodService.asBinder()
    }
}