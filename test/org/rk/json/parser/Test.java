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

public class Test {

    
    public static void runTest() {
        int counter = 0;
        boolean loop = false;

        EOFLoop :
        for(;;) {
            if(counter > 20) {
                break;
            }
            counter++;
            System.out.println(counter);
            if(counter > 10 && loop == true) {
                System.out.println(counter + ", continue");
                continue EOFLoop;
            }
        }
    }

    public static void main(String[] args) {
        try {
            JSONParser parser = new JSONParser();
            JSONArray a = (JSONArray) parser.parse(new FileReader("./test.json"));
            //System.out.println(a.toString());
            char OPEN_BRACE ='{';
            char CLOSE_BRACE ='}';
            char SINGLE_QUOTE ='"';
            
            System.out.println("int value of " + OPEN_BRACE + " = " + (int)OPEN_BRACE);  
            System.out.println("int value of " + CLOSE_BRACE + " = " + (int)CLOSE_BRACE);  
            System.out.println("int value of " + SINGLE_QUOTE + " = " + (int)SINGLE_QUOTE);  
            
           

            String json = "{\"name\":\"sonoo\",\"salary\":600000.0,\"age\":27}";
            RJsonParser instance = new RJsonParser(json);
            System.out.println(instance.parse().toString());
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}