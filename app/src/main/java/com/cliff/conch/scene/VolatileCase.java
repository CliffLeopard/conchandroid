package com.cliff.conch.scene;

public class VolatileCase {
    private volatile String name;
    private int age = 10;

    public VolatileCase(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
