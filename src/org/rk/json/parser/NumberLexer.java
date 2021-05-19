package org.rk.json.parser;

public class NumberLexer extends AbstractJsonLexer {
    int curLexState = 0;
    int defaultLexState = 0;
    int jjnewStateCnt;
    int jjround;

    private final int[] jjrounds = new int[1];
    private final int[] jjstateSet = new int[2];

    public NumberLexer(RCharStream input) {
        super(input);
    }

    private boolean isDigit() {
        RLogger.getLogger(NumberLexer.class).info( "\"" + curChar + "\"");
        if(curChar == 46) {
            return true;
        }

        if( curChar > 47 && curChar < 58) {
            return true;
        }

        return false;
    }

    public int findNumber(int startState, int curPos) {
        RLogger.getLogger(NumberLexer.class).info( "\"" + curChar + "\"");
        while(isDigit()) {
            ++curPos;
            try {
                curChar = input_stream.readChar();
            } catch (java.io.IOException e) {
                return curPos;
            }
        }
        RLogger.getLogger(NumberLexer.class).info("\"" + curChar + "\"");
        // only expected curChar is ',' || '}'
        if(curChar == 44 || curChar == 125) {
            --curPos;
            RLogger.getLogger(NumberLexer.class).info("\"" + curChar + "\"");
            jjmatchedKind = RJsonConstants.NUMBER_INTEGER;
            jjmatchedPos = curPos;
            input_stream.backup(1);
            return curPos;
        }

        // not a valid numbers
        return curPos;
    }
}