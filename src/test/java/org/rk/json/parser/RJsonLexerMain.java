package org.rk.json.parser;

import org.rk.json.parser.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
// import org.json.simple.JSONArray;
// import org.json.simple.JSONObject;
// import org.json.simple.parser.JSONParser;
// import org.json.simple.parser.ParseException;

public class RJsonLexerMain {

    public static void main(String[] args) {
        try {
            ///JSONParser parser = new JSONParser();
            //JSONObject a = (JSONObject) parser.parse(new FileReader("./test.json"));
            //System.out.println(a.toString());
            
            //String json = "{\"name\":\"sonoo\",\"salary\":600000.0,\"age\":27}";
            //String json = "{\"name\":\"sonoo\",\"age\":0.1}";
            String json = " {\"name\"   :\"son oo\"   ,\"male\":null,\"age\":{\"age\":22,\"gh\":\"abcd\"}} ";
            RJsonParser instance = new RJsonParser(json);
            StringBuilder builder = new StringBuilder();
            instance.parse().toString(builder,-1);
            System.out.println(builder.toString());
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}