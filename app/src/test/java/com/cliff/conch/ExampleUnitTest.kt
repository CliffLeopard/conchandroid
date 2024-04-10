package com.cliff.conch

import com.cliff.conch.scene.gson.Data
import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
    @Test
    fun gsonTest() {
        val data = Data()
        val gson = Gson()

        val  jsonString =  gson.toJson(data)
        println(jsonString)
        val data2 = gson.fromJson(jsonString,Data::class.java)
        println(data2.items[2].itemName)
    }
}