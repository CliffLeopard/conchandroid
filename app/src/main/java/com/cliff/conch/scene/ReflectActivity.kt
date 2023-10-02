package com.cliff.conch.scene

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cliff.conch.R
import com.cliff.conch.databinding.ActivityReflectBinding
import com.cliff.conch.scene.reflect.FInter
import com.cliff.conch.scene.reflect.Son

class ReflectActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReflectBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReflectBinding.inflate(layoutInflater)
        setContentView(binding.root)
        testReflection()
    }

    private fun testReflection() {
        val son = Son()
        son.aInterface()
        son.otherInterface()

        val claz  = Son::class.java
        claz.declaredMethods.forEach {
            println("declaredMethod: $it")
        }

        claz.declaredFields.forEach {
            println("declaredField: $it")
        }
    }
}