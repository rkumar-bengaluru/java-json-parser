package org.rk.json.parser;

import java.io.IOException;
import java.util.logging.Formatter;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class RLogger {
    private static final Logger LOGGER = Logger.getLogger(RLogger.class.getName());

    public static Logger getLogger(Class clz) {
        Logger l = Logger.getLogger(clz.getName());
        // Handler fileHandler = null;
        // Formatter simpleFormatter = null;
        // try {
        //     fileHandler = new FileHandler("./lexer.log",1024 * 1024,1,true);
        //     simpleFormatter = new SimpleFormatter();
        //     l.addHandler(fileHandler);
        //     fileHandler.setFormatter(simpleFormatter);
        //     fileHandler.setLevel(Level.ALL);
        //     l.setLevel(Level.ALL);
        // } catch(IOException exception) {
        //     LOGGER.log(Level.SEVERE, "Error occur in FileHandler.", exception);
        // }
        return l;
    }
    public static void debug(Class clz,String msg) {
        System.out.println(clz.getName() + "::" + msg);
    }

    public static void debug(Class clz,String m , String msg) {
        System.out.println(clz.getName() + "::" + m + "::" + msg);
    }

    public static void debug(Class clz,String m , char msg) {
        System.out.println(clz.getName() + "::" + m + "::" + msg);
    }

    public static void debug(Class clz,String m , long msg) {
        System.out.println(clz.getName() + "::" + m + "::" + msg);
    }
}