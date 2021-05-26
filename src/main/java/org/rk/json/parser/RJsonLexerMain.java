package org.rk.json.parser;

import org.rk.json.parser.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import static org.junit.Assert.assertEquals;  
import static org.junit.Assert.*;
import org.junit.After;  
import org.junit.AfterClass;  
import org.junit.Before;  
import org.junit.BeforeClass;  
import org.junit.Test; 
import java.io.StringReader;

public class RJsonLexerMain {
    static Logger logger = LogManager.getLogger(RJsonLexerMain.class);

    public static void main(String[] args) {
        try {
            // System.out.println(logger.isDebugEnabled());
            // logger.error("starting...");
            // System.out.println("Starting...");
            JSONParser parser = new JSONParser();
            JSONObject a = (JSONObject) parser.parse(new FileReader("./test.json"));
            //System.out.println(a.toString());
            
            String json = "{\"name\":\"sonoo\",\"salary\":600000.0,\"age\":27}";
            //String json = "{\"name\":\"sonoo\",\"age\":0.1}";
            // String json = " {\"name\"   :\"son oo\"   ,\"male\":null,\"age\":{\"age\":22,\"gh\":\"abcd\"} }";
            RJsonParser instance = new RJsonParser(a.toString());
            StringBuilder builder = new StringBuilder();
            instance.parse().toHtml(builder,-1);
            System.out.println(builder.toString());
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}