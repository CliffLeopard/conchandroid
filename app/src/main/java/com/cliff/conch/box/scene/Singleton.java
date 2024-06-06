package com.cliff.conch.box.scene;

import com.cliff.conch.box.reflect.RefClass;
import com.cliff.conch.box.reflect.RefMethod;
import com.cliff.conch.box.reflect.RefObject;

public class Singleton {
    public static Class<?> TYPE = RefClass.load(Singleton.class, "android.util.Singleton");
    public static RefMethod<?> get;
    public static RefObject<Object> mInstance;
}
