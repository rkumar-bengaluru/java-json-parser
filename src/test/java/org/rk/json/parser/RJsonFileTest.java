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
import java.io.FileInputStream;

public class RJsonFileTest {
    static Logger logger = LogManager.getLogger(RJsonFileTest.class);
   
    @Test  
    public void test01File(){  
        try {
            RJsonParser instance = new RJsonParser(new FileInputStream("./src/test/01.json"));
            assertNotNull(instance.parse());
        } catch(Exception e) {
            fail(e.getMessage());
        }
    } 

    @Test  
    public void test02File(){  
        try {
            RJsonParser instance = new RJsonParser(new FileInputStream("./src/test/02.json"));
            assertNotNull(instance.parse());
        } catch(Exception e) {
            fail(e.getMessage());
        }
    } 

    @Test  
    public void test03File(){  
        try {
            RJsonParser instance = new RJsonParser(new FileInputStream("./src/test/03.json"));
            assertNotNull(instance.parse());
        } catch(Exception e) {
            fail(e.getMessage());
        }
    }
    
}