package com.cliff.conch.scene.reflect

import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cliff.conch.bean.Section
import com.cliff.conch.reflect.PActivityThreadImpl
import com.cliff.reflection.common.hidden.HiddenApi
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
            Section("测试复杂类型"){
                val case = ReflectCase("Leopard23", 123)
                val map = PReflectCaseImpl.sCache_o_get_(case)
                if(map == null) {
                    Logger.i("GGL:ReflectCase.sCache == null ")
                } else {
                    Logger.i("GGL:ReflectCase.sCache.size == ${map.size} ")
                    map.forEach { (str, _) ->
                        Logger.i("GGL:ReflectCase.map.key = $str")
                    }
                }

                val newMap:Map<String,IBinder> = HashMap()
                val zz = newMap.plus(Pair<String,IBinder>("ABC",Binder()))
                Logger.i("GGL:ReflectCase.reMap.size == ${newMap.size} ")
                Logger.i("GGL:ReflectCase.reMap.size == ${zz.size} ")
                PReflectCaseImpl.sCache_o_set_(case,zz)

                val reMap = PReflectCaseImpl.sCache_o_get_(case)
                if(reMap == null) {
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
                val name = PReflectCaseImpl.name_o_get_(case)
                Logger.i("GGL:测试反射getFiled:$name")
                PReflectCaseImpl.name_o_set_(case, "zhangsan")
                Logger.i("GGL:测试反射setFiled:$case")

            },
            Section("测试反射Filed2") {
                val case = ReflectCase("Leopard23", 123)
                val getRt = PReflectCaseImpl.rt_o_get_(case)
                Logger.i("GGL:测试反射getFiled:$getRt")
                val rt = ReflectCaseReturn()
                rt.name = "lisi"
                PReflectCaseImpl.rt_o_set_(case, rt)
                Logger.i("GGL:测试反射setFiled:$case")

                val getRt2 = PReflectCaseImpl.rt2_o_get_(case)
                Logger.i("GGL:测试反射getFiled,注解类型:$getRt2")
                val rt2 = ReflectCaseReturn()
                rt2.name = "注解lisi"
                PReflectCaseImpl.rt2_o_set_(case, rt2)
                Logger.i("GGL:测试反射setFiled,注解类型:$case")
            },
            Section("测试反射StaticFiled") {
                val logo = PReflectCaseImpl.logo_s_get_()
                Logger.i("GGL:测试反射getStaticFiled:$logo")

                PReflectCaseImpl.logo_s_set_("NewLogo")
                Logger.i("GGL:测试反射setStaticFiled:${ReflectCase.logo}")
            },
            Section("测试反射StaticFiled2") {
                val srt = PReflectCaseImpl.srt_s_get_()
                Logger.i("GGL:测试反射getStaticFiled2:$srt")

                val newSrt = ReflectCaseReturn()
                newSrt.name = "wangwu"
                PReflectCaseImpl.srt_s_set_(newSrt)
                Logger.i("GGL:测试反射setStaticFiled2:${ReflectCase.srt}")

            },
            Section("测试反射Method") {
                val case = ReflectCase("Leopard23", 123)
                val rt = PReflectCaseImpl.getRT(case,"wl",145,ReflectCasePara())
                Log.i("GGL", rt.toString())
            },
            Section("测试反射Method2") {
                val case = ReflectCase("Leopard23", 123)
                val rt = PReflectCaseImpl.getRT2(case,"wl",145,ReflectCasePara())
                Log.i("GGL", rt.toString())

            },
            Section("测试反射StaticMethod") {
                val rt = PReflectCaseImpl.getRTS("wl",145,ReflectCasePara())
                Log.i("GGL", rt.toString())
            },
            Section("测试反射StaticMethod2") {
                val rt = PReflectCaseImpl.getRTS2("wl",145,ReflectCasePara())
                Log.i("GGL", rt.toString())
            },

            Section("测试反射Constructor") {
                val rt = PReflectCaseImpl.getInstance("zhangsanss",123)
                Log.i("GGL", rt.toString())
            },
            Section("测试反射Constructor") {
                val rt = PReflectCaseImpl.getInstance2("lisis",453)
                Log.i("GGL", rt.toString())
            },
        )
    }
}