package com.cliff.conch.scene.reflect;

import androidx.annotation.NonNull;

public class ReflectCaseReturn {
    public String name;
    public int age;

    public ReflectCaseReturn() {
        this.name = "Leopard";
        this.age = 12;
    }

    @NonNull
    @Override
    public String toString() {
        return "name:" + name + " age:" + age;
    }
}
