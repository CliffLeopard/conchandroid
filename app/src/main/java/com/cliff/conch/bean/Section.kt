package com.cliff.conch.bean

import android.app.NativeActivity
import android.content.Intent
import android.view.View
import com.cliff.conch.box.EgoActivity
import com.cliff.conch.box.show.ShowApkInfoActivity
import com.cliff.conch.install.InstallPackageActivity
import com.cliff.conch.scene.ANRActivity
import com.cliff.conch.scene.ANRSummaryActivity
import com.cliff.conch.scene.BookManagerActivity
import com.cliff.conch.scene.CoroutineActivity
import com.cliff.conch.scene.EventActivity
import com.cliff.conch.scene.EventBusActivity
import com.cliff.conch.scene.FilePathActivity
import com.cliff.conch.scene.HiddenApiActivity
import com.cliff.conch.scene.LeakActivity
import com.cliff.conch.scene.PackageAppInfo
import com.cliff.conch.scene.PineActivity
import com.cliff.conch.scene.ReflectActivity
import com.cliff.conch.scene.RetrofitActivity
import com.cliff.conch.scene.RxJavaActivity
import com.cliff.conch.scene.SelfDefineViewActivity
import com.cliff.conch.scene.ShellActivity
import com.cliff.conch.scene.SimpleCasesActivity
import com.cliff.conch.scene.TextureViewActivity
import com.cliff.conch.scene.WebViewScreenShotActivity
import com.cliff.conch.scene.bp.BinderProviderActivity
import com.cliff.conch.scene.provider.ProviderActivity
import com.cliff.nativelib.FoodActivity

data class Section(
    val title: String, val activity: Class<*> = Int::class.java, val action: (View) -> Unit = {
        val intent = Intent(it.context, activity)
        it.context.startActivity(intent)
    }
) {
    companion object {
        val sections: List<Section> = listOf(
            Section("沙盒先验技术", EgoActivity::class.java),
            Section("显示APK信息", ShowApkInfoActivity::class.java),
            Section("反射研究", ReflectActivity::class.java),
            Section("简单案例验证", SimpleCasesActivity::class.java),
            Section("各种文件路径", FilePathActivity::class.java),
            Section("APK解析", PackageAppInfo::class.java),
            Section("执行Shell命令并获取结果", ShellActivity::class.java),
            Section("Apk安装流程解析", InstallPackageActivity::class.java),
            Section("EventBus验证", EventBusActivity::class.java),
            Section("Service-AIDL文件", BookManagerActivity::class.java),
            Section("Service-AIDL文件2", FoodActivity::class.java),
            Section("Retrofit源码", RetrofitActivity::class.java),
            Section("NativeActivity生成", NativeActivity::class.java),
            Section("自定义View", SelfDefineViewActivity::class.java),
            Section("Pine研究", PineActivity::class.java),
            Section("ANR研究", ANRActivity::class.java),
            Section("ANR研究2", ANRSummaryActivity::class.java),
            Section("ContentProvider研究", ProviderActivity::class.java),
            Section("Provider传递Binder", BinderProviderActivity::class.java),
            Section("RxJava研究", RxJavaActivity::class.java),
            Section("协程研究", CoroutineActivity::class.java),
            Section("事件传递研究", EventActivity::class.java),
            Section("一种内存泄漏研究", LeakActivity::class.java),
            Section("TextureView研究", TextureViewActivity::class.java),
            Section("WebView截图研究", WebViewScreenShotActivity::class.java),
            Section("HiddenApi研究", HiddenApiActivity::class.java),
        )
    }
}
