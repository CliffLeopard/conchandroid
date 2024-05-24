package com.cliff.conch.box;

import android.annotation.SuppressLint;
import android.app.Application;
import com.orhanobut.logger.Logger;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


@SuppressLint("DiscouragedPrivateApi,PrivateApi")
public class ReflectCases {
    public static void invokeStaticMethod() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> clz = Class.forName("android.app.ActivityThread");
        Class<?>[] types = new Class[0];
        Method method = clz.getDeclaredMethod("currentPackageName", types);
        method.setAccessible(true);
        String currentClassName = (String) method.invoke(null);
        Logger.d(currentClassName);
    }

    public static Object invokeStaticField() throws ClassNotFoundException, IllegalAccessException, NoSuchFieldException {
        Class<?> clz = Class.forName("android.app.ActivityThread");
        Field filed = clz.getDeclaredField("sCurrentActivityThread");
        filed.setAccessible(true);
        Object currentActivityThread = filed.get(null);
        assert currentActivityThread != null;
        Logger.d(currentActivityThread.hashCode());
        return currentActivityThread;
    }

    public static void invokeMethod() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Class<?> clz = Class.forName("android.app.ActivityThread");
        Class<?>[] types = new Class[0];
        Method method = clz.getDeclaredMethod("getProcessName", types);
        Object object = invokeStaticField();
        method.setAccessible(true);
        String processName = (String) method.invoke(object);
        Logger.d(processName);
    }

    public static void invokeField() throws ClassNotFoundException, IllegalAccessException, NoSuchFieldException {
        Class<?> clz = Class.forName("android.app.ActivityThread");
        Field field = clz.getDeclaredField("mInitialApplication");
        field.setAccessible(true);
        Object object = invokeStaticField();
        Application application = (Application) field.get(object);
        assert application != null;
        Logger.d(application.getPackageCodePath());
    }
}
