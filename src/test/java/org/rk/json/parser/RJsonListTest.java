package org.rk.json.parser;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.rk.json.pojo.JsonList;
import org.rk.json.pojo.JsonString;
import org.rk.json.pojo.JsonObjType;

import org.junit.Test;  
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

public class RJsonListTest {
    static Logger logger = LogManager.getLogger(RJsonListTest.class);

    @Test  
    public void testList01(){  
        try {
            String expected = "\n[\n\t\"fivesstar\",\n\t\"ratings\"\n]";
            
            JsonList instance = new JsonList(JsonObjType.LIST);
            instance.setRoot();
            instance.add(new JsonString(JsonObjType.STRING,"fivesstar"));
            instance.add(new JsonString(JsonObjType.STRING,"ratings"));
            StringBuilder appendable = new StringBuilder();
            instance.toString(appendable,-1);
            System.out.println("actual = " + appendable.toString());
            System.out.println("expected = " + expected);
            assertEquals(expected,appendable.toString());
        } catch(Exception e) {
            fail(e.getMessage());
        }
    } 
}