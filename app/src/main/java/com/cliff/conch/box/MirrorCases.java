package com.cliff.conch.box;

import android.annotation.SuppressLint;
import android.app.Application;

import com.orhanobut.logger.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import reflect.android.app.ActivityThreadReImpl;


@SuppressLint("DiscouragedPrivateApi,PrivateApi")
public class MirrorCases {
    // 对于RefObject等存在的包，要在混淆中添加配置，因为这些类很多只是通过反射调用，所以编译时可能经过优化而没有被打进apk包中。
    public static void testMirror() {
        try {
            invokeStaticMethod();
            invokeStaticField();
            invokeMethod();
            invokeField();
            refTest();
        } catch (Exception exp) {
            Logger.e("MirrorError", exp);
        }
    }

    private static void refTest() {
        if (ActivityThreadReImpl.INSTANCE.currentActivityThread() == null) {
            Logger.e("currentActivityThread 是NUll");
        } else {
            Logger.d("currentActivityThread不是NUll");
        }
        Object mainThread = ActivityThreadReImpl.INSTANCE.currentActivityThread();
        Object processName = ActivityThreadReImpl.INSTANCE.getProcessName(mainThread);
        Logger.d("processName:" + processName);
    }

    private static void invokeStaticMethod() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> clz = Class.forName("android.app.ActivityThread");
        Class<?>[] types = new Class[0];
        Method method = clz.getDeclaredMethod("currentPackageName", types);
        method.setAccessible(true);
        String currentClassName = (String) method.invoke(null);
        Logger.d(currentClassName);
    }

    private static Object invokeStaticField() throws ClassNotFoundException, IllegalAccessException, NoSuchFieldException {
        Class<?> clz = Class.forName("android.app.ActivityThread");
        Field filed = clz.getDeclaredField("sCurrentActivityThread");
        filed.setAccessible(true);
        Object currentActivityThread = filed.get(null);
        assert currentActivityThread != null;
        Logger.d(currentActivityThread.hashCode());
        return currentActivityThread;
    }

    private static void invokeMethod() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Class<?> clz = Class.forName("android.app.ActivityThread");
        Class<?>[] types = new Class[0];
        Method method = clz.getDeclaredMethod("getProcessName", types);
        Object object = invokeStaticField();
        method.setAccessible(true);
        String processName = (String) method.invoke(object);
        Logger.d(processName);
    }

    private static void invokeField() throws ClassNotFoundException, IllegalAccessException, NoSuchFieldException {
        Class<?> clz = Class.forName("android.app.ActivityThread");
        Field field = clz.getDeclaredField("mInitialApplication");
        field.setAccessible(true);
        Object object = invokeStaticField();
        Application application = (Application) field.get(object);
        assert application != null;
        Logger.d(application.getPackageCodePath());
    }
}
