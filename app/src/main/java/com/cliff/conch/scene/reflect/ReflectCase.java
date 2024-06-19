package com.cliff.conch.scene.reflect;

import androidx.annotation.NonNull;

public class ReflectCase {
    public static String logo = "LOGO-ReflectCase";
    public static ReflectCaseReturn srt = new ReflectCaseReturn();
    private String name;
    private int age;

    private JavaParameter jp;
    private ReflectCaseReturn rt = new ReflectCaseReturn();
    private ReflectCaseReturn rt2 = new ReflectCaseReturn();

    public ReflectCase(String name) {
        this.name = name;
        this.age = 0;
    }

    public ReflectCase(int age) {
        this.age = age;
        this.name = "CliffLeopard";
    }

    public ReflectCase(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public ReflectCaseReturn getRT(String name, int age, ReflectCasePara rp) {
        ReflectCaseReturn newRT = new ReflectCaseReturn();
        newRT.age = this.age;
        newRT.name = rp.type;
        return newRT;
    }

    public ReflectCaseReturn getRT2(String name, int age, ReflectCasePara rp) {
        ReflectCaseReturn newRT = new ReflectCaseReturn();
        newRT.age = this.age;
        newRT.name = rp.type;
        return newRT;
    }

    public static ReflectCaseReturn getRTS(String name, int age, ReflectCasePara rp) {
        ReflectCaseReturn newRT = new ReflectCaseReturn();
        newRT.age = newRT.age + 10;
        return newRT;
    }

    public static ReflectCaseReturn getRTS2(String name, int age, ReflectCasePara rp) {
        ReflectCaseReturn newRT = new ReflectCaseReturn();
        newRT.age = newRT.age + 10;
        return newRT;
    }

    @NonNull
    @Override
    public String toString() {
        return "name:" + name + " age:" + age + "  rt:" + rt.toString() + "  rt2:" + rt2.toString();
    }
}
