package org.rk.json.parser;

import org.rk.json.parser.*;
import static org.junit.Assert.assertEquals;  
import org.junit.After;  
import org.junit.AfterClass;  
import org.junit.Before;  
import org.junit.BeforeClass;  
import org.junit.Test;  
import java.io.StringReader;

public class RJsonLexerTest {
    @BeforeClass  
    public static void setUpBeforeClass() throws Exception {  
        System.out.println("before class");  
    }  
    @Before  
    public void setUp() throws Exception {  
        System.out.println("before");  
    }  
    @Test  
    public void testFindMax(){  
        StringReader stream = new StringReader("{");
        RCharStream jj_input_stream = new RCharStream(stream, 1, 1);
        RJsonLexer l = new RJsonLexer(jj_input_stream);
        RToken t = l.getNextToken();
        assertEquals(RJsonConstants.BRACE_OPEN,t.kind);
    }  
}