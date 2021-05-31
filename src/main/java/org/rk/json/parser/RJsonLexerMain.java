package org.rk.json.parser;

import org.rk.json.parser.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import static org.junit.Assert.assertEquals;  
import static org.junit.Assert.*;
import org.junit.After;  
import org.junit.AfterClass;  
import org.junit.Before;  
import org.junit.BeforeClass;  
import org.junit.Test; 
import java.io.*;

import org.rk.json.pojo.JsonList;
import org.rk.json.pojo.JsonString;
import org.rk.json.pojo.JsonObjType;

public class RJsonLexerMain {
    static Logger logger = LogManager.getLogger(RJsonLexerMain.class);

    public static void main(String[] args) {
        try {
            // System.out.println(logger.isDebugEnabled());
            // logger.error("starting...");
            // System.out.println("Starting...");
            // 
            //System.out.println(a.toString());
            
            //String json = "{\"name\":\"sonoo\",\"salary\":600000.0,\"age\":27}";
            //String json = "{\"name\":\"sonoo\",\"age\":0.1}";
            // String json = " {\"name\"   :\"son oo\"   ,\"male\":null,\"age\":{\"age\":22,\"gh\":\"abcd\"} }";
<<<<<<< HEAD
            //RJsonParser instance = new RJsonParser(json);
            // RJsonParser instance = new RJsonParser(new FileInputStream("./src/test/03.json"));
            // StringBuilder builder = new StringBuilder();
            // instance.parse().toHtml(builder,-1);
            // System.out.println(builder.toString());

            JsonList instance = new JsonList(JsonObjType.LIST);
            instance.add(new JsonString(JsonObjType.STRING,"fivestar"));
            instance.add(new JsonString(JsonObjType.STRING,"ratings"));
            StringBuilder appendable = new StringBuilder();
            instance.toString(appendable,-1);
            logger.debug(appendable.toString());
=======
            RJsonParser instance = new RJsonParser(json);
            //RJsonParser instance = new RJsonParser(new FileInputStream("./src/test/03.json"));
            StringBuilder builder = new StringBuilder();
            instance.parse().toHtml(builder,-1);
            System.out.println(builder.toString());
>>>>>>> aaf7f528661429d4c9cc49e6f39a5f542b5d6bb2
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}