@file:Suppress("SetTextI18n")

package com.cliff.xposedhook

import android.os.Process
import android.util.Log
import com.highcapable.yukihookapi.YukiHookAPI
import com.highcapable.yukihookapi.annotation.xposed.InjectYukiHookWithXposed
import com.highcapable.yukihookapi.hook.core.annotation.LegacyResourcesHook
import com.highcapable.yukihookapi.hook.factory.classOf
import com.highcapable.yukihookapi.hook.factory.constructor
import com.highcapable.yukihookapi.hook.factory.method
import com.highcapable.yukihookapi.hook.param.PackageParam
import com.highcapable.yukihookapi.hook.type.android.ApplicationClass
import com.highcapable.yukihookapi.hook.xposed.bridge.event.YukiXposedEvent
import com.highcapable.yukihookapi.hook.xposed.proxy.IYukiHookXposedInit
import dalvik.system.BaseDexClassLoader
import dalvik.system.DexClassLoader
import dalvik.system.InMemoryDexClassLoader
import dalvik.system.PathClassLoader
import java.io.File
import java.nio.ByteBuffer
import kotlin.math.exp

@InjectYukiHookWithXposed(isUsingResourcesHook = true)
object HookEntry : IYukiHookXposedInit {

    override fun onInit() {
        YukiHookAPI.configs {
            debugLog {
                tag = "YukiHookAPI-Demo"
                isEnable = true
                isRecord = false
                elements(TAG, PRIORITY, PACKAGE_NAME, USER_ID)
            }
            isDebug = true
            isEnableModuleAppResourcesCache = true
            isEnableHookSharedPreferences = false
            isEnableDataChannel = true
        }
    }

    @OptIn(LegacyResourcesHook::class)
    override fun onHook() {
        YukiHookAPI.encase {
            loadApp(name = "com.oxcc.leaveTraces") {
                onCreate()
                hookAdvancedEncryptionStandard()
//                hookClassLoader()
//                hookPathClassLoader()
//                hookDexClassLoader()
//                hookInMemoryDexClassLoader()
//                hookBaseDexClassLoader()
            }
        }
    }

    private fun PackageParam.hookAdvancedEncryptionStandard() {
        try {
            "com.stardust.util.AdvancedEncryptionStandard".toClass().constructor {
                param(ByteArray::class.java, String::class.java)
            }.hook {
                before {
                    Log.e("GGL", "hook AdvancedEncryptionStandard")
                    val bytes = args[0] as ByteArray
                    val vector = args[1] as String
                    Log.e("GGL", "AdvancedEncryptionStandard:arg0:${String(bytes)} arg1:$vector")
                }
            }
        } catch (xpp: Exception) {
            xpp.printStackTrace()
            Log.e("GGL", "hook AdvancedEncryptionStandard error:1 " + xpp.message)
        }
    }

