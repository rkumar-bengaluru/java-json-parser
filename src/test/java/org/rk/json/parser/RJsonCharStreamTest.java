package org.rk.json.parser;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.StringReader;

import org.rk.json.parser.RJsonCharStream;

import static org.junit.Assert.assertEquals;  
import static org.junit.Assert.fail; 
import org.junit.Test;

public class RJsonCharStreamTest {
    static Logger logger = LogManager.getLogger(RJsonCharStreamTest.class);
    
    // beginToken Test
    @Test  
    public void beginToken() {
        try {
            String jsonStr = new String("{\"name\":\"rupak\"}");
            StringReader jsonStream = new StringReader(jsonStr);
            RJsonCharStream stream = new RJsonCharStream(jsonStream,1,1);
            char c = stream.beginToken();
            assertEquals('{',c);
        } catch(Exception e) {
            fail(e.getMessage());
        }
    }  
}