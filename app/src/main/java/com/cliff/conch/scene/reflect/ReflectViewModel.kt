package com.cliff.conch.scene.reflect

import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cliff.conch.bean.Section
import com.cliff.conch.scene.reflect.PReflectCase__Functions.__instance__
import com.cliff.conch.scene.reflect.PReflectCase__Functions.getInstance
import com.cliff.conch.scene.reflect.PReflectCase__Functions.getInstance2
import com.cliff.conch.scene.reflect.PReflectCase__Functions.getLogo
import com.cliff.conch.scene.reflect.PReflectCase__Functions.getRTS
import com.cliff.conch.scene.reflect.PReflectCase__Functions.getRTS2
import com.cliff.conch.scene.reflect.PReflectCase__Functions.getSrt
import com.cliff.conch.scene.reflect.PReflectCase__Functions.setLogo
import com.cliff.conch.scene.reflect.PReflectCase__Functions.setSrt
import com.cliff.hidden.HiddenApi
import com.orhanobut.logger.Logger

class ReflectViewModel : ViewModel() {
    private val _sections = MutableLiveData(egos)
    val sections: LiveData<List<Section>> get() = _sections

    companion object {
        val egos = listOf(
            Section("测试隐藏API") {
                val clz = Class.forName("android.app.ActivityThread")
                val obj = HiddenApi.invoke(clz, null, "getPermissionManager")
                Log.i("GGL", obj?.javaClass?.name ?: "KKK")

            },
            Section("测试复杂类型") {
                val case = ReflectCase("Leopard23", 123)
                val rCase = PReflectCase.__instance__(case)
                val map = rCase.sCache
                if (map == null) {
                    Logger.i("GGL:ReflectCase.sCache == null ")
                } else {
                    Logger.i("GGL:ReflectCase.sCache.size == ${map.size} ")
                    map.forEach { (str, _) ->
                        Logger.i("GGL:ReflectCase.map.key = $str")
                    }
                }

                val newMap: Map<String, IBinder> = HashMap()
                val zz = newMap.plus(Pair<String, IBinder>("ABC", Binder()))
                Logger.i("GGL:ReflectCase.reMap.size == ${newMap.size} ")
                Logger.i("GGL:ReflectCase.reMap.size == ${zz.size} ")
                rCase.sCache = zz

                val reMap = rCase.sCache
                if (reMap == null) {
                    Logger.i("GGL:ReflectCase.reMap == null ")
                } else {
                    Logger.i("GGL:ReflectCase.reMap.size == ${reMap.size} ")
                    reMap.forEach { (str, _) ->
                        Logger.i("GGL:ReflectCase.reMap.key = $str")
                    }
                }

            },
            Section("测试反射Filed") {
                val case = ReflectCase("Leopard23", 123)
                val rCase = PReflectCase.__instance__(case)
                val name = rCase.name
                Logger.i("GGL:测试反射getFiled:$name")
                rCase.name = "zhangsan"
                Logger.i("GGL:测试反射setFiled:$case")

            },
            Section("测试反射Filed2") {
                val case = ReflectCase("Leopard23", 123)
                val rCase = PReflectCase.__instance__(case)
                val getRt = rCase.rt
                Logger.i("GGL:测试反射getFiled:$getRt")
                val rt = ReflectCaseReturn()
                rt.name = "lisi"
                rCase.rt = rt
                Logger.i("GGL:测试反射setFiled:$case")

                val getRt2 = rCase.rt2
                Logger.i("GGL:测试反射getFiled,注解类型:$getRt2")
                val rt2 = ReflectCaseReturn()
                rt2.name = "注解lisi"
                rCase.rt2 = rt2
                Logger.i("GGL:测试反射setFiled,注解类型:$case")
            },
            Section("测试反射StaticFiled") {
                val logo = PReflectCase.getLogo()
                Logger.i("GGL:测试反射getStaticFiled:$logo")
                PReflectCase.setLogo("NewLogo")
                Logger.i("GGL:测试反射setStaticFiled:${ReflectCase.logo}")
            },
            Section("测试反射StaticFiled2") {
                val srt = PReflectCase.getSrt()
                Logger.i("GGL:测试反射getStaticFiled2:$srt")

                val newSrt = ReflectCaseReturn()
                newSrt.name = "wangwu"
                PReflectCase.setSrt(newSrt)
                Logger.i("GGL:测试反射setStaticFiled2:${ReflectCase.srt}")

            },
            Section("测试反射Method") {
                val case = ReflectCase("Leopard23", 123)
                val rCase = PReflectCase.__instance__(case)
                val rt = rCase.getRT("wl", 145, ReflectCasePara())
                Log.i("GGL", rt.toString())
            },
            Section("测试反射Method2") {
                val case = ReflectCase("Leopard23", 123)
                val rCase = PReflectCase.__instance__(case)
                val rt = rCase.getRT2("wl", 145, ReflectCasePara())
                Log.i("GGL", rt.toString())

            },
            Section("测试反射StaticMethod") {
                val rt = PReflectCase.getRTS("wl", 145, ReflectCasePara())
                Log.i("GGL", rt.toString())
            },
            Section("测试反射StaticMethod2") {
                val rt = PReflectCase.getRTS2("wl", 145, ReflectCasePara())
                Log.i("GGL", rt.toString())
            },

            Section("测试反射Constructor") {
                val rt = PReflectCase.getInstance("zhangsanss", 123)
                Log.i("GGL", rt.toString())
            },
            Section("测试反射Constructor") {
                val rt = PReflectCase.getInstance2("lisis", 453)
                Log.i("GGL", rt.toString())
            },
        )
    }
}