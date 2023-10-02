package com.cliff.conch.scene.reflect;

public interface FInter {
    String interfaceName = "InterfaceName";

    void aInterface();

    default void otherInterface() {
        System.out.println("Other InterFace");
    }
}
