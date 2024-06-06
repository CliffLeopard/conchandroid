package com.cliff.conch.box.scene;

import com.cliff.conch.box.reflect.RefClass;
import com.cliff.conch.box.reflect.RefStaticMethod;
import com.cliff.conch.box.reflect.RefStaticObject;

public class ActivityTaskManager {
    public static Class<?> TYPE = RefClass.load(ActivityTaskManager.class, "android.app.ActivityTaskManager");
    public static RefStaticObject<Singleton> IActivityTaskManagerSingleton;
    public static RefStaticMethod<?> getService;
}
