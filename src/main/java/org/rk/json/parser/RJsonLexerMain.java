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
import org.rk.json.pojo.JsonMap;

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
            //RJsonParser instance = new RJsonParser(json);
            // RJsonParser instance = new RJsonParser(new FileInputStream("./src/test/03.json"));
            // StringBuilder builder = new StringBuilder();
            // instance.parse().toHtml(builder,-1);
            // System.out.println(builder.toString());

            JsonList list = new JsonList(JsonObjType.LIST);
            list.add(new JsonString(JsonObjType.STRING,"Material: High Modulus Graphite"));
            list.add(new JsonString(JsonObjType.STRING,"Power Frame Series"));
            JsonString listKey = new JsonString(JsonObjType.STRING,"keyFeatures");
            listKey.setKey();

            JsonMap instance = new JsonMap(JsonObjType.MAP);
            instance.setRoot();
            instance.put(new JsonString(JsonObjType.STRING,true,"productId"),new JsonString(JsonObjType.STRING,false,true,"0ENJMZAXX2"));
            instance.put(new JsonString(JsonObjType.STRING,true,"Title"),new JsonString(JsonObjType.STRING,false,true,"APACS Finapi 262 Unstrung Badminton Racquet"));
            instance.put(listKey,list);
            String expectedList = "\n\t[\n\t\t\"Material: High Modulus Graphite\",\n\t\t\"Power Frame Series\"\n\t]";
            String keyValues = "\n\t\"productId\" : \"0ENJMZAXX2\",\n\t\"Title\" : \"APACS Finapi 262 Unstrung Badminton Racquet\",\n\t";
            String expected = "\n{" + keyValues + "\"keyFeatures\"" + " : " + expectedList + "\n}"; 
    
            StringBuilder appendable = new StringBuilder();
            instance.toString(appendable,-1);
            System.out.println("response = " + appendable.toString());
            System.out.println("expected = " + expected);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}