// ServiceFetcher.aidl
package com.cliff.conch.box.service;

interface ServiceFetcher {
    IBinder getService(in String name);
    void registService(in String name, in IBinder service);
    void removeService(in String name);
    void cleanService();
}