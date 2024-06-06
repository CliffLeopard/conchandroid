package com.cliff.conch.box.scene;

import com.cliff.conch.box.reflect.RefClass;
import com.cliff.conch.box.reflect.RefStaticMethod;

public class IActivityTaskManager {
    public static Class<?> TYPE = RefClass.load(IActivityTaskManager.class, "android.app.IActivityTaskManager");

    public static class Stub {
        public static Class<?> TYPE = RefClass.load(Stub.class, "android.app.IActivityTaskManager$Stub");
        public static RefStaticMethod<?> asInterface;
    }
}
