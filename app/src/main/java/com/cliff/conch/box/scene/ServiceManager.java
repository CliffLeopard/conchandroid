package com.cliff.conch.box.scene;

import android.os.IBinder;

import com.cliff.conch.box.reflect.RefClass;
import com.cliff.conch.box.reflect.RefStaticObject;

import java.util.Map;

public class ServiceManager {
    public static Class<?> TYPE = RefClass.load(ServiceManager.class, "android.os.ServiceManager");
    public static RefStaticObject<Map<String, IBinder>> sCache;
}
