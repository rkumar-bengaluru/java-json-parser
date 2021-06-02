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

public class RJsonCommentTest {
    static Logger logger = LogManager.getLogger(RJsonCommentTest.class);
    
    //<C_SINGLE_COMMENT: "//" (~["\n","\r","\f"])* <EOL>>
    @Test  
    public void test_C_SINGLE_COMMENT() {  
        StringReader stream = new StringReader("//test comment\n//another comment\n{\"name\"://simple comment\n\"sonoo\",\"salary\":600000.0,\"age\":27}");
            RJsonCharStream jj_input_stream = new RJsonCharStream(stream, 1, 1);
            RJsonLexer l = new RJsonLexer(jj_input_stream);
            RJsonToken t = l.getNextToken();
            assertEquals(RJsonConstants.C_SINGLE_COMMENT,t.kind);
            t = l.getNextToken();
            assertEquals(RJsonConstants.C_SINGLE_COMMENT,t.kind);
            t = l.getNextToken();
            assertEquals(RJsonConstants.BRACE_OPEN,t.kind);
            t = l.getNextToken();
            assertEquals(RJsonConstants.STRING_DOUBLE_NONEMPTY,t.kind);
            t = l.getNextToken();
            assertEquals(RJsonConstants.COLON,t.kind);
            t = l.getNextToken();
            assertEquals(RJsonConstants.C_SINGLE_COMMENT,t.kind);
    } 

    @Test  
    public void test_C_MULTILINE_COMMENT() {  
            StringReader stream = new StringReader("/*test comment*///another comment\n{\"name\":/*simple comment*/\"sonoo\",\"salary\":600000.0,\"age\":27}");
            RJsonCharStream jj_input_stream = new RJsonCharStream(stream, 1, 1);
            RJsonLexer l = new RJsonLexer(jj_input_stream);
            RJsonToken t = l.getNextToken();
            assertEquals(RJsonConstants.C_MULTILINE_COMMENT,t.kind);
            t = l.getNextToken();
            assertEquals(RJsonConstants.C_SINGLE_COMMENT,t.kind);
            t = l.getNextToken();
            assertEquals(RJsonConstants.BRACE_OPEN,t.kind);
            t = l.getNextToken();
            assertEquals(RJsonConstants.STRING_DOUBLE_NONEMPTY,t.kind);
            t = l.getNextToken();
            assertEquals(RJsonConstants.COLON,t.kind);
            t = l.getNextToken();
            assertEquals(RJsonConstants.C_MULTILINE_COMMENT,t.kind);
            t = l.getNextToken();
            assertEquals(RJsonConstants.STRING_DOUBLE_NONEMPTY,t.kind);
    }

    @Test  
    public void test_SH_SINGLE_COMMENT() {  
            StringReader stream = new StringReader("/*test comment*/#another comment\n{\"name\":/*simple comment*/\"sonoo\",\"salary\":600000.0,\"age\":27}");
            RJsonCharStream jj_input_stream = new RJsonCharStream(stream, 1, 1);
            RJsonLexer l = new RJsonLexer(jj_input_stream);
            RJsonToken t = l.getNextToken();
            assertEquals(RJsonConstants.C_MULTILINE_COMMENT,t.kind);
            t = l.getNextToken();
            assertEquals(RJsonConstants.C_SINGLE_COMMENT,t.kind);
            t = l.getNextToken();
            assertEquals(RJsonConstants.BRACE_OPEN,t.kind);
            t = l.getNextToken();
            assertEquals(RJsonConstants.STRING_DOUBLE_NONEMPTY,t.kind);
            t = l.getNextToken();
            assertEquals(RJsonConstants.COLON,t.kind);
            t = l.getNextToken();
            assertEquals(RJsonConstants.C_MULTILINE_COMMENT,t.kind);
            t = l.getNextToken();
            assertEquals(RJsonConstants.STRING_DOUBLE_NONEMPTY,t.kind);
    }

     @Test  
    public void test_StringReader() {  
            try {
                StringReader stream = new StringReader("/*test comment*/#another comment\n{\"name\":/*simple comment*/\"sonoo\",\"salary\":600000.0,\"age\":27}");
                char[] dst = new char[4096];
                stream.read(dst,0,4096);
            } catch(Exception e) {
                    fail(e.getMessage());
            }
    }
    
}