    private fun PackageParam.onCreate() {
//        val clz = Class.forName("com.stardust.auojs.inrt.App")
        ApplicationClass.method {
            name = "onCreate"
            emptyParam()
        }.hook {
            before {
                Log.e("GGL", "hook onCreate")
            }
        }
    }

//    private fun PackageParam.hookClassLoader() {
//        classOf<ClassLoader>().method {
//            name = "createSystemClassLoader"
//            emptyParam()
//        }.hook {
//            before {
//                Log.e("GGL", "ClassLoader.createSystemClassLoader")
//            }
//        }
//    }
//
//    private fun PackageParam.hookPathClassLoader() {
//        classOf<PathClassLoader>().constructor {
//            param(
//                classOf<String>(),
//                classOf<ClassLoader>(),
//            )
//        }.hook {
//            before {
//                Log.e(
//                    "GGL",
//                    "${Process.myPid()}-PathClassLoader1:${args.size} ${
//                        args.joinToString {
//                            it?.toString() ?: "null"
//                        }
//                    }"
//                )
//            }
//        }
//
//        classOf<PathClassLoader>().constructor {
//            param(
//                classOf<String>(),
//                classOf<String>(),
//                classOf<ClassLoader>(),
//            )
//        }.hook {
//            before {
//                Log.e(
//                    "GGL",
//                    "${Process.myPid()}-PathClassLoader2:${args.size} ${
//                        args.joinToString {
//                            it?.toString() ?: "null"
//                        }
//                    }"
//                )
//            }
//        }
//
//        classOf<PathClassLoader>().constructor {
//            param(
//                classOf<String>(),
//                classOf<String>(),
//                classOf<ClassLoader>(),
//                classOf<Array<ClassLoader>>(),
//            )
//        }.hook {
//            before {
//                Log.e(
//                    "GGL",
//                    "${Process.myPid()}-PathClassLoader3:${args.size} ${
//                        args.joinToString {
//                            it?.toString() ?: "null"
//                        }
//                    }"
//                )
//            }
//        }
//
//        classOf<PathClassLoader>().constructor {
//            param(
//                classOf<String>(),
//                classOf<String>(),
//                classOf<ClassLoader>(),
//                classOf<Array<ClassLoader>>(),
//                classOf<Array<ClassLoader>>(),
//            )
//        }.hook {
//            before {
//                Log.e(
//                    "GGL",
//                    "${Process.myPid()}-PathClassLoader4:${args.size} ${
//                        args.joinToString {
//                            it?.toString() ?: "null"
//                        }
//                    }"
//                )
//            }
//        }
//    }
//
//    private fun PackageParam.hookDexClassLoader() {
//        classOf<DexClassLoader>().constructor {
//            param(
//                classOf<String>(),
//                classOf<String>(),
//                classOf<String>(),
//                classOf<ClassLoader>()
//            )
//        }.hook {
//            before {
//                Log.e(
//                    "GGL",
//                    "${Process.myPid()}-DexClassLoader:${args.size} ${
//                        args.joinToString {
//                            it?.toString() ?: "null"
//                        }
//                    }"
//                )
//            }
//        }
//    }
//
//    private fun PackageParam.hookInMemoryDexClassLoader() {
//        classOf<InMemoryDexClassLoader>().constructor {
//            param(
//                classOf<Array<ByteBuffer>>(),
//                classOf<String>(),
//                classOf<ClassLoader>()
//            )
//        }.hook {
//            before {
//                Log.e(
//                    "GGL",
//                    "${Process.myPid()}-InMemoryDexClassLoader1:${args.size} ${
//                        args.joinToString {
//                            it?.toString() ?: "null"
//                        }
//                    }"
//                )
//            }
//        }
//
//        classOf<InMemoryDexClassLoader>().constructor {
//            param(
//                classOf<Array<ByteBuffer>>(),
//                classOf<ClassLoader>()
//            )
//        }.hook {
//            before {
//                Log.e(
//                    "GGL",
//                    "${Process.myPid()}-InMemoryDexClassLoader2:${args.size} ${
//                        args.joinToString {
//                            it?.toString() ?: "null"
//                        }
//                    }"
//                )
//            }
//        }
//
//        classOf<InMemoryDexClassLoader>().constructor {
//            param(
//                classOf<ByteBuffer>(),
//                classOf<ClassLoader>()
//            )
//        }.hook {
//            before {
//                Log.e(
//                    "GGL",
//                    "${Process.myPid()}-InMemoryDexClassLoader3:${args.size} ${
//                        args.joinToString {
//                            it?.toString() ?: "null"
//                        }
//                    }"
//                )
//            }
//        }
//
//    }
//
//    private fun PackageParam.hookBaseDexClassLoader() {
//        classOf<BaseDexClassLoader>().constructor {
//            param(
//                classOf<String>(),
//                classOf<File>(),
//                classOf<String>(),
//                classOf<ClassLoader>(),
//            )
//        }.hook {
//            before {
//                Log.e(
//                    "GGL",
//                    "${Process.myPid()}-BaseDexClassLoader:${args.size} ${
//                        args.joinToString {
//                            it?.toString() ?: "null"
//                        }
//                    }"
//                )
//            }
//        }
//
//        classOf<BaseDexClassLoader>().constructor {
//            param(
//                classOf<String>(),
//                classOf<String>(),
//                classOf<ClassLoader>(),
//                classOf<Array<ClassLoader>>(),
//            )
//        }.hook {
//            before {
//                Log.e(
//                    "GGL",
//                    "${Process.myPid()}-BaseDexClassLoader:${args.size} ${
//                        args.joinToString {
//                            it?.toString() ?: "null"
//                        }
//                    }"
//                )
//            }
//        }
//
//        classOf<BaseDexClassLoader>().constructor {
//            param(
//                classOf<String>(),
//                classOf<String>(),
//                classOf<ClassLoader>(),
//                classOf<Array<ClassLoader>>(),
//                classOf<Array<ClassLoader>>(),
//            )
//        }.hook {
//            before {
//                Log.e(
//                    "GGL",
//                    "${Process.myPid()}-BaseDexClassLoader:${args.size} ${
//                        args.joinToString {
//                            it?.toString() ?: "null"
//                        }
//                    }"
//                )
//            }
//        }

    override fun onXposedEvent() {
        YukiXposedEvent.events {
            onInitZygote {
                // Implement listening for the initZygote event
                // 实现监听 initZygote 事件
            }
            onHandleLoadPackage {
                // Implement listener handleLoadPackage event
                // Call native Xposed API methods
                // 实现监听 handleLoadPackage 事件
                // 可调用原生 Xposed API 方法
                // XposedHelpers.findAndHookMethod("className", it.classLoader, "methodName", object : XC_MethodHook())
            }
            onHandleInitPackageResources {
                // Implement the listener handleInitPackageResources event
                // Call native Xposed API methods
                // 实现监听 handleInitPackageResources 事件
                // 可调用原生 Xposed API 方法
                // it.res.setReplacement(0x7f060001, "replaceMent")
            }
        }
    }
}