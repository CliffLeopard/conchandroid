package com.cliff.xposedhook

import com.highcapable.yukihookapi.YukiHookAPI
import com.highcapable.yukihookapi.annotation.xposed.InjectYukiHookWithXposed
import com.highcapable.yukihookapi.hook.xposed.bridge.event.YukiXposedEvent
import com.highcapable.yukihookapi.hook.xposed.proxy.IYukiHookXposedInit

@InjectYukiHookWithXposed
object HookEntry : IYukiHookXposedInit {
    override fun onHook() = YukiHookAPI.encase {
        // Your code here.
    }

    override fun onInit() = YukiHookAPI.configs {
        isDebug = BuildConfig.DEBUG
    }

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