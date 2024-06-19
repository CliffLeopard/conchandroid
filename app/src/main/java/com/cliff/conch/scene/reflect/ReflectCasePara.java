package com.cliff.conch.scene.reflect;

import androidx.annotation.NonNull;

public class ReflectCasePara {
    String type;
    int sum;

    public ReflectCasePara() {
        this.type = "ReflectCasePara";
        this.sum = 10222;
    }

    @NonNull
    @Override
    public String toString() {
        return "type:" + type + " sum:" + sum;
    }
}
