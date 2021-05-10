package org.rk.json.parser;

import org.rk.json.parser.*;

public class LexTest {

    public static void debug(String m , long msg) {
        System.out.println(m + "::" + msg);
    }

    public static void debug(String m , int msg) {
        System.out.println(m + "::" + msg);
    }

    public static void debug(String m , String msg) {
        System.out.println(m + "::" + msg);
    }

    public static void debug(String m , char msg) {
        System.out.println(m + "::" + msg);
    }

    public static int jjMoveStringLiteralDfa1_0 (long active) {
        debug("jjMoveStringLiteralDfa1_0", active);
        StringBuilder buffer = new StringBuilder();
        curChar = readByte();
        buffer.append(curChar);
        debug("curChar-------------", curChar);
        while( (curChar = readByte()) != '"' ) {
            //debug("jjMoveStringLiteralDfa1_0", curChar);
            buffer.append(curChar);
        }

        jjmatchedKind = 27;
        jjmatchedPos = nextCharInd + 1;
        debug("jjMoveStringLiteralDfa1_0", buffer.toString());
        RToken t = RToken.newToken(jjmatchedKind, buffer.toString());
        return jjmatchedKind;
    }
    static int nextCharInd = -1;
    static String input = "{\"name\"}";
    static char curChar;
    static int beginToken = -1;

    public static void simulateParsing() {
        RToken t = getNextToken();
        debug("simulateParsing() Token = ", t.toString());
        t = getNextToken();
        debug("simulateParsing() Token = ", t.toString());
        t = getNextToken();
        debug("simulateParsing() Token = ", t.toString());
    }

    public static char readByte() {
        debug("readByte()" , nextCharInd + 1);
        return input.charAt(++nextCharInd);
    }

    public static RToken getNextToken() {
        RToken matchedToken;
        int curPos = 0;

        EOFLoop :
        for (;;)
        {
            curChar = readByte();
            jjmatchedKind = 0x7fffffff;
            jjmatchedPos = 0;
            debug("findToken() curPos = ",curPos);
            debug("findToken() curChar = ",curChar);
            curPos = identifyToken();
            if (jjmatchedKind != 0x7fffffff) {
                matchedToken = jjFillToken();
                return matchedToken;
            }

            debug("findToken" , "Error Reached...");
        }
    }

    public static int identifyToken() {
        switch(curChar) {
            case 123: // '{'
                return jjStopAtPos(0 , 123);
            case 125: // '}'
                return jjStopAtPos(0 , 125);
            case 34: // '"'
                beginToken = nextCharInd;
                return jjMoveStringLiteralDfa1_0(0x800000L);
            default :
                return -1;
        }
    }

    public static void main(String[] args) {
        try {
           simulateParsing();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static int jjStopAtPos(int pos, int kind)
    {
        jjmatchedKind = kind;
        jjmatchedPos = pos;
        return pos + 1;
    }
    static int jjmatchedPos;
    static int jjmatchedKind;

    protected static RToken jjFillToken()
    {
        RToken t = null;
        if(jjmatchedKind == 123)
            t = RToken.newToken(jjmatchedKind, "{");
        if(jjmatchedKind == 125)
             t = RToken.newToken(jjmatchedKind, "}");
        if(jjmatchedKind == 27)
            t = RToken.newToken(jjmatchedKind, input.substring(beginToken,jjmatchedPos));
        return t;
    }
}