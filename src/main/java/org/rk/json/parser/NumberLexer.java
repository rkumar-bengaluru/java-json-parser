package org.rk.json.parser;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class NumberLexer extends AbstractJsonLexer {
    static Logger logger = LogManager.getLogger(NumberLexer.class);
    int curLexState = 0;
   
    public NumberLexer(RJsonCharStream input) {
        super(input);
    }

    private boolean isDigit() {
        logger.debug( "\"" + curChar + "\"");
        if(curChar == 46) {
            return true;
        }

        if( curChar > 47 && curChar < 58) {
            return true;
        }

        return false;
    }

    public int findNumber(int startState, int curPos) {
        logger.debug( "\"" + curChar + "\"");
        while(isDigit()) {
            ++curPos;
            try {
                curChar = input_stream.readChar();
            } catch (java.io.IOException e) {
                return curPos;
            }
        }
        logger.debug("\"" + curChar + "\"");
        // only expected curChar is ',' || '}'
        if(curChar == 44 || curChar == 125) {
            --curPos;
            logger.debug("\"" + curChar + "\"");
            matchedKind = RJsonConstants.NUMBER_INTEGER;
            matchedPos = curPos;
            input_stream.backup(1);
            return curPos;
        }

        // not a valid numbers
        return curPos;
    }
}