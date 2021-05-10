package org.rk.json.parser;

public class RLogger {
    public static void debug(Class clz,String msg) {
        System.out.println(clz.getName() + "::" + msg);
    }

    public static void debug(Class clz,String m , String msg) {
        System.out.println(clz.getName() + "::" + m + "::" + msg);
    }
}