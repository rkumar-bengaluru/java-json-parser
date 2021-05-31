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
            JsonList instance = new JsonList(JsonObjType.LIST);
            instance.setKey();
            instance.add(new JsonString(JsonObjType.STRING,"fivesstar"));
            instance.add(new JsonString(JsonObjType.STRING,"ratings"));
            StringBuilder appendable = new StringBuilder();
            instance.toString(appendable,-1);
            System.out.println("response = " + appendable.toString());
            assertEquals("\n[\n\"fivesstar\",\"ratings\"\n]",appendable.toString());
        } catch(Exception e) {
            fail(e.getMessage());
        }
    } 
}