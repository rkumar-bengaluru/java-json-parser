package org.rk.json.parser;

import org.rk.json.parser.*;
import org.rk.json.pojo.*;
import static org.junit.Assert.assertEquals;  
import static org.junit.Assert.*;
import org.junit.After;  
import org.junit.AfterClass;  
import org.junit.Before;  
import org.junit.BeforeClass;  
import org.junit.Test;  
import java.io.StringReader;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class RJsonLexerTest {
    static Logger logger = LogManager.getLogger();
    @BeforeClass  
    public static void setUpBeforeClass() throws Exception {  
        System.out.println("before class");  
        //PropertyConfigurator.configure("log4j.properties");
    }  
    @Before  
    public void setUp() throws Exception {  
        System.out.println("before");  
    }  
    @Test  
    public void testStartBrace(){  
        StringReader stream = new StringReader("{");
        RJsonCharStream jj_input_stream = new RJsonCharStream(stream, 1, 1);
        RJsonLexer l = new RJsonLexer(jj_input_stream);
        RJsonToken t = l.getNextToken();
        assertEquals(RJsonConstants.BRACE_OPEN,t.kind);
    } 
    @Test  
    public void testDoubleParse(){  
        try {
            String json = "{\"name\":\"sonoo\",\"salary\":600000.0,\"age\":27}";
            RJsonParser instance = new RJsonParser(json);
            StringBuilder builder = new StringBuilder();
            JsonObject obj = instance.parse();
            logger.debug("JsonObject=" + obj.toString());
            assertNotNull(obj);
        }catch(Exception e) {
            fail(e.getMessage());
        }
    }

    @Test  
    public void testDecimalParse() {  
        try {
            String json = "{\"name\":\"sonoo\",\"age\":0.1}";
            RJsonParser instance = new RJsonParser(json);
            StringBuilder builder = new StringBuilder();
            JsonObject obj = instance.parse();
            logger.debug("JsonObject=" + obj.toString());
            assertNotNull(obj);
        }catch(Exception e) {
                fail(e.getMessage());
        }
    }
    @Test  
    public void testSpaceParse() {  
        try {
            String json = " {\"name\"   :\"son oo\"   ,\"male\":null,\"age\":{\"age\":22,\"gh\":\"abcd\"} }";
            RJsonParser instance = new RJsonParser(json);
            StringBuilder builder = new StringBuilder();
            JsonObject obj = instance.parse();
            logger.debug("JsonObject=" + obj.toString());
            assertNotNull(obj);
        }catch(Exception e) {
                fail(e.getMessage());
        }
    }    
}