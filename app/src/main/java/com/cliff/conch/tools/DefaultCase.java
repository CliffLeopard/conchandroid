package com.cliff.conch.tools;

import com.orhanobut.logger.Logger;

/**
 * @Author CliffLeopard
 * @Email precipiceleopard@gmail.com
 * @Time 2024/10/28 09:01
 */
public class DefaultCase {
    public static void  println() {
        A a = new A();
        a.hello();
        AInterface aInterface = new AInterface() {
        };
        aInterface.hello();
    }
    interface AInterface {
        default void  hello() {
            Logger.i("default hello");
        }
    }

    static class  A implements  AInterface {
        @Override
        public void hello() {
            Logger.i("A AHello");
        }
    }
}
