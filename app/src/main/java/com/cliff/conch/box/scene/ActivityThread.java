package com.cliff.conch.box.scene;

import com.cliff.conch.box.reflect.RefClass;
import com.cliff.conch.box.reflect.RefMethod;
import com.cliff.conch.box.reflect.RefStaticMethod;

public class ActivityThread {

    // 关键就在于Type的赋值，RefClass.load函数中遍历了当前ActivityThread的所有static值，并对其进行赋值，通过反射封装调用
    @SuppressWarnings("unused")
    public static Class<?> TYPE = RefClass.load(ActivityThread.class, "android.app.ActivityThread");
    public static RefStaticMethod<?> currentActivityThread;
    public static RefMethod<String> getProcessName;
}
