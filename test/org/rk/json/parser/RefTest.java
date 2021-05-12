package org.rk.json.parser;

import ref.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class RefTest {
    public static void main(String[] args) {
        try {
            //String json = "{\"name\":\"sonoo\",\"salary\":600000.0,\"age\":27}";
            String json = "{\"age\":27}";
            NJSONParser instance = new NJSONParser(json);
            System.out.println(instance.parse());
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}