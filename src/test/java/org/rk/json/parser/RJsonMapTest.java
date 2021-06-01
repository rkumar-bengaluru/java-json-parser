package org.rk.json.parser;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.rk.json.pojo.JsonMap;
import org.rk.json.pojo.JsonList;
import org.rk.json.pojo.JsonString;
import org.rk.json.pojo.JsonObjType;

import org.junit.Test;  
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

public class RJsonMapTest {
    static Logger logger = LogManager.getLogger(RJsonListTest.class);

    @Test  
    public void testMap01(){  
        try {
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
            assertEquals(expected,appendable.toString());
        } catch(Exception e) {
            fail(e.getMessage());
        }
    } 

    @Test  
    public void testMap02(){  
        try {
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
            System.out.println("actual = " + appendable.toString());
            System.out.println("expected = " + expected);
            assertEquals(expected,appendable.toString());
        } catch(Exception e) {
            fail(e.getMessage());
        }
    } 
}