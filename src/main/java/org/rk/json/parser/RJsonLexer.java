
package org.rk.json.parser;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class RJsonLexer extends NumberLexer implements RJsonConstants {
    static Logger logger = LogManager.getLogger(RJsonLexer.class);
    public RJsonLexer(RJsonCharStream input) {
        super(input);
    }

    public RJsonToken getNextToken() {
        RJsonToken matchedToken = null;
        int curPos = 0;

        EOFLoop :
        for(;;) {
            try {     
                curChar = input_stream.beginToken();
            } catch(java.io.IOException e) {        
                matchedKind = 0;
                matchedToken = fillToken();
                return matchedToken;
            }

            matchedKind = 0x7fffffff;
            matchedPos = 0;
            curPos = analyzeCurrentCharacter();
            //System.out.println( "matchedKind()" + "matchedKind" + matchedKind );
            logger.debug( "matchedKind()" + "matchedKind" + matchedKind );
            if (matchedKind != 0x7fffffff) {
                 if (matchedPos + 1 < curPos) {
                     input_stream.backup(curPos - matchedPos - 1); // possible backtracking.
                 }
                 logger.debug( "curPos=" + curPos );
                 if( (matchedKind == RJsonConstants.C_SINGLE_COMMENT) || (matchedKind == RJsonConstants.C_MULTILINE_COMMENT)) {
                     matchedToken = fillToken();
                     logger.debug("getNextToken()::Matched" + matchedToken.toString());
                     return matchedToken;
                 }
                 if ((toToken[matchedKind >> 6] & (1L << (matchedKind & 077))) != 0L) {
                     matchedToken = fillToken();
                     logger.debug("getNextToken()::Matched" + matchedToken.toString());
                     return matchedToken;
                 } else {
                     continue EOFLoop;
                 }
            }
            // error begins.....
            int error_line = input_stream.getEndLine();
            int error_column = input_stream.getEndColumn();
            String error_after = null;
            boolean EOFSeen = false;
            try { 
                input_stream.readChar(); 
                input_stream.backup(1); 
            } catch (java.io.IOException e1) { 
                EOFSeen = true;
                error_after = curPos <= 1 ? "" : input_stream.getImage();
                if (curChar == '\n' || curChar == '\r') {
                    error_line++;
                    error_column = 0;
                } else {
                    error_column++;
                }
            }

            if (!EOFSeen) {
                input_stream.backup(1);
                error_after = curPos <= 1 ? "" : input_stream.getImage();
            }

            throw new RJsonTokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, RJsonTokenMgrError.LEXICAL_ERROR);
        }
    }


    protected int analyzeCurrentCharacter() {
        
        if (curChar == ' ' || curChar == '\t' || curChar == '\n' || curChar == '\r') {
            consume_char();
            try { 
             curChar = input_stream.readChar(); 
            } catch(java.io.IOException e) {
                return 1;
            }
            return analyzeCurrentCharacter();
        }

        switch(curChar) {
            case 123: // '{'
                return stopAtPos(0, RJsonConstants.BRACE_OPEN);
            case 125: // '}'
                return stopAtPos(0, RJsonConstants.BRACE_CLOSE);
            case 34: // '"'
                return moveChar01(0x800000L);
            case 39: // '\''
                return moveChar01(0x40000L);
            case 44: // ','
                return stopAtPos(0, RJsonConstants.COMMA);
            case 58: // ':'
                return stopAtPos(0, RJsonConstants.COLON);
            case 91: // '['
                return stopAtPos(0, RJsonConstants.BRACKET_OPEN);
            case 93: // ']'
                return stopAtPos(0, RJsonConstants.BRACKET_CLOSE);
            case 70: // 'F'
            case 102: // 'f'
                return moveChar01(0x40000L);
            case 78: // 'N'
            case 110: // 'n'
                 return moveChar01(0x80000L);
            case 84: // 'T'
            case 116: // 't'
                return moveChar01(0x20000L);
            case 47: // '/'
                return moveChar01(0x60000L);
            case 35: // '#'
                return moveChar(0,'\n');
            default:
                return findNumber(0,0);
        }
    }

    protected void consume_char() {
        assert curChar != -1;
        char res = (char) curChar;
        if (curChar == '\n') {
        } else if(curChar == ' ') {
            input_stream.spaceDetected();
        }
    }

    public void ReInit(RJsonCharStream stream)
    {
    //    jjmatchedPos = jjnewStateCnt = 0;
    //    curLexState = defaultLexState;
    //    input_stream = stream;
    //ReInitRounds();
    }
}