package com.cliff.conch.scene.reflect;

public class Son extends Father implements FInter {
    private String sonName = "sonName";
    protected String protectedSonName = "protectedSonName";
    public String publicSonName = "publicSonName";

    @Override
    protected void aFather() {
        System.out.println("Son-AFather");
    }

    @Override
    public void aInterface() {
        System.out.println("aInterface");
    }

    private void aSon() {
        System.out.println("aSon");
    }
}
