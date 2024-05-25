package com.cliff.conch.box.dp;

import com.cliff.conch.scene.aidl.Book;
import com.orhanobut.logger.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxyCase {
    public static void testDynamicProxy() {
        ManagerImpl target = new ManagerImpl();
        testNewInstance(target);
        testGetProxyClass(target);
    }

    private static void testNewInstance(ManagerImpl target) {
        ManagerInvocationHandler handler = new ManagerInvocationHandler(target);

        Object proxy = Proxy.newProxyInstance(ManagerImpl.class.getClassLoader(), ManagerImpl.class.getInterfaces(), handler);
        IManager bookManger1 = (IManager) proxy;
        bookManger1.addBook(new Book());

        IManager2 bookManger2 = (IManager2) proxy;
        bookManger2.lendBook();
    }

    private static void testGetProxyClass(ManagerImpl target) {
        ManagerInvocationHandler handler2 = new ManagerInvocationHandler(target);
        Class<?> proxyClass = Proxy.getProxyClass(ManagerImpl.class.getClassLoader(), ManagerImpl.class.getInterfaces());
        try {
            Object proxy2 = proxyClass.getConstructor(InvocationHandler.class).newInstance(handler2);
            IManager bookManger3 = (IManager) proxy2;
            bookManger3.addBook(new Book());
        } catch (IllegalAccessException | NoSuchMethodException | InstantiationException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static class ManagerInvocationHandler implements InvocationHandler {
        private final ManagerImpl target;

        public ManagerInvocationHandler(ManagerImpl target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            callBefore(method.getName());
            Object result = call(proxy, method, args);
            callAfter(method.getName());
            return result;
        }

        private void callBefore(String methodName) {
            Logger.i("callBefore:" + methodName);
        }

        private Object call(Object proxy, Method method, Object[] args) throws Throwable {
            if (proxy == this) {
                Logger.i("proxy == this@ManagerProxy");
            } else {
                Logger.i("proxy != this@ManagerProxy, proxy是testDynamicProxy中动态创建的对象proxy");
            }
            return method.invoke(target, args);
        }

        private void callAfter(String methodName) {
            Logger.i("callAfter:" + methodName);
        }
    }

}

