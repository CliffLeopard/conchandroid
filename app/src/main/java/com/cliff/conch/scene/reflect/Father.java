package com.cliff.conch.scene.reflect;

public class Father {
    private String name = "Father";
    protected String protectedName = "ProtectedFather";
    public String publicName = "PublicFather";

    protected void aFather() {
        System.out.println("Father-aFather");
    }

    protected void pFather() {
        System.out.println("Father-pFather");
    }

    private void priFather() {
        System.out.println("Father-pFather");
    }
}
